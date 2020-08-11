package com.example.assginment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.example.assginment.EndlessRecyclerViewScrollListener;
import com.example.assginment.R;
import com.example.assginment.adapter.PhotoAdapter;
import com.example.assginment.model.ExamplePhoto;
import com.example.assginment.model.PhotoGP;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryActivity extends AppCompatActivity {
//    private static final String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
//    private static final String KEY_TOKEN = "9d788c3ae7173a1cda830edcc1be5792";
//    private static final String KEY_TOKEN1="214108567c95be5668351078c66d4829";
//    private static final String GET_FAVORITE = "flickr.galleries.getPhotos";
    String idPhoto;
    int pages = 1;
    SwipeRefreshLayout srlRefesh1;
    RecyclerView rvPhoto;
    List<PhotoGP> photoGPList;
    PhotoAdapter photoAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ExamplePhoto examplePhoto;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        srlRefesh1 = findViewById(R.id.srlRefesh1);
        rvPhoto = findViewById(R.id.rvPhoto);
        getId();
        FastPhoto();
        srlRefesh1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photoAdapter.notifyDataSetChanged();
                srlRefesh1.setRefreshing(false);
            }
        });

        photoGPList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(photoGPList, PhotoGalleryActivity.this);
        rvPhoto.setAdapter(photoAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvPhoto.setLayoutManager(staggeredGridLayoutManager);

        // thực thi lệnh loadmore khi kéo xuống
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pages++;
                Log.e("33", 3333 + "");
                FastPhoto();

            }
        };
        rvPhoto.addOnScrollListener(endlessRecyclerViewScrollListener);

    }

    private void getId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idPhoto = bundle.getString("idPhoto");
    }

    private void FastPhoto() {
        AndroidNetworking.get("https://www.flickr.com/services/rest/")
                .addQueryParameter("method", "flickr.galleries.getPhotos")
                .addQueryParameter("api_key", "214108567c95be5668351078c66d4829")
                .addQueryParameter("gallery_id", idPhoto)
                .addQueryParameter("extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                .addQueryParameter("per_page", "10")
                .addQueryParameter("page", String.valueOf(pages))
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responseq) {
                        examplePhoto = new Gson().fromJson(responseq.toString(), ExamplePhoto.class);

                        photoGPList.addAll(examplePhoto.getPhotosGP().getPhotoGP());
                        photoAdapter.notifyItemInserted(photoGPList.size());
                        Log.e("GGGG", examplePhoto.getPhotosGP().getPhotoGP().size() + "");
                        //nếu đến page cuối thì không load nữa
                        if (examplePhoto.getPhotosGP().getPhotoGP().size() == examplePhoto.getPhotosGP().getPhotoGP().size() - 1) {
                            rvPhoto.addOnScrollListener(null);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.xml.slide_in_from_left, R.xml.slide_out_to_right);

    }
}