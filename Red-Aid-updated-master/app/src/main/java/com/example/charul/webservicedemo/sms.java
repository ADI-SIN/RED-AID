package com.example.charul.webservicedemo;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class sms extends AppCompatActivity {

    Button sendBtn;
    Button call;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        call = (Button) findViewById(R.id.button7);
        txtphoneNo = (EditText) findViewById(R.id.editText);
        txtMessage = (EditText) findViewById(R.id.editText2);
        Intent intn = new Intent();
        final String phoneNo= intn.getStringExtra("contacts1");
       // txtphoneNo.setText(phone);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

              //  phoneNo = txtphoneNo.getText().toString().trim();
                message = "Message from Red Aid :- "+txtMessage.getText().toString().trim();

                sendSMS(phoneNo, message);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneNo));

                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });
    }

    private void sendSMS(String phoneNumber, String message)
    {
        ActivityCompat.requestPermissions(sms.this,new String[]{android.Manifest.permission.SEND_SMS},1);
        Log.v("phoneNumber",phoneNumber);
        Log.v("message : ",message);
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, ignore.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }
}
