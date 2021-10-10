package com.example.composenoteappdemo.feature_note.domain.usecase

import androidx.compose.ui.graphics.toArgb
import com.example.composenoteappdemo.feature_note.data.repository.FakeNoteRepository
import com.example.composenoteappdemo.feature_note.domain.model.Note
import com.example.composenoteappdemo.feature_note.domain.util.NoteOrder
import com.example.composenoteappdemo.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {

    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNotesUseCase = GetNotesUseCase(fakeRepository)
        populateRepository()

    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Title(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title < notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Title(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title > notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp < notes[i + 1].timestamp)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp > notes[i + 1].timestamp)
        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Color(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color < notes[i + 1].color)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Color(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color > notes[i + 1].color)
        }
    }

    private fun populateRepository() {
        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    color = index,
                    timestamp = index.toLong(),
                    id = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeRepository.addNote(it) }
        }
    }

}