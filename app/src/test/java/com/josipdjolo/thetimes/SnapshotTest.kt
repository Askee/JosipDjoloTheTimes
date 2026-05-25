package com.josipdjolo.thetimes

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Density
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.airbnb.android.showkase.models.Showkase
import com.android.ide.common.rendering.api.SessionParams
import com.android.resources.NightMode
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.google.testing.junit.testparameterinjector.TestParameterValuesProvider
import com.josipdjolo.thetimes.core.ui.theme.JosipTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale
import java.util.logging.LogManager

@RunWith(TestParameterInjector::class)
internal class SnapshotTest {
    object PreviewProvider : TestParameterValuesProvider() {
        override fun provideValues(context: Context): List<ComponentPreview> {
            return Showkase.getMetadata().componentList.map(::ComponentPreview)
                .filter { componentPreview -> componentPreview.previewType != PreviewType.UNKNOWN }
                .sortedBy { it.toString() }
        }
    }

    @get:Rule
    val paparazzi = Paparazzi(
        maxPercentDifference = 0.0,
        deviceConfig = DeviceConfig.PIXEL_6,
        renderingMode = SessionParams.RenderingMode.SHRINK
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        LogManager.getLogManager().reset()
        Locale.setDefault(Locale.US)
        //TODO Add if using Lottie: LottieTask.EXECUTOR = Executor(Runnable::run)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun snapshotTest(
        @TestParameter(valuesProvider = PreviewProvider::class) componentPreview: ComponentPreview,
        @TestParameter(value = ["1.0", "1.5"]) fontScale: Float
    ) {
        paparazzi.unsafeUpdateConfig(deviceConfig = componentPreview.toDeviceConfig() ?: return)

        val view = ComposeView(paparazzi.context).apply {
            setContent {
                CompositionLocalProvider(
                    LocalInspectionMode provides true,
                    LocalDensity provides Density(
                        density = LocalDensity.current.density,
                        fontScale = fontScale
                    )
                ) {
                    JosipTheme {
                        componentPreview.content()
                    }
                }
            }
        }

        if (componentPreview.isAnimated()) {
            paparazzi.gif(view = view, start = 0, end = 300, fps = 30)
        } else if (componentPreview.hasOffset()) {
            paparazzi.snapshot(view = view, offsetMillis = 50)
        } else {
            paparazzi.snapshot(view = view)
        }
    }

    private fun ComponentPreview.toDeviceConfig(): DeviceConfig? {
        return when (previewType) {
            PreviewType.PHONE_LIGHT -> {
                DeviceConfig.PIXEL_9.copy(nightMode = NightMode.NOTNIGHT)
            }

            PreviewType.TABLET_DARK -> {
                DeviceConfig.NEXUS_10.copy(nightMode = NightMode.NIGHT)
            }

            else -> {
                null
            }
        }
    }
}

enum class PreviewType {
    PHONE_LIGHT, TABLET_DARK, UNKNOWN
}