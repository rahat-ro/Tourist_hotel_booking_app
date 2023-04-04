package com.argina.touristapp.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.argina.touristapp.R;
import com.argina.touristapp.booking.BookingModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mCtx;
    private List<BookingModel> bookingModelList;

    public HistoryAdapter (Context mCtx, List<BookingModel> bookingModelList) {
        this.mCtx = mCtx;
        this.bookingModelList = bookingModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.history_card_view,viewGroup,false);
        return new HistoryAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder viewHolder, int i) {

        BookingModel bookingModel = bookingModelList.get(i);
        viewHolder.t1.setText(bookingModel.getRoom_name());
        viewHolder.t2.setText(bookingModel.getRoom_type());
        viewHolder.t3.setText(bookingModel.getFinal_price());
        viewHolder.t6.setText(bookingModel.getHotel_name());
       // viewHolder.t5.setText(bookingModel.getClient_mob());
        viewHolder.t7.setText(bookingModel.getCheckin_date());
        viewHolder.t8.setText(bookingModel.getCheckout_date());
        viewHolder.t4.setText(bookingModel.getRoom_number());
        viewHolder.t9.setText(bookingModel.getClient_mob());

    }

    @Override
    public int getItemCount () {
        return bookingModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;

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

        }
    }
}
