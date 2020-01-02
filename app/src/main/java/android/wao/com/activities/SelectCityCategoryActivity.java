package android.wao.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.wao.com.Database.FillData;
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
    FillData fillData;
    Spinner citySpinner;
    Spinner categorySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city_category_layout);
        FillData fillData = new FillData();
        fillSpinners();
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

    public void mapButtonClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MapsActivity.class);
        intent.putExtra("city", citySpinner.getSelectedItem().toString());
        intent.putExtra("type_business", categorySpinner.getSelectedItem().toString());
        Log.d(TAG, citySpinner.getSelectedItem().toString());
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);
    }

    public void listButtonClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, StoresListActivity.class);
        intent.putExtra("city", citySpinner.getSelectedItem().toString());
        intent.putExtra("type_business", categorySpinner.getSelectedItem().toString());
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);

    }
}
