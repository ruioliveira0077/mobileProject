package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sheduleorganizer.MainActivity.EXTRA_MESSAGE;

public class DayGrid extends AppCompatActivity {

    private ArrayList<Classes> listTime = new ArrayList<>();
    private static UserManager userManager;
    Date date = null;
    TextView datePicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_grid);

        userManager = userManager.getInstance();
        SharedPreferences shared = getSharedPreferences("info", MODE_PRIVATE);
        final String userId = shared.getString("id", "DEFAULT");

        Intent i = getIntent();
        String dateSelected = i.getStringExtra("date");

        datePicked = findViewById(R.id.dateOfToday);
        datePicked.setText(dateSelected);
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        //Recycler View Stuff/////////////////////////////////////////////////////////////////////////////
        userManager.getClassesByDate(dateSelected, Long.parseLong(userId),  new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                Log.w(" => ",new Gson().toJson(response));
                if (response.code() == 200) {
                    Log.d("status", "200");

                    for(int i=0; i<response.body().size();i++){
                        listTime.add(response.body().get(i));
                    }

                    // Recycler view Call
                    RecyclerView coursesList = (RecyclerView) findViewById(R.id.timeTable);
                    ListAdapterTimeTable adpater = new ListAdapterTimeTable(listTime, DayGrid.this);
                    coursesList.setAdapter(adpater);
                    coursesList.setLayoutManager(new LinearLayoutManager(DayGrid.this));

                } else {
                    Log.d("status", "Failed");
                }
            }
            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                Toast.makeText(DayGrid.this,
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////Navigation Bar ///////////////////////////////////////////////////////////////////
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.signOut:
                        SharedPreferences loginPreferences = getSharedPreferences("info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginPreferences.edit();
                        editor.putString("id",null);
                        editor.putString("username",null);
                        editor.commit();

                        Intent i = new Intent(DayGrid.this, Login.class);
                        i.putExtra(EXTRA_MESSAGE, "1");
                        startActivity(i);
                        return true;
                    case R.id.menu:
                        Intent is = new Intent(DayGrid.this, Menu.class);
                        startActivity(is);
                        return true;
                }
                return false;
            }
        });



    }
}
