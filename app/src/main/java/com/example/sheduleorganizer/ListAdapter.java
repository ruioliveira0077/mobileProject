package com.example.sheduleorganizer;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.ViewHolder>{



    private ArrayList<Courses> textStrings = new ArrayList<>();
    private Context mContext;

    public ListAdapter(ArrayList<Courses> textStrings, Context mContext) {
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.text.setText(textStrings.get(i).getTitle());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Delete at position"+i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return textStrings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView  text;
        ImageView delete;
        RelativeLayout parentLayout;
        public ViewHolder(View itemView){
            super(itemView);
            text = itemView.findViewById(R.id.text);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

