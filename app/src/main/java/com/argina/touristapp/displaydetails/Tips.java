package com.argina.touristapp.displaydetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.argina.touristapp.home.MainActivity;
import com.bumptech.glide.Glide;
import com.argina.touristapp.R;

public class Tips extends AppCompatActivity {

    private ImageView im;
    private TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        im = (ImageView) findViewById(R.id.tips_Pic);
        t1 = (TextView) findViewById(R.id.tips_Title);
        t2 = (TextView) findViewById(R.id.tips_Des);


        Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){

            Glide.with(this)
                    .load(mBundle.get("Image2"))
                    .into(im);
            t1.setText(mBundle.getString("Title"));
            t2.setText(mBundle.getString("Description"));
            getSupportActionBar().setTitle(mBundle.getString("Title"));




        }

    }    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(Tips.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
