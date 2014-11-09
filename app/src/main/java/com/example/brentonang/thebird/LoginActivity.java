package com.example.brentonang.thebird;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ian on 11/9/14.
 */
public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        if (!((EditText)findViewById(R.id.username)).getText().toString().equals("") && !((EditText)findViewById(R.id.password)).getText().toString().equals("")) {
            MainActivity.userAuthorized = true;
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Missing username/password";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void forgotPassword(View view) {

    }
}
