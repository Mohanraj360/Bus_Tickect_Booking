package com.example.ticketbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ticketbooking.databinding.ActivityLoginBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
   String verificatnId ;
   ActivityLoginBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnLogin.setOnClickListener(view -> {
            if (binding.edPhonenum.getText().toString() != null && binding.edPhonenum.getText().toString().length() ==10){
                phonenumber(binding.edPhonenum.getText().toString());
                binding.progresbar.setVisibility(View.VISIBLE);

            }else
                Toast.makeText(this, "Please Enter Valid number", Toast.LENGTH_SHORT).show();
        });

        binding.verifyOtp.setOnClickListener(view -> {
            binding.progresbar.setVisibility(View.VISIBLE);

            verifyCode(binding.edPassword.getText().toString());
        });


        binding.admin.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,AdminLoginActivity.class));
        });





    }



    private  void phonenumber(String phoneNum){

//        String phoneNum = "+16505554567";
        String testVerificationCode = "123456";

// Whenever verification is triggered with the whitelisted number,
// provided it is not set for auto-retrieval, onCodeSent will be triggered.
         auth = FirebaseAuth.getInstance();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91"+ phoneNum)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // Save the verification id somewhere
                        // ...
                        binding.progresbar.setVisibility(View.GONE);

                        binding.verifyOtpLayout.setVisibility(View.VISIBLE);
                        binding.phonenumLayout.setVisibility(View.GONE);


                        Toast.makeText(LoginActivity.this, "done", Toast.LENGTH_SHORT).show();
                        verificatnId=verificationId;
                        // The corresponding whitelisted code above should be used to complete sign-in.
//                        MainActivity.this.enableUserManuallyInputCode();
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        String code = phoneAuthCredential.getSmsCode();

                        verifyCode(code);
                        // Sign in with the credential
                        // ...
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // ...
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatnId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, TicketBookingActivity.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                    } else {
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


//    private void verify(){
//        String phoneNum = "+919994113892";
//        String testVerificationCode = "123456";
//        FirebaseAuthSettings firebaseAuthSettings = auth.getFirebaseAuthSettings();
//
//// Configure faking the auto-retrieval with the whitelisted numbers.
//        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNum, binding.edPassword.getText().toString());
//
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
//                .setPhoneNumber(phoneNum)
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(LoginActivity.this)
//                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(PhoneAuthCredential credential) {
//
//
//                        startActivity(new Intent(LoginActivity.this,TicketBookingActivity.class));
//                        finish();
//                    }
//
//                    @Override
//                    public void onVerificationFailed(@NonNull FirebaseException e) {
//
//                    }
//
//                    // ...
//                })
//                .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
}