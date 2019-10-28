package android.wao.com;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.wao.com.data.StoresContract;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase storesDB;

    Cursor cursor = getAllGuests();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button loginButton = findViewById(R.id.button_login);
        Button registerButton = findViewById(R.id.button_register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //nieuwe databasehelper (zoals Context bij .net
        StoresDatabaseHelper dbHelper = new StoresDatabaseHelper(this);
        //nieuwe SQLiteDatabase vullen met onze database
        storesDB = dbHelper.getWritableDatabase();
    }

    private Cursor getAllGuests() {
        return storesDB.query(StoresContract.StoreEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                StoresContract.StoreEntry.COLUMN_NAME_NAME);
    }

}
