package org.gpfister.aosp.kiosk

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun showsHelloWorld() {
        val text = composeRule.activity.getString(R.string.hello_world)
        composeRule.onNodeWithText(text).assertIsDisplayed()
    }

    // Instrumented tests run against the debug build, where the button is shown.
    @Test
    fun showsForceCrashButton() {
        val label = composeRule.activity.getString(R.string.force_app_crash)
        composeRule.onNodeWithText(label).assertIsDisplayed().assertHasClickAction()
    }

    @Test
    fun screenFitsWithoutScrolling() {
        composeRule.onAllNodes(hasScrollAction()).assertCountEquals(0)
    }
}
