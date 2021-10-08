package com.example.composenoteappdemo.feature_note.presentation.addeditnote.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.example.composenoteappdemo.feature_note.domain.model.Note
import com.example.composenoteappdemo.feature_note.domain.usecase.NotesUseCases
import com.example.composenoteappdemo.feature_note.presentation.addeditnote.NotesTextFieldState
import com.example.composenoteappdemo.feature_note.presentation.addeditnote.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {
    private val _noteTitle = mutableStateOf<NotesTextFieldState>(
        NotesTextFieldState(
            hint = "Enter title..."
        )
    )
    val noteTitle: State<NotesTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf<NotesTextFieldState>(
        NotesTextFieldState(
            hint = "Enter some content..."
        )
    )
    val noteContent: State<NotesTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
}