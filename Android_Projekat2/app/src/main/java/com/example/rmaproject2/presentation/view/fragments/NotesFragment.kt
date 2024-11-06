package com.example.rmaproject2.presentation.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmaproject2.R
import com.example.rmaproject2.data.models.note.Note
import com.example.rmaproject2.data.models.note.NoteEntity
import com.example.rmaproject2.databinding.FragmentNotesBinding
import com.example.rmaproject2.presentation.contract.NoteContract
import com.example.rmaproject2.presentation.view.activities.AddNoteActivity
import com.example.rmaproject2.presentation.view.recycler.adapter.NotesAdapter
import com.example.rmaproject2.presentation.view.states.NoteState
import com.example.rmaproject2.presentation.viewModels.NotesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


class NotesFragment : Fragment(R.layout.fragment_notes) {

    private val noteViewModel: NoteContract.ViewModel by sharedViewModel<NotesViewModel>()
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initRecycler()
        initListeners()
        initObservers()
    }


    private fun initRecycler() {
        binding.notesRV.layoutManager = LinearLayoutManager(context)
        adapter = NotesAdapter(::archiveButton, ::deleteButton, ::edit)//:: prosledjivanje funkcije po referenci, i ta fuja prima ceo Note element na koji smo kliknuli
        binding.notesRV.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.notesRV.adapter = adapter
    }

    private fun archiveButton(note: Note){
        val bool: Boolean = !note.archived//dajemo suprotno
        noteViewModel.changeArchived(note.id, bool)
    }

    private fun deleteButton(note: Note){
        noteViewModel.deleteById(note.id)
    }

    private fun edit(note: Note){
        startEditActivity(note.title, note.content, note.id)
    }

    private fun initListeners() {
        var bool: Int = 0

        binding.switch1.setOnClickListener {//todo ima bug opravi ga (kada se vise puta arhivira promeni se ekran)
            if (binding.switch1.isChecked) {
                noteViewModel.getAll()
                bool = 1
            } else {
                noteViewModel.getAllNonArchived()
                bool = 0
            }
        }


        binding.addNoteBtn.setOnClickListener {
            val intent = Intent(activity, AddNoteActivity::class.java)
            intent.putExtra("type", "add")
            addNoteActivity.launch(intent)
        }

        binding.notesSearch.doAfterTextChanged {
            val filter = it.toString()
            noteViewModel.getAllBySearch(filter, bool)
        }
    }

    private fun initObservers() {
        noteViewModel.noteState.observe(viewLifecycleOwner, Observer { noteState ->
            Timber.e(noteState.toString())
            renderState(noteState)
        })
        noteViewModel.getAllNonArchived()
    }

    private fun renderState(state: NoteState) {
        when (state) {
            is NoteState.Success -> {
                adapter.submitList(state.notes)
            }
            is NoteState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun startEditActivity(title: String, content: String, id: Long) {
        val intent = Intent(activity, AddNoteActivity::class.java)
        intent.putExtra("type", "edit")
        intent.putExtra("title", title)
        intent.putExtra("content", content)
        intent.putExtra("id", id)

        editNoteActivity.launch(intent)
    }

    private val addNoteActivity: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())

        if (it.resultCode == RESULT_OK) {
                val data = it.data
                val title = data?.getStringExtra("title")
                val content = data?.getStringExtra("content")
                if (title != null && content != null) {
                    noteViewModel.insert(
                        NoteEntity(
                            0,
                            title,
                            content,
                            date,
                            false
                        )
                    )
                }
            }
        }

    private val editNoteActivity: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val data = it.data
                val title = data?.getStringExtra("title")
                val content = data?.getStringExtra("content")
                val id = data?.getStringExtra("id").toString().toLong()

                if (title != null && content != null)
                    noteViewModel.updateNote(id, title, content)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}