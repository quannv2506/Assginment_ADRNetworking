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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assginment.R;
import com.example.assginment.activity.DetailActivity;
import com.example.assginment.model.PhotoGP;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.Holder> {
    List<PhotoGP> list;
    Context context;

    public PhotoAdapter(List<PhotoGP> photoGPList, Context context) {
        this.list = photoGPList;
        this.context = context;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        Glide.with(context).load(list.get(position).getUrlS()).into(holder.imgPicture);
        holder.tvView.setText(list.get(position).getViews()+" views");
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
                bundle.putString("Title", list.get(position).getTitle());
                Log.e("title", list.get(position).getTitle());
                bundle.putString("Pathalias", list.get(position).getPathalias());
                bundle.putString("urlM", list.get(position).getUrlM());
                bundle.putString("urlC", list.get(position).getUrlC());
                bundle.putString("urlL", list.get(position).getUrlL());
                bundle.putString("heightM", String.valueOf(list.get(position).getHeightM()));
                bundle.putString("widthM", String.valueOf(list.get(position).getWidthM()));
                bundle.putString("heightC", String.valueOf(list.get(position).getHeightC()));
                bundle.putString("widthC", String.valueOf(list.get(position).getWidthC()));
                bundle.putString("heightL", String.valueOf(list.get(position).getHeightL()));
                bundle.putString("widthL", String.valueOf(list.get(position).getWidthL()));
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
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgPicture;
        TextView tvView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imgPicture=itemView.findViewById(R.id.imgPicture1);
            tvView=itemView.findViewById(R.id.tvView1);
        }
    }
}
