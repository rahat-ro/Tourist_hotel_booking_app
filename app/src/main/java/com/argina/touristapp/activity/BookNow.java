package com.argina.touristapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.argina.touristapp.booking.BookingSummary;
import com.bumptech.glide.Glide;
import com.argina.touristapp.R;
import com.argina.touristapp.hotel.HotelActivity;
import com.argina.touristapp.hotelroom.RoomModel;

import java.util.List;

public class BookNow extends AppCompatActivity {

    private TextView t1,t2,t3,t4,t5,t6;
    private ImageView img;
    private Button b1,b2;

    private List<RoomModel> roomModelList;
    RoomModel roomModel;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle("MyTitle");

        t1 = (TextView) findViewById(R.id.id_roomName);
        t2 = (TextView) findViewById(R.id.id_roomType);
        t3 = (TextView) findViewById(R.id.id_roomPrice);
        t4 = (TextView) findViewById(R.id.id_roomDiscount);
        t5 = (TextView) findViewById(R.id.id_finalPrice);
        img = (ImageView) findViewById(R.id.room_img);


        b1 = (Button) findViewById(R.id.book_now);
        b2 = (Button) findViewById(R.id.contact_us);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"arginaakter001@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(BookNow.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });





        final Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){

            Glide.with(this).load(mBundle.get("Image2")).into(img);
            t1.setText(mBundle.getString("roomName"));
            t2.setText(mBundle.getString("roomType"));
            t3.setText(mBundle.getString("roomPrice"));
            t4.setText(mBundle.getString("roomDiscount"));
            t5.setText(mBundle.getString("finalPrice"));
            getSupportActionBar().setTitle(mBundle.getString("hotelName"));




            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    Intent i = new Intent(BookNow.this, BookingSummary.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("roomName", mBundle.getString("roomName"));
                    i.putExtra("roomType", mBundle.getString("roomType"));
                    i.putExtra("finalPrice", mBundle.getString("finalPrice"));
                    i.putExtra("hotelName", mBundle.getString("hotelName"));
                    startActivity(i);
                }
            });


        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(BookNow.this, HotelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
