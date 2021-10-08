package com.example.composenoteappdemo.feature_note.domain.usecase

import com.example.composenoteappdemo.feature_note.domain.model.Note
import com.example.composenoteappdemo.feature_note.domain.repository.NoteRepository
import com.example.composenoteappdemo.feature_note.domain.util.NoteOrder
import com.example.composenoteappdemo.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(private val repository: NoteRepository) {

    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(orderType = OrderType.Descending)): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder) {
                is NoteOrder.Title -> sortByTitle(notes, noteOrder.orderType)
                is NoteOrder.Date -> sortByData(notes, noteOrder.orderType)
                is NoteOrder.Color -> sortByColor(notes, noteOrder.orderType)
            }
        }
    }

    private fun sortByTitle(notes: List<Note>, orderType: OrderType): List<Note> {
        return when (orderType) {
            is OrderType.Ascending -> notes.sortedBy { it.title.lowercase() }
            is OrderType.Descending -> notes.sortedByDescending { it.title.lowercase() }
        }
    }

    private fun sortByData(notes: List<Note>, orderType: OrderType): List<Note> {
        return when (orderType) {
            is OrderType.Ascending -> notes.sortedBy { it.timestamp }
            is OrderType.Descending -> notes.sortedByDescending { it.timestamp }
        }
    }

    private fun sortByColor(notes: List<Note>, orderType: OrderType): List<Note> {
        return when (orderType) {
            is OrderType.Ascending -> notes.sortedBy { it.color }
            is OrderType.Descending -> notes.sortedByDescending { it.color }
        }
    }
}

