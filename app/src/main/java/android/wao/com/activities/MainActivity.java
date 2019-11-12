package android.wao.com.activities;

import androidx.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main); // dit is de main scherm ik heb veranderd naar list voor test
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        mAuth = FirebaseAuth.getInstance();

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
}
