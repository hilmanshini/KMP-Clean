package kmp.learn.copynews.data.test.battery

import kmp.learn.copynews.data.BatteryPercentageDataSourceDesktopWindows
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds


class BatteryTest {

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun `flow emits battery status`() = runTest {
        val fakeDataSource = BatteryPercentageDataSourceDesktopWindows()
        runBlocking {
            GlobalScope.launch {
                fakeDataSource.observeBatteryLevel().collect {
                    val status = it
                    println(status)
                }
            }
            delay(5.seconds)

        }

    }
}