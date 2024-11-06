package com.example.rmaproject2.data.repositories

import com.example.rmaproject2.data.models.note.Note
import com.example.rmaproject2.data.models.note.NoteEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface NotesRepository {
    fun getAll(): Observable<List<Note>>
    fun getAllBySearch(search: String, bool: Int): Observable<List<Note>>
    fun getAllNonArchived(): Observable<List<Note>>
    fun deleteById(id: Long): Completable
    fun changeArchived(id: Long, arch:Boolean): Completable
    fun insert(noteEntity: NoteEntity): Completable
    fun updateNote(id: Long, title: String, content: String): Completable
}