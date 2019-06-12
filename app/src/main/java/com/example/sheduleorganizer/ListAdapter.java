package com.example.sheduleorganizer;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.ViewHolder>{



    private ArrayList<String> textStrings = new ArrayList<>();
    private Context mContext;

    public ListAdapter(ArrayList<String> textStrings, Context mContext) {
        this.textStrings = textStrings;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text.setText(textStrings.get(i));
    }

    @Override
    public int getItemCount() {
        return textStrings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView  text;
        RelativeLayout parentLayout;
        public ViewHolder(View itemView){
            super(itemView);
            text = itemView.findViewById(R.id.text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

