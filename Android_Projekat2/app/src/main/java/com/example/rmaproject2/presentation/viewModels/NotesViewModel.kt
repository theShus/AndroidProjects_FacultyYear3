package com.example.rmaproject2.presentation.viewModels

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rmaproject2.data.models.note.Note
import com.example.rmaproject2.data.models.note.NoteEntity
import com.example.rmaproject2.data.models.note.StatisticsHolder
import com.example.rmaproject2.data.repositories.NotesRepository
import com.example.rmaproject2.presentation.contract.NoteContract
import com.example.rmaproject2.presentation.view.states.NoteState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

@SuppressLint("CheckResult")
class NotesViewModel(
    private val notesRepository: NotesRepository,
    override val statisticsHolder: StatisticsHolder
) : ViewModel(), NoteContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val noteState: MutableLiveData<NoteState> = MutableLiveData()

    init {//uzimamo saljemo listu svih notova u statisuku
        var result: List<Note>?
        notesRepository
            .getAll()
            .subscribe(
                { it ->
                    result = NoteState.Success(it).notes
                    result!!.forEach { println("PRINT $it") }
                    statisticsHolder.fillWithExistingData(result!!.reversed())
                },
                {
                    noteState.value = NoteState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                },
                {
                    Timber.e("ON COMPLETE")
                }
            )
    }

    override fun getAll() {
        val subscription = notesRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    noteState.value = NoteState.Success(it)
                },
                {
                    noteState.value = NoteState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                },
                {
                    Timber.e("ON COMPLETE")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllBySearch(search: String, bool: Int) {
        val subscription = notesRepository
            .getAllBySearch(search, bool)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    noteState.value = NoteState.Success(it)
                },
                {
                    noteState.value = NoteState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                },
                {
                    Timber.e("ON COMPLETE")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllNonArchived() {
        val subscription = notesRepository
            .getAllNonArchived()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    noteState.value = NoteState.Success(it)
                },
                {
                    noteState.value = NoteState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                },
                {
                    Timber.e("ON COMPLETE")
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteById(id: Long) {
        val subscription = notesRepository
            .deleteById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("DELETED")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)

        statisticsHolder.cutGraphSizes()
    }

    override fun changeArchived(id: Long, arch: Boolean) {
        val subscription = notesRepository
            .changeArchived(id, arch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("ARCHIVE CHANGED")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insert(noteEntity: NoteEntity) {
        val subscription = notesRepository
            .insert(noteEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("COMPLETE")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)

        statisticsHolder.addToCurrentDay()//todo ne zaboravi ovoga
    }

    override fun updateNote(id: Long, title: String, content: String) {
        val subscription = notesRepository
            .updateNote(id, title, content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("UPDATED")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

}