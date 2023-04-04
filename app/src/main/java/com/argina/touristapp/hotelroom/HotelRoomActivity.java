package com.argina.touristapp.hotelroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.argina.touristapp.hotel.HotelActivity;
import com.bumptech.glide.Glide;
import com.argina.touristapp.R;
import com.argina.touristapp.helper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelRoomActivity extends AppCompatActivity {

    private ImageView img;
    private ImageButton map;
    private TextView t1,t2,t3;

    private static final String URL_ROOM = "http://programmersjail.com/apps/argina/room_search.php";


    //................... Room.......................
    RecyclerView recyclerViewThree;
    List<RoomModel> roomModelList;
    private RecyclerView.LayoutManager layoutManagerThree;





    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        img = (ImageView) findViewById(R.id.room_pic);
        map = (ImageButton) findViewById(R.id.hotelMapLink);
        t1 = (TextView)  findViewById(R.id.hotelName);
        t2 = (TextView)  findViewById(R.id.hotelAddress);
        t3 = (TextView)  findViewById(R.id.aboutHotel);

        final Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){

            Glide.with(this).load(mBundle.get("Image2")).into(img);
            t1.setText(mBundle.getString("HotelName"));
            t2.setText(mBundle.getString("HotelAddress"));
            t3.setText(mBundle.getString("AboutHotel"));
            getSupportActionBar().setTitle(mBundle.getString("HotelName"));

            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(mBundle.getString("MapLink")));
                    startActivity(intent);
                }
            });


        }




        //................. room ...........


        recyclerViewThree = (RecyclerView) findViewById(R.id.room_rv);
        recyclerViewThree.setHasFixedSize(true);
        layoutManagerThree = new LinearLayoutManager(this);
        recyclerViewThree.setLayoutManager(layoutManagerThree);



        roomModelList = new ArrayList<>();
        loadRoom();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(HotelRoomActivity.this, HotelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadRoom () {

        //first getting the values
        final String hotelName = t1.getText().toString().trim();



        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ROOM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);




                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                roomModelList.add(new RoomModel(
                                        room.getInt("id"),
                                        room.getString("roomImg"),
                                        room.getString("roomName"),
                                        room.getString("roomType"),
                                        room.getString("BDT"),
                                        room.getString("price"),
                                        room.getString("discount"),
                                        room.getString("finalPrice"),
                                        room.getString("hotelName")

                                ));
                            }

                            RoomAdapter adapter = new RoomAdapter(HotelRoomActivity.this,roomModelList);
                           // adapter.notifyDataSetChanged();
                            recyclerViewThree.setAdapter(adapter);




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
                params.put("hotelName", hotelName);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }
}
