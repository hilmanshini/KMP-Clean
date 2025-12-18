package kmp.learn.copynews.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.coroutineContext

class BatteryPercentageDataSourceDesktopLInux : BatteryPercentageDataSource {
    private val reader by lazy {
        LinuxBatteryReader()
    }

    override fun observeBatteryLevel(): Flow<BatteryStatus> = flow{
        while(currentCoroutineContext().isActive){
            emit(reader.read())
        }
    }
}

private class LinuxBatteryReader {

    private val basePath = "/sys/class/power_supply"

    fun read(): BatteryStatus {
        val batteryDir = findBatteryDir() ?: return BatteryStatus.NoBattery

        val present = readInt(batteryDir, "present") ?: return BatteryStatus.Unknown
        if (present == 0) return BatteryStatus.NoBattery

        val capacity = readInt(batteryDir, "capacity") ?: return BatteryStatus.Unknown
        val status = readString(batteryDir, "status") ?: return BatteryStatus.Unknown

        return when (status) {
            "Charging" ->
                BatteryStatus.Charging(capacity)

            "Discharging" ->
                BatteryStatus.Discharging(capacity)

            "Full" ->
                BatteryStatus.FullyCharged

            else ->
                BatteryStatus.Unknown
        }
    }

    // --- helpers ---

    private fun findBatteryDir(): File? =
        File(basePath)
            .listFiles()
            ?.firstOrNull { it.name.startsWith("BAT") && File(it, "capacity").exists() }

    private fun readInt(dir: File, file: String): Int? =
        runCatching {
            File(dir, file).readText().trim().toInt()
        }.getOrNull()

    private fun readString(dir: File, file: String): String? =
        runCatching {
            File(dir, file).readText().trim()
        }.getOrNull()
}
