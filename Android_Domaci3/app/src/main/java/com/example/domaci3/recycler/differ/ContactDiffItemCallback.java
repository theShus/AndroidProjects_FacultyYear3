package com.example.domaci3.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.domaci3.model.Contact;

// Proverava da li se promenio neki item u RecyclerView-u

public class ContactDiffItemCallback extends DiffUtil.ItemCallback<Contact> {
    @Override
    public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
        return oldItem.getPicture().equals(newItem.getPicture())
                && oldItem.getFirstName().equals(newItem.getFirstName())
                && oldItem.getLastName().equals(newItem.getLastName())
                && oldItem.getEmail().equals(newItem.getEmail())
                && oldItem.getPhoneNumber().equals(newItem.getPhoneNumber());
    }
}
