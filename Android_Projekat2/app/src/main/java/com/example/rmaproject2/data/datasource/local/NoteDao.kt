package com.example.rmaproject2.data.datasource.local

import androidx.room.*
import com.example.rmaproject2.data.models.note.NoteEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class NoteDao {
    /*
    getAll i getAllArchived su okrenuti jer originalno se prvo prikazivali svi a onda se sklanjali
    a sada je da se prikazu sklonjeni arhivirani, a na klik dugmeta se prikazu, mrzelo me da menjam imena metoda tako da je samo sql promenjen
     */

    @Query("SELECT * FROM notes ORDER BY creationDate")// ovo je samo ne arhivirani
    abstract fun getAll(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE (title LIKE :search OR content LIKE :search) AND archived == :bool")
    abstract fun getAllBySearch(search: String, bool: Int): Observable<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE archived == '0' ")// ovo je get all
    abstract fun getAllNonArchived(): Observable<List<NoteEntity>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )//todo mozda jos
    abstract fun insert(noteEntity: NoteEntity): Completable

    @Query("UPDATE notes SET title = :title, content = :content  WHERE id == :id ")
    abstract fun update(id: Long, title: String, content: String): Completable

    @Query("UPDATE notes SET archived = :arch WHERE id == :id")
    abstract fun changeArchived(id: Long, arch: Boolean): Completable

    @Query("DELETE FROM notes WHERE id == :id")
    abstract fun deleteById(id: Long): Completable

    @Query("DELETE FROM notes")
    abstract fun deleteAll(): Completable



}