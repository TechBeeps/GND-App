package quran.gnd.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PrayerTimings extends AppCompatActivity {
    TextView fajr, sunrise, duhr, asar, magrib, isha, today, sunset, fajr_remaining, sunrise_remaining, duhr_remaining, asar_remaining, sunset_remaining, magrib_remaining, isha_remaining;
    Button edtFajr,edtsunrise,edtduhr,edtasar,edtsunset,edtmagrib,edtisha,resetTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_timings);

        SharedPreferences settings = this.getSharedPreferences("locs", MODE_PRIVATE);
        SharedPreferences timers = this.getSharedPreferences("timers", MODE_PRIVATE);

        double sharedLat = Double.parseDouble(settings.getString("lats", ""));
        double sharedLong = Double.parseDouble(settings.getString("longs", ""));


        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        double offsetFromUtc = tz.getOffset(now.getTime()) / 3600000.0;
        double timezone = offsetFromUtc;
        String [] typo = {"fajr","sunrise","duhr","asar","magrib","isha","sunset"};
        fajr = findViewById(R.id.fajr);
        sunrise = findViewById(R.id.sunrise);
        duhr = findViewById(R.id.duhr);
        asar = findViewById(R.id.asar);
        magrib = findViewById(R.id.magrib);
        isha = findViewById(R.id.isha);

        edtFajr = findViewById(R.id.edtFajr);
        edtsunrise = findViewById(R.id.edtsunrise);
        edtduhr = findViewById(R.id.edtduhr);
        edtasar = findViewById(R.id.edtasar);
        edtsunset = findViewById(R.id.edtsunset);
        edtmagrib = findViewById(R.id.edtmagrib);
        edtisha = findViewById(R.id.edtisha);
        resetTime = findViewById(R.id.resetTime);

        fajr_remaining = findViewById(R.id.fajr_remaining);
        sunrise_remaining = findViewById(R.id.sunrise_remaining);
        duhr_remaining = findViewById(R.id.duhr_remaining);
        asar_remaining = findViewById(R.id.asar_remaining);
        sunset_remaining = findViewById(R.id.sunset_remaining);
        magrib_remaining = findViewById(R.id.magrib_remaining);
        isha_remaining = findViewById(R.id.isha_remaining);

        sunset = findViewById(R.id.sunset);
        today = findViewById(R.id.today);
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(prayers.Time12);
        //prayers.setKarachi(1);
        prayers.setCalcMethod(prayers.Karachi);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                sharedLat, sharedLong, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        today.setText(date);
        String fajrTime = timers.getString("fajr", "");
        String sunriseTime = timers.getString("sunrise", "");
        String duhrTime = timers.getString("duhr", "");
        String asarTime = timers.getString("asar", "");
        String sunsetTime = timers.getString("sunset", "");
        String magribTime = timers.getString("magrib", "");
        String ishaTime = timers.getString("isha", "");

        if(fajrTime.isEmpty()){
            fajr.setText(prayerTimes.get(0));
            fajr_remaining.setText(getDiff(prayerTimes.get(0)));
        }else{
            fajr.setText(fajrTime);
            fajr_remaining.setText(getDiff(fajrTime));
        }
        if(sunriseTime.isEmpty()){
            sunrise.setText(prayerTimes.get(1));
            sunrise_remaining.setText(getDiff(prayerTimes.get(1)));
        }else{
            sunrise.setText(sunriseTime);
            sunrise_remaining.setText(getDiff(sunriseTime));
        }
        if(duhrTime.isEmpty()){
            duhr.setText(prayerTimes.get(2));
            duhr_remaining.setText(getDiff(prayerTimes.get(2)));
        }else{
            duhr.setText(duhrTime);
            duhr_remaining.setText(getDiff(duhrTime));
        }
        if(asarTime.isEmpty()){
            asar.setText(prayerTimes.get(3));
            asar_remaining.setText(getDiff(prayerTimes.get(3)));
        }else{
            asar.setText(asarTime);
            asar_remaining.setText(getDiff(asarTime));
        }
        if(sunsetTime.isEmpty()){
            sunset.setText(prayerTimes.get(4));
            sunset_remaining.setText(getDiff(prayerTimes.get(4)));
        }else{
            sunset.setText(sunsetTime);
            sunset_remaining.setText(getDiff(sunsetTime));
        }
        if(magribTime.isEmpty()){
            magrib.setText(prayerTimes.get(5));
            magrib_remaining.setText(getDiff(prayerTimes.get(5)));
        }else{
            magrib.setText(magribTime);
            magrib_remaining.setText(getDiff(magribTime));
        }
        if(ishaTime.isEmpty()){
            isha.setText(prayerTimes.get(6));
            isha_remaining.setText(getDiff(prayerTimes.get(6)));
        }else{
            isha.setText(ishaTime);
            isha_remaining.setText(getDiff(ishaTime));
        }
        edtFajr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrayerTimings.this,EditPrayTime.class);
                intent.putExtra("name","fajr");
                startActivity(intent);
            }
        });
        edtsunrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrayerTimings.this,EditPrayTime.class);
                intent.putExtra("name","sunrise");
                startActivity(intent);
            }
        });
        edtduhr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrayerTimings.this,EditPrayTime.class);
                intent.putExtra("name","duhr");
                startActivity(intent);
            }
        });
        edtasar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrayerTimings.this,EditPrayTime.class);
                intent.putExtra("name","asar");
                startActivity(intent);
            }
        });
        edtsunset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrayerTimings.this,EditPrayTime.class);
                intent.putExtra("name","sunset");
                startActivity(intent);
            }
        });
        edtmagrib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrayerTimings.this,EditPrayTime.class);
                intent.putExtra("name","magrib");
                startActivity(intent);
            }
        });
        edtisha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrayerTimings.this,EditPrayTime.class);
                intent.putExtra("name","isha");
                startActivity(intent);
            }
        });
        resetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = timers.edit();
                editor.putString("fajr", "");
                editor.putString("sunrise", "");
                editor.putString("duhr", "");
                editor.putString("asar", "");
                editor.putString("sunset", "");
                editor.putString("magrib", "");
                editor.putString("isha", "");
                editor.apply();
                finish();
                startActivity(getIntent());
            }
        });

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
            //hours = (hours < 0 ? -hours : hours);

            if(hours<=0 && min<=0){
                diff = "--:--";
            }else{
                if(hours<=0){
                    hours = 0;
                }
                if(min<=0){
                    min = 0;
                }
                diff =hours+"h "+min+"m to go";

            }

        }catch (Exception e){

        }
        return diff;
    }
}