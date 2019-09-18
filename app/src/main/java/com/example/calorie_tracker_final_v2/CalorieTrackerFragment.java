package com.example.calorie_tracker_final_v2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.calorie_tracker_final_v2.MainFragment.pref;
import static com.example.calorie_tracker_final_v2.StepsFragment.db;


public class CalorieTrackerFragment extends Fragment {
    View cTracker;
    TextView setGoal;
    TextView totalSteps;
    TextView totalConsumed;
    TextView totalBurned;
    Double burnedPerStep;
    int userID = 0;
    Double burnedAtRest;
    Double totalCaloriesBurned;
    int totalCaloriesConsumed = 0;
    int s =0;
    String date;
    Integer Goal;
    String name;
    Button postToReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cTracker = inflater.inflate(R.layout.fragment_calorie_tracker, container, false);
        setGoal = cTracker.findViewById(R.id.tvSetGoal);
        totalSteps = cTracker.findViewById(R.id.tvTotalStepsTaken);
        totalConsumed = cTracker.findViewById(R.id.tvTotalConsumed);
        totalBurned = cTracker.findViewById(R.id.tvTotalBurned);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(pref, Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("userId",0);
        name = sharedPreferences.getString("firstName","");

        Goal = sharedPreferences.getInt("calorieGoal",1);
        String goal1 = Goal.toString();
        setGoal.setText(goal1);

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        TotalStepsTaken totalStepsTaken = new TotalStepsTaken();
        totalStepsTaken.execute();

        TotalCalBurned totalCalBurned = new TotalCalBurned();
        totalCalBurned.execute();

        TotalCalConsumed totalCalConsumed = new TotalCalConsumed();
        totalCalConsumed.execute();

        postToReport = cTracker.findViewById(R.id.btnSaveReport);
        postToReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostToReportAsyncTask postToReportAsyncTask = new PostToReportAsyncTask();
                postToReportAsyncTask.execute();

            }
        });




        return cTracker;
    }
    private class TotalStepsTaken extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            s = db.stepsRoomDBDao().getSummisionofSteps();
            return s;
        }

        @Override
        protected void onPostExecute(Integer k) {
            String m = k.toString();
            totalSteps.setText(m);

        }
    }

    private class TotalCalBurned extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            burnedPerStep = UrlHttp.getUserCalBurnedPerStep(userID);
            burnedAtRest = UrlHttp.getUserCalBurnedAtRest(userID);

            totalCaloriesBurned = burnedAtRest+s*burnedPerStep;
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            String parser = totalCaloriesBurned.toString();
            totalBurned.setText(parser);
        }
    }

    private class TotalCalConsumed extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void...params) {

            return totalCaloriesConsumed = UrlHttp.getUserTotalCaloriesConsumed(userID,date);

        }

        @Override
        protected void onPostExecute(Integer result) {
            String parser1 = result.toString();
            totalConsumed.setText(parser1);
        }
    }

    private class PostToReportAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject report = new JSONObject();
            try{

                report.put("calorieGoal", Goal);
                report.put("date",date);
                report.put("reportId",UrlHttp.getReportID());
                report.put("totalCalBurned", totalCaloriesBurned.intValue());
                report.put("totalCalConsumed",totalCaloriesConsumed);
                report.put("totalStepsTaken",s);
                report.put("userId",UrlHttp.getUserDetail(name));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            db.stepsRoomDBDao().deleteAll();

            UrlHttp.enterUserReport(report);

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

        }
    }
}