package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ticketbooking.databinding.ActivityAdminLoginBinding;


public class AdminLoginActivity extends AppCompatActivity {
    ActivityAdminLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginAdmin.setOnClickListener(view -> {
            if (binding.edUsername.getText().toString().equals("rokesh") && binding.edPassword.getText().toString().equals("rokesh03")) {
                Intent intent = new Intent(getApplicationContext(), adminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
//                startActivity(new Intent(this,adminActivity.class));
                else
                Toast.makeText(this, "Please Enter valid username/password", Toast.LENGTH_SHORT).show();

        });

    }
}