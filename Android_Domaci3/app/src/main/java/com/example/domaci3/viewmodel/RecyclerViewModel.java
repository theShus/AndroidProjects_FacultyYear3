package com.example.domaci3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domaci3.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecyclerViewModel extends ViewModel {

    private final MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();
    private ArrayList<Contact> contactList = new ArrayList<>();
    public static int counter = 101;

    public RecyclerViewModel() {
        for (int i = 0; i <= 100; i++) {
            Contact contact = new Contact(i,"Neko Ime", "069/666-999", "ppetrovic@raf.rs", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cG9ydHJhaXQlMjBtYW58ZW58MHx8MHx8&w=1000&q=80");
            contactList.add(contact);
        }
        ArrayList<Contact> listToSubmit = new ArrayList<>(contactList);
        contacts.setValue(listToSubmit);
    }


    public void filterContacts(String filter) {
        List<Contact> filteredList = contactList.stream().filter(contact -> contact.getFirstName().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());
        contacts.setValue(filteredList);
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public void removeCar(int id) {
        Optional<Contact> contactObject = contactList.stream().filter(contact -> contact.getId() == id).findFirst();
        if (contactObject.isPresent()) {
            contactList.remove(contactObject.get());
            ArrayList<Contact> listToSubmit = new ArrayList<>(contactList);
            contacts.setValue(listToSubmit);
        }
    }

    public int addContact(String fullName, String phoneNumber, String email, String picture) {
        int id = counter++;
        Contact contact = new Contact(id, fullName, phoneNumber, email, picture);
        contactList.add(contact);
        ArrayList<Contact> listToSubmit = new ArrayList<>(contactList);
        contacts.setValue(listToSubmit);
        return id;
    }
}
