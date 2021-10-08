package com.example.composenoteappdemo.feature_note.presentation.notes.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenoteappdemo.feature_note.domain.model.InvalidNoteException
import com.example.composenoteappdemo.feature_note.domain.model.Note
import com.example.composenoteappdemo.feature_note.domain.usecase.NotesUseCases
import com.example.composenoteappdemo.feature_note.domain.util.NoteOrder
import com.example.composenoteappdemo.feature_note.domain.util.OrderType
import com.example.composenoteappdemo.feature_note.presentation.notes.NotesEvent
import com.example.composenoteappdemo.feature_note.presentation.notes.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {

    private val _state = mutableStateOf<NotesState>(NotesState())
    val state: State<NotesState> = _state

    private var lastDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.ChangeOrder -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }

                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    lastDeletedNote = event.note
                    notesUseCases.deleteNoteUseCase(event.note)
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    try {
                        notesUseCases.addNoteUseCase(lastDeletedNote ?: return@launch)
                        lastDeletedNote = null
                    } catch (e: InvalidNoteException) {

                    }
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotesUseCase(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }
}