package com.example.assginment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assginment.R;
import com.example.assginment.adapter.ViewPagerAdapter;
import com.example.assginment.model.Image;
import com.example.assginment.model.PhotoFavorite;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final int PERMISSION_STORAGE_CODE1 = 1000;
    private static final int PERMISSION_STORAGE_CODE2 = 2000;
    private static final int PERMISSION_STORAGE_CODE3 = 3000;
    public static List<PhotoFavorite> photoFavoriteList;
    ImageView imgPicture;
    TextView tvTitle;
    FloatingActionButton btnDownload1, btnDownload2, btnDownload3, btnShare, btnSetWall;
    String pathalias, title, urlM, urlC, urlL, heightM, widthM, heightC, widthC, heightL, widthL;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();
        imgPicture = findViewById(R.id.imgPicture1);
        tvTitle = findViewById(R.id.tvTitle);

        btnDownload1 = findViewById(R.id.btnDownload1);
        btnDownload2 = findViewById(R.id.btnDownload2);
        btnDownload3 = findViewById(R.id.btnDownload3);
        btnShare = findViewById(R.id.btnShare);
        btnSetWall = findViewById(R.id.btnSetWall);

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");
        title = bundle.getString("Title");
        pathalias = bundle.getString("Pathalias");
        urlM = bundle.getString("urlM");
        urlC = bundle.getString("urlC");
        urlL = bundle.getString("urlL");
        heightM = bundle.getString("heightM");
        widthM = bundle.getString("widthM");
        heightC = bundle.getString("heightC");
        widthC = bundle.getString("widthC");
        heightL = bundle.getString("heightL");
        widthL = bundle.getString("widthL");


        Log.e("PT", position + "");



        tvTitle.setText(title);
        if (heightM == null || widthM == null){
            btnDownload3.setVisibility(View.GONE);
        } else {
            btnDownload3.setLabelText(heightM + " x " + widthM);
        }

        if (heightC == null || widthC == null){
            btnDownload2.setVisibility(View.GONE);
        } else {
            btnDownload2.setLabelText(heightC + " x " + widthC);
        }

        if (heightL == null || widthL == null){
            btnDownload1.setVisibility(View.GONE);
        } else {
            btnDownload1.setLabelText(heightL + " x " + widthL);
        }

        btnShare.setLabelText("Chia sẻ Facebook");
        btnSetWall.setLabelText("Đặt hình nền");

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Picasso.get().load(urlM).into(imgPicture);
        btnDownload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission denied request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE1);
                    } else {
                        startDownLoading1(urlL);
                    }
                } else {
                    startDownLoading1(urlL);
                }
            }
        });
        btnDownload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission denied request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE2);
                    } else {
                        startDownLoading1(urlC);
                    }
                } else {
                    startDownLoading1(urlC);
                }
            }
        });

        btnDownload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission denied request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE3);
                    } else {
                        startDownLoading1(urlM);
                    }
                } else {
                    startDownLoading1(urlM);
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, urlM);
                shareIntent.setPackage("com.facebook.katana");
                startActivity(shareIntent);
            }
        });

        btnSetWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                WallpaperManager wpm = WallpaperManager.getInstance(DetailActivity.this);
                InputStream ins = null;
                try {
                    ins = new URL(urlL).openStream();
                    wpm.setStream(ins);
                    Toast.makeText(DetailActivity.this, "Đã đặt ảnh nền", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });
    }


    private void startDownLoading1(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("BackgroundHD" + url);
        request.setDescription(url);
        Toast.makeText(this, "Downloading...!", Toast.LENGTH_SHORT).show();
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + System.currentTimeMillis());
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownLoading1(urlL);
                } else {
                    Toast.makeText(DetailActivity.this, "Denied", Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_STORAGE_CODE2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownLoading1(urlC);
                } else {
                    Toast.makeText(DetailActivity.this, "Denied", Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_STORAGE_CODE3: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownLoading1(urlM);
                } else {
                    Toast.makeText(DetailActivity.this, "Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}