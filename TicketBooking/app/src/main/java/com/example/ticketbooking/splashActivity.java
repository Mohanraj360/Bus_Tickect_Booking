package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
//        Log.e("TAG", fUser.getUid().toString() );

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (fUser != null) {
                    change(TicketBookingActivity.class);

                } else {
                    change(LoginActivity.class);
                }

            }
        }, 3000);
//        change(SampleActivity.class);

    }

    private void change(Class context) {

        Intent intent = new Intent(getApplicationContext(), context);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}