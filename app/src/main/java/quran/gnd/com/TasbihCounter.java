package quran.gnd.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class TasbihCounter extends AppCompatActivity {
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbih_counter);

        TextView box = findViewById(R.id.count);
        Button step = findViewById(R.id.step);
        Button reset = findViewById(R.id.reset);
        AtomicInteger current = new AtomicInteger();
        DecimalFormat formatter = new DecimalFormat("##,##,###");
        reset.setOnClickListener(view -> {
            count = 0;box.setText("0");
        });
        step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String counter = formatter.format(count += 1);
                box.setText(counter);
            }
        });
    }
}