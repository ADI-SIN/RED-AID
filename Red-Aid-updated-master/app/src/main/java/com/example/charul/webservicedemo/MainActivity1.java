package com.example.charul.webservicedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity1 extends AppCompatActivity implements View.OnClickListener{

    private Button button;
    private EditText email;
    private EditText password;
    private TextView signin;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseAuth;

    public GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        progressdialog=new ProgressDialog(this);
        button= (Button) findViewById(R.id.register);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        signin=(TextView)findViewById(R.id.signin);

        button.setOnClickListener(this);
        signin.setOnClickListener(this);
        firebaseAuth=FirebaseAuth.getInstance();


        //if getcurrentuser does not return null
        if (firebaseAuth.getCurrentUser()!=null){
            //implies user is already logged in
            //so close this activity and
            //start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }



    }
    private void registerUser(){
        String em= email.getText().toString().trim();
        String pass= password.getText().toString().trim();

        if(TextUtils.isEmpty(em)){
            //email is empty
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }

        if(TextUtils.isEmpty(pass)){
            //password is empty
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.getTrimmedLength(pass)<6){
            Toast.makeText(this, "password should be of min 6 characters", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }


        if(TextUtils.getTrimmedLength(em)<6){
            Toast.makeText(this, "enter valid email id", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }
        //if both validations are ok
        //we will first show a progressdialog
        progressdialog.setMessage("Registering user..");
        progressdialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user is successfully registered and logged in
                    //we will start the profile activity here
                    Toast.makeText(MainActivity1.this, "Registered successfuly", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), details.class));
                }
                else{
                    Toast.makeText(MainActivity1.this, "Could not register! Please try again", Toast.LENGTH_SHORT).show();
                }
                progressdialog.dismiss();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view == button) {
            registerUser();
        }
        if (view == signin) {
            //will open login activity here

           startActivity(new Intent(this, LoginActivity.class));
        }
    }


}
