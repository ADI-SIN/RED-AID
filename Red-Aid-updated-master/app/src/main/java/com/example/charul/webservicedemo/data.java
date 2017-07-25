package com.example.charul.webservicedemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ActivityNotFoundException;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class data extends AppCompatActivity {

    private static final String TAG = ".data";
    private TextView textview, head1;
    private String message1 = "Your Blood is Needed";
    private String phoneNo;
    private String s,blood;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        textview = (TextView)findViewById(R.id.tv) ;
        head1 = (TextView)findViewById(R.id.head) ;
        textview.setMovementMethod(new ScrollingMovementMethod());

        Bundle bundle=getIntent().getExtras();
        s = bundle.getString("string");
        phoneNo = bundle.getString("number");
        blood=bundle.getString("blood");
        head1.setText("DONORS WITH BLOOD GROUP "+blood+" ARE:\n");

        Button details = (Button)findViewById(R.id.button1) ;
        Button contact = (Button)findViewById(R.id.button5);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* ActivityCompat.requestPermissions(data.this,new String[]{android.Manifest.permission.SEND_SMS},1);

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phoneNo, null, message1, null, null);*/
                Intent cont=new Intent(data.this,sms.class);
                startActivity(cont);


            }
        });
       details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview.append(s);
            }
        });



    }

 /*   private void sendSMS(String phone, String message)
    {
       // Log.v("phoneNumber",phoneNumber);
       // Log.v("message : ",message1);
        //PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, ignore.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, message, null, null);
    }*/


}
