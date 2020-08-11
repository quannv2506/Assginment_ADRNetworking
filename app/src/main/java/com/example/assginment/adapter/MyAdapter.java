package com.example.assginment.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assginment.R;
import com.example.assginment.activity.DetailActivity;
import com.example.assginment.activity.PictureActivity;
import com.example.assginment.model.PhotoFavorite;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> implements Filterable {
    private List<PhotoFavorite> photoFavoriteList;
    private List<PhotoFavorite> photoFavoriteListFull;
    Context context;
    public ConstraintSet set;
//    ArrayList<Image> listUrl;
//    Image image;

    public MyAdapter(List<PhotoFavorite> photoFavoriteList, Context context) {
        this.photoFavoriteList = photoFavoriteList;
        this.context = context;
        photoFavoriteListFull = new ArrayList<>(photoFavoriteList);

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        set = new ConstraintSet();
        int width = photoFavoriteList.get(position).getWidthS();
        int height = photoFavoriteList.get(position).getHeightS();
        String ratio = String.format("%d:%d", width, height);
        set.clone(holder.constraintLayout);
        set.setDimensionRatio(holder.imgPicture.getId(), ratio);
        set.applyTo(holder.constraintLayout);
        Glide.with(context).load(photoFavoriteList.get(position).getUrlS()).into(holder.imgPicture);
        holder.tvView.setText(photoFavoriteList.get(position).getViews() + " views");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, PictureActivity.class);
                Intent intent = new Intent(context, DetailActivity.class);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context, holder.imgPicture, "imageTransition");
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("Title", photoFavoriteList.get(position).getTitle());
                Log.e("title", photoFavoriteList.get(position).getTitle());
                bundle.putString("Pathalias", photoFavoriteList.get(position).getPathalias());
                bundle.putString("urlM", photoFavoriteList.get(position).getUrlM());
                bundle.putString("urlC", photoFavoriteList.get(position).getUrlC());
                bundle.putString("urlL", photoFavoriteList.get(position).getUrlL());
                bundle.putString("heightM", String.valueOf(photoFavoriteList.get(position).getHeightM()));
                bundle.putString("widthM", String.valueOf(photoFavoriteList.get(position).getWidthM()));
                bundle.putString("heightC", String.valueOf(photoFavoriteList.get(position).getHeightC()));
                bundle.putString("widthC", String.valueOf(photoFavoriteList.get(position).getWidthC()));
                bundle.putString("heightL", String.valueOf(photoFavoriteList.get(position).getHeightL()));
                bundle.putString("widthL", String.valueOf(photoFavoriteList.get(position).getWidthL()));
                Log.e("POSITION",position+"");
                intent.putExtras(bundle);
                context.startActivity(intent, options.toBundle());
//                for (int i = 0; i < listUrl.size(); i++) {
//                    Log.e("GGGG", listUrl.get(i).getUrl());
//                }
//                listUrl.clear();

            }
        });
    }

    @Override
    public int getItemCount() {
        return photoFavoriteList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<PhotoFavorite> filterList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filterList.addAll(photoFavoriteList);
            } else {
                String Pattern = charSequence.toString().toLowerCase().trim();
                for (PhotoFavorite item : photoFavoriteList) {
                    if (item.getTitle().toLowerCase().contains(Pattern)) {
                        Log.e("title", item.getTitle());
                        filterList.add(item);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            photoFavoriteList.clear();
            photoFavoriteList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imgPicture;
        TextView tvView;
        ConstraintLayout constraintLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.imgPicture1);
            constraintLayout = itemView.findViewById(R.id.contraintLayout);
            tvView = itemView.findViewById(R.id.tvView1);
        }
    }
}
