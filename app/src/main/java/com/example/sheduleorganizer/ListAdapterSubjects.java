package com.example.sheduleorganizer;


import android.content.Context;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapterSubjects  extends RecyclerView.Adapter<ListAdapterSubjects.ViewHolder>{

    private ArrayList<Subjects> textStrings = new ArrayList<>();
    private Context mContext;
    private static UserManager userManager;

    public ListAdapterSubjects(ArrayList<Subjects> textStrings, Context mContext) {
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

                userManager.deleteSubject(textStrings.get(i).getId(), new Callback<ResponseBody>() {
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
                        Log.d("eroos",t.getMessage() );
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
