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

import com.bumptech.glide.Glide;
import com.argina.touristapp.displaydetails.DescriptionDisplay;
import com.argina.touristapp.R;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private Context mCtx;
    private List<PlaceModel> placeModelList;

    public PlaceAdapter(Context mCtx, List<PlaceModel> placeModelList) {
        this.mCtx = mCtx;
        this.placeModelList = placeModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.place_model_view,viewGroup,false);
        return new PlaceAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


        PlaceModel placeModel = placeModelList.get(i);
        Glide.with(mCtx).load(placeModel.getPlaceImg()).into(viewHolder.placeImg);
        viewHolder.placeTitle.setText(placeModel.getPlaceTitle());
        viewHolder.Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mCtx, DescriptionDisplay.class);
                //mIntent.putExtra("BarTitle",getString());
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("Image2", placeModelList.get(viewHolder.getAdapterPosition()).getPlaceImg());
                mIntent.putExtra("Title", placeModelList.get(viewHolder.getAdapterPosition()).getPlaceTitle());
                mIntent.putExtra("Description", placeModelList.get(viewHolder.getAdapterPosition()).getPlaceDescription());
                mIntent.putExtra("Division", placeModelList.get(viewHolder.getAdapterPosition()).getPlaceDivision());
                mCtx.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImg;
        TextView placeTitle;
        CardView Rl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImg = (ImageView) itemView.findViewById(R.id.place_Pic);
            placeTitle = (TextView) itemView.findViewById(R.id.place_Title);
            Rl =(CardView) itemView.findViewById(R.id.rl);

        }
    }
}
