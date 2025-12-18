package kmp.learn.copynews.data

import kotlinx.coroutines.flow.Flow

interface BatteryPercentageDataSource {
    fun observeBatteryLevel(): Flow<BatteryStatus>
}

sealed class BatteryStatus {

    data class Charging(
        val percentage: Int
    ) : BatteryStatus()

    data class Discharging(
        val percentage: Int
    ) : BatteryStatus()

    object FullyCharged : BatteryStatus()

    object NoBattery : BatteryStatus()

    object Unknown : BatteryStatus()
}
