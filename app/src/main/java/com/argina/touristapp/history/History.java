package com.argina.touristapp.history;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.argina.touristapp.R;
import com.argina.touristapp.home.MainActivity;
import com.argina.touristapp.helper.VolleySingleton;
import com.argina.touristapp.booking.BookingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity {

    private EditText e1;
    private ImageView im1;

    private static final String URL_HISTORY_SEARCH = "http://programmersjail.com/apps/argina/history.php";
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String mobNo = "mob";

    RecyclerView recyclerViewThree;
    List<BookingModel> bookingModelList;
    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Reservation History");


        final Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){
            //e1.setText(mBundle.getString("mob"));
            SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
            editor.putString(mobNo,mBundle.getString("mob"));
            editor.apply();
        }

        recyclerViewThree = (RecyclerView) findViewById(R.id.history_rrv) ;
        recyclerViewThree.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewThree.setLayoutManager(layoutManager);

        e1= (EditText) findViewById(R.id.history_search);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String mob_number = prefs.getString(mobNo, "");//"No name defined" is the default value.
        e1.setText(mob_number);


        im1 = (ImageView) findViewById(R.id.search);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                loadHistory();
            }
        });


        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                loadHistory();

            }
        });
        swipe.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        bookingModelList = new ArrayList<>();
        loadHistory();


    }

    public void loadHistory(){


        //first getting the values
        final String clientMob = e1.getText().toString().trim();


        if (TextUtils.isEmpty(clientMob)) {
            e1.setError("Please enter your mob no");
            e1.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HISTORY_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        swipe.setRefreshing(false);
                        try {


                            JSONArray array = new JSONArray(response);

                            // clear data and couldn't double
                           if(bookingModelList != null){
                                bookingModelList.clear();
                            }

                            for (int i = 0; i < array.length(); i++) {


                                JSONObject history = array.getJSONObject(i);
                                bookingModelList.add(new BookingModel(
                                        history.getInt("id"),
                                        history.getString("room_name"),
                                        history.getString("room_type"),
                                        history.getString("final_price"),
                                        history.getString("hotel_name"),
                                        history.getString("client_mob"),
                                        history.getString("checkin_date"),
                                        history.getString("checkout_date"),
                                        history.getString("room_number"),
                                        history.getString("room_status")

                                ));
                            }

                            HistoryAdapter adapter = new HistoryAdapter(History.this,bookingModelList);
                            // adapter.notifyDataSetChanged();
                            recyclerViewThree.setAdapter(adapter);

                            //recyclerViewThree.swapAdapter(adapter,false);
                            adapter.notifyDataSetChanged();




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
                params.put("client_mob", clientMob);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(History.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
