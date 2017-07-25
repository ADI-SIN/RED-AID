package com.example.charul.webservicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class fbg extends AppCompatActivity implements View.OnClickListener {

    private Button b1;
    private  Button b2;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbg);

        b1 = (Button)findViewById(R.id.b1);
        b2 =  (Button)findViewById(R.id.b2);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==b1){
          //  signout();

            LoginManager.getInstance().logOut();
            finish();
            startActivity(new Intent(this,MainActivity1.class));
        }

        else if(view==b2){
            finish();
            startActivity(new Intent(this,Profile_map.class));
        }
    }

/*    public void signout(){
        fbg session = new fbg();

        Auth.GoogleSignInApi.signOut(session.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(fbg.this, "loggedOut", Toast.LENGTH_SHORT).show();
                    }
                });

    }*/
}
