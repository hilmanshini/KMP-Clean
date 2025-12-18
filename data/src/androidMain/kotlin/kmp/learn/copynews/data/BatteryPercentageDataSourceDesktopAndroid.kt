package kmp.learn.copynews.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

class BatteryPercentageDataSourceDesktopAndroid(
    val context: Context
) : BatteryPercentageDataSource {
    override fun observeBatteryLevel(): Flow<BatteryStatus> = callbackFlow {
        println()
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                val present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, true)

                if (!present) {
                    trySend(BatteryStatus.NoBattery)
                    return
                }

                if (level < 0 || scale <= 0) {
                    trySend(BatteryStatus.Unknown)
                    return
                }

                val percentage = (level * 100) / scale

                val batteryStatus = when (status) {
                    BatteryManager.BATTERY_STATUS_CHARGING ->
                        BatteryStatus.Charging(percentage)

                    BatteryManager.BATTERY_STATUS_DISCHARGING,
                    BatteryManager.BATTERY_STATUS_NOT_CHARGING ->
                        BatteryStatus.Discharging(percentage)

                    BatteryManager.BATTERY_STATUS_FULL ->
                        BatteryStatus.FullyCharged

                    else ->
                        BatteryStatus.Unknown
                }

                trySend(batteryStatus)
            }
        }


        context.registerReceiver(receiver, intentFilter)

        awaitClose {
            context.unregisterReceiver(receiver)
        }
    }.distinctUntilChanged()
}

//class AndroidBatteryPercentageDataSource(
//    private val context: Context
//) : BatteryPercentageDataSource {
//
//
//}
