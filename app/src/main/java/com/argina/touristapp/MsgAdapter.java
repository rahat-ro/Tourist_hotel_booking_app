package com.argina.touristapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.argina.touristapp.booking.BookingModel;
import com.argina.touristapp.history.HistoryAdapter;
import com.argina.touristapp.hotelroom.HotelRoomActivity;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private Context mCtx;
    private List<BookingModel> bookingModelList;

    public MsgAdapter (Context mCtx, List<BookingModel> bookingModelList) {
        this.mCtx = mCtx;
        this.bookingModelList = bookingModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.msg_layout,viewGroup,false);
        return new MsgAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder (@NonNull final ViewHolder viewHolder, int i) {

        final BookingModel bookingModel = bookingModelList.get(i);
        viewHolder.t1.setText(bookingModel.getRoom_name());
        viewHolder.t2.setText(bookingModel.getRoom_type());
        viewHolder.t3.setText(bookingModel.getFinal_price());
        viewHolder.t6.setText(bookingModel.getHotel_name());
        viewHolder.t5.setText(bookingModel.getRoom_status());
        viewHolder.t7.setText(bookingModel.getCheckin_date());
        viewHolder.t8.setText(bookingModel.getCheckout_date());
        viewHolder.t4.setText(bookingModel.getRoom_number());
        viewHolder.t9.setText(bookingModel.getClient_mob());
        viewHolder.l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent mIntent = new Intent(mCtx, SMS.class);
                //mIntent.putExtra("BarTitle",getString());
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("mob_no", bookingModelList.get(viewHolder.getAdapterPosition()).getClient_mob());
                mCtx.startActivity(mIntent);

            }
        });

    }

    @Override
    public int getItemCount () {
        return bookingModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
        LinearLayout l1;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);


            t1 = (TextView) itemView.findViewById(R.id.h_roomName);
            t2 = (TextView) itemView.findViewById(R.id.h_roomType);
            t3 = (TextView) itemView.findViewById(R.id.h_roomFinalPrice);
            t4 = (TextView) itemView.findViewById(R.id.h_roomNumber);
            t5 = (TextView) itemView.findViewById(R.id.h_roomStatus);
            t6 = (TextView) itemView.findViewById(R.id.h_HotelName);
            t7 = (TextView) itemView.findViewById(R.id.h_in);
            t8 = (TextView) itemView.findViewById(R.id.h_out);
            t9 = (TextView) itemView.findViewById(R.id.h_client_mob);
            l1 = (LinearLayout) itemView.findViewById(R.id.l1);

        }
    }

}
