package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static String usernameKey;

    public void onButtonClick(View view){
        EditText user = (EditText) findViewById(R.id.user);
        String str = user.getText().toString();

        //1. Get username and password via EditText view
        //2. Add username to SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", str).apply();
        //3. Start second activity
        goToActivity2(str);
    }

    public void goToActivity2(String s) {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(usernameKey, "").equals("")){
            // "username" key exists in SharedPreferences object, which means user was logged in.
            String str = sharedPreferences.getString(usernameKey, "");
            goToActivity2(str);
        }
        else{
            //SharedPreferences object has no username key set.
            //Start screen 1, that is the main activity.
            setContentView(R.layout.activity_main);
        }
        setContentView(R.layout.activity_main);
    }
}
