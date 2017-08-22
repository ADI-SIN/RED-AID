package com.example.charul.webservicedemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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
    private LinearLayout mainLayout;
    private String message1 = "Your Blood is Needed";
    private String phoneNo;
    private String s,blood;
    private int count1,i;

  // private String listy[]=new String[10];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

      //  textview.setMovementMethod(new ScrollingMovementMethod());
        head1 = (TextView)findViewById(R.id.head) ;
        mainLayout = (LinearLayout) findViewById(R.id.activity_data);


        Bundle bundle=getIntent().getExtras();
        s = bundle.getString("string");
        phoneNo = bundle.getString("number");
        blood=bundle.getString("blood");
         count1=bundle.getInt("county");
        final String[] listy =bundle.getStringArray("lists");
        final String[] conty = bundle.getStringArray("contacts");

       head1.setText("DONORS WITH BLOOD GROUP "+blood+" ARE:\n");

        final Button details = (Button)findViewById(R.id.button1) ;
    //    Button contact = (Button)findViewById(R.id.button5);

     /*   contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent cont=new Intent(data.this,sms.class);
                startActivity(cont);
               // textview.setText(Integer.toString(count1)+listy[2]);


            }
        });*/
       details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(data.this,Integer.toString(count1),Toast.LENGTH_SHORT).show();
                for(i=0;i<count1;i++){
                    TextView textView = new TextView(data.this);
                    textView.setText(listy[i]+"\n\n");
                    final String thenum = conty[i];


                    LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(textViewLayoutParams);
                    mainLayout.addView(textView);
                    textView.setId(i);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           /* Intent cont=new Intent(data.this,sms.class);
                            cont.putExtra("contacts1",thenum);

                            startActivity(cont);*/

                                    try {
                                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                        callIntent.setData(Uri.parse("tel:" + thenum));

                                        startActivity(callIntent);
                                    } catch (ActivityNotFoundException activityException) {
                                        Log.e("Calling a Phone Number", "Call failed", activityException);
                                    }



                        }
                    });

                }
                //textview.setText(s);
            }
        });



    }


}
