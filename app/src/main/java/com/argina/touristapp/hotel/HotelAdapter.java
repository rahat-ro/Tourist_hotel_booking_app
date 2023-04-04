package com.argina.touristapp.hotel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.argina.touristapp.hotelroom.HotelRoomActivity;
import com.argina.touristapp.R;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private Context mCtx;
    private List<HotelModel> hotelModelList;

    public HotelAdapter (Context mCtx, List<HotelModel> hotelModelList) {
        this.mCtx = mCtx;
        this.hotelModelList = hotelModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.hotel_card_view,viewGroup,false);
        return new HotelAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder (@NonNull final ViewHolder viewHolder, int i) {

        HotelModel hotelModel = hotelModelList.get(i);
        Glide.with(mCtx).load(hotelModel.getImgOne()).into(viewHolder.imgOne);
        viewHolder.t1.setText(hotelModel.getHotelName());
        viewHolder.t2.setText(hotelModel.getHotelAddress());
        viewHolder.t3.setText(hotelModel.getHotelBDT());
        viewHolder.t4.setText(hotelModel.getHotelPreviousBDT());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent mIntent = new Intent(mCtx, HotelRoomActivity.class);
                //mIntent.putExtra("BarTitle",getString());
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("Image2", hotelModelList.get(viewHolder.getAdapterPosition()).getImgTwo());
                mIntent.putExtra("HotelName", hotelModelList.get(viewHolder.getAdapterPosition()).getHotelName());
                mIntent.putExtra("HotelAddress", hotelModelList.get(viewHolder.getAdapterPosition()).getHotelAddress());
                mIntent.putExtra("AboutHotel", hotelModelList.get(viewHolder.getAdapterPosition()).getAboutHotel());
                mIntent.putExtra("MapLink",hotelModelList.get(viewHolder.getAdapterPosition()).getHotelMapLink());
                mCtx.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return hotelModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgOne;
        TextView t1,t2,t3,t4;
        CardView cardView;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            imgOne = (ImageView) itemView.findViewById(R.id.hotel_img);
            t1 = (TextView) itemView.findViewById(R.id.hotel_name);
            t2 = (TextView) itemView.findViewById(R.id.hotel_address);
            t3 = (TextView) itemView.findViewById(R.id.bdt);
            t4 = (TextView) itemView.findViewById(R.id.previous_bdt);
            cardView = (CardView) itemView.findViewById(R.id.card);

        }
    }
}
