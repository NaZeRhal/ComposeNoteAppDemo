package com.example.composenoteappdemo.feature_note.presentation.notes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composenoteappdemo.feature_note.presentation.notes.viewmodel.NotesViewModel

@Composable
fun NotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {

}