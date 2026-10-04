package org.gpfister.aosp.kiosk.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/*
 * Corsair Xeneon Edge 14.5" (2560x720 px, ~183 PPI), the target display. It can be mounted in either
 * orientation, so screens must fit both viewports without scrolling.
 */
const val XENEON_EDGE_LANDSCAPE = "spec:width=2560px,height=720px,dpi=183,orientation=landscape"
const val XENEON_EDGE_PORTRAIT = "spec:width=720px,height=2560px,dpi=183,orientation=portrait"

private const val NIGHT = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL

/** Previews a screen on the Xeneon Edge in landscape and portrait, in both light and dark mode. */
@Preview(name = "Xeneon Edge landscape - Light", device = XENEON_EDGE_LANDSCAPE, showSystemUi = true)
@Preview(name = "Xeneon Edge landscape - Dark", device = XENEON_EDGE_LANDSCAPE, showSystemUi = true, uiMode = NIGHT)
@Preview(name = "Xeneon Edge portrait - Light", device = XENEON_EDGE_PORTRAIT, showSystemUi = true)
@Preview(name = "Xeneon Edge portrait - Dark", device = XENEON_EDGE_PORTRAIT, showSystemUi = true, uiMode = NIGHT)
annotation class XeneonEdgePreviews
