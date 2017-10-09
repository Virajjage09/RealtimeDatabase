package com.coderVJ.realtimedb.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coderVJ.realtimedb.R;
import com.coderVJ.realtimedb.adapters.DeliveryRecordAdapter;
import com.coderVJ.realtimedb.model.User;
import com.coderVJ.realtimedb.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Viraj Jage on 11/109/17.
 */

public class AdminDashboard extends AppCompatActivity {

    private static final String TAG = AdminDashboard.class.getSimpleName();
    private EditText edtSelectDate;
    private TextView txtNoRecords;
    private Button btnGetRecords;
    List<User> userList;
    User user;
    Context mContext;
    ListView listRecords;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    DeliveryRecordAdapter recordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        getSupportActionBar().setTitle("Delivery Records");

        mContext = AdminDashboard.this;

        edtSelectDate = (EditText) findViewById(R.id.selectDate);
        btnGetRecords = (Button) findViewById(R.id.btn_getRecords);
        listRecords = (ListView) findViewById(R.id.lstRecords);
        txtNoRecords = (TextView) findViewById(R.id.txtNoRecords);

        txtNoRecords.setVisibility(View.GONE);
        userList = new ArrayList<User>();

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Customer Records");

        edtSelectDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        int month = selectedmonth + 1;
                        String date = selectedday + "/" + month + "/" + selectedyear;
                        edtSelectDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        btnGetRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = edtSelectDate.getText().toString().trim();
                userList.clear();

                if(!Utils.checkEmpty(date)) {
                    mFirebaseDatabase.orderByChild("deliveryDate").equalTo(date).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot != null && dataSnapshot.getChildren() != null
                                    && dataSnapshot.getChildren().iterator().hasNext()) {

                                Log.e(TAG, "Fetched Data1" + dataSnapshot.toString());
                                Log.e(TAG, "Fetched Data2" + dataSnapshot.getKey());
                                Log.e(TAG, "Fetched Data3" + dataSnapshot.getValue().toString());
                                Log.e(TAG, "Data Count" + dataSnapshot.getChildrenCount());
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    // Toast.makeText(mContext, "Fetched Data " + dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                    user = postSnapshot.getValue(User.class);
                                    userList.add(user);
                                }

                                //Log.e(TAG, "User 1st Delivery date" + userList.get(0).getDeliveryDate());
                                if (userList.size() > 0 && userList != null) {
                                    recordAdapter = new DeliveryRecordAdapter(mContext, userList);
                                    listRecords.setAdapter(recordAdapter);
                                    txtNoRecords.setVisibility(View.GONE);
                                } else {
                                    txtNoRecords.setVisibility(View.VISIBLE);
                                }
                            }else {
                                Toast.makeText(mContext, "No Records Found", Toast.LENGTH_SHORT).show();
                                txtNoRecords.setVisibility(View.VISIBLE);
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "Fetched Daadadat" + databaseError);
                        }
                    });
                }else {
                    Toast.makeText(mContext,"Please enter date",Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}
