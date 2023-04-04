package com.argina.touristapp.displaycategory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.argina.touristapp.R;
import com.argina.touristapp.home.MainActivity;
import com.argina.touristapp.home.OfferAdapter;
import com.argina.touristapp.helper.VolleySingleton;
import com.argina.touristapp.home.OfferModel;
import com.argina.touristapp.dropdown.ZoneModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageZone extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private static final String URL_ZONE_NAME = "http://programmersjail.com/apps/argina/zone_name.php";
    private static final String URL_OFFER = "http://programmersjail.com/apps/argina/offer_search.php";


    List<OfferModel> offerModelList;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;


    private ArrayList<String> zoneName = new ArrayList<String>();
    private Spinner spinner;
    private List<ZoneModel> zoneModelList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_zone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        recyclerView = (RecyclerView) findViewById(R.id.pk_rv);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(PackageZone.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);

        offerModelList = new ArrayList<>();



        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        zoneModelList = new ArrayList<>();
        loadZoneName();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(PackageZone.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadZoneName () {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ZONE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray array = new JSONArray(response);


                            for (int i = 0; i < array.length(); i++) {

                                JSONObject location = array.getJSONObject(i);

                                zoneModelList.add(new ZoneModel(
                                        location.getInt("id"),
                                        location.getString("zone_name")



                                ));

                            }

                            for (int i = 0; i < zoneModelList.size(); i++){
                                zoneName.add(zoneModelList.get(i).getZone_Name().toString());
                            }


                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PackageZone.this,
                                    android.R.layout.simple_spinner_item, zoneName);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinner.setAdapter(spinnerArrayAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PackageZone.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }

        );
        //adding our string request to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    @Override
    public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {


        final String item = parent.getItemAtPosition(position).toString();

        if(item == parent.getItemAtPosition(position)){
            offerModelList.clear();

        }

        /////............................................................

        //first getting the values
        final String zoneName = item.toString();



        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_OFFER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object



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

                            OfferAdapter adapter = new OfferAdapter(PackageZone.this,offerModelList);
                            recyclerView.swapAdapter(adapter,false);
                            adapter.notifyDataSetChanged();




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PackageZone.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("zone", zoneName);
                return params;
            }
        };

        VolleySingleton.getInstance(PackageZone.this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onNothingSelected (AdapterView<?> parent) {

    }
}
