package quran.gnd.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
public class ZakatCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat_calculator);
        EditText cash = findViewById(R.id.handCash);
        EditText bank = findViewById(R.id.bankCash);
        EditText gold = findViewById(R.id.glodValue);
        EditText silver = findViewById(R.id.silverValue);
        EditText loan = findViewById(R.id.loanValue);
        Button calc = findViewById(R.id.calculate);
        TextView results = findViewById(R.id.result);
        TextView bankDetailsText = findViewById(R.id.bankDetailsText);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("bank_details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bankDetailsText.setText(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        results.setText("");
        calc.setOnClickListener(view -> {
            double cash_val,bank_val,gold_val,silver_val,loan_val;
            if(cash.getText().toString().length()==0){
                cash_val = 0;
            }else{
                cash_val = Double.parseDouble(cash.getText().toString());
            }
            if(bank.getText().toString().length()==0){
                bank_val = 0;
            }else{
                bank_val = Double.parseDouble(bank.getText().toString());
            }
            if(gold.getText().toString().length()==0){
                gold_val = 0;
            }else{
                gold_val = Double.parseDouble(gold.getText().toString());
            }
            if(loan.getText().toString().length()==0){
                loan_val = 0;
            }else{
                loan_val = Double.parseDouble(loan.getText().toString());
            }
            if(silver.getText().toString().length()==0){
                silver_val = 0;
            }else{
                silver_val = Double.parseDouble(silver.getText().toString());
            }
            double totalAmount = (cash_val + bank_val + gold_val + silver_val)-loan_val;
            double calculatedZakat = totalAmount * 0.025;
            NumberFormat formatter = new DecimalFormat("#,##,###");
            String formattedNumber = formatter.format(calculatedZakat);
            results.setBackgroundColor(Color.parseColor("#ffe6b8"));
            if (totalAmount >= 80933) {
                results.setText("Payable Zakat: "+formattedNumber);
            } else {
                results.setText("Zakat is not applicable at your finances.");
            }

        });
    }
}