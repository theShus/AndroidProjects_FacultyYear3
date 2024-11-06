package com.example.rmaproject2.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.rmaproject2.databinding.NoteItemBinding
import com.example.rmaproject2.data.models.note.Note
import com.example.rmaproject2.presentation.view.recycler.diff.NotesDiffCallback
import com.example.rmaproject2.presentation.view.recycler.viewHolder.NoteViewHolder

class NotesAdapter (
    val archiveNote: (note: Note) -> Unit ,//ovde je note zato sto on je iz proslog (sa layoutPosition) primio objekat nota itema na koji smo kliknuli
    val deleteById: (note: Note) -> Unit,
    val startEditActivity: (note: Note) -> Unit,
) : ListAdapter<Note, NoteViewHolder>(NotesDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(
            itemBinding,
            {archiveNote(getItem(it))},//get item na osnovu kordinata u gui zna na koji smo kliknuli, i salje note dalje, tj u ovu gornju funkciju
            {deleteById(getItem(it))},
            {startEditActivity(getItem(it))},
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {//
        holder.bind(getItem(position))
    }
}