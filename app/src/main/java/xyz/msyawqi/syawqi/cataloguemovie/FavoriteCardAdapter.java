package xyz.msyawqi.syawqi.cataloguemovie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;

import xyz.msyawqi.syawqi.cataloguemovie.database.Favorite;

import static xyz.msyawqi.syawqi.cataloguemovie.database.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class FavoriteCardAdapter extends RecyclerView.Adapter<FavoriteCardAdapter.CardViewViewHolder>{
    private Cursor listNotes;
    private Activity activity;
    private Context context;

    public FavoriteCardAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListNotes(Cursor listNotes) {
        this.listNotes = listNotes;
    }

    @Override
    public FavoriteCardAdapter.CardViewViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_item, parent, false);


        return new FavoriteCardAdapter.CardViewViewHolder(itemRow);

    }



    @Override
    public void onBindViewHolder(FavoriteCardAdapter.CardViewViewHolder holder, final int position) {
        final Favorite favorite = getItem(position);
        holder.textViewTitle.setText(favorite.getTitle());
        holder.textViewDesc.setText(favorite.getDesc());
        holder.textViewDate.setText(favorite.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(activity , DetaillFavoriteActivity.class);
                detail.putExtra("id" , favorite.getId());
                detail.putExtra("name" , favorite.getTitle());
                detail.putExtra("date" , favorite.getDate());
                detail.putExtra("desc" , favorite.getDesc());
                detail.putExtra("image" , favorite.getImage());
                activity.startActivity(detail);
            }
        });


        Glide.with(activity)
                .load(favorite.getImage())
                .override(55, 55)
                .crossFade()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (listNotes == null) return 0;
        return listNotes.getCount();
    }
    private Favorite getItem(int position){
        if (!listNotes.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(listNotes);
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDesc;
        TextView textViewDate;
        ImageView imageView;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView)itemView.findViewById(R.id.tv_item_name);
            textViewDate = (TextView)itemView.findViewById(R.id.tv_date);
            textViewDesc = (TextView)itemView.findViewById(R.id.tv_item_remarks);
            imageView = (ImageView)itemView.findViewById(R.id.img_item_photo);
        }
    }
}