package com.example.charul.webservicedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private EditText email;
    private EditText password;
    private TextView signup;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseAuth;

    private SignInButton btnn;


    //fb login
    LoginButton log;
    TextView txt;
    private TextView forgot;
    CallbackManager callback;                                                               //as the name suggest
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());                             //this is to initialize fb sdk, it should be done before setContentView

        setContentView(R.layout.activity_login);

        progressdialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.register);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signup = (TextView) findViewById(R.id.signin);

        forgot= (TextView)findViewById(R.id.button4) ;

        btnn = (SignInButton)findViewById(R.id.sign_in_button);


        button.setOnClickListener(this);
        signup.setOnClickListener(this);

        forgot.setOnClickListener(this);

        btnn.setOnClickListener(this);

        //facebook
        log=(LoginButton)findViewById(R.id.login_button);
        callback = CallbackManager.Factory.create();                       //initialising callback manager
       log.registerCallback(callback, new FacebookCallback<LoginResult>() {                //operation on callback manager after login button
            @Override
            public void onSuccess(LoginResult loginResult) {                                             //FacebookCallback<LoginResult> this is the class which manages login activity

                Log.d("LOGIN_SUCCESS", "Success");
                log.setVisibility(View.INVISIBLE);


                startActivity(new Intent(LoginActivity.this, fbg.class));

                finish();
            }

            @Override
            public void onCancel() {

                Log.d("LOGIN_CANCEL", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {

                Log.d("LOGIN_ERROR", "Error");
            }
        });
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
        // If already logged in show the home view
        if (accessToken != null) {
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            //enter profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callback.onActivityResult(requestCode,resultCode,data);                                 //pass the result of new activity to callback manager
    }


    private void userlogin(){
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
        //if both validations are ok
        //we will first show a progressdialog
        progressdialog.setMessage("Logging in..");
        progressdialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressdialog.dismiss();
                if(task.isSuccessful()){
                    //start profile activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==button){
            userlogin();
        }
        else if(view==signup){
            finish();
            startActivity(new Intent(this, MainActivity1.class));
        }
        else if(view==forgot){
            finish();
            startActivity(new Intent(this,forgot_activity.class));
        }
        else if(view==btnn){
            finish();
            startActivity(new Intent(this,profilego.class));
        }
    }
}
