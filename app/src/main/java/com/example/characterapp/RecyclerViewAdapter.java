package com.example.characterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<String> mData;
    private final LayoutInflater mInflater;
    private final ClickListener mClickListener;
    private final List<Item> items = new ArrayList<>();

    RecyclerViewAdapter(Context context, List<String> data, ClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = mClickListener;

        for (int i=0; i<data.size(); i++) {
            items.add(new Item(data.get(i), ""));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.alertDialog = new AlertDialog.Builder(parent.getContext()).create();

        // Set click listeners
        viewHolder.myTextView.setOnClickListener(mClickListener::onItemClick);
        viewHolder.myNotesBtnView.setOnClickListener(mClickListener::onNotesBtnClick);
        viewHolder.myInfoBtnView.setOnClickListener(mClickListener::onInfoBtnClick);

        return viewHolder;
    }

    // binds the data to the 3 views in a single row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String person = mData.get(position);
        holder.myTextView.setText(person);

        Item item = items.get(position);
        if (item.isUpdated()) {
            item.setUpdated(false);
        }
    }

    public List<String> getmData() {
        return mData;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<Item> getItems() {
        return items;
    }

    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AlertDialog alertDialog;
        TextView myTextView;
        Button myNotesBtnView, myInfoBtnView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.contactName);
            myNotesBtnView = (Button) itemView.findViewById(R.id.btnNotes);
            myInfoBtnView = (Button) itemView.findViewById(R.id.btnInfo);
        }

        @Override
        public void onClick(View view) {}
    }

}
