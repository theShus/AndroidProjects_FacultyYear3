package com.example.projekat1.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projekat1.R;
import com.example.projekat1.activities.MainActivity;
import com.example.projekat1.models.Ticket;

public class TicketDetailsFragment extends Fragment {
    private TextView type;
    private TextView title;
    private TextView priority;
    private TextView estimation;
    private TextView description;
    private Button loggedTimeButton;
    private Button editButton;
    private ImageView image;
    private int buttonNum;
    private Ticket ticket;
    public static boolean increase = true;

    public TicketDetailsFragment(Ticket ticket) {
        super(R.layout.fragment_ticket_details);
        this.ticket = ticket;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        buttonNum = ticket.getLoggedTime();
        initText();
        initObservers();
    }

    private void initViews(View view){
        type = view.findViewById(R.id.tdType);
        title = view.findViewById(R.id.tdTitle);
        priority = view.findViewById(R.id.tdPriority);
        editButton = view.findViewById(R.id.goToEdit);
        image = view.findViewById(R.id.ticketDetailsImg);
        estimation = view.findViewById(R.id.tdEstimation);
        description = view.findViewById(R.id.tdDescription);
        loggedTimeButton = view.findViewById(R.id.tdLoggedButton);
    }

    private void initText(){
        type.setText(ticket.getType());
        title.setText(ticket.getTitle());
        priority.setText(ticket.getPriority());
        description.setText(ticket.getDescription());
        estimation.setText(String.valueOf(ticket.getEstimated()));
        loggedTimeButton.setText(String.valueOf(buttonNum));

        if (ticket.getType().equals("Bug")){
            image.setImageResource(R.drawable.ic_baseline_bug_report_24);
        }
        else image.setImageResource(R.drawable.ic_engance);
    }

    private void initObservers(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();//pokrenemo main fragment

        loggedTimeButton.setOnClickListener(v ->{
            if (increase){
                loggedTimeButton.setText(String.valueOf(++buttonNum));
                ticket.setLoggedTime(buttonNum);
                increase = false;
            }
            else {
                loggedTimeButton.setText(String.valueOf(--buttonNum));
                ticket.setLoggedTime(buttonNum);
                increase = true;
            }
            if (buttonNum > ticket.getEstimated()) loggedTimeButton.setBackgroundColor(Color.RED);
        });

        if (sharedPreferences.getString(MainActivity.LOGGED_USER, "").contains("admin")){
            editButton.setOnClickListener(e -> {
                transaction.replace(R.id.singleFratView, new EditTicketFragment(ticket));
//                transaction.addToBackStack(null); //ovo moze da bude funkcionalnost ali nema potrebe
                transaction.commit();
            });
        }
        else editButton.setVisibility(View.GONE);

        if (ticket.getProgress().equals(MainActivity.DONE)){
            loggedTimeButton.setEnabled(false);
            editButton.setVisibility(View.GONE);
        }
    }
}
