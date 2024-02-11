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

import java.util.ArrayList;

public class SocialPosts extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<PostClass> infoList;
    private RecyclerAdapterPosts recyclerAdapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.socialPosts);
        recyclerView.setLayoutManager(layoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        infoList = new ArrayList<>();
        clearAll();
        GetDataFromFirebase();
    }
    private void GetDataFromFirebase() {
        Query query = databaseReference.child("social_posts");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    PostClass dt = new PostClass();
                    dt.setName(dataSnapshot.child("a").getValue().toString());
                    dt.setDated(dataSnapshot.child("b").getValue().toString());
                    dt.setDetails(dataSnapshot.child("d").getValue().toString());
                    dt.setImage(dataSnapshot.child("c").getValue().toString());
                    infoList.add(dt);
                }
                recyclerAdapter = new RecyclerAdapterPosts(getApplicationContext(),infoList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
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