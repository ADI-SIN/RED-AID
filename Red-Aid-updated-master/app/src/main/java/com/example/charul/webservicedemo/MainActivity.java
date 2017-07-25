package com.example.charul.webservicedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by charul on 18/4/17.
 */
public class MainActivity extends AppCompatActivity {

    public String string = "Need";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button buttonDonate = (Button)findViewById(R.id.button2);

        buttonDonate.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the donate category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link MainActivity1}
                Intent  a= new Intent(MainActivity.this, LoginActivity.class);

                // Start the new activity
                startActivity(a);
            }
        });

       Button buttonNeed = (Button)findViewById(R.id.button3);

        buttonNeed.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the need category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link MainActivity1}
                Intent b = new Intent(MainActivity.this, MapsActivity.class);
                b.putExtra("var",string);
                // Start the new activity
                startActivity(b);
            }
        });

    }

}