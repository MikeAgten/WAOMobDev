package android.wao.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.wao.com.Database.WaoDatabase;
import android.wao.com.R;
import android.wao.com.adapters.RecyclerViewAdapter;
import android.wao.com.model.Shop;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class SelectCityCategoryActivity extends AppCompatActivity {

    private static final String TAG = "SelectCityCategoryActiv";
    WaoDatabase db = MainActivity.getDb();
    Spinner citySpinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city_category_layout);

        fillSpinners();

        if(db.shopDAO().getAll().size() == 0){
            List<Shop> shopsdummy = fillDatabase();

            for(int i=0; i< shopsdummy.size(); i++){
                db.shopDAO().insertAll(shopsdummy.get(i));
            }
        }else{
            Log.d(TAG,"er zit al data in de database");
        }


    }

    private List<Shop> fillDatabase(){
        List<Shop> shopsDummy = new ArrayList<>();
        Shop shop1 = new Shop();
        shop1.shopName = "Zara";
        shop1.typeBusiness = "clothing";
        shop1.visitCounter = 1;

        Shop shop2 = new Shop();
        shop2.shopName = "Jack en Jones";
        shop2.typeBusiness = "clothing";
        shop2.visitCounter = 3;

        Shop shop3 = new Shop();
        shop3.shopName = "H en M";
        shop3.typeBusiness = "clothing";
        shop3.visitCounter= 5;

        Shop shop4 = new Shop();
        shop4.shopName = "Pizza hut";
        shop4.typeBusiness = "Restaurant";
        shop4.visitCounter= 5;

        shopsDummy.add(shop1);
        shopsDummy.add(shop2);
        shopsDummy.add(shop3);
        shopsDummy.add(shop4);
     return shopsDummy;
    }




    private void fillSpinners() {
        citySpinner = findViewById(R.id.citySpinner);
        //create a list of items for the spinner.
        String[] cities = new String[]{"Hasselt", "Genk", "Peer", "Antwerpen"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
//set the spinners adapter to the previously created one.
        citySpinner.setAdapter(cityAdapter);

        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        //create a list of items for the spinner.
        String[] categories = new String[]{"MannenMode", "Doe het zelf", "Schoenen", "Media"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
//set the spinners adapter to the previously created one.
        categorySpinner.setAdapter(categoryAdapter);
    }

    public void mapButtonClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, MapsActivity.class);
        intent.putExtra("city",citySpinner.getSelectedItem().toString());
        Log.d(TAG,citySpinner.getSelectedItem().toString());
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);
    }

    public void listButtonClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, StoresListActivity.class);
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);
    }
}
