package quran.gnd.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditPrayTime extends AppCompatActivity {
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private String format = "";String title ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pray_time);
        Intent intent = getIntent();
        title = intent.getStringExtra("name");
        settings = this.getSharedPreferences("timers", MODE_PRIVATE);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);
        Button save = findViewById(R.id.save);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                int modified = 0;
                if(hour>12){
                    modified = hour-12;
                }else{
                    modified = hour;
                }
                String t = String.valueOf(modified)+":"+String.valueOf(minute)+" "+showTime(hour, minute);
                editor = settings.edit();
                editor.putString(title, t);
                editor.apply();
                Intent intent = new Intent(EditPrayTime.this,PrayerTimings.class);
                startActivity(intent);
            }
        });
    }
    public String showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "am";
        } else if (hour == 12) {
            format = "pm";
        } else if (hour > 12) {
            hour -= 12;
            format = "pm";
        } else {
            format = "am";
        }
        return format;
    }
}