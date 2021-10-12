package com.example.composenoteappdemo.feature_note.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.composenoteappdemo.core.util.TestTags
import com.example.composenoteappdemo.di.AppModule
import com.example.composenoteappdemo.feature_note.presentation.addeditnote.AddEditNoteScreen
import com.example.composenoteappdemo.feature_note.presentation.notes.NotesScreen
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
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            ComposeNoteAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(Screen.NotesScreen.route) { NotesScreen(navController = navController) }
                    composable(
                        Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        with(composeRule) {
            //list screen
            addEditClick()

            //add note screen
            titleTextFiled().performTextInput(TEST_TITLE)
            contentTextFiled().performTextInput(TEST_CONTENT)
            saveClick()

            //list screen
            onNodeWithText(TEST_TITLE).assertIsDisplayed()
            onNodeWithText(TEST_TITLE).performClick()

            //add note screen
            titleTextFiled().assertTextEquals(TEST_TITLE)
            contentTextFiled().assertTextEquals(TEST_CONTENT)
            titleTextFiled().performTextInput(TEST_TITLE_NEW)
            saveClick()

            //list screen
            onNodeWithText(TEST_TITLE_UPDATED).assertIsDisplayed()
        }
    }

    private fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.titleTextFiled() =
        onNodeWithTag(TestTags.TITLE_TEXT_FIELD)

    private fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.contentTextFiled() =
        onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)

    private fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.addEditClick() {
        onNodeWithContentDescription("Add note").performClick()
    }

    private fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.saveClick() {
        onNodeWithContentDescription("Save note").performClick()
    }

    companion object {
        private const val TEST_TITLE = "Test title"
        private const val TEST_TITLE_NEW = " new"
        private const val TEST_TITLE_UPDATED = "Test title new"
        private const val TEST_CONTENT = "Test content"
        private const val TEST_CONTENT_NEW = "Test content new"
    }
}