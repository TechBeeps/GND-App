package quran.gnd.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PostDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String dated = intent.getStringExtra("dated");
        String details = intent.getStringExtra("details");
        String image = intent.getStringExtra("image");

        ImageView imv = findViewById(R.id.postImg);
        TextView ttl = findViewById(R.id.nameFld);
        TextView dtd = findViewById(R.id.dateFld);
        TextView dtls = findViewById(R.id.detailsFld);
        Glide.with(this)
                .load(image)
                .into(imv);
        ttl.setText(title);
        dtd.setText(dated);
        dtls.setText(details);
    }
}