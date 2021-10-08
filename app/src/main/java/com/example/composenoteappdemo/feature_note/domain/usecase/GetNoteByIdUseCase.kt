package com.example.composenoteappdemo.feature_note.domain.usecase

import com.example.composenoteappdemo.feature_note.domain.model.Note
import com.example.composenoteappdemo.feature_note.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}