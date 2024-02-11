package quran.gnd.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    TextView profName,profNumber,points,ref_num,logOut,copier,total_balance_main;
    String referral,amount_available,points_available,req_point,new_amount,key;
    Button converter,topup;
    EditText pointToConvert,recharge_amount,phone_number;
    Spinner operators;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        profName = findViewById(R.id.profName);
        profNumber = findViewById(R.id.profNumber);
        points = findViewById(R.id.points);
        ref_num = findViewById(R.id.ref_num);
        logOut = findViewById(R.id.logOut);
        copier = findViewById(R.id.copier);
        converter = findViewById(R.id.point_to_balance_convert);
        pointToConvert = findViewById(R.id.pointToConvert);
        total_balance_main = findViewById(R.id.total_balance_main);
        topup = findViewById(R.id.recharge);
        recharge_amount = findViewById(R.id.recharge_amount);
        operators = findViewById(R.id.operator);
        phone_number = findViewById(R.id.mobile_number);
        String[] items = new String[]{"Airtel", "Vi", "BSNL", "Jio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        operators.setAdapter(adapter);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogOut.class);
                startActivity(intent);
                finish();
            }
        });


        if (user != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

            reference.orderByChild("b").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot datas: dataSnapshot.getChildren()){
                        profName.setText(datas.child("a").getValue().toString());
                        profNumber.setText(datas.child("c").getValue().toString());
                        points.setText(datas.child("g").getValue().toString());
                        ref_num.setText(datas.child("d").getValue().toString());
                        referral = datas.child("f").getValue().toString();
                        points_available = datas.child("g").getValue().toString();
                        amount_available = datas.child("e").getValue().toString();
                        total_balance_main.setText(datas.child("e").getValue().toString());
                        key = datas.getKey();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            topup.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    String op = operators.getSelectedItem().toString();String operator="";
                    if(op == "Airtel"){
                        operator = "A";
                    } else if (op == "Vi") {
                        operator = "V";
                    } else if (op == "BSNL") {
                        operator = "BT";
                    } else if (op == "Jio") {
                        operator = "RC";
                    }
                    String recharge_amount_input = recharge_amount.getText().toString();
                    String phone_num_to_recharge = phone_number.getText().toString();
                    if(TextUtils.isEmpty(phone_num_to_recharge)){
                        Toast.makeText(Profile.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(recharge_amount_input)){
                        Toast.makeText(Profile.this, "Please Enter Recharge Amount", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int recharge = Integer.parseInt(recharge_amount_input);
                    int avail_amount = Integer.parseInt(amount_available);
                    if(avail_amount - recharge>=0){
                        RequestQueue queue = Volley.newRequestQueue(Profile.this);
                        String url = "https://business.a1topup.com/recharge/api?username=501428&pwd=kmvr35fu&circlecode=5&operatorcode="+operator+"&number="+phone_num_to_recharge+"&amount="+recharge_amount_input+"&orderid=485668&format=json";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.contains("\"status\":\"Failure\"")){
                                            Toast.makeText(Profile.this,"Topup Failed ", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Profile.this,"Topup Success", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(stringRequest);
                    }else{
                        Toast.makeText(Profile.this,"Topup Failed! You do not have enough balance.", Toast.LENGTH_SHORT).show();
                    }



                }
            });
            converter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    req_point = String.valueOf(pointToConvert.getText());
                    if(TextUtils.isEmpty(req_point)){
                        Toast.makeText(Profile.this, "Please Enter Point Value (Minimum 100)", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try{
                        int amount_int = Integer.parseInt(req_point);
                        int amount_available_int = Integer.parseInt(points_available);
                        int remain_points,remain_amount;
                        if(amount_available_int - amount_int>=0){
                            remain_points = Integer.parseInt(points_available)-Integer.parseInt(req_point);
                            remain_amount = Integer.parseInt(amount_available)+(Integer.parseInt(req_point)/10);
                            reference.child(key).child("g").setValue(String.valueOf(remain_points));
                            reference.child(key).child("e").setValue(String.valueOf(remain_amount));
                            Toast.makeText(Profile.this, "Point convert successful", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        }else{
                            Toast.makeText(Profile.this, "Insufficient point", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }
            });

        }else{
            profName.setText("Login");
        }

        copier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ID", referral);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Profile.this,"ID Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }
}