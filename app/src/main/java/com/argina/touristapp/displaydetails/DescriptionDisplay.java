package com.argina.touristapp.displaydetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.argina.touristapp.home.MainActivity;
import com.bumptech.glide.Glide;
import com.argina.touristapp.activity.CustomDeals;
import com.argina.touristapp.R;

public class DescriptionDisplay extends AppCompatActivity {

    private ImageView im;
    private TextView t1,t2,t3,t4,abc;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);



        im = (ImageView) findViewById(R.id.place_Pic_D);
        t1 = (TextView) findViewById(R.id.place_Title_D);
        t2 = (TextView) findViewById(R.id.placeDes_D);
        t3 = (TextView) findViewById(R.id.placeDivision_D);
        t4 = (TextView) findViewById(R.id.offer_bdt);
        abc = (TextView) findViewById(R.id.abc);
        b1 = (Button) findViewById(R.id.btn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent mIntent = new Intent(DescriptionDisplay.this, CustomDeals.class);
                //mIntent.putExtra("BarTitle",getString());
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
            }
        });

        Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){

            Glide.with(this)
                    .load(mBundle.get("Image2"))
                    .into(im);
            t1.setText(mBundle.getString("Title"));
            t2.setText(mBundle.getString("Description"));
            Linkify.addLinks(t2,Linkify.ALL);
            t2.setMovementMethod(LinkMovementMethod.getInstance());
            t3.setText(mBundle.getString("Division"));
            t4.setText(mBundle.getString("Offer Money"));
            abc.setText(mBundle.getString("visible"));



            getSupportActionBar().setTitle(mBundle.getString("Title"));



        }

        String check = abc.getText().toString().trim();

        if (check.equals("deals")){

            b1.setVisibility(View.VISIBLE);
        }else {
            b1.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(DescriptionDisplay.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
