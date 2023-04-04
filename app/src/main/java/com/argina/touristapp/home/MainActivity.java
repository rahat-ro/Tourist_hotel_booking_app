package com.argina.touristapp.home;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.argina.touristapp.AdminLogin;
import com.argina.touristapp.AdminPanelActivity;
import com.argina.touristapp.SharedPrefManager;
import com.argina.touristapp.User;
import com.argina.touristapp.displaycategory.PlaceActivity;
import com.argina.touristapp.helper.VolleySingleton;
import com.argina.touristapp.history.History;
import com.argina.touristapp.activity.AdminWeb;
import com.argina.touristapp.displaycategory.PackageZone;
import com.argina.touristapp.R;
import com.argina.touristapp.hotel.HotelActivity;

import com.argina.touristapp.message.MessageActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private TextView deals,destination;

    private ImageView im;

    private static final String URL_PLACE = "http://programmersjail.com/apps/argina/place.php";
    private static final String URL_OFFER = "http://programmersjail.com/apps/argina/offer.php";
    private static final String URL_TIPS = "http://programmersjail.com/apps/argina/tips.php";

    //................. destination ...........
    List<PlaceModel> placeModelList;
    RecyclerView recyclerView;
   // LinearLayoutManager linearLayoutManager;

    private RecyclerView.LayoutManager layoutManager;
//..................... Offer ..........
    List<OfferModel> offerModelList;
    RecyclerView recyclerViewOne;
    // LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager layoutManagerOne;
//................... tips.......................
    RecyclerView recyclerViewTwo;
    List<TipsModel> tipsModelList;
    GridLayoutManager gridLayoutManager;

    OfferAdapter offerAdapter;

    //////////////////////////////////

    private static final String TAG = MainActivity.class.getSimpleName();




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_hotel:


                     Intent intent = new Intent(MainActivity.this, HotelActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);

                    return true;

                    case R.id.admin_panel :

                        Intent i1 = new Intent(MainActivity.this, AdminLogin.class);
                        i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i1);

                        //callLoginDialog();

                        return true;

                case R.id.notification:

                    Intent i = new Intent(MainActivity.this, MessageActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    return true;


            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setResult(RESULT_OK);


        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        im = (ImageView) findViewById(R.id.im);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent mIntent = new Intent(MainActivity.this, History.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
            }
        });

        //................. destination ...........


        recyclerView = (RecyclerView) findViewById(R.id.destination_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        recyclerView.setHasFixedSize(true);


        placeModelList = new ArrayList<>();
        loadPlace();

        //..................... Offer ..........

        recyclerViewOne = (RecyclerView) findViewById(R.id.deals_rv);
        recyclerViewOne.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        recyclerViewOne.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        offerModelList = new ArrayList<>();
        loadOffer();

        //................... tips.......................

        recyclerViewTwo = (RecyclerView) findViewById(R.id.tips_rv);
        gridLayoutManager = new GridLayoutManager(MainActivity.this,3);

        recyclerViewTwo.setLayoutManager(gridLayoutManager);

        tipsModelList = new ArrayList<>();


        loadTips();

        destination = (TextView) findViewById(R.id.id_destination);
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        deals = (TextView) findViewById(R.id.id_deals);
        deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(MainActivity.this, PackageZone.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });










    }



    private void loadTips () {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TIPS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {


                            JSONArray array = new JSONArray(response);





                            for (int i = 0; i < array.length(); i++) {

                                JSONObject tips = array.getJSONObject(i);

                                tipsModelList.add(new TipsModel(
                                        tips.getInt("id"),
                                        tips.getString("tipsImg"),
                                        tips.getString("tipsTitle"),
                                        tips.getString("tipsDescription")


                                ));
                            }

                            TipsAdapter adapter = new TipsAdapter(MainActivity.this,tipsModelList);
                            recyclerViewTwo.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }

        );
        //adding our string request to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void loadOffer () {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_OFFER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {


                            JSONArray array = new JSONArray(response);




                            for (int i = 0; i < array.length(); i++) {

                                JSONObject offer = array.getJSONObject(i);

                                offerModelList.add(new OfferModel(
                                        offer.getInt("id"),
                                        offer.getString("offerImg"),
                                        offer.getString("locationTitle"),
                                        offer.getString("offerMoney"),
                                        offer.getString("offerTitle"),
                                        offer.getString("offerDes"),
                                        offer.getString("zone")

                                ));
                            }

                            OfferAdapter adapter = new OfferAdapter(MainActivity.this,offerModelList);
                            recyclerViewOne.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }

        );
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }


    private void loadPlace() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PLACE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {


                            JSONArray array = new JSONArray(response);




                            for (int i = 0; i < array.length(); i++) {

                                JSONObject score = array.getJSONObject(i);

                                placeModelList.add(new PlaceModel(
                                        score.getInt("id"),
                                        score.getString("placeImg"),
                                        score.getString("placeTitle"),
                                        score.getString("placeDescription"),
                                        score.getString("placeDivision")

                                ));
                            }

                            PlaceAdapter adapter = new PlaceAdapter(MainActivity.this,placeModelList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }

        );
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }













}
