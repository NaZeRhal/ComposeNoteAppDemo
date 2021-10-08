package com.example.composenoteappdemo.feature_note.data.repository

import com.example.composenoteappdemo.feature_note.data.source.NoteDao
import com.example.composenoteappdemo.feature_note.domain.model.Note
import com.example.composenoteappdemo.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNoteById(id: String): Note {
        return noteDao.getNoteById(id)
    }

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

}