package com.josipdjolo.thetimes.core.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone - Light",
    device = Devices.PIXEL_9,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Tablet - Dark",
    device = Devices.PIXEL_TABLET,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class JosipPreview