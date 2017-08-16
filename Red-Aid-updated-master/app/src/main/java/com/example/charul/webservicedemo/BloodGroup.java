package com.example.charul.webservicedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by charul on 18/4/17.
 */
public class BloodGroup extends AppCompatActivity {
    public CheckBox A_pos ;
    public CheckBox A_neg ;
    public CheckBox B_pos ;
    public CheckBox B_neg;
    public CheckBox AB_pos;
    public CheckBox AB_neg;
    public CheckBox O_pos ;
    public CheckBox O_neg;

    private TextView infor;


    public Firebase mref;
    public String blood;
    public String f;
    public String num;
    public String n;

    public String s;

    private double lat1;
    private double lat2;
    private double long1;
    private double long2;
    private double dist;
    private String address;
    private ProgressDialog progressdialog;

    private String[] list = new String[10];
    private  String[] numbr = new String[10];






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_group);
        Button buttonDonate = (Button) findViewById(R.id.proceed);


        A_pos = (CheckBox) findViewById(R.id.A_positive);
        A_neg = (CheckBox) findViewById(R.id.A_negative);
        B_pos = (CheckBox) findViewById(R.id.B_positive);
        B_neg = (CheckBox) findViewById(R.id.B_negative);
        AB_pos = (CheckBox) findViewById(R.id.AB_positive);
        AB_neg = (CheckBox) findViewById(R.id.AB_negative);
        O_pos = (CheckBox) findViewById(R.id.O_positive);
        O_neg = (CheckBox) findViewById(R.id.O_negative);
        progressdialog=new ProgressDialog(this);
        infor = (TextView)findViewById(R.id.infor);



        // FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
        Firebase.setAndroidContext(this);
        // final String uid=u.getUid();

        Bundle bundle=getIntent().getExtras();
        lat1 = bundle.getDouble("lat");
        long1 = bundle.getDouble("long");
        // infor.setText(Double.toString(lat1)+Double.toString(long1));


        buttonDonate.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the donate category is clicked on.
            @Override
            public void onClick(View view) {
                bloodGroup();
                //  distance(lat1,long1,lat2,long2);


                //mref=new Firebase("https://red-aid.firebaseio.com/"+uid);


                mref = new Firebase("https://red-aid.firebaseio.com/");



                Query info= mref.orderByChild("blood").equalTo(blood);

                info.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int count=-1;

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            n = (String) postSnapshot.child("name").getValue();
                            num = (String) postSnapshot.child("number").getValue();

                            address = (String) postSnapshot.child("address").getValue();


                            lat2 = (Double) postSnapshot.child("lat").getValue();
                            long2 = (Double) postSnapshot.child("long").getValue();
                            Location locationA = new Location("point A");
                            locationA.setLatitude(lat1);
                            locationA.setLongitude(long1);
                            Location locationB = new Location("point B");
                            locationB.setLatitude(lat2);
                            locationB.setLongitude(long2);
                            dist = locationA.distanceTo(locationB) ;


                            if (dist < 10000) {
                                f = "Name: "+n+ "\nContact: "+num+"\nAddress: "+address;
                                count++;
                                list[count]=f;
                                numbr[count]=num;

                                /*infor.append(f);
                                 s=infor.getText().toString();*/
                            }


                        }
                        infor.setText(Integer.toString(count)+list[1]);


                       Intent a= new Intent(getApplicationContext(), data.class);
                        a.putExtra("string", s);
                        a.putExtra("number", num);
                        a.putExtra("blood",blood);
                        a.putExtra("county",count);
                        a.putExtra("lists",list);
                        a.putExtra("contacts",numbr);
                        if(address!=null){
                            finish();
                            //Toast.makeText(BloodGroup.this,Integer.toString(count),Toast.LENGTH_SHORT).show();
                            startActivity(a);

                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });


    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }



    public void bloodGroup() {

        if (A_pos.isChecked()){
            blood="A+";
        }
        else if(A_neg.isChecked()){
            blood="A-";
        }
        else if(B_pos.isChecked()){
            blood="B+";
        }
        else if(B_neg.isChecked()){
            blood="B-";
        }
        else if(AB_pos.isChecked()){
            blood="AB+";
        }
        else if(AB_neg.isChecked()){
            blood="AB-";
        }
        else if(O_pos.isChecked()){
            blood="O+";
        }
        else if(O_neg.isChecked()){
            blood="O-";
        }
    }


}