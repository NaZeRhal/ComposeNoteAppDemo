package com.example.composenoteappdemo.feature_note.presentation.addeditnote

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
    object AfterNoteSaved : UiEvent()
}
