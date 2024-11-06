package com.example.projekat1.recycler;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.projekat1.R;
import com.example.projekat1.activities.MainActivity;
import com.example.projekat1.models.Ticket;
import com.example.projekat1.viewModels.SharedViewModel;

import java.util.function.Consumer;


public class TicketAdapter extends ListAdapter<Ticket, TicketAdapter.ViewHolder> {

    private final Consumer<Ticket> onTicketClicked;
    public static SharedViewModel sharedViewModel;

    public TicketAdapter(SharedViewModel sharedViewModel, @NonNull DiffUtil.ItemCallback<Ticket> diffCallback, Consumer<Ticket> onTicketClicked) {
        super(diffCallback);
        this.onTicketClicked = onTicketClicked;
        TicketAdapter.sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            Ticket ticket = getItem(position);//todo premesti sve on slick listeners u constructor
            onTicketClicked.accept(ticket);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = getItem(position);
        holder.bind(ticket);
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

        public void bind(Ticket ticket) {
            SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences(itemView.getContext().getPackageName(), Context.MODE_PRIVATE);
            boolean isAdmin = false;

            ImageView imageView = itemView.findViewById(R.id.ticketIcon);
            ImageButton iTopButton = itemView.findViewById(R.id.ticketTopButton);
            ImageButton iButtonBottom = itemView.findViewById(R.id.ticketBottomButton);

            ((TextView) itemView.findViewById(R.id.ticketTitleListItem)).setText(ticket.getTitle());//postavljamo title na list item
            ((TextView) itemView.findViewById(R.id.ticketDescriptionListItem)).setText(ticket.getDescription());


            //da li je ulogovan admin
            if((sharedPreferences.getString(MainActivity.LOGGED_USER, "").contains("admin")))
                isAdmin = true;

            //postavljanje glavne slike na osnovu tipa tiketa
            if (ticket.getType().equals("Bug")){
                imageView.setImageResource(R.drawable.ic_baseline_bug_report_24);
            }
            else imageView.setImageResource(R.drawable.ic_engance);

            //za to-do recycler
            if (ticket.getProgress().equals(MainActivity.TODO)){//ako ima to-do marker u sebi
                iTopButton.setImageResource(R.drawable.ic_arrow_right_24);

                iTopButton.setOnClickListener(tb ->{//move ticket to progress from to-do
                    sharedViewModel.moveTicketToProgress(ticket);
                });

                if(isAdmin){
                    iButtonBottom.setImageResource(R.drawable.ic_remove);//ako je admin ima remove
                    iButtonBottom.setOnClickListener(bb ->{
                        sharedViewModel.removeTodoTicket(ticket);
                    });
                }
                else iButtonBottom.setVisibility(View.INVISIBLE);//ako nije admin sakri dugme
            }
            //za in progress recycler
            else if (ticket.getProgress().equals(MainActivity.IN_PROGRESS)){
                iTopButton.setImageResource(R.drawable.ic_arrow_right_24);
                iButtonBottom.setImageResource(R.drawable.ic_arrow_left_24);

                iTopButton.setOnClickListener(tb ->{//move ticket to to-do from progress
                    sharedViewModel.moveTicketToDone(ticket);
                });

                iButtonBottom.setOnClickListener(bb ->{//move ticket to done from progress
                    sharedViewModel.moveTicketToTodo(ticket);
                });
            }
            //za done recycler
            else{
                iTopButton.setVisibility(View.INVISIBLE);//ne trebaju nam drugmici vise, samo ih sklonim
                iButtonBottom.setVisibility(View.INVISIBLE);
            }
        }
    }
}
