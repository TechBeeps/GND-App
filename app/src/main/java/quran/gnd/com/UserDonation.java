package quran.gnd.com;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.PaymentApp;

public class UserDonation extends AppCompatActivity{
    Button payNow;
    private EasyUpiPayment easyUpiPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_donation);
        payNow = findViewById(R.id.payNow);
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDonation.this,PayNow.class);
                startActivity(intent);
                try{
                    //makePayment("10.00","gndeewana@ybl","GNDeewanaTrust","From Test","123456789");
                }catch (Exception e){
                    Log.e("PAYMENT",String.valueOf(e));
                }

            }
        });
    }
    private void makePayment(String amount, String upi, String name, String desc, String transactionId) {
        //PaymentApp paymentApp;
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(UserDonation.this)
                .with(PaymentApp.PHONE_PE)
                // on below line we are adding upi id.
                .setPayeeVpa(upi)
                // on below line we are setting name to which we are making payment.
                .setPayeeName(name)
                // on below line we are passing transaction id.
                .setTransactionId(transactionId)
                // on below line we are passing transaction ref id.
                .setTransactionRefId(transactionId)
                .setPayeeMerchantCode(name)
                // on below line we are adding description to payment.
                .setDescription(desc)
                // on below line we are passing amount which is being paid.
                .setAmount(amount);

        try {
            // Build instance
            easyUpiPayment = builder.build();

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener((PaymentStatusListener) UserDonation.this);

            // Start payment / transaction
            easyUpiPayment.startPayment();
        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(UserDonation.this,String.valueOf(exception),Toast.LENGTH_SHORT).show();
        }
    }
}