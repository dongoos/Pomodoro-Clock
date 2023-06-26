package com.example.tomato.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tomato.MainActivity;
import com.example.tomato.R;
import com.example.tomato.model.Model;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private List<Model> eventsList;
    private MainActivity activity;

    public EventListAdapter(MainActivity activity){
        this.activity = activity;

    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_layout, parent, false);
        return new ViewHolder(itemView);

    }

    public void onBindViewHolder( ViewHolder holder, int position) {
        Model item = eventsList.get(position);
        holder.eventName.setText(item.getTask());

    }

    public int getItemCount(){
        return  eventsList.size();
    }

    //idk wut this does probably don't need it but it's here just in case :)
    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setEvent (List<Model> eventsList){
        this.eventsList = eventsList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView event;
        TextView eventName;

        ViewHolder(View view){
            super(view);
            event = view.findViewById(R.id.eventItem);
            eventName = view.findViewById(R.id.eventName);
        }
    }
}
