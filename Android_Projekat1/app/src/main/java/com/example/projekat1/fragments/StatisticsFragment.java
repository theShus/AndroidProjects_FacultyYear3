package com.example.projekat1.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projekat1.R;
import com.example.projekat1.models.Ticket;
import com.example.projekat1.viewModels.SharedViewModel;

public class StatisticsFragment extends Fragment {
    private TextView todoNum1;
    private TextView todoNum2;
    private TextView todoNum3;
    private TextView progressNum1;
    private TextView progressNum2;
    private TextView progressNum3;
    private TextView doneNum1;
    private TextView doneNum2;
    private TextView doneNum3;
    private SharedViewModel sharedViewModel;

    public StatisticsFragment() {
        super(R.layout.fragment_statistics);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setStatistics();
    }

    private void initViews(View view){
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        todoNum1 = view.findViewById(R.id.numTodo1);
        todoNum2 = view.findViewById(R.id.numToDo2);
        todoNum3 = view.findViewById(R.id.numToDo3);
        progressNum1 = view.findViewById(R.id.numProgress1);
        progressNum2 = view.findViewById(R.id.numProgress2);
        progressNum3 = view.findViewById(R.id.numProgress3);
        doneNum1 = view.findViewById(R.id.numDone1);
        doneNum2 = view.findViewById(R.id.numDone2);
        doneNum3 = view.findViewById(R.id.numDone3);
    }

    private void setStatistics(){
        sharedViewModel.getStatisticsToDoLD().observe(getViewLifecycleOwner(), tickets -> {
            int todoBug = 0;
            int todoEnhancement = 0;

            for (Ticket t: tickets) {
                if (t.getType().equals("Bug")){
                    todoBug++;
                }
                else todoEnhancement++;
            }
            todoNum1.setText(String.valueOf(tickets.size()));
            todoNum2.setText(String.valueOf(todoBug));
            todoNum3.setText(String.valueOf(todoEnhancement));
        });

        sharedViewModel.getStatisticsProgressLD().observe(getViewLifecycleOwner(), tickets -> {
            int progressBug = 0;
            int progressEnhancement = 0;

            for (Ticket t: tickets) {
                if (t.getType().equals("Bug")){
                    progressBug++;
                }
                else progressEnhancement++;
            }

            progressNum1.setText(String.valueOf(tickets.size()));
            progressNum2.setText(String.valueOf(progressBug));
            progressNum3.setText(String.valueOf(progressEnhancement));
        });

        sharedViewModel.getStatisticsDoneLD().observe(getViewLifecycleOwner(), tickets -> {
            int doneBug = 0;
            int doneEnhancement = 0;

            for (Ticket t: tickets) {
                if (t.getType().equals("Bug")){
                    doneBug++;
                }
                else doneEnhancement++;
            }
            doneNum1.setText(String.valueOf(tickets.size()));
            doneNum2.setText(String.valueOf(doneBug));
            doneNum3.setText(String.valueOf(doneEnhancement));
        });
    }

}
