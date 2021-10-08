package com.example.composenoteappdemo.feature_note.presentation.notes

import com.example.composenoteappdemo.feature_note.domain.model.Note
import com.example.composenoteappdemo.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class ChangeOrder(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
