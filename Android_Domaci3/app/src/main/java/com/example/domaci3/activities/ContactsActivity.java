package com.example.domaci3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.domaci3.R;
import com.example.domaci3.recycler.adapter.ContactAdapter;
import com.example.domaci3.recycler.differ.ContactDiffItemCallback;
import com.example.domaci3.viewmodel.RecyclerViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerViewModel recyclerViewModel;
    private RecyclerView recyclerView;
    private EditText filterContacts;
    private EditText addContact;
    private Button addContactButton;
    private ConstraintLayout mainLayout;
    private ContactAdapter contactAdapter;
    private ImageButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewModel = new ViewModelProvider(this).get(RecyclerViewModel.class);
        init();
    }

    private void init(){
        initView();
        initListeners();
        initObservers();
        initRecycler();
    }

    private void initView(){
        mainLayout = findViewById(R.id.constraintLayout);
        recyclerView = findViewById(R.id.contactListRV);
        filterContacts = findViewById(R.id.contactsFilterET);
        addContact = findViewById(R.id.contactInputET);
        addContactButton = findViewById(R.id.addButton);
//        cancelButton = (ImageButton) findViewById(R.id.cancel_button);
    }

    private void initListeners(){
        filterContacts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                recyclerViewModel.filterContacts(s.toString());
            }
        });

        addContactButton.setOnClickListener(v -> {
            String fullName = addContact.getText().toString();
            int contactId = recyclerViewModel.addContact(fullName, "069/666-999", "ppetrovic@raf.rs", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cG9ydHJhaXQlMjBtYW58ZW58MHx8MHx8&w=1000&q=80");
            showAddSnackBar(contactId);
        });
    }


    private void showAddSnackBar(int id) {
        Snackbar
                .make(mainLayout, "Contact created", Snackbar.LENGTH_SHORT)
                .setAction("Undo", view -> recyclerViewModel.removeCar(id))
                .show();
    }

    private void initObservers() {
        recyclerViewModel.getContacts().observe(this, cars -> {
            contactAdapter.submitList(cars);
        });
    }

    private void initRecycler() {
        contactAdapter   = new ContactAdapter(new ContactDiffItemCallback(), contact -> {
            Toast.makeText(this, contact.getId() + "", Toast.LENGTH_SHORT).show();
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(contactAdapter);
    }


}