package com.example.calorie_tracker_final_v2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity<radioBtn> extends AppCompatActivity {

    private String surname;
    private String gender;
    private Integer activity_level;
    private String first_name;
    private String dateo;
    private String heightVar;
    private String postVar;
    private String weightVar;
    private String stepsVar;
    private String addressVar;
    private String emailVar;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView showDate;
    private String dateOfBirth;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String usernameVar;
    private String passwordVar;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    String prepend = "";
    String prependMonth = "";
    String s = "";
    private int levelOfAct = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        EditText edit_first = (EditText) findViewById(R.id.fname);
        EditText sur = (EditText) findViewById(R.id.sname);
        EditText height = findViewById(R.id.Height);
        EditText postC = findViewById(R.id.postc);
        EditText steps = findViewById(R.id.steps);
        EditText weight = findViewById(R.id.weight);
        EditText address = findViewById(R.id.Address);
        EditText email = findViewById(R.id.email);
        EditText username = findViewById(R.id.regUserName);
        EditText password = findViewById(R.id.regPassword);
        radioGroup = (RadioGroup) findViewById(R.id.GenderGroup);
        showDate = (TextView) findViewById(R.id.dateOfBirth);






        Button btnRegister = (Button) findViewById(R.id.btn_confirm);

        btnRegister.setOnClickListener(v -> {
            first_name = edit_first.getText().toString();
            surname = sur.getText().toString();
            heightVar = height.getText().toString();
            postVar = postC.getText().toString();
            stepsVar = steps.getText().toString();
            weightVar = weight.getText().toString();
            addressVar = address.getText().toString();
            emailVar = email.getText().toString();
            usernameVar = username.getText().toString();
            passwordVar = password.getText().toString();

            if(TextUtils.isEmpty(usernameVar))
            {
                username.setError("Please enter a username");
                return;
            }


            PostAsyncTask post = new PostAsyncTask();
            post.execute();

        });


        showDate = (TextView) findViewById(R.id.dateOfBirth);
        showDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Integer year = cal.get(Calendar.YEAR);
                Integer month = cal.get(Calendar.MONTH);
                Integer day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth
                        , dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }


        });


        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                prepend = ""+dayOfMonth+"";
                prependMonth = ""+month+"";
                if (dayOfMonth < 10)
                    prepend = "0" + dayOfMonth;
                if(month < 10)
                    prependMonth = "0"+month;

                s = prepend + "-" + prependMonth + "-" + year;
                showDate.setText(s);
            }
        };

        Spinner levelOfActivity = findViewById(R.id.activityLevel_spinner);

        ArrayAdapter<CharSequence> foodCategoryAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.level_of_activity_array, android.R.layout.simple_dropdown_item_1line);

        levelOfActivity.setAdapter(foodCategoryAdapter);
        levelOfActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getId()==R.id.activityLevel_spinner){
                    String item = parent.getItemAtPosition(position).toString();
                    levelOfAct = Integer.parseInt(item);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void rChoice(View v){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        gender = radioButton.getText().toString();
    }

    public static String encryptionForRegister(String passwordVar) {

        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordVar.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
        return generatedPassword;
    }


    private class PostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            JSONObject userDetail = new JSONObject();
            JSONObject credential = new JSONObject();
            try {
                userDetail.put("address", addressVar);
                userDetail.put("dateOfBirth", s);
                userDetail.put("email", emailVar);
                userDetail.put("firstName", first_name);
                userDetail.put("gender",gender);
                userDetail.put("height", Double.parseDouble(heightVar));
                userDetail.put("levelOfActivity", levelOfAct);
                userDetail.put("postCode", Integer.parseInt(postVar));
                userDetail.put("stepsPerMile", Integer.parseInt(stepsVar));
                userDetail.put("surname", surname);
                userDetail.put("userId", UrlHttp.getUserId());
                userDetail.put("weight", Double.parseDouble(weightVar));
                credential.put("passwordHash",encryptionForRegister(passwordVar));
                credential.put("signUpDate",date);
                credential.put("userId",userDetail);
                credential.put("username",usernameVar);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            UrlHttp.enterUserInfo(userDetail);
            UrlHttp.enterUserCredentials(credential);
            return "";

        }

        @Override
        protected void onPostExecute(String response) {
        }
    }
    public void onBackPressed(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

