package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    /* SESSION MANAGEMENT */
    workySessionMgt session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* ACCESS A CLOUD FIRESTORE INSTANCE FROM YOUR ACTIVITY */
        FirebaseApp.initializeApp(this);


        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());

        Intent launchLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(launchLoginActivity);
        finish();
    }
}