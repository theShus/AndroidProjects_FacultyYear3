package com.example.projekat1.viewModels;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projekat1.activities.MainActivity;
import com.example.projekat1.fragments.tabs.DoneFragment;
import com.example.projekat1.fragments.tabs.InProgessFragment;
import com.example.projekat1.fragments.tabs.ToDoFragment;
import com.example.projekat1.models.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SharedViewModel extends ViewModel {

    public static int counter = 1;

    private final MutableLiveData<List<Ticket>> ticketsTodoLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Ticket>> ticketsInProgressLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Ticket>> ticketsDoneLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Ticket>> statisticsToDoLD = new MutableLiveData<>();
    private final MutableLiveData<List<Ticket>> statisticsProgressLD = new MutableLiveData<>();
    private final MutableLiveData<List<Ticket>> statisticsDoneLD = new MutableLiveData<>();

    private final ArrayList<Ticket> ticketsTodoTempList = new ArrayList<>();
    private final ArrayList<Ticket> ticketsInProgressTempList = new ArrayList<>();
    private final ArrayList<Ticket> ticketsDoneTempList = new ArrayList<>();


    public SharedViewModel() {

        if (MainActivity.refill){
            addTestTickets();//napunjeno sa filler ticketima
            MainActivity.refill = false;
        }

        ArrayList<Ticket> listToSubmit = new ArrayList<>(ticketsTodoTempList);
        ticketsTodoLiveData.setValue(listToSubmit);
        statisticsToDoLD.setValue(listToSubmit);
        ArrayList<Ticket> listToSubmit2 = new ArrayList<>(ticketsInProgressTempList);
        ticketsInProgressLiveData.setValue(listToSubmit2);
        statisticsProgressLD.setValue(listToSubmit2);
        ArrayList<Ticket> listToSubmit3 = new ArrayList<>(ticketsDoneTempList);
        ticketsDoneLiveData.setValue(listToSubmit3);
        statisticsDoneLD.setValue(listToSubmit3);
    }

    //getteri
    public MutableLiveData<List<Ticket>> getTicketsTodoLiveData() {
        return ticketsTodoLiveData;
    }
    public MutableLiveData<List<Ticket>> getTicketsInProgressLiveData() {
        return ticketsInProgressLiveData;
    }
    public MutableLiveData<List<Ticket>> getTicketsDoneLiveData() {
        return ticketsDoneLiveData;
    }
    public MutableLiveData<List<Ticket>> getStatisticsToDoLD() {
        return statisticsToDoLD;
    }
    public MutableLiveData<List<Ticket>> getStatisticsProgressLD() {
        return statisticsProgressLD;
    }
    public MutableLiveData<List<Ticket>> getStatisticsDoneLD() {
        return statisticsDoneLD;
    }

    //search filteri
    public void filterTodoTickets(String filter) {
        List<Ticket> filteredList = ticketsTodoTempList.stream().filter(
                ticket -> ticket.getTitle().toLowerCase().contains(filter.toLowerCase())
                || ticket.getDescription().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
        ticketsTodoLiveData.setValue(filteredList);
    }
    public void filterProgressTickets(String filter) {
        List<Ticket> filteredList = ticketsInProgressTempList.stream().filter(
                ticket -> ticket.getTitle().toLowerCase().contains(filter.toLowerCase())
                        || ticket.getDescription().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
        ticketsInProgressLiveData.setValue(filteredList);
    }
    public void filterDoneTickets(String filter) {
        List<Ticket> filteredList = ticketsDoneTempList.stream().filter(
                ticket -> ticket.getTitle().toLowerCase().contains(filter.toLowerCase())
                        || ticket.getDescription().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
        ticketsDoneLiveData.setValue(filteredList);
    }

    //dodatvanje / brisanje / pomeranje tiketa iz jednog taba u drugi
    public void addTodoTicket(Ticket ticket) {//good
        ticket.setId(counter++);
        ticketsTodoTempList.add(ticket);
        ArrayList<Ticket> listToSubmit = new ArrayList<>(ticketsTodoTempList);
        ticketsTodoLiveData.setValue(listToSubmit);
        statisticsToDoLD.setValue(listToSubmit);
    }

    public void removeTodoTicket(Ticket ticket) {//good
        if (ticketsTodoTempList.contains(ticket)){
            ticketsTodoTempList.remove(ticket);
            ArrayList<Ticket> listToSubmit = new ArrayList<>(ticketsTodoTempList);
            ticketsTodoLiveData.setValue(listToSubmit);
            statisticsToDoLD.setValue(listToSubmit);
        }
    }

    public void moveTicketToProgress(Ticket ticket) {//good
        ticket.setProgress(MainActivity.IN_PROGRESS);
        ticketsInProgressTempList.add(ticket);
        ArrayList<Ticket> listToSubmit = new ArrayList<>(ticketsInProgressTempList);
        ticketsInProgressLiveData.setValue(listToSubmit);
        statisticsProgressLD.setValue(listToSubmit);
        removeTodoTicket(ticket);
    }

    public void removeProgressTicket(Ticket ticket){
        if (ticketsInProgressTempList.contains(ticket)){
            ticketsInProgressTempList.remove(ticket);
            ArrayList<Ticket> listToSubmit = new ArrayList<>(ticketsInProgressTempList);
            ticketsInProgressLiveData.setValue(listToSubmit);
            statisticsProgressLD.setValue(listToSubmit);
        }
    }

    public void moveTicketToTodo(Ticket ticket){
        ticket.setProgress(MainActivity.TODO);
        ticketsTodoTempList.add(ticket);
        ArrayList<Ticket> listToSubmit = new ArrayList<>(ticketsTodoTempList);
        ticketsTodoLiveData.setValue(listToSubmit);
        statisticsToDoLD.setValue(listToSubmit);
        removeProgressTicket(ticket);
    }

    public void moveTicketToDone(Ticket ticket) {
        ticket.setProgress(MainActivity.DONE);
        ticketsDoneTempList.add(ticket);
        ArrayList<Ticket> listToSubmit = new ArrayList<>(ticketsDoneTempList);
        ticketsDoneLiveData.setValue(listToSubmit);
        statisticsDoneLD.setValue(listToSubmit);
        removeProgressTicket(ticket);
    }

/*
* 0 - id
* 1 - type
* 2 - priority
* 3 - estimated
* 4 - title
* 5 - description
* 6 - progress
* 7 - loggedTime;
*/
    @SuppressLint("NotifyDataSetChanged")
    public void updateTicket(String ticketString){
        String [] ticketSplit = ticketString.split("-");
        ArrayList<Ticket> listToSubmit;
        Ticket ticket;

        if(ticketSplit[6].equals(MainActivity.TODO)){
            ticket = findTicket(Integer.parseInt(ticketSplit[0]), ticketsTodoTempList);
            listToSubmit = new ArrayList<>(ticketsTodoTempList);
            ticketsTodoLiveData.setValue(listToSubmit);
            statisticsToDoLD.setValue(listToSubmit);

            ToDoFragment.ticketAdapter.notifyDataSetChanged();//updateujemo listu
        }
        else if (ticketSplit[6].equals(MainActivity.IN_PROGRESS)){
            ticket = findTicket(Integer.parseInt(ticketSplit[0]), ticketsInProgressTempList);
            listToSubmit = new ArrayList<>(ticketsInProgressTempList);
            ticketsInProgressLiveData.setValue(listToSubmit);
            statisticsProgressLD.setValue(listToSubmit);

            InProgessFragment.ticketAdapter.notifyDataSetChanged();

        }
        else  {
            ticket = findTicket(Integer.parseInt(ticketSplit[0]), ticketsDoneTempList);
            listToSubmit = new ArrayList<>(ticketsDoneTempList);
            ticketsDoneLiveData.setValue(listToSubmit);
            statisticsDoneLD.setValue(listToSubmit);

            DoneFragment.ticketAdapter.notifyDataSetChanged();
        }

        if(ticket != null){
            ticket.setType(ticketSplit[1]);
            ticket.setPriority(ticketSplit[2]);
            ticket.setEstimated(Integer.parseInt(ticketSplit[3]));
            ticket.setTitle(ticketSplit[4]);
            ticket.setDescription(ticketSplit[5]);
            ticket.setLoggedTime(Integer.parseInt(ticketSplit[7]));
        }
    }

    private Ticket findTicket(int id, List<Ticket> list){
        for (Ticket t: list) {
            if(t.getId() == id) return t;
        }
        return null;
    }

    //samo za testiranje dodati tiketi
    public void addTestTickets(){
        for (int i = 0; i < 5; i++) {
            Ticket ticket = new Ticket("Bug","Low",5,"Test ticket bug" + counter, "Ovo je test ticket za bug, treba da bude dovoljno dugacak da stane u dva reda");
            ticket.setId(counter++);
            ticketsTodoTempList.add(ticket);

            Ticket ticket2 = new Ticket("Enhancement","Medium",5,"Test ticket " + counter, "Ovo je test ticket za enhancement, treba dosta testirati da ne bi doslo do gresaka");
            ticket2.setId(counter++);
            ticketsTodoTempList.add(ticket2);
        }

        Ticket ticket3 = new Ticket("Bug","Low",5,"Test ticket PROGRESS" + counter, "Ovo je test ticket za bug");
        ticket3.setId(counter++);
        Ticket ticket4 = new Ticket("Enhancement","Low",5,"Test ticket PROGRESS" + counter, "Ovo je test ticket za Enhancement");
        ticket4.setId(counter++);
        Ticket ticket5 = new Ticket("Enhancement","Low",5,"Test ticket PROGRESS" + counter, "Ovo je test ticket za Enhancement");
        ticket5.setId(counter++);
        ticket3.setProgress(MainActivity.IN_PROGRESS);
        ticket5.setProgress(MainActivity.IN_PROGRESS);
        ticket4.setProgress(MainActivity.IN_PROGRESS);
        ticketsInProgressTempList.add(ticket3);
        ticketsInProgressTempList.add(ticket4);
        ticketsInProgressTempList.add(ticket5);

        Ticket ticket6 = new Ticket("Bug","Low",5,"Test ticket DONE" + counter, "Ovo je test ticket za bug");
        ticket6.setId(counter++);
        Ticket ticket7 = new Ticket("Enhancement","Medium",5,"Test ticket DONE" + counter, "Ovo je test ticket za Enhancement");
        ticket7.setId(counter++);
        ticket6.setProgress(MainActivity.DONE);
        ticket7.setProgress(MainActivity.DONE);
        ticketsDoneTempList.add(ticket6);
        ticketsDoneTempList.add(ticket7);
    }
}

