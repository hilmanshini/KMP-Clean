package kmp.learn.copynews.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidBatteryPercentageDataSourceTest {

    private lateinit var context: Context
    private lateinit var dataSource: BatteryPercentageDataSource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        dataSource = BatteryPercentageDataSourceDesktopAndroid(context)
    }

    @Test
    fun observeBatteryLevel_emitsValidBatteryStatus() {
        runBlocking {
            val status = dataSource.observeBatteryLevel().first()
            assertTrue(status  is BatteryStatus.Charging)
        }
//        val status = dataSource.observeBatteryLevel().first()
//
//        when (status) {
//            is BatteryStatus.Charging -> {
//                assertTrue(status.percentage in 0..100)
//            }
//
//            is BatteryStatus.Discharging -> {
//                assertTrue(status.percentage in 0..100)
//            }
//
//            BatteryStatus.FullyCharged -> {
//                // valid state, nothing to assert
//            }
//
//            BatteryStatus.NoBattery -> {
//                // valid for emulators / desktops
//            }
//
//            BatteryStatus.Unknown -> {
//                // acceptable fallback
//            }
//        }
    }
}
