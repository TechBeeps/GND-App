package quran.gnd.com;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public String sharedLat,sharedLong,username;
    TextView home_date,upcomingPrayer,profileName;
    private final String PDF_LINK = "https://www.hajum.techbeeps.agency/quran/Quran.pdf";
    private final String LOCATE_PDF = "quran.pdf";
    private AdView adview;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ImageSlider slider;
    ImageButton profile;
    CardView prayertimes,readPDF,kalmas,qiblaDirection,zakatCalculator,ramadanTimings,tasbihCounter,hajj_umrah,daily_dua,donation,islCalendar,socialBlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        askLocationPermission();
        profile = findViewById(R.id.profile);
        profileName = findViewById(R.id.profileName);
        slider = findViewById(R.id.image_slider_main);
        final List<SlideModel> imagesList= new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("slider").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    imagesList.add(new SlideModel(snap.child("b").getValue().toString(), ScaleTypes.CENTER_CROP));
                }
                slider.setImageList(imagesList,ScaleTypes.CENTER_CROP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (user != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

            reference.orderByChild("b").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot datas: dataSnapshot.getChildren()){
                        String nameProf=datas.child("a").getValue().toString();
                        profileName.setText(nameProf);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else{
            profileName.setText("Login");
        }
        settings = this.getSharedPreferences("locs", MODE_PRIVATE);
        sharedLat = settings.getString("lats","");
        sharedLong = settings.getString("longs", "");
        String def_lat = "28.6112856";
        String def_long = "77.2277946";
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String pos = "";int flag = 0;
        if(sharedLat.isEmpty() && sharedLong.isEmpty()){
            sharedLat = def_lat;
            sharedLong = def_long;
            editor = settings.edit();
            editor.putString("lats", sharedLat);
            editor.putString("longs", sharedLong);
            editor.apply();
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(user!=null){
                    intent = new Intent(MainActivity.this,Profile.class);
                }else{
                    intent = new Intent(MainActivity.this,Login.class);
                }
                startActivity(intent);
            }
        });
        adview = findViewById(R.id.adView);
        upcomingPrayer = findViewById(R.id.upcomingPrayer);//Next event
        prayertimes = findViewById(R.id.prayerTimes);
        kalmas = findViewById(R.id.kalmas);
        qiblaDirection = findViewById(R.id.qiblaDirection);
        ramadanTimings = findViewById(R.id.ramadanTimings);
        zakatCalculator = findViewById(R.id.zakatCalculator);
        tasbihCounter = findViewById(R.id.tasbihCounter);
        hajj_umrah = findViewById(R.id.hajjUmrah);
        daily_dua = findViewById(R.id.dailyDua);
        donation = findViewById(R.id.donationView);
        readPDF = findViewById(R.id.readPDF);
        islCalendar = findViewById(R.id.islCalendar);
        socialBlog = findViewById(R.id.socialBlog);
        home_date = findViewById(R.id.home_date);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        home_date.setText(date);
        prayertimes.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,PrayerTimings.class);
            startActivity(intent);
        });
        kalmas.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,KalmaView.class);
            startActivity(intent);
        });
        qiblaDirection.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,QiblaDirection.class);
            startActivity(intent);
        });
        tasbihCounter.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,TasbihCounter.class);
            startActivity(intent);
        });
        zakatCalculator.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,ZakatCalculator.class);
            startActivity(intent);
        });
        ramadanTimings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,RamadanTimings.class);
            startActivity(intent);
        });
        hajj_umrah.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,HajjUmrah.class);
            startActivity(intent);
        });
        daily_dua.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,DailyDua.class);
            startActivity(intent);
        });
        donation.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,UserDonation.class);
            startActivity(intent);
        });
        readPDF.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,QuranView.class);
            startActivity(intent);
        });
        islCalendar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,IslamicCalendar.class);
            startActivity(intent);
        });
        socialBlog.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,SocialPosts.class);
            startActivity(intent);
        });
        PrayTime prayers = new PrayTime();
        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Karachi);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = {0, 0, 0, 0, 0, 0, 0};
        prayers.tune(offsets);
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        double offsetFromUtc = tz.getOffset(now.getTime()) / 3600000.0;
        double timezone = offsetFromUtc;
        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                Double.parseDouble(sharedLat), Double.parseDouble(sharedLong), timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();
        for(int i=0;i<prayerTimes.size();i++){
            if( checkTime(getDiff(prayerTimes.get(i)),i) =="-1"){
                flag = 1;
            }else{
                flag = 0;
                pos = checkTime(getDiff(prayerTimes.get(i)),i);
                break;
            }
        }
        if(flag == 1){
            upcomingPrayer.setVisibility(View.GONE);
        }else{
            int pos_int = Integer.parseInt(pos);

            String fullTime = getDiff(prayerTimes.get(pos_int));
            String[] parts = fullTime.split("-");
            int first = Integer.parseInt(String.valueOf(parts[0]));
            int second = Integer.parseInt(String.valueOf(parts[1]));

            int millisToGo = second*1000*60+first*1000*60*60;
            new CountDownTimer(millisToGo,1000) {

                @Override
                public void onTick(long millis) {
                    int minutes = (int) ((millis / (1000*60)) % 60);
                    int hours   = (int) ((millis / (1000*60*60)) % 24);
                    upcomingPrayer.setText(prayerNames.get(pos_int)+" in "+String.format("%02d h %02d m",hours,minutes));
                }

                @Override
                public void onFinish() {
                    upcomingPrayer.setVisibility(View.GONE);
                }
            }.start();


        }
    }
    private void getLastLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
            }
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    editor = settings.edit();
                    editor.putString("lats", String.valueOf(location.getLatitude()));
                    editor.putString("longs", String.valueOf(location.getLongitude()));
                    editor.apply();
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Error: ",e.getLocalizedMessage());
            }
        });
    }
    private void askLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {getLastLocation();} else {askLocationPermission();}
    }
    public String checkTime(String time, int position){
        String pos;
        if(time == "0"){
            pos = "-1";
        }else{
            pos = String.valueOf(position);
        }
        return pos;
    }
    public String getDiff(String prayTime){
        String diff = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        int days;
        int hours;
        int min;
        try {
            Date date1 = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date date2 = simpleDateFormat.parse(prayTime);
            long difference = date2.getTime() - date1.getTime();
            days = (int) (difference / (1000*60*60*24));
            hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            if(hours<=0 && min<=0){
                diff = "0";
            }else{
                if(hours<=0){
                    hours = 0;
                }
                if(min<=0){
                    min = 0;
                }
                diff =hours+"-"+min;

            }
        }catch (Exception e){

        }
        return diff;
    }
    private void downloadPdf(final String locate_pdf) {
        new AsyncTask<Void,Integer,Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids) {
                return downloadPdf();
            }
            private boolean downloadPdf(){
                try{
                    File file = getFileStreamPath(locate_pdf);
                    if(file.exists())
                        return true;

                    try{

                        FileOutputStream fileOutputStream = openFileOutput(locate_pdf, Context.MODE_PRIVATE);
                        URL u = new URL(PDF_LINK);
                        URLConnection conn = u.openConnection();
                        int contentLength = conn.getContentLength();
                        InputStream input = new BufferedInputStream(u.openStream());
                        byte data[] = new byte[contentLength];
                        long total = 0;
                        int count;
                        while((count = input.read(data)) !=-1){
                            total +=count;
                            fileOutputStream.write(data,0,count);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        input.close();
                        return true;
                    }catch(Exception e){}
                }catch (Exception e){}
                return false;
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if(aBoolean){readPDF.setVisibility(View.VISIBLE);}else{}
            }
        }.execute();
    }

    public void onStart() {
        super.onStart();


        if (user != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

            reference.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot datas: dataSnapshot.getChildren()){
                        String name=datas.child("name").getValue().toString();
                        profileName.setText(name);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else{
            profileName.setText("Login");
        }

    }


}