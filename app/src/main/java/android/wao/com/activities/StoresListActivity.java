package android.wao.com.activities;

import android.content.Intent;
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

    //Vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();//urls van images in te laden

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Log.d(TAG, "onClick: CLICKED");

        startActivity(intent);
    }

    private void initImageBitMaps(){
        Log.d(TAG, "initImageBitMaps: preparing bitmaps");
        List<Shop> shopsData = db.shopDAO().getAll();
        for(int i=0; i< shopsData.size(); i++){
            mNames.add(shopsData.get(i).shopName);
        }
        /*
        mNames.add("Jack and Jones");
        mImageUrls.add("R.drawable.jackandjones");
        mNames.add("Primark");
        mNames.add("Balenciaga");
        mNames.add("Gucci");
        mNames.add("Stone Island");

         */

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recuclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNames, mImageUrls, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
