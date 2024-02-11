package quran.gnd.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SignUp extends AppCompatActivity {
    EditText name,email,phone,password,refered_by_inp;
    Button register;
    FirebaseAuth mAuth;
    TextView log;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String referral,refered_by;
    Users userClass;
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        referral = getRandomNumberString();

        name = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        refered_by_inp = findViewById(R.id.referral);

        register = findViewById(R.id.register);
        log = findViewById(R.id.loginShift);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uEmail,uPass,uName,uPhone,uRef;
                uName = name.getText().toString();
                uEmail = email.getText().toString();
                uPhone = phone.getText().toString();
                uPass = password.getText().toString();
                uRef = refered_by_inp.getText().toString();
                if(TextUtils.isEmpty(uName)){
                    Toast.makeText(SignUp.this, "Enter Your Full Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(uEmail)){
                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(uPhone)){
                    Toast.makeText(SignUp.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(uPass)){
                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(uRef)){
                    refered_by = "NONE";
                }else{
                    refered_by = uRef;
                }

                userClass = new Users(uName,uEmail,uPhone,referral.toString(),"0",uRef,"0");
                mAuth.createUserWithEmailAndPassword(uEmail, uPass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = String.valueOf(user.getUid());
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            //getValues();
                                            databaseReference.child(uid).setValue(userClass);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this,Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                if(refered_by != "NONE") {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.orderByChild("refferal").equalTo(refered_by).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot datas: dataSnapshot.getChildren()){
                                String name = datas.getKey();
                                int amount = Integer.parseInt(datas.child("amount").getValue().toString())+10;
                                reference.child(name).child("amount").setValue(String.valueOf(amount));
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }else {

                }
            }
        });
    }
    private void getValues(){
        userClass.setName(name.getText().toString());
        userClass.setEmail(email.getText().toString());
        userClass.setPhone(phone.getText().toString());
        userClass.setRefferal(referral);
        userClass.setRefered_by(refered_by);
        userClass.setAmount("0");
        userClass.setPoint("0");
    }
    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}