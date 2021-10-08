package com.example.composenoteappdemo.feature_note.presentation.addeditnote

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnterTitle(val newTitle: String): AddEditNoteEvent()
    data class FocusOnTitle(val focusState: FocusState): AddEditNoteEvent()
    data class EnterContent(val newContent: String): AddEditNoteEvent()
    data class FocusOnContent(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColor(val newColor: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
