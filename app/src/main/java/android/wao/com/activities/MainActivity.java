package android.wao.com.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.wao.com.Database.WaoDatabase;
import android.wao.com.R;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private ImageButton seePasswordButton;
    private static WaoDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEditText = findViewById(R.id.userNameEditText);
        seePasswordButton = findViewById(R.id.seePasswordButton);
        passwordEditText = findViewById(R.id.passwordNameEditText);
        Log.d(TAG, "onCreate: started");
        mAuth = FirebaseAuth.getInstance();
        db = Room.databaseBuilder(getApplicationContext(),
                WaoDatabase.class, "WaoDB").allowMainThreadQueries().build();
        Log.d(TAG, db.toString());
        //checkIfDatabaseExists(db);

    }

    public static WaoDatabase getDb(){
        return db;
    }

    public void checkIfDatabaseExists(WaoDatabase database){
            if(database.isOpen()){
                //alles in orde
            }else{
                db = Room.databaseBuilder(getApplicationContext(),
                        WaoDatabase.class, "WaoDB").build();
            }
        //app/schemas has a json file with database info now

    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

    }

    public void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //update the UI
                        }else{
                            Log.w(TAG,"CreateUSerWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this,"Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signInUser(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // update the ui
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                            // update the uit
                        }

                        // ...
                    }
                });

    }


    public void loginButtonClick(View view) {
        EditText  user = (EditText) findViewById(R.id.userNameEditText);
        String username = user.getText().toString();
        EditText  pass = (EditText) findViewById(R.id.passwordNameEditText);
        String password = pass.getText().toString();
        if(username.isEmpty()  || password.isEmpty()){
            Toast.makeText(MainActivity.this, "Fill in user credentials",
                    Toast.LENGTH_SHORT).show();
        }else{
            signInUser(username,password);
            if(mAuth.getCurrentUser() != null){
                Intent intent = new Intent();
                intent.setClass(this, SelectCityCategoryActivity.class);
                Log.d(TAG, "onClick: CLICKED");
                startActivity(intent);
            }
        }
    }

    public void registerButtonClick(View view) {
        //userNameTextView
        EditText  user = (EditText) findViewById(R.id.userNameEditText);
        String username = user.getText().toString();
        EditText  pass = (EditText) findViewById(R.id.passwordNameEditText);
        String password = pass.getText().toString();

        createUser(username,password);


       /*
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        Log.d(TAG, "onClick: CLICKED");
        startActivity(intent);

        */
    }

    public void seePasswordButtonClick(View view) {
        seePasswordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println(" pressed ");
                        passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passwordEditText.setSelection(passwordEditText.getText().length());
                        return true;
                    case MotionEvent.ACTION_UP:
                        System.out.println(" released ");
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordEditText.setSelection(passwordEditText.getText().length());
                        return true;
                }
                return false;
            }
        });
    }
}
