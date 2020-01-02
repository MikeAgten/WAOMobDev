package android.wao.com.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.wao.com.Database.WaoDatabase;
import android.wao.com.R;
import android.wao.com.adapters.RecyclerViewAdapter;
import android.wao.com.model.Shop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StoresListActivity extends AppCompatActivity {

    private static final String TAG = "StoresListActivity";
    WaoDatabase db = MainActivity.getDb();
    String typeBusiness;
    String city;

    //Vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();//urls van images in te laden
    private ArrayList<String> websiteUrls = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeBusiness = getIntent().getExtras().getString("type_business","defaultKey");
        city = getIntent().getExtras().getString("city","defaultKey");
        setContentView(R.layout.activity_stores_list);
        initImageBitMaps();
        logDb();
    }

    //Just to check whats in the database
    public void logDb(){
        List<Shop> shops = db.shopDAO().getAll();
        for(int i=0; i< shops.size(); i++){
            Log.d(TAG,shops.get(i).shopName);
        }
    }

    public void mapsButtonClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MapsActivity.class);
        intent.putExtra("city",city);
        intent.putExtra("type_business", typeBusiness);
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);
    }


    private void initImageBitMaps(){
        Log.d(TAG, "initImageBitMaps: preparing bitmaps");
        List<Shop> shopsData = db.shopDAO().getAll();
        for(int i=0; i< shopsData.size(); i++){

            if(typeBusiness.equals(shopsData.get(i).typeBusiness) && city.equals(shopsData.get(i).city)){
                mNames.add(shopsData.get(i).shopName);
                mImageUrls.add(shopsData.get(i).imageUrl);
                websiteUrls.add(shopsData.get(i).website);
            }
        }
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recuclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNames, mImageUrls, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void websiteButtonClick(View view) {
        String url = "https://www.youtube.com/watch?v=_KLZhtbI3js";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
