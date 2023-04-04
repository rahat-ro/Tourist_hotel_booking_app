package com.argina.touristapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.argina.touristapp.R;
import com.argina.touristapp.home.MainActivity;
import com.argina.touristapp.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomDeals extends AppCompatActivity {

    private static final String URL_CUSTOM_DEAL = "http://programmersjail.com/apps/argina/custom_deal.php";

    private EditText e1,e2,e3,e4;
    private Button b1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_deals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Customize your deal");


        e1 = (EditText) findViewById(R.id.cs_phone);
        e2 = (EditText) findViewById(R.id.cs_name);
        e3 = (EditText) findViewById(R.id.cs_email);
        e4 = (EditText) findViewById(R.id.cs_deal);

        b1 = (Button) findViewById(R.id.submit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                submitdeal();
            }
        });



    }

    private void submitdeal () {


        //first getting the values
        final String csPhone = e1.getText().toString().trim();
        final String csName = e2.getText().toString().trim();
        final String csEmail = e3.getText().toString().trim();
        final String csDeal = e4.getText().toString().trim();


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CUSTOM_DEAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {


                            Intent intent = new Intent(CustomDeals.this,MainActivity.class);
                            startActivity(intent);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("booking");

                                //userJson.getString("user");

                                //creating a new user object





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
                params.put("phone", csPhone);
                params.put("name", csName);
                params.put("email", csEmail);
                params.put("deal_msg", csDeal);

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

                Intent intent = new Intent(CustomDeals.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
