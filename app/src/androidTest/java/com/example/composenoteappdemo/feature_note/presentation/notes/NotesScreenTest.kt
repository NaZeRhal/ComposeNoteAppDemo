package com.example.composenoteappdemo.feature_note.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenoteappdemo.di.AppModule
import com.example.composenoteappdemo.feature_note.presentation.MainActivity
import com.example.composenoteappdemo.feature_note.presentation.util.Screen
import com.example.composenoteappdemo.ui.theme.ComposeNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
@ExperimentalAnimationApi
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            ComposeNoteAppTheme {
                NavHost(navController = navController, startDestination = Screen.NotesScreen.route) {
                    composable(Screen.NotesScreen.route) { NotesScreen(navController = navController) }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag()
    }
}
