package quran.gnd.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RamadanTimings extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<DateTimes> infoList;
    private RecyclerAdapter recyclerAdapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramadan_timings);
        recyclerView = findViewById(R.id.loaderView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        infoList = new ArrayList<>();
        clearAll();


        GetDataFromFirebase();
    }
    private void GetDataFromFirebase() {
        Query query = databaseReference.child("times");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DateTimes dt = new DateTimes();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    String gregorianString = dataSnapshot.child("a").getValue().toString();
                    LocalDate gregorianDate = LocalDate.parse(gregorianString, dateFormatter);
                    HijrahDate islamicDate = HijrahDate.from(gregorianDate);
                    String db_date = dataSnapshot.child("a").getValue().toString();
                    dt.setDate(db_date+" | "+islamicDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
                    dt.setStart(dataSnapshot.child("b").getValue().toString());
                    dt.setEnd(dataSnapshot.child("c").getValue().toString());
                    infoList.add(dt);
                }
                recyclerAdapter = new RecyclerAdapter(getApplicationContext(),infoList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void clearAll(){
        if(infoList != null){
            infoList.clear();
            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        infoList = new ArrayList<>();
    }
}