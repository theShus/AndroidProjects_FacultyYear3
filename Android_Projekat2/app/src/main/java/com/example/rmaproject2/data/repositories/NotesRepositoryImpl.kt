package com.example.rmaproject2.data.repositories

import com.example.rmaproject2.data.datasource.local.NoteDao
import com.example.rmaproject2.data.models.note.Note
import com.example.rmaproject2.data.models.note.NoteEntity
import io.reactivex.Completable
import io.reactivex.Observable

class NotesRepositoryImpl(private val localDataSource: NoteDao) : NotesRepository {

    override fun getAll(): Observable<List<Note>> {
        return localDataSource
            .getAll()
            .map { it ->
                it.map {
                    Note(
                        it.id,
                        it.title,
                        it.content,
                        it.creationDate,
                        it.archived
                    )
                }
            }
    }

    override fun getAllBySearch(search: String, bool: Int): Observable<List<Note>> {

        val querySearch = "%$search%"


        return localDataSource
            .getAllBySearch(querySearch, bool)
            .map { it ->
                it.map {
                    Note(
                        it.id,
                        it.title,
                        it.content,
                        it.creationDate,
                        it.archived
                    )
                }
            }
    }

    override fun getAllNonArchived(): Observable<List<Note>> {
        return localDataSource
            .getAllNonArchived()
            .map { it ->
                it.map {
                    Note(
                        it.id,
                        it.title,
                        it.content,
                        it.creationDate,
                        it.archived
                    )
                }
            }
    }

    override fun deleteById(id: Long): Completable {
         return localDataSource.deleteById(id)
    }

    override fun changeArchived(id: Long, arch: Boolean): Completable {
        return localDataSource.changeArchived(id, arch)
    }

    override fun insert(noteEntity: NoteEntity): Completable {
        return localDataSource.insert(noteEntity)
    }

    override fun updateNote(id: Long, title: String, content: String): Completable {
         return localDataSource.update(id, title, content)
    }

}