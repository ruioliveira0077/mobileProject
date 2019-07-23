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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private ArrayList<Courses> textStrings = new ArrayList<>();
    private Context mContext;
    private static UserManager userManager;

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
        userManager = userManager.getInstance();

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Delete at position"+textStrings.get(i).getId(), Toast.LENGTH_SHORT).show();

                userManager.deleteCourse(textStrings.get(i).getId(), new Callback<okhttp3.ResponseBody>() {
                    @Override
                    public void onResponse(Call<okhttp3.ResponseBody> call, Response<okhttp3.ResponseBody> response) {
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
                        Log.d("eroos",t.getMessage() );
                    }
                });
            }
        });

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), GenericForm.class);
                intent.putExtra("id_course", ""+textStrings.get(i).getId());
                intent.putExtra("name", textStrings.get(i).getTitle());
                v.getContext().startActivity(intent);

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
        ImageView edit;
        RelativeLayout parentLayout;
        public ViewHolder(View itemView){
            super(itemView);
            text = itemView.findViewById(R.id.text);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

