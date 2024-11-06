package com.example.rmaproject2.presentation.view.states

import com.example.rmaproject2.data.models.note.Note

sealed class
NoteState {
    data class Success(val notes: List<Note>): NoteState()
    data class Error(val message: String): NoteState()
}