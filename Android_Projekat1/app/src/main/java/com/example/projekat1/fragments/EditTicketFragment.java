package com.example.projekat1.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projekat1.R;
import com.example.projekat1.models.Ticket;

public class EditTicketFragment extends Fragment {

    private Spinner typeSpinner;
    private Spinner prioritySpinner;
    private Button editButton;
    private EditText estimated;
    private EditText title;
    private EditText description;
    private Ticket ticket;

    public EditTicketFragment(Ticket ticket) {
        super(R.layout.fragment_edit_ticket);
        this.ticket = ticket;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initOvservers();
    }

    private void initView(View view){
        typeSpinner = (Spinner) view.findViewById(R.id.typeSpinnerEdit);
        prioritySpinner = (Spinner) view.findViewById(R.id.prioritySpinnerEdit);
        editButton = (Button) view.findViewById(R.id.editButton);
        estimated = (EditText) view.findViewById(R.id.estimatedETEdit);
        title = (EditText) view.findViewById(R.id.titleETEdit);
        description = (EditText) view.findViewById(R.id.descriptionETEdit);

        if (ticket != null){
            estimated.setText(String.valueOf(ticket.getEstimated()));
            title.setText(ticket.getTitle());
            description.setText(ticket.getDescription());
        }

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.priority, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.ticket_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        prioritySpinner.setAdapter(priorityAdapter);
        typeSpinner.setAdapter(typeAdapter);
    }

    private void initOvservers(){
        editButton.setOnClickListener(v -> {
            if (checkIfAllSelected()){
                ticket.setType(typeSpinner.getSelectedItem().toString());
                ticket.setPriority(prioritySpinner.getSelectedItem().toString());
                ticket.setEstimated(Integer.parseInt(estimated.getText().toString()));
                ticket.setTitle(title.getText().toString());
                ticket.setDescription(description.getText().toString());
//                clearAllFields();
                requireActivity().onBackPressed();
            } else showErrorToast();
        });
    }



    private boolean checkIfAllSelected(){
        return !title.getText().toString().equals("")
                && !description.getText().toString().equals("")
                && !estimated.getText().toString().equals("")
                && !typeSpinner.getSelectedItem().toString().equals("Type")
                && !prioritySpinner.getSelectedItem().toString().equals("Priority");
    }

    private void showErrorToast(){
        Toast toast = Toast.makeText(this.getActivity(), "All fields must be filled in to create ticket", Toast.LENGTH_SHORT);
        View view = toast.getView();
        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    private void clearAllFields(){
        estimated.setText("");
        title.setText("");
        description.setText("");
    }
}
