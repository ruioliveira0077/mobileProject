package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesActivity extends AppCompatActivity {

    private ArrayList<String> textStrings = new ArrayList<>();
    private static IUserApi service;
    private static UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        userManager = userManager.getInstance();

        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        final String userId = shared.getString("id","DEFAULT");
        Log.d("userId", userId);

        userManager.getCoursesByIdUser(Long.parseLong(userId), new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                Log.w(" => ",new Gson().toJson(response));
                if (response.code() == 200) {
                    Log.d("status", "200");

                    for(int i=0; i<response.body().size();i++){
                        textStrings.add(response.body().get(0).getTitle().toString());
                    }
                    RecyclerView coursesList = (RecyclerView) findViewById(R.id.coursesList);
                    ListAdapter adpater = new ListAdapter(textStrings, CoursesActivity.this);
                    coursesList.setAdapter(adpater);
                    coursesList.setLayoutManager(new LinearLayoutManager(CoursesActivity.this));

                } else {
                    Log.d("status", "Failed");
                }
            }
            @Override
            public void onFailure(Call<List<Courses>> call, Throwable t) {
                Log.d("eroos",t.getMessage() );
                Toast.makeText(CoursesActivity.this,
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

    }
}
