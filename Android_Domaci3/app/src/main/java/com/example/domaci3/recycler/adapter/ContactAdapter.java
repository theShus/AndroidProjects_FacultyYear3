package com.example.domaci3.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.domaci3.R;
import com.example.domaci3.model.Contact;

import java.util.function.Consumer;

public class ContactAdapter extends ListAdapter<Contact, ContactAdapter.ViewHolder> {

    private final Consumer<Contact> onContactClicked;

    public ContactAdapter(@NonNull DiffUtil.ItemCallback<Contact> diffCallback, Consumer<Contact> onContactClicked) {
        super(diffCallback);
        this.onContactClicked = onContactClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            Contact contact = getItem(position);
            onContactClicked.accept(contact);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = getItem(position);
        holder.bind(contact);
    }

    // unutrasnja klasa
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }



        public void bind(Contact contact) {
            ImageView imageView = itemView.findViewById(R.id.contactPicture);
            Glide
                .with(context)
                .load(contact.getPicture())
                .circleCrop()
                .into(imageView);
            ((TextView) itemView.findViewById(R.id.contactFirstName)).setText(contact.getFirstName());
            ((TextView) itemView.findViewById(R.id.contactLastName)).setText(contact.getLastName());
            ((TextView) itemView.findViewById(R.id.contactPhoneNumber)).setText(contact.getPhoneNumber());
            ((TextView) itemView.findViewById(R.id.contactEmail)).setText(contact.getEmail());

            ((ImageButton) itemView.findViewById(R.id.cancelBut)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(contact.getId());
                }
            });


        }

    }
}
