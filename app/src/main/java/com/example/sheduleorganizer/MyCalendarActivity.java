package com.example.sheduleorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.sheduleorganizer.MainActivity.EXTRA_MESSAGE;

public class MyCalendarActivity extends AppCompatActivity {


    private Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);

        menu = findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MyCalendarActivity.this, Menu.class);
                startActivity(i);
            }
        });
    }
}
