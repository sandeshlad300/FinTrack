package com.sandesh.fintrack


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun dashboard_shows_user_name() {
        composeTestRule
            .onNodeWithText("Hello")
            .assertExists()
    }

    @Test
    fun dashboard_greeting_is_visible() {
        composeTestRule
            .onNodeWithTag("dashboard_greeting")
            .assertIsDisplayed()
    }

    @Test
    fun clicking_add_transaction_opens_screen() {
        composeTestRule
            .onNodeWithTag("add_transaction_button")
            .performClick()

        composeTestRule
            .onNodeWithText("Add Transaction")
            .assertExists()
    }


}
