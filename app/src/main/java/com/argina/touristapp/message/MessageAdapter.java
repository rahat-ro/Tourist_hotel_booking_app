package com.argina.touristapp.message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.argina.touristapp.R;


import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mCtx;
    private List<MessageModel> messageModelList;

    public MessageAdapter(Context mCtx, List<MessageModel> messageModelList) {
        this.mCtx = mCtx;
        this.messageModelList = messageModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.message_card_view,viewGroup,false);
        return new MessageAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        MessageModel messageModel = messageModelList.get(i);

        viewHolder.t1.setText(messageModel.getClientMob());
        viewHolder.t2.setText(messageModel.getMessageBody());

    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView t1,t2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = (TextView) itemView.findViewById(R.id.m_mob);
            t2 = (TextView) itemView.findViewById(R.id.m_body);


        }
    }

}
