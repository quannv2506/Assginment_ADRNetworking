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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.example.assginment.R;
import com.example.assginment.adapter.ViewPagerAdapter;
import com.example.assginment.model.Image;
import com.example.assginment.model.PhotoFavorite;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PictureActivity extends AppCompatActivity {
    private static final int PERMISSION_STORAGE_CODE1 = 1000;
    private static final int PERMISSION_STORAGE_CODE2 = 2000;
    private static final int PERMISSION_STORAGE_CODE3 = 3000;
    public static List<PhotoFavorite> photoFavoriteList;
    ImageView imgPicture;
    TextView tvName;
    FloatingActionButton btnDownload1, btnDownload2, btnDownload3, btnShare, btnSetWall;
    String pathalias, title;
    int position;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;
    ArrayList<Image> listUrl;
    Image image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        imgPicture = findViewById(R.id.imgPicture1);
        tvName = findViewById(R.id.tvName);
        viewPager = findViewById(R.id.viewPager);
        btnDownload1 = findViewById(R.id.btnDownload1);
        btnDownload2 = findViewById(R.id.btnDownload2);
        btnDownload3 = findViewById(R.id.btnDownload3);
        btnShare = findViewById(R.id.btnShare);
        btnSetWall = findViewById(R.id.btnSetWall);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");
        title = bundle.getString("Title");
        pathalias = bundle.getString("Pathalias");

        Log.e("PT", position + "");
        viewPagerAdapter = new ViewPagerAdapter(photoFavoriteList, PictureActivity.this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(position);

        check(position);
        tvName.setText(listUrl.get(listUrl.size() - 1).getTitle());
        btnDownload1.setLabelText(listUrl.get(listUrl.size() - 1).getHeight() + " x " + listUrl.get(listUrl.size() - 1).getWidth());
        btnDownload2.setLabelText(listUrl.get(listUrl.size() - 2).getHeight() + " x " + listUrl.get(listUrl.size() - 2).getWidth());
        btnDownload3.setLabelText(listUrl.get(listUrl.size() - 3).getHeight() + " x " + listUrl.get(listUrl.size() - 3).getWidth());
        btnShare.setLabelText("Chia sẻ Facebook");
        btnSetWall.setLabelText("Đặt hình nền");
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                check(position);
                tvName.setText(listUrl.get(listUrl.size() - 1).getTitle());
                btnDownload1.setLabelText(listUrl.get(listUrl.size() - 1).getHeight() + " x " + listUrl.get(listUrl.size() - 1).getWidth());
                btnDownload2.setLabelText(listUrl.get(listUrl.size() - 2).getHeight() + " x " + listUrl.get(listUrl.size() - 2).getWidth());
                btnDownload3.setLabelText(listUrl.get(listUrl.size() - 3).getHeight() + " x " + listUrl.get(listUrl.size() - 3).getWidth());

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


        btnDownload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission denied request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE1);
                    } else {
                        startDownLoading1();
                    }
                } else {
                    startDownLoading1();
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
                        startDownLoading2();
                    }
                } else {
                    startDownLoading2();
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
                        startDownLoading3();
                    }
                } else {
                    startDownLoading3();
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(listUrl.get(listUrl.size() - 1).getUrl()))
                        .build();

                ShareDialog shareDialog = new ShareDialog(PictureActivity.this);
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
            }
        });

        btnSetWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                WallpaperManager wpm = WallpaperManager.getInstance(PictureActivity.this);
                InputStream ins = null;

                try {
                    ins = new URL(listUrl.get(listUrl.size() - 1).getUrl()).openStream();
                    wpm.setStream(ins);
                    Toast.makeText(PictureActivity.this, "Đã thay đổi ảnh nền", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });

    }

    private void check(int position) {
        listUrl = new ArrayList<>();
        if (photoFavoriteList.get(position).getUrlSq() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlSq());
            image.setWidth(photoFavoriteList.get(position).getWidthSq().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightSq().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoFavoriteList.get(position).getUrlT() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlT());
            image.setWidth(photoFavoriteList.get(position).getWidthT().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightT().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoFavoriteList.get(position).getUrlS() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlS());
            image.setWidth(photoFavoriteList.get(position).getWidthS().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightS().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoFavoriteList.get(position).getUrlQ() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlQ());
            image.setWidth(photoFavoriteList.get(position).getWidthQ().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightQ().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoFavoriteList.get(position).getUrlM() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlM());
            image.setWidth(photoFavoriteList.get(position).getWidthM().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightM().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoFavoriteList.get(position).getUrlN() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlN());
            image.setWidth(photoFavoriteList.get(position).getWidthN().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightN().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoFavoriteList.get(position).getUrlZ() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlZ());
            image.setWidth(photoFavoriteList.get(position).getWidthZ().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightZ().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoFavoriteList.get(position).getUrlC() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlC());
            image.setWidth(photoFavoriteList.get(position).getWidthC().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightC().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoFavoriteList.get(position).getUrlL() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlL());
            image.setWidth(photoFavoriteList.get(position).getWidthL().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightL().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoFavoriteList.get(position).getUrlO() != null) {
            image = new Image();
            image.setUrl(photoFavoriteList.get(position).getUrlO());
            image.setWidth(photoFavoriteList.get(position).getWidthO().toString());
            image.setHeight(photoFavoriteList.get(position).getHeightO().toString());
            image.setTitle(photoFavoriteList.get(position).getTitle());
            listUrl.add(image);
        }
    }


    private void startDownLoading1() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(listUrl.get(listUrl.size() - 1).getUrl()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("BackgroundHD" + listUrl.get(listUrl.size() - 1).getUrl().substring(35));
        request.setDescription(listUrl.get(listUrl.size() - 1).getUrl());
        Toast.makeText(this, "Downloading...!", Toast.LENGTH_SHORT).show();
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + System.currentTimeMillis());
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    private void startDownLoading2() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(listUrl.get(listUrl.size() - 2).getUrl()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("BackgroundHD" + listUrl.get(listUrl.size() - 2).getUrl().substring(35));
        request.setDescription(listUrl.get(listUrl.size() - 2).getUrl());
        Toast.makeText(this, "Downloading...!", Toast.LENGTH_SHORT).show();
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + System.currentTimeMillis());
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    private void startDownLoading3() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(listUrl.get(listUrl.size() - 3).getUrl()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("BackgroundHD" + listUrl.get(listUrl.size() - 3).getUrl().substring(35));
        request.setDescription(listUrl.get(listUrl.size() - 3).getUrl());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + System.currentTimeMillis());
        Toast.makeText(this, "Downloading...!", Toast.LENGTH_SHORT).show();
        request.allowScanningByMediaScanner();
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownLoading1();
                } else {
                    Toast.makeText(PictureActivity.this, "Denied", Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_STORAGE_CODE2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownLoading2();
                } else {
                    Toast.makeText(PictureActivity.this, "Denied", Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_STORAGE_CODE3: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownLoading3();
                } else {
                    Toast.makeText(PictureActivity.this, "Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // arrayUrl.clear();

    }
}