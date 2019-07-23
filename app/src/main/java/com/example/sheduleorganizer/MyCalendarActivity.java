package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;


import com.google.gson.Gson;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sheduleorganizer.MainActivity.EXTRA_MESSAGE;

public class MyCalendarActivity extends AppCompatActivity {

    private Button addClass;
    private String selectedDate;
    private Spinner dropdownType;
    private Adapter dropdownAdapterTypes;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> coursesList = new ArrayList<String>();
    private ArrayList<String> subjectsList = new ArrayList<String>();
    String dateParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        /// SPINERS//////////////////////////////////////////////////////////////////////////////////////////////

        typeList.add("Create Class");
        typeList.add("Contact Hours");

        dropdownType = findViewById(R.id.selectType);
        dropdownAdapterTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeList);
        dropdownType.setAdapter((ArrayAdapter)dropdownAdapterTypes);

        final Intent create = new Intent(MyCalendarActivity.this, CreateClass.class);

        dropdownType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0 ){
                    create.putExtra("type", "0");
                }
                else if(position ==1){
                    create.putExtra("type", "1");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addClass = findViewById(R.id.addClass);

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateParam != null)
                {
                    create.putExtra("date", dateParam);
                    startActivity(create);
                }
            }
        });


        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        addClass = findViewById(R.id.addClass);
        final CalendarView myCalendar = (CalendarView) findViewById(R.id.calendar);

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        selectedDate = sdf.format(new Date(myCalendar.getDate()));


        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                String currentDate = ""+dayOfMonth+"/"+(month+1)+"/"+year;

                    month = month +1;
                    String monthFix = ""+month;
                    String dayFix = ""+dayOfMonth;
                    if(month< 10){
                        monthFix="0"+month;
                    }

                    if(dayOfMonth< 10){
                        dayFix="0"+dayOfMonth;
                    }

                    dateParam = ""+year+"-"+monthFix+"-"+dayFix;
                if(currentDate.equals(selectedDate)){
                    Intent i = new Intent(MyCalendarActivity.this, DayGrid.class);
                    i.putExtra("date", dateParam);
                    startActivity(i);
                }
                selectedDate = currentDate;
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

                        Intent i = new Intent(MyCalendarActivity.this, Login.class);
                        i.putExtra(EXTRA_MESSAGE, "1");
                        startActivity(i);
                        return true;
                    case R.id.menu:
                        Intent is = new Intent(MyCalendarActivity.this, Menu.class);
                        startActivity(is);
                        return true;
                }
                return false;
            }
        });

    }
}
