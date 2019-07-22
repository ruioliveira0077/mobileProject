package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sheduleorganizer.MainActivity.EXTRA_MESSAGE;

public class Login extends AppCompatActivity {

    private SharedPreferences loginPreferences;
    private Button loginButton;
    private UserManager userManager;
    private TextView email, password, invalidLogin, linkSignup;
    private SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userManager = userManager.getInstance();

        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        String userId = shared.getString("id","DEFAULT");
        Log.d("userId", userId);

        if(userId!="DEFAULT"){
            Intent i = new Intent(Login.this, Menu.class);
            i.putExtra(EXTRA_MESSAGE, "0");
            startActivity(i);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        invalidLogin = findViewById(R.id.invalidLogin);
        linkSignup = findViewById(R.id.link_signup);


        // Google login
        googleSignInButton = findViewById(R.id.signInButton);

        googleSignInButton.setOnClickListener((view) -> {googleSignIn();});

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


        linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();

                userManager.login(emailTxt, passwordTxt, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.code() == 200) {
                            loginPreferences = getSharedPreferences("info", MODE_PRIVATE);
                            SharedPreferences.Editor editor = loginPreferences.edit();
                            editor.putString("id","1");
                            editor.putString("email", emailTxt);
                            editor.commit();

                            Intent i = new Intent(Login.this, Menu.class);
                            i.putExtra(EXTRA_MESSAGE, "1");
                            startActivity(i);
                        }
                        else {
                            invalidLogin.setVisibility(View.VISIBLE);
                            invalidLogin.setText("Invalid credentials. Try again...");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Login.this,
                                "Error is " + t.getMessage()
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent i = new Intent(Login.this, Menu.class);
            i.putExtra(EXTRA_MESSAGE, "1"); //TODO
            startActivity(i);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GOOGLE", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(Login.this, "Failed Google Login", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            Intent i = new Intent(Login.this, Menu.class);
            i.putExtra(EXTRA_MESSAGE, "1"); //TODO
            startActivity(i);
        }
        super.onStart();
    }
}