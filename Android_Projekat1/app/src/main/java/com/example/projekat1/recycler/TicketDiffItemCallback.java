package com.example.projekat1.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.projekat1.models.Ticket;


// Proverava da li se promenio neki item u RecyclerView-u

public class TicketDiffItemCallback extends DiffUtil.ItemCallback<Ticket> {
    @Override
    public boolean areItemsTheSame(@NonNull Ticket oldItem, @NonNull Ticket newItem) {
        return oldItem.getId() == (newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Ticket oldItem, @NonNull Ticket newItem) {
        return oldItem.getTitle().equals(newItem.getTitle())
                && oldItem.getDescription().equals(newItem.getDescription())
                && oldItem.getPriority().equals(newItem.getPriority())
                && oldItem.getLoggedTime() == (newItem.getLoggedTime())
                && oldItem.getEstimated() ==(newItem.getEstimated());
    }
}
