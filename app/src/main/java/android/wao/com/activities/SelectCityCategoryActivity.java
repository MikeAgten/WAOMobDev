package android.wao.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.wao.com.R;
import android.wao.com.adapters.RecyclerViewAdapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectCityCategoryActivity extends AppCompatActivity {

    private static final String TAG = "SelectCityCategoryActiv";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city_category_layout);
        fillSpinners();
    }


    private void fillSpinners() {
        Spinner citySpinner = findViewById(R.id.citySpinner);
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
