package com.example.rmaproject2.data.datasource.local

import androidx.room.*
import com.example.rmaproject2.data.models.course.CourseEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class CourseDao {

//    @Insert( onConflict = OnConflictStrategy.REPLACE )
//    abstract fun insert(entity: CourseEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<CourseEntity>): Completable

    @Query("SELECT * FROM courses")
    abstract fun getAll(): Observable<List<CourseEntity>>

    @Query("DELETE FROM courses")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<CourseEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
//where professor LIKE :subjectOrProf OR subject LIKE :subjectOrProf     AND day LIKE :day AND groups LIKE :group
    @Query("SELECT * FROM courses WHERE day LIKE :day AND groups LIKE :group AND (professor LIKE :subjectOrProf OR subject LIKE :subjectOrProf) ")//todo napravi query
    abstract fun getByFilter(subjectOrProf: String, group: String, day: String): Observable<List<CourseEntity>>

}