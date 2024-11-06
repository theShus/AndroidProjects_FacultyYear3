package com.example.rmaproject2.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.rmaproject2.data.models.note.Note

class NotesDiffCallback : DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return  oldItem.title == newItem.title &&
                oldItem.content == newItem.content
    }
}