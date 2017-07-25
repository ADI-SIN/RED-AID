package com.example.charul.webservicedemo;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class details extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private EditText et1;
    private EditText et2;
    private EditText et3;

    private EditText et5;
    private EditText et6;
    private Spinner sp;
    private Button submit;
    private RadioButton yup;
    private RadioButton nope;
    private Firebase mRef;
    private Firebase mRef1;
    private AutoCompleteTextView mAutocompleteTextView;
    private double lat2;
    private double long2;

    private static final String LOG_TAG = "details";
    private static final int GOOGLE_API_CLIENT_ID = 0;


    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Firebase.setAndroidContext(this);



        mGoogleApiClient = new GoogleApiClient.Builder(details.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);


        et1= (EditText)findViewById(R.id.Name);
        et2= (EditText)findViewById(R.id.age);
        et3= (EditText)findViewById(R.id.Number);
        mAutocompleteTextView= (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        et5= (EditText)findViewById(R.id.height);
        et6= (EditText)findViewById(R.id.weight);
        sp=(Spinner)findViewById(R.id.spinner1);
        submit = (Button)findViewById(R.id.submit);
        yup=(RadioButton)findViewById(R.id.radioyes);
        nope=(RadioButton)findViewById(R.id.radiono);


        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);


        FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
        final String uid= u.getUid();

        mRef= new Firebase("https://red-aid.firebaseio.com/");


        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the donate category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link MainActivity1}


                String name = et1.getText().toString().trim();
                String age = et2.getText().toString().trim();
                String number = et3.getText().toString().trim();
                String address = mAutocompleteTextView.getText().toString().trim();
                String height = et5.getText().toString().trim();
                String weight = et6.getText().toString().trim();
                String blood=sp.getSelectedItem().toString();



                mRef1= new Firebase("https://red-aid.firebaseio.com/"+uid);



                //Creating Person object
                Person person = new Person();

                //Adding values
                person.setName(name);
                person.setAddress(address);
                person.setAge(age);
                person.setNumber(number);
                person.setHeight(height);
                person.setWeight(weight);
                person.setBlood(blood);

                Geocoder coder = new Geocoder(details.this);
                try {
                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 100);
                    for (Address add : adresses) {
                        //Controls to ensure it is right address such as country etc.
                        long2 = add.getLongitude();
                        lat2 = add.getLatitude();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                person.setLat(lat2);
                person.setLong(long2);

                //Storing values to firebase
                mRef1.setValue(person);



                if(TextUtils.isEmpty(et1.getText().toString().trim())){
                    //email is empty
                    Toast.makeText(details.this, "please enter your name", LENGTH_SHORT).show();
                    //stopping the function execution further
                    return;
                }
                else  if(TextUtils.isEmpty(et2.getText().toString().trim())){

                    Toast.makeText(details.this, "please enter your age", LENGTH_SHORT).show();
                    //stopping the function execution further
                    return;
                }
                else  if(TextUtils.isEmpty(et3.getText().toString().trim())){

                    Toast.makeText(details.this, "please enter your contact no.", LENGTH_SHORT).show();
                    //stopping the function execution further
                    return;
                }
                else  if(TextUtils.isEmpty(et5.getText().toString().trim())){
                    //email is empty
                    Toast.makeText(details.this, "please enter your height in meters", LENGTH_SHORT).show();
                    //stopping the function execution further
                    return;
                }
                else  if(TextUtils.isEmpty(et6.getText().toString().trim())){
                    //email is empty
                    Toast.makeText(details.this, "please enter your weight in Kgs", LENGTH_SHORT).show();
                    //stopping the function execution further
                    return;
                }

                else if(((et3.getText().toString().trim().length())<10)&&((et3.getText().toString().trim().length())>11)){
                    Toast.makeText(details.this, "Enter the correct phone no.", LENGTH_SHORT).show();
                    return;
                }

                else if(yup.isChecked()){
                    Toast.makeText(details.this, "You cannot donate blood!", LENGTH_SHORT).show();
                    return;
                }








                Toast.makeText(details.this, "Information Saved ...", LENGTH_SHORT).show();
                finish();
                Intent a= new Intent(details.this, ProfileActivity.class);
                 a.putExtra("name",name);
                // Start the new activity
                startActivity(a);
            }
        });
    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

        }
    };



    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {

                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

        }
    };


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }
}
