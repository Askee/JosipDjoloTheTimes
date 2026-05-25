package com.josipdjolo.thetimes

import androidx.compose.runtime.Composable
import com.airbnb.android.showkase.models.ShowkaseBrowserComponent

/**
 * If we want to record a preview as an animation, we just need to make sure the method name
 * contains the [ANIMATED_PREVIEW_DESIGNATION], or that we've set a tag or extraMetadata with
 * such a value.
 * If we however want to record a preview with an offset, we just need to make sure the method name
 * contains the [OFFSET_PREVIEW_DESIGNATION], or that we've set a tag or extraMetadata with
 * such a value.
 */
internal data class ComponentPreview(
    private val showkaseBrowserComponent: ShowkaseBrowserComponent
) {
    val content: @Composable () -> Unit = showkaseBrowserComponent.component

    val previewType = toPreviewType()
    override fun toString(): String {
        /*
         * There are sometimes indices added in the component name after "mode" which is the end
         * of all our preview names created via @JosipPreviews.
         * They can cause issues when adding/removing previews, so here we cleared them up.
         */
        val clearedComponentName =
            showkaseBrowserComponent.componentName.substringBeforeLast("mode") + " mode"

        /*
         * There is no easy way to get the data on which Preview Parameter the component is using.
         * This is the best way to do that for now.
         */
        val previewParameterIndex =
            showkaseBrowserComponent.componentKey.substringAfterLast("_").toIntOrNull()

        /*
         * Example returns:
         * AddTravellerContentPreview - Phone - light mode
         * AddTravellerContentPreview - Phone - dark mode
         * AddTravellerContentPreview - Tablet - dark mode
         * PdpItineraryDayTitlePreview - Phone - light mode : Preview Parameter 0
         * PdpItineraryDayTitlePreview - Phone - light mode : Preview Parameter 1
         * PdpItineraryDayTitlePreview - Phone - dark mode : Preview Parameter 0
         * PdpItineraryDayTitlePreview - Phone - dark mode : Preview Parameter 1
         * PdpItineraryDayTitlePreview - Tablet - dark mode : Preview Parameter 0
         * PdpItineraryDayTitlePreview - Tablet - dark mode : Preview Parameter 1
         */
        return listOfNotNull(
            clearedComponentName,
            previewParameterIndex
        ).joinToString(" : Preview Parameter ")
    }

    fun isAnimated(): Boolean =
        showkaseBrowserComponent.componentName.contains(
            ANIMATED_PREVIEW_MARK,
            ignoreCase = true
        ) || showkaseBrowserComponent.tags.any {
            it.equals(
                ANIMATED_PREVIEW_MARK,
                ignoreCase = true
            )
        } ||
                showkaseBrowserComponent.extraMetadata.any {
                    it.equals(
                        ANIMATED_PREVIEW_MARK,
                        ignoreCase = true
                    )
                }

    fun hasOffset(): Boolean =
        showkaseBrowserComponent.componentName.contains(
            OFFSET_PREVIEW_MARK,
            ignoreCase = true
        ) || showkaseBrowserComponent.tags.any {
            it.equals(
                OFFSET_PREVIEW_MARK,
                ignoreCase = true
            )
        } ||
                showkaseBrowserComponent.extraMetadata.any {
                    it.equals(
                        OFFSET_PREVIEW_MARK,
                        ignoreCase = true
                    )
                }

    private fun ComponentPreview.toPreviewType(): PreviewType {
        with(showkaseBrowserComponent.componentName) {
            return when {
                contains(PHONE_LIGHT_MODE_PREVIEW_NAME, true) -> {
                    PreviewType.PHONE_LIGHT
                }

                contains(TABLET_DARK_MODE_PREVIEW_NAME, true) -> {
                    PreviewType.TABLET_DARK
                }

                else -> PreviewType.UNKNOWN
            }
        }
    }
}

private const val ANIMATED_PREVIEW_MARK = "AnimatedPreview"
private const val OFFSET_PREVIEW_MARK = "OffsetPreview"
private const val PHONE_LIGHT_MODE_PREVIEW_NAME = "Phone - Light"
private const val TABLET_DARK_MODE_PREVIEW_NAME = "Tablet - Dark"