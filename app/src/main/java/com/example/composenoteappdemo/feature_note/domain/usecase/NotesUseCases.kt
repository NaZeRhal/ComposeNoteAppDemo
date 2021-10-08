package com.example.composenoteappdemo.feature_note.domain.usecase

data class NotesUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase
)
