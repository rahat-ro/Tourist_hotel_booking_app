package com.argina.touristapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.argina.touristapp.home.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.argina.touristapp.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SplashScreen extends AppCompatActivity {

    Timer timer = new Timer();




    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        },1000);

        initBackgroundImage();






    }

    private void initBackgroundImage () {
        ImageView imageView = findViewById(R.id.IV_background);

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(new ColorDrawable(Color.BLACK));

        Glide.with(this)
                .load(R.drawable.travel_background)
                .transition(withCrossFade())
                .apply(options)
                .into(imageView);
    }


}
