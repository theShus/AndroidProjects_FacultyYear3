package com.example.rmaproject2.presentation.view.recycler.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.rmaproject2.data.models.note.Note
import com.example.rmaproject2.databinding.NoteItemBinding

class NoteViewHolder(
    private val itemBinding: NoteItemBinding,
    val archiveNote: (position: Int) -> Unit,
    val deleteById: (position: Int) -> Unit,
    val startEditActivity: (position: Int) -> Unit//saljemo mu poziciju/ id itema iz recycler
) : RecyclerView.ViewHolder(itemBinding.root) {

    init{
        itemBinding.archiveBtn.setOnClickListener{
//            val bool: Boolean = itemBinding.archived.text.toString() == "false"
//            itemBinding.archived.text = bool.toString()
            archiveNote(layoutPosition)//layoutPosition je onaj na koji smo kliknuli
        }

        itemBinding.deleteBtn.setOnClickListener{
            deleteById(layoutPosition)
        }

        itemBinding.editBtn.setOnClickListener{
            startEditActivity(layoutPosition)

        }

    }

    fun bind(note: Note){
        itemBinding.notesTitle.text = note.title
        itemBinding.noteContent.text = note.content
        itemBinding.id.text = note.id.toString()
        itemBinding.archived.text = note.archived.toString()
    }
}