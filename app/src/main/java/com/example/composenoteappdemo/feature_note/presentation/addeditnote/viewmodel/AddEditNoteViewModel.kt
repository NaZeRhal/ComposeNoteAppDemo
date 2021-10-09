package com.example.composenoteappdemo.feature_note.presentation.addeditnote.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenoteappdemo.feature_note.domain.model.InvalidNoteException
import com.example.composenoteappdemo.feature_note.domain.model.Note
import com.example.composenoteappdemo.feature_note.domain.usecase.NotesUseCases
import com.example.composenoteappdemo.feature_note.presentation.addeditnote.AddEditNoteEvent
import com.example.composenoteappdemo.feature_note.presentation.addeditnote.NotesTextFieldState
import com.example.composenoteappdemo.feature_note.presentation.addeditnote.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentNoteId: Int? = null

    private val _noteTitle = mutableStateOf<NotesTextFieldState>(
        NotesTextFieldState(hint = "Enter title...")
    )
    val noteTitle: State<NotesTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf<NotesTextFieldState>(
        NotesTextFieldState(hint = "Enter some content...")
    )
    val noteContent: State<NotesTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    notesUseCases.getNoteByIdUseCase(id)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.newColor
            }
            is AddEditNoteEvent.EnterContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.newContent
                )
            }
            is AddEditNoteEvent.EnterTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.newTitle,
                )
            }
            is AddEditNoteEvent.ChangeFocusContent -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeFocusTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        notesUseCases.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.AfterNoteSaved)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(e.message ?: "Unexpected error"))
                    }
                }
            }
        }
    }
}