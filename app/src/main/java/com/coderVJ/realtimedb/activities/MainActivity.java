package com.coderVJ.realtimedb.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coderVJ.realtimedb.R;
import com.coderVJ.realtimedb.model.User;
import com.coderVJ.realtimedb.utils.Utils;
import com.coderVJ.realtimedb.utils.preferences.PreferenceKeys;
import com.coderVJ.realtimedb.utils.preferences.Preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Viraj Jage on 11/109/17.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText edtCustEmail, edtCudtMobileNo;
    private Button btnCustLogin, btnAdminLogin;
    TextView txtNewCust;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.coderVJ.realtimedb.R.layout.activity_main);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("RealTime Database");
        //  getSupportActionBar().setIcon(R.drawable.appicon);


        mContext = MainActivity.this;


        edtCustEmail = (EditText) findViewById(R.id.edtEmail);
        edtCudtMobileNo = (EditText) findViewById(R.id.edtMobile);
        txtNewCust = (TextView) findViewById(R.id.txt_cust_registration);
        btnCustLogin = (Button) findViewById(R.id.btnCustLogin);

        btnAdminLogin = (Button) findViewById(R.id.btn_AdminLogin);


        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Customer Records");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle("RealTime Database");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        txtNewCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CustomerRegistration.class);
                startActivity(intent);
            }
        });
        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AdminDashboard.class);
                startActivity(intent);
            }
        });

        btnCustLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtCustEmail.getText().toString();
                String mobileNo = edtCudtMobileNo.getText().toString();

                if (Utils.isValidEmail(email)) {
                    if (!Utils.checkEmpty(mobileNo) && mobileNo.length() == 10) {
                        validateCustLogin(email, mobileNo);
                    } else {
                        Toast.makeText(mContext, "Please enter valid Mobile No.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please enter valid Email.", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void validateCustLogin(final String email, final String mobileno) {

        mFirebaseDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null
                        && dataSnapshot.getChildren().iterator().hasNext()) {

                    mFirebaseDatabase.orderByChild(("mobileno")).equalTo(mobileno).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null && dataSnapshot.getChildren() != null
                                    && dataSnapshot.getChildren().iterator().hasNext()) {

                                Log.e(TAG, "Key" + dataSnapshot.getValue());
                                String key = dataSnapshot.getValue().toString();
                                String [] dataset = key.split("=");
                                key = dataset[0].substring(1,dataset[0].length());
                                Log.e(TAG, "Key " + key);
                                Preferences.addPreference(mContext, PreferenceKeys.dbKey, key);
                                Toast.makeText(mContext, "Login Successfull", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(mContext, CustomerDashBoard.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(mContext, "Please enter correct mobile no", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(mContext, "Email Id is not yet Registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}