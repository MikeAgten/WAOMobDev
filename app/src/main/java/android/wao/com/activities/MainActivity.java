package android.wao.com.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.wao.com.R;
import android.wao.com.adapters.RecyclerViewAdapter;
import android.wao.com.data.StoresContract;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main); // dit is de main scherm ik heb veranderd naar list voor test
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

    }


    public void loginButtonClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, StoresListActivity.class);
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);
    }

    public void registerButtonClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);
    }
}
