package com.example.assginment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.assginment.InternetConnection;
import com.google.gson.Gson;
import com.example.assginment.EndlessRecyclerViewScrollListener;
import com.example.assginment.adapter.MyAdapter;
import com.example.assginment.R;
import com.example.assginment.model.ExampleFavorite;
import com.example.assginment.model.PhotoFavorite;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView rvList;
    /*    private static final String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
        private static final String USER_ID = "187016535@N03";
        private static final String KEY_TOKEN = "9d788c3ae7173a1cda830edcc1be5792";
        private static final String KEY_TOKEN1 = "214108567c95be5668351078c66d4829";
        private static final String GET_FAVORITE = "flickr.favorites.getList";*/
    int pages = 1;
    List<PhotoFavorite> photoFavoriteList;

    MyAdapter myAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ExampleFavorite exampleFavorite;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    SwipeRefreshLayout srlRefesh;

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        rvList = findViewById(R.id.rvList);
        srlRefesh = findViewById(R.id.srlRefesh);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        srlRefesh.setOnRefreshListener(this);
        if (InternetConnection.checkConnection(this)) {
            showData();
            srlRefesh.setOnRefreshListener(this);

        } else {
            Toast.makeText(this, "Xin hãy kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void showData() {
        photoFavoriteList = new ArrayList<>();

        myAdapter = new MyAdapter(photoFavoriteList, MainActivity.this);
        rvList.setHasFixedSize(true);
        rvList.setAdapter(myAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvList.setLayoutManager(staggeredGridLayoutManager);
        Log.e("11", 3333 + "");

        AndroidFast();
        //loadmore
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pages++;
                Log.e("22", 2222 + "");
                AndroidFast();

            }
        };
        rvList.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

    public void AndroidFast() {
        AndroidNetworking.get("https://www.flickr.com/services/rest/")
                .addQueryParameter("method", "flickr.favorites.getList")
                .addQueryParameter("api_key", "214108567c95be5668351078c66d4829")
                .addQueryParameter("user_id", "187016535@N03")
                .addQueryParameter("extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                .addQueryParameter("per_page", "10")
                .addQueryParameter("page", String.valueOf(pages))
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
//                .setTag(this)
//                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        exampleFavorite = new Gson().fromJson(response.toString(), ExampleFavorite.class);
                        // thêm toàn bộ dữ liệu vào list
                        photoFavoriteList.addAll(exampleFavorite.getPhotosFavorite().getPhotoFavorite());
                        // thông báo cập nhật lại một vị trí được thêm mới
                        myAdapter.notifyItemInserted(photoFavoriteList.size());

                        PictureActivity.photoFavoriteList = photoFavoriteList;
                        Log.e("11", 2222 + "");
                        //nếu đến page cuối thì không load nữa
                        if (exampleFavorite.getPhotosFavorite().getPhotoFavorite().size() == exampleFavorite.getPhotosFavorite().getPhotoFavorite().size() - 1) {
                            rvList.addOnScrollListener(null);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("error", error.getMessage());
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.galleries:
                startActivity(new Intent(MainActivity.this, GalleriesActivity.class));
                overridePendingTransition(R.xml.slide_in_from_right, R.xml.slide_out_to_left);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if (InternetConnection.checkConnection(MainActivity.this)) {
            showData();
            photoFavoriteList.clear();
            myAdapter.notifyDataSetChanged();
            myAdapter.notifyItemChanged(0, photoFavoriteList.size());
            pages = 1;
            AndroidFast();
            srlRefesh.setRefreshing(false);
        } else {
            Toast.makeText(MainActivity.this, "Xin hãy kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            srlRefesh.setRefreshing(false);
        }
    }
}