package com.argina.touristapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.argina.touristapp.activity.AdminWeb;
import com.argina.touristapp.booking.BookingModel;
import com.argina.touristapp.helper.VolleySingleton;
import com.argina.touristapp.history.History;
import com.argina.touristapp.history.HistoryAdapter;
import com.argina.touristapp.home.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPanelActivity extends AppCompatActivity {
    private TextView mTextMessage;


    private static final String URL_HISTORY_SEARCH = "http://programmersjail.com/apps/argina/n/msg.php";
    RecyclerView recyclerViewThree;
    List<BookingModel> bookingModelList;
    private RecyclerView.LayoutManager layoutManager;

    private Button b1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                   // mTextMessage.setText(R.string.title_dashboard);
                    Intent i1 = new Intent(AdminPanelActivity.this, AdminWeb.class);
                    i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i1);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this,AdminLogin.class));
        }

        recyclerViewThree = (RecyclerView) findViewById(R.id.history_rrv) ;
        recyclerViewThree.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewThree.setLayoutManager(layoutManager);

         b1 = (Button) findViewById(R.id.reserve);

        bookingModelList = new ArrayList<>();
        loadHistory();


    }


    public void loadHistory(){

        final String Reservation = b1.getText().toString().trim();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HISTORY_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


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

                            MsgAdapter adapter = new MsgAdapter(AdminPanelActivity.this,bookingModelList);
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
                params.put("reservation", Reservation);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
