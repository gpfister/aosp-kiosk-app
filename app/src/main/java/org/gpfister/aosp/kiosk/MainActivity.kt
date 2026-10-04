package org.gpfister.aosp.kiosk

import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.gpfister.aosp.kiosk.ui.preview.XeneonEdgePreviews
import org.gpfister.aosp.kiosk.ui.theme.GPKioskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemBars()
        setContent {
            GPKioskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HelloWorld(
                        // Debug builds only: release builds must never expose a way to crash the kiosk.
                        onForceCrash = if (BuildConfig.DEBUG) ({ throw ForcedCrashException() }) else null,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    /** Kiosk: give the content the whole screen; bars can still be revealed with a swipe. */
    private fun hideSystemBars() {
        window.insetsController?.apply {
            hide(WindowInsets.Type.systemBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

/** Thrown on purpose by the debug-only "Force app crash" button, e.g. to test crash reporting and kiosk recovery. */
class ForcedCrashException : RuntimeException("App crash forced by the user")

/** @param onForceCrash handler for the "Force app crash" button; the button is hidden when `null`. */
@Composable
fun HelloWorld(onForceCrash: (() -> Unit)?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            // The screen is very narrow in portrait and very short in landscape: shrink the text to fit the
            // available space instead of wrapping or overflowing.
            Text(
                text = stringResource(R.string.hello_world),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, maxFontSize = 96.sp),
            )
        }
        if (onForceCrash != null) {
            Button(
                onClick = onForceCrash,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                ),
            ) {
                Text(text = stringResource(R.string.force_app_crash))
            }
        }
    }
}

@XeneonEdgePreviews
@Composable
private fun HelloWorldPreview() {
    GPKioskTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            HelloWorld(onForceCrash = {}, modifier = Modifier.padding(innerPadding))
        }
    }
}
