package com.example.charul.webservicedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;
    private TextView nav_tv;
    private TextView nav_tv1;
    private Firebase mRef;
    private Firebase mRef1;
    private Button button;
    private NavigationView navigation;
    private FirebaseUser u;

    private String uid;
    private  String email;
    public  String string = "Donate";
    private  String navi ;
    private String number ;
    private String address ;

    private Toolbar mt;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile2);
        firebaseAuth = FirebaseAuth.getInstance();
       u= FirebaseAuth.getInstance().getCurrentUser();
        Firebase.setAndroidContext(this);


         uid= u.getUid();
     //   String uname=u.getDisplayName();
        email=u.getEmail();

        navigation = (NavigationView) findViewById(R.id.navigation_view);
       // nav_tv= (TextView)navigation.getHeaderView(0).findViewById(R.id.hn);
        nav_tv1=(TextView)navigation.getHeaderView(0).findViewById(R.id.lv);
        button=(Button)findViewById(R.id.button);

        mt=(Toolbar)findViewById(R.id.nav_action);


        navigation.setNavigationItemSelectedListener(this);

        //change = (Button)findViewById(R.id.btnchange);
        button.setOnClickListener(this);




        mRef= new Firebase("https://red-aid.firebaseio.com/"+uid);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Getting the data from snapshot
                  //  Person person = postSnapshot.getValue(Person.class);

                    //Adding it to a string
                   //  string = person.getName();
                    string = (String) postSnapshot.child("name").getValue();
                    number =  (String) postSnapshot.child("number").getValue();
                    address = (String) postSnapshot.child("address").getValue();

                    navi = email + "\n" + number + "\n" + address + "\n\n";


                  //  nav_tv.setText(string);
                  // nav_tv1.setText(navi);

                    String name = getIntent().getStringExtra("name");

                    nav_tv1.setText(email+"\n"+name);

                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });





        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);

        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



    @Override
    public void onClick(View view) {

                finish();
                Intent i=new Intent(ProfileActivity.this, MapsActivity.class);
                i.putExtra("var",string);
                startActivity(i);
            }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:
                finish();
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                return true;

            case R.id.nav_update:
                finish();
                startActivity(new Intent(ProfileActivity.this, details.class));
                return true;

            case R.id.nav_pass:
                finish();
                startActivity(new Intent(ProfileActivity.this, ChangePassword.class));
                return true;

            case R.id.nav_delac:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);

                //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("PASSWORD");

                // Setting Dialog Message
                alertDialog.setMessage("Enter Password");
                final EditText input = new EditText(ProfileActivity.this);
                alertDialog.setView(input);

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.blood1);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Toast.makeText(getApplicationContext(), "Password Matched", Toast.LENGTH_SHORT).show();
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(email, input.getText().toString());
                                mRef1 = new Firebase("https://red-aid.firebaseio.com/" + uid);
                                mRef1.removeValue();

                                // Prompt the user to re-provide their sign-in credentials
                                u.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                u.delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(ProfileActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                                    //starting login activity
                                                                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                                                                }
                                                            }
                                                        });

                                            }
                                        });

                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });

                // closed

                // Showing Alert Message
                alertDialog.show();


                //closing activity

                return true;

            case R.id.nav_info:
                finish();
                startActivity(new Intent(ProfileActivity.this, DisplayInfo.class));
                return true;

            case R.id.nav_logout:

                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                return true;
            default:
                return false;

        }
    }
}





