package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adminActivity extends AppCompatActivity {
   /*TicketBookingModel model=new TicketBookingModel(uid,source,destination,binding.seatNum.getText().toString()
                    ,binding.date.getText().toString(),binding.age.getText().toString(),amount);

            FirebaseDatabase.getInstance().getReference("ticket").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
//  private ProgressBar mProgressBar;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<TicketBookingModel> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mRecyclerView = findViewById(R.id.rvAdmin);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        model = new ArrayList<>();
          /**   mAdapter.setOnItemClickListener(this);

         */

        mDatabaseRef = FirebaseDatabase.getInstance("https://bus-reservation-d9e3e-default-rtdb.firebaseio.com/").getReference("ticket");

          mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                model.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TicketBookingModel upload = snapshot.getValue(TicketBookingModel.class);
//                    upload.setKey(teacherSnapshot.getKey());
                    model.add(upload);


                    Log.e("TAG8", String.valueOf(upload));
                }
                mAdapter = new RecyclerAdapter (adminActivity.this, model,1);
                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}