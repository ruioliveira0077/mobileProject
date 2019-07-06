package com.example.sheduleorganizer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class GenericForm extends Activity {

    Button save;
    EditText newCourse;
    public static UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_form);
        userManager = userManager.getInstance();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int with = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(with*.6),(int)(height*.3));

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newCourse = findViewById(R.id.addCourse);

                SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
                final String userId = shared.getString("id","DEFAULT");
                Log.d("userId", userId);

                userManager.createCourse(Long.parseLong(userId),newCourse.getText().toString(), new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.w(" => ",new Gson().toJson(response));
                        if (response.code() == 200) {
                            Log.d("status", "200");


                            Intent i = new Intent(GenericForm.this, CoursesActivity.class);
                            startActivity(i);

                        } else {
                            Log.d("status", "Failed");
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(GenericForm.this,
                                "Error is " + t.getMessage()
                                , Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
}
