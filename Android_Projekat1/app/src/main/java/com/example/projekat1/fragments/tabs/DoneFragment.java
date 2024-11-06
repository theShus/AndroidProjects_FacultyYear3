package com.example.projekat1.fragments.tabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat1.R;
import com.example.projekat1.activities.MainActivity;
import com.example.projekat1.activities.SingleFragmentDisplay;
import com.example.projekat1.fragments.BottomNavFragment;
import com.example.projekat1.recycler.TicketAdapter;
import com.example.projekat1.recycler.TicketDiffItemCallback;
import com.example.projekat1.viewModels.SharedViewModel;

public class DoneFragment extends Fragment {

    private RecyclerView recyclerView;
    private SharedViewModel sharedViewModel;
    public static  TicketAdapter ticketAdapter;
    private EditText searchDoneTickets;
    public static final int REQUEST_CODE = 1;

    public DoneFragment() {
        super(R.layout.fragment_done);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        initView(view);
        initObservers();
        initRecycler(view);
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.listRvDone);
        searchDoneTickets = view.findViewById(R.id.searchDoneTickets);
    }

    private void initObservers() {
        sharedViewModel.getTicketsDoneLiveData().observe(getViewLifecycleOwner(), tickets -> ticketAdapter.submitList(tickets));

        searchDoneTickets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                sharedViewModel.filterDoneTickets(s.toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
                sharedViewModel.updateTicket(sharedPreferences.getString(MainActivity.MAIN_FRAGMENT,""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecycler(View view) {
        ticketAdapter = new TicketAdapter(sharedViewModel, new TicketDiffItemCallback(), ticket -> {
//            Toast.makeText(getActivity(), ticket.getId() + "asdfasdf", Toast.LENGTH_SHORT).show();
//            transaction.replace(R.id.mainFragContainer, new EditTicketFragment());
//            transaction.addToBackStack(null);
//            transaction.commit();
            Intent intent = new Intent(view.getContext(),  SingleFragmentDisplay.class);
            intent.putExtra("ticket", ticket);
            startActivityForResult(intent , REQUEST_CODE);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(ticketAdapter);
    }
}
