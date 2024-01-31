package com.example.ticketbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ticketbooking.databinding.ActivityTicketBookingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;


public class TicketBookingActivity extends AppCompatActivity {

    ActivityTicketBookingBinding binding;
    String source,destination,seatNumber,date,age,amount,uid,offer;
    String[] sourceArray;
    String[] destinationArray;
    DatabaseReference reference;
    Boolean isOffer=false;
    int x;
    final Calendar myCalendar= Calendar.getInstance();

    String[] imgs={"https://img.traveltriangle.com/blog/wp-content/uploads/2017/07/Kodaikanal1.jpg",
            "https://assets.traveltriangle.com/blog/wp-content/uploads/2017/07/Pondicherry.jpg",
            "https://st2.depositphotos.com/50318360/46658/i/1600/depositphotos_466587460-stock-photo-shore-temple-built-pallavas-unesco.jpg"
            ,"https://i.pinimg.com/originals/9e/59/c0/9e59c014e43e391e6c21612a99396863.jpg",
            "https://img.veenaworld.com/wp-content/uploads/2020/11/9-Hill-Stations-to-Visit-in-Tamil-Nadu-scaled.jpg"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sourceArray = getResources().getStringArray(R.array.source);
        destinationArray = getResources().getStringArray(R.array.destination);

        spineradopter();
        binding.confirmTicket.setOnClickListener(view -> {
            if(source.equals(destination))
                Toast.makeText(this, "please change Source and destination", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(binding.seatNum.getText().toString()) || TextUtils.isEmpty(binding.age.getText().toString()))
                Toast.makeText(this, "please Fill all fields", Toast.LENGTH_SHORT).show();
            else
                showDialog();
        });

        spinerlistener();
        getRandomnumber();
        binding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    isOffer=isChecked;

                }
        );

        binding.date.setOnClickListener(view ->{

            datepick();
        });

        binding.logout.setOnClickListener(view -> {
            FirebaseAuth mAuth= FirebaseAuth.getInstance();
            FirebaseUser user =  mAuth.getCurrentUser();
            if (user != null){
                mAuth.signOut();
                Toast.makeText(this,  " Sign out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
            }else{
                Toast.makeText(this, "You aren't login Yet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void spinerlistener(){
        binding.source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                source=sourceArray[position];
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        binding.destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                destination=destinationArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }



    private  void spineradopter(){
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, sourceArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        binding.source.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item,  getResources().getStringArray(R.array.destination));
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view

        binding.destination.setAdapter(spinnerArrayAdapter1);

    }


    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.payment_dialogbox);

        TextView btn = (TextView) dialog.findViewById(R.id.start_payment);
        TextView ticketPrice = (TextView) dialog.findViewById(R.id.ticket_price);
        TextView offerPrice = (TextView) dialog.findViewById(R.id.offer_price);
        ImageView img =  dialog.findViewById(R.id.recom_img);
        getRandomnumber();
        ticketPrice.setText("Ticket Price: rs" + amount);

        if(isOffer) {
            offerPrice.setText("Offer Price: rs" + offer);
            amount=offer;
        }else
        {
            offerPrice.setVisibility(View.GONE);
        }


        Random r = new Random();
        int myRandString = r.nextInt(imgs.length );

        Glide
                .with(this)
                .load(imgs[myRandString])
                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
                .into(img);



        btn.setOnClickListener(v -> {
           /*Toast.makeText(TicketBookingActivity.this, " Ticket", Toast.LENGTH_LONG).show();
            TicketBookingModel model=new TicketBookingModel(uid,source,destination,binding.seatNum.getText().toString()
                    ,binding.date.getText().toString(),binding.age.getText().toString(),amount);

            FirebaseDatabase.getInstance().getReference("ticket").child().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"not ok",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            */

            reference = FirebaseDatabase.getInstance("https://bus-reservation-d9e3e-default-rtdb.firebaseio.com/")
                    .getReference().child("ticket").push();

//        reference = FirebaseDatabase.getInstance().getReference("Users").child("uid").child("ticket");

            TicketBookingModel model=new TicketBookingModel(uid,source,destination,binding.seatNum.getText().toString()
                    ,binding.date.getText().toString(),binding.age.getText().toString(),amount);

            Log.e("done", "onComplete: ");

            reference.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(TicketBookingActivity.this, "You have Booked Ticket", Toast.LENGTH_LONG).show();
//                    finish();
                        Log.e("tag", "done" );
                    }
                }
            });
          //  storeDate();
            dialog.dismiss();
        });


        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }

    private void getRandomnumber(){
        Random random = new Random();

        x = random.nextInt(900) + 200;
        amount=Integer.toString(x);
        offer=Integer.toString(x-50);
    }

    private void datepick(){
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };
        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TicketBookingActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        binding.date.setText(dateFormat.format(myCalendar.getTime()));
    }

}