package com.example.moneymanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class WeekSpendingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView totalWeekAmount;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private String onlineUserId = "";
    private DatabaseReference expensesRef;

    private WeekSpendingAdapter weekSpendingAdapter;
    private List<Data> myDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_spending);

        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle("Week's Expenses ");
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#F5BD1F"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);


        totalWeekAmount = findViewById(R.id.totalWeekAmountTextView);
        progressBar = findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        expensesRef = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);


        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //ColorDrawable colorDrawable1=new ColorDrawable(Color.parseColor("#668b008b"));
        //recyclerView.setBackground(colorDrawable1);

        myDataList = new ArrayList<>();
        weekSpendingAdapter = new WeekSpendingAdapter(WeekSpendingActivity.this, myDataList);
        recyclerView.setAdapter(weekSpendingAdapter);

        readWeekItems();

    }

    private void readWeekItems() {

        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("week").equalTo(weeks.getWeeks());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myDataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Data data = dataSnapshot.getValue(Data.class);
                    myDataList.add(data);
                }

                weekSpendingAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                int totalAmount = 0;
                for (DataSnapshot ds: snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>)ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;

                    totalWeekAmount.setText("This week's total expenses: Rs. "+totalAmount);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void readItems() {

        //DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //Calendar cal = Calendar.getInstance();
        //String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("week").equalTo(weeks.getWeeks());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myDataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Data data = dataSnapshot.getValue(Data.class);
                    myDataList.add(data);
                }

                weekSpendingAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                int totalAmount = 0;
                for (DataSnapshot ds: snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>)ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;

                    totalWeekAmount.setText("This week's total expenses: Rs. "+totalAmount);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}