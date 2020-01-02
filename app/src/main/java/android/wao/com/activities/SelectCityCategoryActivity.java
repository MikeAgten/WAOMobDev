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
    Spinner categorySpinner;
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
        shop1.city = "Hasselt";
        shop1.latitute = 50.930432;
        shop1.longitute = 5.3354795;
        shop1.imageUrl = "jackandjones";
        shop1.open = "10:00–18:00";
        shop1.website = "https://www.zara.com/be/nl/";
        shop1.PhoneNumber = "011379690";




        Shop shop2 = new Shop();
        shop2.shopName = "Jack en Jones";
        shop2.typeBusiness = "clothing";
        shop2.visitCounter = 3;
        shop2.city = "Hasselt";
        shop2.latitute = 50.9286418;
        shop2. longitute = 5.3349701;
        shop2.imageUrl = "jackandjones";
        shop2.open = "09:00–18:00";
        shop2.website = "https://www.jackjones.com/be/nl/home?gclid=EAIaIQobChMIn56i3Y2X5gIVhLTtCh2mWgB6EAAYASAAEgJRA_D_BwE&gclsrc=aw.ds";
        shop2.PhoneNumber = "011781022";



        Shop shop3 = new Shop();
        shop3.shopName = "H en M";
        shop3.typeBusiness = "clothing";
        shop3.visitCounter= 5;
        shop3.city = "Hasselt";
        shop3.latitute = 50.9313688;
        shop3.longitute = 5.3357274;
        shop3.imageUrl="jackandjones";
        shop3.open = "08:00–14:00";

        shop3.website = "https://www2.hm.com/nl_be/index.html";
        shop3.PhoneNumber =  "011501920";

        Shop shop4 = new Shop();
        shop4.shopName = "Pizza hut";
        shop4.typeBusiness = "Restaurant";
        shop4.visitCounter= 5;
        shop4.city = "Hasselt";
        shop4.latitute = 50.938738;
        shop4.longitute = 5.3150144;
        shop4.imageUrl = "jackandjones";
        shop4.open = "11:00–18:30";
        shop4.website=  "https://restaurants.pizzahut.be/fr/restaurant/restaurant-hasselt";
        shop4.PhoneNumber = "011255806";

        Shop shop5 = new Shop();
        shop5.shopName = "Veritas";
        shop5.typeBusiness = "clothing";
        shop5.visitCounter = 0;
        shop5.city = "Genk";
        shop5.latitute = 50.9653089;
        shop5.longitute = 5.496772;
        shop5.imageUrl = "jackandjones";
        shop5.open = "10:30-19:00";
        shop5.website = "https://www.veritas.be/be_nl";
        shop5.PhoneNumber = "089858967";


        shopsDummy.add(shop1);
        shopsDummy.add(shop2);
        shopsDummy.add(shop3);
        shopsDummy.add(shop4);
        shopsDummy.add(shop5);
     return shopsDummy;
    }




    private void fillSpinners() {
        citySpinner = findViewById(R.id.citySpinner);
        //create a list of items for the spinner.
        String[] cities = new String[]{"Hasselt", "Genk", "Antwerpen"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
//set the spinners adapter to the previously created one.
        citySpinner.setAdapter(cityAdapter);

         categorySpinner = findViewById(R.id.categorySpinner);
        //create a list of items for the spinner.
        String[] categories = new String[]{"clothing", "Doe het zelf", "Schoenen", "Restaurant"};
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
        intent.putExtra("type_business", categorySpinner.getSelectedItem().toString());
        Log.d(TAG,citySpinner.getSelectedItem().toString());
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);
    }

    public void listButtonClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, StoresListActivity.class);
        intent.putExtra("city",citySpinner.getSelectedItem().toString());
        intent.putExtra("type_business", categorySpinner.getSelectedItem().toString());
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);
    }
}
