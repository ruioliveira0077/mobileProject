package com.example.sheduleorganizer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapterTimeTable  extends RecyclerView.Adapter<ListAdapterTimeTable.ViewHolder>{

    private ArrayList<Classes> textStrings = new ArrayList<>();
    private Context mContext;
    private static UserManager userManager;

    public ListAdapterTimeTable(ArrayList<Classes> textStrings, Context mContext) {
        this.textStrings = textStrings;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ListAdapterTimeTable.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_timetable, viewGroup, false);
        ListAdapterTimeTable.ViewHolder holder = new ListAdapterTimeTable.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ListAdapterTimeTable.ViewHolder viewHolder, final int i) {
        viewHolder.hours.setText(textStrings.get(i).getDate());


        userManager = userManager.getInstance();

        // Delete Class
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userManager.deleteClass(textStrings.get(i).getId(), new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.w(" => ",new Gson().toJson(response));
                        if (response.code() == 200) {
                            Log.d("status", "200");
                            textStrings.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeChanged(i, textStrings.size());
                        } else {
                            Log.d("status", "Failed");
                        }
                    }
                    @Override
                    public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {
                        Log.d("FailureError",t.getMessage() );
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return textStrings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView hours;
        TextView info;
        TextView room;
        ImageView delete;

        RelativeLayout parentLayout;
        public ViewHolder(View itemView){
            super(itemView);

            hours = itemView.findViewById(R.id.hour);
            info = itemView.findViewById(R.id.info);
            room = itemView.findViewById(R.id.room);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
