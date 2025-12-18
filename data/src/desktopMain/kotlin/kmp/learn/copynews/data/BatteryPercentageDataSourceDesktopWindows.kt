package kmp.learn.copynews.data

import com.sun.jna.Structure
import com.sun.jna.win32.StdCallLibrary
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.seconds

class BatteryPercentageDataSourceDesktopWindows : BatteryPercentageDataSource {
    override fun observeBatteryLevel(): Flow<BatteryStatus> = flow {
        val kernel32 = Kernel32.INSTANCE ?: return@flow

        while (currentCoroutineContext().isActive) {

            val status = SYSTEM_POWER_STATUS()

            val result = kernel32.GetSystemPowerStatus(status)
            if (result != 0) {
                emit(status.toBatteryStatus())
            }

            delay(1.seconds) // poll every 5 seconds
        }
    }
}

fun SYSTEM_POWER_STATUS.toBatteryStatus(): BatteryStatus {

    if (BatteryFlag.toInt() and 128 != 0) {
        return BatteryStatus.NoBattery
    }

    val percent =
        if (BatteryLifePercent == 255.toByte()) -1
        else BatteryLifePercent.toInt()

    if (percent == 100 && ACLineStatus.toInt() == 1) {
        return BatteryStatus.FullyCharged
    }

    if (BatteryFlag.toInt() and 8 != 0) {
        return BatteryStatus.Charging(percent)
    }

    if (percent >= 0) {
        return BatteryStatus.Discharging(percent)
    }

    return BatteryStatus.Unknown
}

class SYSTEM_POWER_STATUS : Structure() {
    @JvmField var ACLineStatus: Byte = 0
    @JvmField var BatteryFlag: Byte = 0
    @JvmField var BatteryLifePercent: Byte = 0
    @JvmField var Reserved1: Byte = 0
    @JvmField var BatteryLifeTime: Int = 0
    @JvmField var BatteryFullLifeTime: Int = 0

    override fun getFieldOrder(): List<String> = listOf(
        "ACLineStatus",
        "BatteryFlag",
        "BatteryLifePercent",
        "Reserved1",
        "BatteryLifeTime",
        "BatteryFullLifeTime"
    )
    val aCLineStatusString: String
        /**
         * The AC power status
         */
        get() {
            when (ACLineStatus) {
                (0).toByte() -> return "Offline"
                (1).toByte() -> return "Online"
                else -> return "Unknown"
            }
        }

    val batteryFlagString: String
        /**
         * The battery charge status
         */
        get() {
            when (BatteryFlag) {
                1.toByte() -> return "High, more than 66 percent"
                2.toByte() -> return "Low, less than 33 percent"
                (4).toByte() -> return "Critical, less than five percent"
                (8).toByte() -> return "Charging"
                (128.toByte()) -> return "No system battery"
                else -> return "Unknown"
            }
        }

    /**
     * The percentage of full battery charge remaining
     */
    fun getBatteryLifePercent(): String {
        return if (BatteryLifePercent == 255.toByte()) "Unknown" else BatteryLifePercent.toString() + "%"
    }

    /**
     * The number of seconds of battery life remaining
     */
    fun getBatteryLifeTime(): String {
        return if (BatteryLifeTime == -1) "Unknown" else BatteryLifeTime.toString() + " seconds"
    }

    /**
     * The number of seconds of battery life when at full charge
     */
    fun getBatteryFullLifeTime(): String {
        return if (BatteryFullLifeTime == -1) "Unknown" else BatteryFullLifeTime.toString() + " seconds"
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("ACLineStatus: " + this.aCLineStatusString + "\n")
        sb.append("Battery Flag: " + this.batteryFlagString + "\n")
        sb.append("Battery Life: " + getBatteryLifePercent() + "\n")
        sb.append("Battery Left: " + getBatteryLifeTime() + "\n")
        sb.append("Battery Full: " + getBatteryFullLifeTime() + "\n")
        return sb.toString()
    }
}


interface Kernel32 : StdCallLibrary {
    /**
     * @see https://learn.microsoft.com/en-us/windows/win32/api/winbase/ns-winbase-system_power_status
     */

    /**
     * Fill the structure.
     */
    fun GetSystemPowerStatus(result: SYSTEM_POWER_STATUS?): Int

    companion object {
        val INSTANCE: Kernel32? = com.sun.jna.Native.load<Kernel32?>("Kernel32", Kernel32::class.java)
    }
}
