package com.argina.touristapp.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.argina.touristapp.R;
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

import static com.argina.touristapp.history.History.MyPREFERENCES;
import static com.argina.touristapp.history.History.mobNo;

public class MessageActivity extends AppCompatActivity {


    private TextView tv;

    RecyclerView recyclerViewThree;
    List<MessageModel> messageModelList;
    private RecyclerView.LayoutManager layoutManager;
    private static final String URL_HISTORY_MESSAGE = "http://programmersjail.com/apps/argina/message.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Conformation Message");


        // Create object of SharedPreferences.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //now get Editor
        SharedPreferences.Editor editor = sharedPref.edit();
        //put your value
        editor.putString(mobNo, "stackoverlow");

        //commits your edits
        editor.commit();


        tv= (TextView) findViewById(R.id.mm_mob);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String mob_number = prefs.getString(mobNo, "");//"No name defined" is the default value.
        tv.setText(mob_number);

        recyclerViewThree = (RecyclerView) findViewById(R.id.message_rv) ;
        recyclerViewThree.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewThree.setLayoutManager(layoutManager);


        messageModelList = new ArrayList<>();
        loadMessage();


    }



    public void loadMessage(){


        //first getting the values
        final String clientMob = tv.getText().toString().trim();



        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HISTORY_MESSAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {


                            JSONArray array = new JSONArray(response);



                            for (int i = 0; i < array.length(); i++) {


                                JSONObject history = array.getJSONObject(i);
                                messageModelList.add(new MessageModel(
                                        history.getInt("id"),
                                        history.getString("client_mob"),
                                        history.getString("message_body")


                                ));
                            }

                            MessageAdapter adapter = new MessageAdapter(MessageActivity.this,messageModelList);
                            // adapter.notifyDataSetChanged();
                            recyclerViewThree.setAdapter(adapter);
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

                Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
