package com.example.calorie_tracker_final_v2;

import android.app.DatePickerDialog;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.example.calorie_tracker_final_v2.LoginActivity.pref;

public class ReportFragment extends Fragment {
    View rFragment;
    EditText enterDate;
    PieChart pchart;
    Button btnPieChart;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener1;
    private DatePickerDialog.OnDateSetListener dateSetListener2;
    String enteredDate;
    String prepend = "";
    String prepend1="";
    String prepend2 = "";
    String prependMonth = "";
    String prependMonth1 = "";
    String prependMonth2 = "";

    BarChart barGraph;
    Button btnBarGraph;
    int userid = 0;
    SharedPreferences sp;
    EditText fromDate;
    EditText toDate;
    String fromD;
    String toD;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rFragment = inflater.inflate(R.layout.fragment_report, container, false);
        btnPieChart = rFragment.findViewById(R.id.btnPieChart);

        enterDate = (EditText) rFragment.findViewById(R.id.editDatePickerForPieChart);

        enterDate = (EditText) rFragment.findViewById(R.id.editDatePickerForPieChart);
        enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Integer year = cal.get(Calendar.YEAR);
                Integer month = cal.get(Calendar.MONTH);
                Integer day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth
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

                enteredDate = prepend + "-" + prependMonth + "-" + year;
                enterDate.setText(enteredDate);
            }
        };

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(pref, Context.MODE_PRIVATE);
        userid = sharedPreferences.getInt("userId", 0);

        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAsyncTask checkAsyncTask = new CheckAsyncTask();
                checkAsyncTask.execute();
            }
        });

        barGraph = rFragment.findViewById(R.id.barGraph);


        fromDate = rFragment.findViewById(R.id.enterFromDate);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Integer year = cal.get(Calendar.YEAR);
                Integer month = cal.get(Calendar.MONTH);
                Integer day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth
                        , dateSetListener1, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }


        });


        dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                prepend1 = ""+dayOfMonth+"";
                prependMonth1 = ""+month+"";
                if (dayOfMonth < 10)
                    prepend1 = "0" + dayOfMonth;
                if(month < 10)
                    prependMonth1 = "0"+month;

                fromD = prepend1 + "-" + prependMonth1 + "-" + year;
                fromDate.setText(fromD);
            }
        };

        toDate = rFragment.findViewById(R.id.enterToDate);
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Integer year = cal.get(Calendar.YEAR);
                Integer month = cal.get(Calendar.MONTH);
                Integer day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth
                        , dateSetListener2, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }


        });


        dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                prepend2 = ""+dayOfMonth+"";
                prependMonth2 = ""+month+"";
                if (dayOfMonth < 10)
                    prepend2 = "0" + dayOfMonth;
                if(month < 10)
                    prependMonth2 = "0"+month;

                toD = prepend2 + "-" + prependMonth2 + "-" + year;
                toDate.setText(toD);
            }
        };
        btnBarGraph = rFragment.findViewById(R.id.btnBarChart);

        btnBarGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getActivity().getSharedPreferences(pref, Context.MODE_PRIVATE);
                userid = sp.getInt("userId", 0);

                PopulateBarChart obj = new PopulateBarChart();
                obj.execute(fromD, toD);
            }
        });


        return rFragment;
    }





    private class CheckAsyncTask extends AsyncTask<String, Void, HashMap<String, Integer>> {
        @Override
        protected HashMap doInBackground(String... params) {

            return UrlHttp.getUserCaloriesConsumedANDBurnedANDRemainingCalorie(userid, enteredDate);
        }

        @Override
        protected void onPostExecute(HashMap s) {
            List<PieEntry> records = new ArrayList<>();
            Iterator iterator = s.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Integer value = (Integer) s.get(key);
                records.add(new PieEntry(value, key));
            }

            PieDataSet dataSet = new PieDataSet(records, "Data");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            PieData info = new PieData(dataSet);
            com.github.mikephil.charting.charts.PieChart pie = rFragment.findViewById(R.id.pie);
            pie.setData(info);
            pie.animateY(2000);
            pie.invalidate();

        }

    }
    private class PopulateBarChart extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {

            return UrlHttp.getUserCalories(fromD,toD,userid);}

        @Override
        protected void onPostExecute(JSONObject data) {

            List<Integer> caloriesConsumed = new ArrayList<>();
            List<String> reportDates = new ArrayList<>();
            List<Integer> caloriedBurned = new ArrayList<>();
            try {
                caloriesConsumed = (List<Integer>) data.get("UserCalConsumed");
                caloriedBurned = (List<Integer>) data.get("UserCalBurned");;
                reportDates = (List<String>) data.get("Date");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<BarEntry> calorieConsumedData = new ArrayList<>();
            ArrayList<BarEntry> calorieBurnedData = new ArrayList<>();

            for(int i = 0; i < caloriedBurned.size(); i++)
            {
                calorieConsumedData.add(new BarEntry(i+1,caloriesConsumed.get(i)));
                calorieBurnedData.add(new BarEntry(i+1,caloriedBurned.get(i)));
            }


            BarDataSet bardataset1 = new BarDataSet(calorieConsumedData, "Calories Consumed");

            BarDataSet bardataset2 = new BarDataSet(calorieBurnedData, "Calories Burned");
            barGraph.animateY(5000);
            //BarData dataSet = new BarData(reportDates, bardataset);
            BarData dataSet  = new BarData(bardataset1,bardataset2);
            bardataset1.setColors(Color.rgb(244, 67, 54));
            bardataset2.setColors(Color.rgb(3, 169, 244));

            barGraph.setData(dataSet);

            XAxis xAxis = barGraph.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(reportDates));
            barGraph.getAxisLeft().setAxisMinimum(0);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            xAxis.setCenterAxisLabels(true);
            xAxis.setGranularityEnabled(true);

            float barSpace = 0.1f;
            float groupSpace = 0.7f;
            int groupCount = 2;

            //IMPORTANT *****
            dataSet.setBarWidth(0.45f);
            barGraph.getXAxis().setAxisMinimum(0);
            barGraph.getXAxis().setAxisMaximum(0 + barGraph.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
            barGraph.groupBars(0, groupSpace, barSpace);
        }

    }

}

