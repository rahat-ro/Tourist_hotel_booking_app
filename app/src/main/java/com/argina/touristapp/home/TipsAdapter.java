package com.argina.touristapp.home;

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

import com.argina.touristapp.displaydetails.Tips;
import com.bumptech.glide.Glide;
import com.argina.touristapp.R;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {

    private Context mCtx;
    private List<TipsModel> tipsModelList;

    public TipsAdapter(Context mCtx, List<TipsModel> tipsModelList) {
        this.mCtx = mCtx;
        this.tipsModelList = tipsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.tips_view,viewGroup,false);
        return new TipsAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


        TipsModel tipsModel = tipsModelList.get(i);
        Glide.with(mCtx).load(tipsModel.getTipsImg()).into(viewHolder.tipsImg);
        viewHolder.tipsTitle.setText(tipsModel.getTipsTitle());
        viewHolder.Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mCtx, Tips.class);
                //mIntent.putExtra("BarTitle",getString());
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("Image2", tipsModelList.get(viewHolder.getAdapterPosition()).getTipsImg());
                mIntent.putExtra("Title", tipsModelList.get(viewHolder.getAdapterPosition()).getTipsTitle());
                mIntent.putExtra("Description", tipsModelList.get(viewHolder.getAdapterPosition()).getTipsDescription());
                mCtx.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tipsModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView tipsImg;
        TextView tipsTitle;
        CardView Rl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tipsImg = (ImageView) itemView.findViewById(R.id.tips_pic);
            tipsTitle = (TextView) itemView.findViewById(R.id.tips_titleOne);
            Rl =(CardView) itemView.findViewById(R.id.rl);

        }
    }
}