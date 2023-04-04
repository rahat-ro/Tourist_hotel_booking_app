package com.argina.touristapp.booking;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.argina.touristapp.R;
import com.argina.touristapp.helper.VolleySingleton;
import com.argina.touristapp.history.History;
import com.argina.touristapp.home.MainActivity;
import com.argina.touristapp.hotel.HotelActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingSummary extends AppCompatActivity {


    private TextView t1, t2, t3, t4, t5, t6, dateIn, dateOut,update;
    private ImageView imOne, imTwo;
    private EditText mob;
    private Button b1;
    private static final String URL_BOOKING_DATA = "http://programmersjail.com/apps/argina/booking_summery.php";
    private static final String URL_BOOKING_DATA_UPDATE = "http://programmersjail.com/apps/argina/booking_summery_up.php";

    DatePickerDialog picker;

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;





    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        t1 = (TextView) findViewById(R.id.bs_roomName);
        t2 = (TextView) findViewById(R.id.bs_roomType);
        t3 = (TextView) findViewById(R.id.bs_roomFinalPrice);
        t4 = (TextView) findViewById(R.id.bs_HotelName);
        t5 = (TextView) findViewById(R.id.bs_roomStatus);
        t6 = (TextView) findViewById(R.id.bs_roomNumber) ;
        dateIn = (TextView) findViewById(R.id.in);
        dateOut = (TextView) findViewById(R.id.out);
        mob = (EditText) findViewById(R.id.client_mob);
        update = (TextView) findViewById(R.id.bs_roomStatus_update);



        imOne = (ImageView) findViewById(R.id.calender_one);
        imOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                getDateNow();

            }
        });
        imTwo = (ImageView) findViewById(R.id.calender_Two);
        imTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                getDateNowOne();
            }
        });

        b1 = (Button) findViewById(R.id.reserve);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                reserve();
            }
        });

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {

            //Glide.with(this).load(mBundle.get("Image2")).into(img);
            t1.setText(mBundle.getString("roomName"));
            t2.setText(mBundle.getString("roomType"));
            //t3.setText(mBundle.getString("finalPrice"));
            t4.setText(mBundle.getString("hotelName"));
            //t5.setText(mBundle.getString("finalPrice"));

            loadData();


        }



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(BookingSummary.this, HotelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void getDateNow(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(BookingSummary.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateIn.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }

    public void getDateNowOne(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(BookingSummary.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateOut.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }



    private void reserve(){

        //first getting the values
        final String roomName = t1.getText().toString().trim();
        //final String roomType = t2.getText().toString().trim();
        final String hotelName = t4.getText().toString().trim();
        final String In = dateIn.getText().toString().trim();
        final String Out = dateOut.getText().toString().trim();
        final String mobNo = mob.getText().toString().trim();
        final String Reservation = b1.getText().toString().trim();
        final String Update = update.getText().toString().trim();

        //validating inputs
        if (TextUtils.isEmpty(In)) {
            dateIn.setError("Please enter your date");
            dateIn.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Out)) {
            dateOut.setError("Please enter your date");
            dateOut.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mobNo)) {
            mob.setError("Please enter your mob no");
            mob.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BOOKING_DATA_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            Intent i = new Intent(BookingSummary.this, History.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("mob", mobNo);
                            startActivity(i);
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                             //if no error in response
                             if (!obj.getBoolean("error")) {
                             Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                             //getting the user from the response
                             JSONObject userJson = obj.getJSONObject("booking");

                             //userJson.getString("user");

                             //creating a new user object
                             BookingModel booking = new BookingModel(
                             userJson.getInt("id"),
                             userJson.getString("room_name"),
                             userJson.getString("room_type"),
                             userJson.getString("final_price"),
                             userJson.getString("hotel_name"),
                             userJson.getString("client_mob"),
                             userJson.getString("checkin_date"),
                             userJson.getString("checkout_date"),
                             userJson.getString("room_number"),
                             userJson.getString("room_status")
                             );

                             t3.setText(booking.getFinal_price());




                             } else {
                             Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                             }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hotel_name", hotelName);
                params.put("room_name", roomName);
                params.put("checkin_date", In);
                params.put("checkout_date", Out);
                params.put("client_mob", mobNo);
                params.put("room_status", Update);
                params.put("reservation", Reservation);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void loadData(){


        //first getting the values
        final String roomName = t1.getText().toString().trim();
        final String roomType = t2.getText().toString().trim();
        final String hotelName = t4.getText().toString().trim();


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BOOKING_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("booking");

                                //userJson.getString("user");

                                //creating a new user object
                                BookingModel booking = new BookingModel(
                                        userJson.getInt("id"),
                                        userJson.getString("room_name"),
                                        userJson.getString("room_type"),
                                        userJson.getString("final_price"),
                                        userJson.getString("hotel_name"),
                                        userJson.getString("client_mob"),
                                        userJson.getString("checkin_date"),
                                        userJson.getString("checkout_date"),
                                        userJson.getString("room_number"),
                                        userJson.getString("room_status")
                                );

                                t3.setText(booking.getFinal_price());
                                t5.setText(booking.getRoom_status());
                                t6.setText(booking.getRoom_number());
                                String check = t5.getText().toString().trim();
                                if (!check.equals("unavailable")){
                                    b1.setVisibility(View.VISIBLE);
                                }else {
                                    b1.setVisibility(View.GONE);
                                }


                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("room_name", roomName);
                params.put("room_type", roomType);
                params.put("hotel_name", hotelName);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }





}
