package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSubjectForm extends AppCompatActivity {

    Button save;
    EditText newSubject;
    public static UserManager userManager;
    long subject_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_subject_form);
        userManager = userManager.getInstance();
        newSubject = findViewById(R.id.addSubject);
        Intent i = getIntent();
        final String course_id = i.getStringExtra("course_id");

        if (i.getStringExtra("id_subject") != null)
        {
            subject_id = Long.parseLong(i.getStringExtra("id_subject"));
            newSubject = findViewById(R.id.addSubject);
            newSubject.setText(i.getStringExtra("name"));
        }


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int with = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(with*.6),(int)(height*.3));

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
                final String userId = shared.getString("id","DEFAULT");
                Log.d("userId", userId);

                if(subject_id==0) {

                    userManager.createSubject(Long.parseLong(course_id), newSubject.getText().toString(), new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.w(" => ", new Gson().toJson(response));
                            if (response.code() == 200) {
                                Log.d("status", "200");


                                Intent i = new Intent(CreateSubjectForm.this, DisciplinesActivity.class);
                                startActivity(i);

                            } else {
                                Log.d("status", "Failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(CreateSubjectForm.this,
                                    "Error is " + t.getMessage()
                                    , Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    userManager.editSubject(subject_id, newSubject.getText().toString(), new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.w(" => ", new Gson().toJson(response));
                            if (response.code() == 200) {
                                Log.d("status", "200");


                                Intent i = new Intent(CreateSubjectForm.this, DisciplinesActivity.class);
                                startActivity(i);

                            } else {
                                Log.d("status", "Failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(CreateSubjectForm.this,
                                    "Error is " + t.getMessage()
                                    , Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}