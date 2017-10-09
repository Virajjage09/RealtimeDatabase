package com.coderVJ.realtimedb.activities;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by Viraj Jage on 11/109/17.
 */

public class CustomerRegistration extends AppCompatActivity {

    private EditText inputName, inputEmail, inputMobileNo, inputAddress;
    private Button btnCreateAccount;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    private Context mContext;

    private static final String TAG = CustomerRegistration.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_registration);
        
        getSupportActionBar().setTitle("Registration");

        mContext = CustomerRegistration.this;
        inputName = (EditText) findViewById(com.coderVJ.realtimedb.R.id.name);
        inputEmail = (EditText) findViewById(com.coderVJ.realtimedb.R.id.email);
        inputMobileNo = (EditText) findViewById(R.id.mobileno);
        inputAddress = (EditText) findViewById(R.id.address);

        btnCreateAccount = (Button) findViewById(com.coderVJ.realtimedb.R.id.btn_save);


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
                // getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String mobileNo = inputMobileNo.getText().toString();
                String address = inputAddress.getText().toString();


                if (!Utils.checkEmpty(name)) {
                    if (Utils.isValidEmail(email)) {
                        if (!Utils.checkEmpty(mobileNo) && mobileNo.length() == 10) {
                            if (!Utils.checkEmpty(address)) {
                                // Check for already existed userId
                                //if (TextUtils.isEmpty(userId)) {
                                createUser(name, email, mobileNo, address);
                                /*} else {
                                    Toast.makeText(mContext, "User Id Already Exist", Toast.LENGTH_SHORT).show();
                                }*/
                            } else {
                                Toast.makeText(mContext, "Please enter valid Residential Address", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Please enter valid Mobile No.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Please enter valid Email.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please enter valid Name.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    /**
     * Creating new user node under 'users'
     */
    private void createUser(final String name, final String email, final String mobileno, final String address) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        mFirebaseDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null
                        && dataSnapshot.getChildren().iterator().hasNext()) {
                    Toast.makeText(mContext, "Email Id already exist.", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseDatabase.orderByChild(("mobileno")).equalTo(mobileno).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null && dataSnapshot.getChildren() != null
                                    && dataSnapshot.getChildren().iterator().hasNext()) {
                                Toast.makeText(mContext, "Mobile No already exist.", Toast.LENGTH_SHORT).show();
                            } else {
                                User user = new User(name, email, mobileno, address, "");
                                Preferences.addPreference(mContext, PreferenceKeys.dbKey, userId);
                                mFirebaseDatabase.child(userId).setValue(user);

                                Toast.makeText(mContext, "Registration Successfull.", Toast.LENGTH_SHORT).show();
                                Preferences.addPreferenceBoolean(mContext, PreferenceKeys.isCustLogin, true);
                                Intent intent = new Intent(mContext, CustomerDashBoard.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);


                // clear edit text
                inputEmail.setText("");
                inputName.setText("");
                inputMobileNo.setText("");
                inputAddress.setText("");

                //toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }
}
