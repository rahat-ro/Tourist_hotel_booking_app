package com.argina.touristapp.hotelroom;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.argina.touristapp.activity.BookNow;
import com.argina.touristapp.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context mCtx;
    private List<RoomModel> roomModelList;

    public RoomAdapter (Context mCtx, List<RoomModel> roomModelList) {
        this.mCtx = mCtx;
        this.roomModelList = roomModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.room_card_view,viewGroup,false);
        return new RoomAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder (@NonNull final ViewHolder viewHolder, int i) {

        RoomModel roomModel = roomModelList.get(i);
        Glide.with(mCtx).load(roomModel.getRoomImg()).into(viewHolder.img);
        viewHolder.t1.setText(roomModel.getRoomName());
        viewHolder.t2.setText(roomModel.getRoomType());
        viewHolder.t3.setText(roomModel.getBDT());
        viewHolder.l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent mIntent = new Intent(mCtx, BookNow.class);
                //mIntent.putExtra("BarTitle",getString());
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("Image2", roomModelList.get(viewHolder.getAdapterPosition()).getRoomImg());
                mIntent.putExtra("roomName", roomModelList.get(viewHolder.getAdapterPosition()).getRoomName());
                mIntent.putExtra("roomType", roomModelList.get(viewHolder.getAdapterPosition()).getRoomType());
                mIntent.putExtra("roomPrice", roomModelList.get(viewHolder.getAdapterPosition()).getPrice());
                mIntent.putExtra("roomDiscount", roomModelList.get(viewHolder.getAdapterPosition()).getDiscount());
                mIntent.putExtra("finalPrice", roomModelList.get(viewHolder.getAdapterPosition()).getFinalPrice());
                mIntent.putExtra("hotelName", roomModelList.get(viewHolder.getAdapterPosition()).getHotelName());
                mCtx.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return roomModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView t1,t2,t3;
        LinearLayout l1;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.room_img);
            t1 = (TextView) itemView.findViewById(R.id.room_name);
            t2 = (TextView) itemView.findViewById(R.id.room_type);
            t3 = (TextView) itemView.findViewById(R.id.bdt_r);
            l1 = (LinearLayout) itemView.findViewById(R.id.l1);

        }
    }

}
