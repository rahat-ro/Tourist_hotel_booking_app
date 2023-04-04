package com.argina.touristapp.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.argina.touristapp.activity.CustomDeals;
import com.argina.touristapp.displaydetails.DescriptionDisplay;
import com.argina.touristapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private Context mCtx;
    private List<OfferModel> offerModelList;


    private static final int abc = 1000;

    ArrayList<String> result;

    public OfferAdapter (Context mCtx, List<OfferModel> offerModelList) {
        this.mCtx = mCtx;
        this.offerModelList = offerModelList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.activity_offer,viewGroup,false);
        return new OfferAdapter.ViewHolder(mView);
    }


    public void getSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say, Custom deal");

        if (intent.resolveActivity(mCtx.getPackageManager()) != null)
            ((Activity) mCtx).startActivityForResult(intent,abc);
            //startActivityForResult(intent, 100);
        else {
            Toast.makeText(mCtx, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }


   // @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txvResult.setText(result.get(0));

                }
                break;
        }
    }



    @Override
    public void onBindViewHolder (@NonNull final ViewHolder viewHolder, int i) {


        OfferModel offerModel = offerModelList.get(i);
        Glide.with(mCtx).load(offerModel.getOfferImg()).into(viewHolder.img);
        viewHolder.t1.setText(offerModel.getLocationTitle());
        viewHolder.t2.setText(offerModel.getOfferMoney());
        viewHolder.t3.setText(offerModel.getOfferTitle());
        viewHolder.r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mCtx, DescriptionDisplay.class);
                //mIntent.putExtra("BarTitle",getString());
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("Image2", offerModelList.get(viewHolder.getAdapterPosition()).getOfferImg());
                mIntent.putExtra("Title", offerModelList.get(viewHolder.getAdapterPosition()).getLocationTitle());
                mIntent.putExtra("Offer Money", offerModelList.get(viewHolder.getAdapterPosition()).getOfferMoney());
                mIntent.putExtra("Offer Title", offerModelList.get(viewHolder.getAdapterPosition()).getOfferTitle());
                mIntent.putExtra("Description", offerModelList.get(viewHolder.getAdapterPosition()).getOfferDes());
                mIntent.putExtra("visible", "deals");
                mCtx.startActivity(mIntent);
            }
        });

        viewHolder.mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                //getSpeechInput();
                Intent mIntent = new Intent(mCtx, CustomDeals.class);
                //mIntent.putExtra("BarTitle",getString());
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mCtx.startActivity(mIntent);

            }
        });



    }



    @Override
    public int getItemCount () {
        return offerModelList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img,mic;
        TextView t1,t2,t3,txvResult;
        RelativeLayout r1;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.offer_Pic);
            t1 = (TextView) itemView.findViewById(R.id.locationTitle);
            t2 = (TextView) itemView.findViewById(R.id.offer_money);
            t3 = (TextView) itemView.findViewById(R.id.offer_d);
            r1 = (RelativeLayout) itemView.findViewById(R.id.rl);
            mic = (ImageView) itemView.findViewById(R.id.mic);
            txvResult = (TextView) itemView.findViewById(R.id.tv);



        }
    }









}
