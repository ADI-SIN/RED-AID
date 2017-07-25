package com.example.charul.webservicedemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText etv1;
    private EditText etv2;
    private Button sve;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        etv1=(EditText)findViewById(R.id.et1);
        etv2=(EditText)findViewById(R.id.et2);


        sve=(Button)findViewById(R.id.save);

        sve.setOnClickListener(this);

    }

    public void onClick(View view) {

        final String p1=etv1.getText().toString();
        final String p2=etv2.getText().toString();
        String email= user.getEmail();
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, p1);


// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(p2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChangePassword.this,"Password updated",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ChangePassword.this,"Error Password could not change",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(ChangePassword.this,"Error Auth failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
