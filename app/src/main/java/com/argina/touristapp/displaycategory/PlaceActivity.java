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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.argina.touristapp.R;
import com.argina.touristapp.helper.VolleySingleton;
import com.argina.touristapp.home.MainActivity;
import com.argina.touristapp.dropdown.PlaceLocationModel;
import com.argina.touristapp.home.PlaceAdapter;
import com.argina.touristapp.home.PlaceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String URL_PLACE = "http://programmersjail.com/apps/argina/place_search.php";
    private static final String URL_LOCATION_NAME = "http://programmersjail.com/apps/argina/location.php";


    private Button b1;


    //................. destination ...........
    List<PlaceModel> placeModelList;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    private RecyclerView.LayoutManager layoutManager;

    //...........Spinner..........

    private List<PlaceLocationModel> placeLocationModelList;
    private ArrayAdapter<PlaceLocationModel> locationAdapter;
    private ArrayList<String> placeName = new ArrayList<String>();
    private Spinner spinner;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Spinner element
         spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        placeLocationModelList = new ArrayList<>();
        loadLocationName();

        //..................

        recyclerView = (RecyclerView) findViewById(R.id.destination_rv);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(PlaceActivity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        placeModelList = new ArrayList<>();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(PlaceActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadLocationName () {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOCATION_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray array = new JSONArray(response);


                            for (int i = 0; i < array.length(); i++) {

                                JSONObject location = array.getJSONObject(i);

                                placeLocationModelList.add(new PlaceLocationModel(
                                        location.getInt("id"),
                                        location.getString("placeLocationName")



                                ));

                            }

                            for (int i = 0; i < placeLocationModelList.size(); i++){
                                placeName.add(placeLocationModelList.get(i).getPlaceLocationName().toString());
                            }


                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PlaceActivity.this,
                                    android.R.layout.simple_spinner_item, placeName);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinner.setAdapter(spinnerArrayAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlaceActivity.this, "Error", Toast.LENGTH_SHORT).show();

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
            placeModelList.clear();

        }

        /////............................................................

        //first getting the values
        final String placeDivision = item.toString();



        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PLACE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object



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

                            PlaceAdapter adapter = new PlaceAdapter(PlaceActivity.this,placeModelList);
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
                        Toast.makeText(PlaceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("placeDivision", placeDivision);
                return params;
            }
        };

        VolleySingleton.getInstance(PlaceActivity.this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onNothingSelected (AdapterView<?> parent) {

    }
}
