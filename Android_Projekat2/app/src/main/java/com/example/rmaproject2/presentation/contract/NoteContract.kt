package com.example.rmaproject2.presentation.contract

import androidx.lifecycle.LiveData
import com.example.rmaproject2.data.models.note.NoteEntity
import com.example.rmaproject2.data.models.note.StatisticsHolder
import com.example.rmaproject2.presentation.view.states.NoteState

interface NoteContract {

    interface ViewModel {
        val noteState: LiveData<NoteState>
        val statisticsHolder: StatisticsHolder
        //        val addDone: LiveData<AddMovieState> todo mozda
        fun getAll()
        fun getAllBySearch(search: String, bool: Int)
        fun getAllNonArchived()
        fun deleteById(id: Long)
        fun changeArchived(id: Long, arch: Boolean)
        fun insert(noteEntity: NoteEntity)
        fun updateNote(id: Long, title: String, content: String)
    }
}