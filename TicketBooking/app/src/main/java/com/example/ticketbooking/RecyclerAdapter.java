package com.example.ticketbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<TicketBookingModel> payemodel;
    private AdapterView.OnItemClickListener mListener;
    private int s;

    public RecyclerAdapter(Context context, List<TicketBookingModel> paymodel, int s) {
        mContext = context;
        payemodel = paymodel;
        s = s;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_ticket, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        TicketBookingModel current = payemodel.get(position);
        holder.date.setText(current.getDate());
        holder.source.setText("source : " +current.getSource());
        holder.destination.setText("destination : " +current.getDestination());

        holder.amount.setText( current.getAmount() + " Rs");


    }

    @Override
    public int getItemCount() {
        return payemodel.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView source, date, amount,destination;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            source = itemView.findViewById(R.id.source);
            destination = itemView.findViewById(R.id.destination);

            itemView.setOnClickListener(this);
//            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
            }
        }

        private String getDateToday() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String today = dateFormat.format(date);
            return today;
        }
    }
}
