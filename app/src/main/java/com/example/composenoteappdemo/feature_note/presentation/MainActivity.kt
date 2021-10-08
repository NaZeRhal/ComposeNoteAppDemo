package com.example.composenoteappdemo.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.composenoteappdemo.feature_note.domain.usecase.GetNotesUseCase
import com.example.composenoteappdemo.ui.theme.ComposeNoteAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNoteAppTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}