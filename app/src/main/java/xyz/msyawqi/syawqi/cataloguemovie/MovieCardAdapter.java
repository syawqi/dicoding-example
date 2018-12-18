package xyz.msyawqi.syawqi.cataloguemovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by muhammadsyawqi on 06/06/18.
 */

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.CardViewViewHolder>{
    private Context context;
    private ArrayList<MovieItem> mData;
    RecyclerView mmRecyclerView;
    public MovieCardAdapter(Context context) {
        this.context = context;
    }

    public void addItem(final MovieItem item){
        mData.add(item);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<MovieItem> mData){
        this.mData=mData;
    }

    ArrayList<MovieItem> getmData() {
        return mData;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    @Override
    public CardViewViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_item, parent, false);


        return new CardViewViewHolder(itemRow);

    }

    @Override
    public int getItemCount() {
        if(mData == null) {
            return 0;
        }else{
            return mData.size();
        }
    }


    @Override
    public void onBindViewHolder(CardViewViewHolder holder, final int position) {
//        Log.d("C", "onBindViewHolder: " + getmData().get(position).getTitle());
        holder.textViewTitle.setText(getmData().get(position).getTitle());
        holder.textViewDesc.setText(getmData().get(position).getDesc());
//        holder.textViewDate.setText(getmData().get(position).getDate());
//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context , DetailActivity.class);
                detail.putExtra("name" , getmData().get(position).getTitle());
                detail.putExtra("date" , getmData().get(position).getDate());
                detail.putExtra("desc" , getmData().get(position).getDesc());
                detail.putExtra("image" , getmData().get(position).getImage());
                context.startActivity(detail);
            }
        });

        Glide.with(context)
                .load(getmData().get(position).getImage())
                .override(55, 55)
                .crossFade()
                .into(holder.imageView);

    }

    public class CardViewViewHolder extends ViewHolder {
        TextView textViewTitle;
        TextView textViewDesc;
        TextView textViewDate;
        ImageView imageView;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView)itemView.findViewById(R.id.tv_item_name);
//            textViewDate = (TextView)itemView.findViewById(R.id.tv_date);
            textViewDesc = (TextView)itemView.findViewById(R.id.tv_item_remarks);
            imageView = (ImageView)itemView.findViewById(R.id.img_item_photo);
        }
    }
}
