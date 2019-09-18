package com.example.calorie_tracker_final_v2;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static com.example.calorie_tracker_final_v2.LoginActivity.firstName;


public class MainFragment extends Fragment {
    ImageView fitness;
    TextView dateTime;
    EditText logi;
    EditText editCalGoal;
    Button calBtn;
    View vHomeScreen;
    public TextView welcome;
    String user = "";

    public static final String pref = "Preferences";
    public static int userId = 0;
    public static SharedPreferences sp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vHomeScreen = inflater.inflate(R.layout.fragment_main, container, false);

        fitness = vHomeScreen.findViewById(R.id.fitImage);
        fitness.setImageResource(R.drawable.fitnessimage);
        dateTime = vHomeScreen.findViewById(R.id.dateTime);
        logi = vHomeScreen.findViewById(R.id.userName);
        welcome = vHomeScreen.findViewById(R.id.WelcomeUser);
        calBtn = vHomeScreen.findViewById(R.id.calorieBtn);
        editCalGoal = vHomeScreen.findViewById(R.id.editCalGoal);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dateTime.setText(currentDateTimeString);


        SharedPreferences sp = getActivity().getSharedPreferences(pref, MODE_PRIVATE);
        user = sp.getString("username", "");
        GetFirstName getFirstName = new GetFirstName();
        getFirstName.execute();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("userId",userId);
        //editor.apply();
        editor.putString("firstName",firstName);
        editor.apply();
/*        GetUserID getUserID = new GetUserID();
        getUserID.execute();*/


        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calGoal = editCalGoal.getText().toString();
                int Goal = Integer.parseInt(calGoal);
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt("calorieGoal", Goal);
                edit.apply();
                Toast.makeText(getActivity(), "Calorie Goal Entered and your goal for today is " + Goal, Toast.LENGTH_SHORT).show();
            }
        });

        return vHomeScreen;

    }

    private class GetFirstName extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {


            firstName = UrlHttp.getUserFirstName(user);

            userId = UrlHttp.getUserID(firstName);

            return null;
        }

        @Override
        protected void onPostExecute(Void value) {
            welcome.setText("Welcome " + firstName + " :)");
        }
    }

}



