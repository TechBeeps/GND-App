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

public class KalmaView extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<KalmaCLass> infoList;
    private RecyclerAdapterKalma recyclerAdapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalma_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.duaView);
        recyclerView.setLayoutManager(layoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        infoList = new ArrayList<>();
        clearAll();
        GetDataFromFirebase();

       /*String[] ks = {
                "لآ اِلَهَ اِلّا اللّهُ مُحَمَّدٌ رَسُوُل اللّهِ",
                "اشْهَدُ انْ لّآ اِلهَ اِلَّا اللّهُ وَحْدَه لَا شَرِيْكَ لَه، وَ اَشْهَدُ اَنَّ مُحَمَّدً اعَبْدُهوَرَسُولُه",
                "سُبْحَان لِلّه وَ الْحَمْدُ   لِلّهِ وَ لآ اِلهَ اِلّا اللّهُ، وَ اللّهُ اَكْبَرُ وَلا حَوْلَ وَلاَ قُوَّة  ِلَّا بِاللّهِ الْعَلِىّ الْعَظِيْم",
                "لا الهَ اِلَّا اللّهُ وَحْدَهُ لا شَرِيْكَ لَهْ، لَهُ الْمُلْكُ وَ لَهُ الْحَمْدُ يُحْى وَ يُمِيْتُ وَ هُوَحَىُّ لَّا يَمُوْتُ اَبَدًا اَبَدًا طذُو الْجَلَالِ وَ الْاِكْرَامِ ط بِيَدِهِ الْخَيْرُ ط وَهُوَ عَلى كُلِّ شَئ ٍ قَدِيْرٌ ط",
                "اسْتَغْفِرُ اللّهَ رَبِّىْ مِنْ كُلِّ ذَنْبٍ اَذْنَبْتُه عَمَدًا اَوْ خَطَاً سِرًّا اَوْ عَلَانِيَةً وَاَتُوْبُ اِلَيْهِ مِنْ الذَّنْبِ الَّذِىْ اَعْلَمُ وَ مِنْ الذَّنْبِ الَّذِىْ لا اَعْلَمُ اِنَّكَ اَنْتَ عَلَّامُ الغُيُبِ وَ سَتَّارُ الْعُيُوْبِ و َغَفَّارُ الذُّنُوْبِ وَ لا حَوْلَ وَلا قُوَّةَ اِلَّا بِاللّهِ الْعَلِىِّ العَظِيْم",
                "اَللّٰهُمَّ اِنِّیْٓ اَعُوْذُ بِكَ مِنْ اَنْ اُشْرِكَ بِكَ شَيْئًا وَّاَنَآ اَعْلَمُ بِهٖ وَ اَسْتَغْفِرُكَ لِمَا لَآ اَعْلَمُ بِهٖ تُبْتُ عَنْهُ وَ تَبَرَّأْتُ مِنَ الْكُفْرِ وَ الشِّرْكِ وَ الْكِذْبِ وَ الْغِيْبَةِ وَ الْبِدْعَةِ وَ النَّمِيْمَةِ وَ الْفَوَاحِشِ وَ الْبُهْتَانِ وَ الْمَعَاصِىْ كُلِّهَا وَ اَسْلَمْتُ وَ اَقُوْلُ لَآ اِلٰهَ اِلَّا اللهُ مُحَمَّدٌ رَّسُوْلُ اللهِؕ"
        };*/

    }

    private void GetDataFromFirebase() {
        Query query = databaseReference.child("kalma").orderByChild("e");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    KalmaCLass dt = new KalmaCLass();
                    dt.setName(dataSnapshot.child("a").getValue().toString());
                    dt.setKalma(dataSnapshot.child("b").getValue().toString());
                    dt.setMeaning(dataSnapshot.child("c").getValue().toString());
                    infoList.add(dt);
                }
                recyclerAdapter = new RecyclerAdapterKalma(getApplicationContext(),infoList);
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