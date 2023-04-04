package com.argina.touristapp.hotel;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.argina.touristapp.R;
import com.argina.touristapp.home.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HotelActivity extends AppCompatActivity {

    private static final String URL_HOTEL = "http://programmersjail.com/apps/argina/hotel.php";

    List<HotelModel> hotelModelList;
    RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Hotel List");




        recyclerView = (RecyclerView) findViewById(R.id.hotel_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                loadHotel();

            }
        });
        swipe.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        hotelModelList = new ArrayList<>();
        loadHotel();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(HotelActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadHotel () {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HOTEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        swipe.setRefreshing(false);
                        try {


                            JSONArray array = new JSONArray(response);

                            // clear data and couldn't double
                            if(hotelModelList != null){
                                hotelModelList.clear();
                            }



                            for (int i = 0; i < array.length(); i++) {

                                JSONObject hotel = array.getJSONObject(i);



                                hotelModelList.add(new HotelModel(
                                        hotel.getInt("id"),
                                        hotel.getString("imgOne"),
                                        hotel.getString("imgTwo"),
                                        hotel.getString("hotelName"),
                                        hotel.getString("hotelAddress"),
                                        hotel.getString("hotelMapLink"),
                                        hotel.getString("aboutHotel"),
                                        hotel.getString("hotelBDT"),
                                        hotel.getString("hotelPreviousBDT")


                                ));
                            }

                            HotelAdapter adapter = new HotelAdapter(HotelActivity.this,hotelModelList);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HotelActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }

        );
        //adding our string request to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }
}
