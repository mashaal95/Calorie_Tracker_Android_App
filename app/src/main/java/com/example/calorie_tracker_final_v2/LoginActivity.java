package com.example.calorie_tracker_final_v2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.calorie_tracker_final_v2.GMapsActivity.retrievedAddress;
import static com.example.calorie_tracker_final_v2.MainFragment.pref;


public class LoginActivity extends AppCompatActivity {
    EditText logi;
    EditText passq;
    public static final String pref = "Preferences";
    public static final String userID = "userId";
    public static final String calorieGoal = "Calorie Goal";
    public static final String address = "address";
    public static final String email = "email";

    public static String firstName = "";
    public static String hashtext = "";
    public static Boolean checker1;
    SharedPreferences sp;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logi = findViewById(R.id.userName);
        passq = findViewById(R.id.password);
        sp = getSharedPreferences(pref, MODE_PRIVATE);



        Button findAllCoursesBtn = findViewById(R.id.login);

        findAllCoursesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserIdAsyncTask us = new UserIdAsyncTask();
                us.execute();
            }
        });
        Button reg = findViewById(R.id.register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    public void loginChecker(String userna, String pas) {
        if (userna.equals("123") && pas.equals("123")) {
            UserIdAsyncTask us = new UserIdAsyncTask();
            us.execute();
            Intent yy = new Intent(this, MainActivity.class);
            startActivity(yy);
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Login Success ", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            System.out.println("Login FAILED");
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Login Failed ", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class UserIdAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            username = logi.getText().toString();
            String password = passq.getText().toString();
            String encrypt = encryption1(password);
            return UrlHttp.getUserName(username, encrypt);
        }

        @Override
        protected void onPostExecute(Boolean check) {
            if (check) {
                MainActivity.logResponse = true;
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                Retrieve retrieve = new Retrieve();
                retrieve.execute();
            } else
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static String encryption1(String password) {

        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
        return generatedPassword;
    }

    private class Retrieve extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences preferences = getSharedPreferences(pref, MODE_PRIVATE);

            SharedPreferences.Editor edit = sp.edit();

            String user = sp.getString("username","");
            String first = UrlHttp.getUserFirstName(user);
            retrievedAddress = UrlHttp.getUserAddress(first);
            edit.putString("address",retrievedAddress);
            edit.putString("username",username);
            edit.apply();
            return null;
        }



    }

}
