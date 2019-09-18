package com.example.calorie_tracker_final_v2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.calorie_tracker_final_v2.MainFragment.pref;

public class DailyDietFragment extends Fragment {
    View dDietFragment;
    EditText newFood;
    EditText servings;
    EditText localServings;
    Spinner foodCategory;
    Spinner foodSubCategory;
    String category = "";
    String subCategory = "";
    Button consumption_search;
    Button consumption;
    ArrayList<String> food = new ArrayList<>();
    ImageView googleImage;
    TextView tv;
    String currDate;
    JSONObject foodObject = new JSONObject();
    SharedPreferences sp;
    String user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dDietFragment = inflater.inflate(R.layout.fragment_daily_diet, container, false);
        newFood = dDietFragment.findViewById(R.id.enter_new_food);
        foodCategory = dDietFragment.findViewById(R.id.spin_food_category);
        foodSubCategory = dDietFragment.findViewById(R.id.spin_food_items);
        googleImage = dDietFragment.findViewById(R.id.google_image);
        tv = (TextView) dDietFragment.findViewById(R.id.google_description);
        servings = dDietFragment.findViewById(R.id.editServings);
        localServings = dDietFragment.findViewById(R.id.editServings2);
        currDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        SharedPreferences sp = getActivity().getSharedPreferences(pref, Context.MODE_PRIVATE);
        user = sp.getString("firstName","");


        ArrayAdapter<CharSequence> foodCategoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.food_category_array, android.R.layout.simple_dropdown_item_1line);

        foodCategory.setAdapter(foodCategoryAdapter);


        foodCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getId()==R.id.spin_food_category) {
                    category = parent.getItemAtPosition(position).toString();
                    SpinnerManipulation sm = new SpinnerManipulation();
                    sm.execute();


                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        foodSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getId()==R.id.spin_food_items) {
                    subCategory = parent.getItemAtPosition(position).toString();
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        consumption = dDietFragment.findViewById(R.id.consumption_btn);
        consumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostAsyncTaskLocal postAsyncTaskLocal = new PostAsyncTaskLocal();
                postAsyncTaskLocal.execute();

            }
        });


        consumption_search = dDietFragment.findViewById(R.id.consumption_search);
        consumption_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String food = newFood.getText().toString();
                SearchAsyncTask searchAsyncTask = new SearchAsyncTask();
                ImageAsyncTask imageAsyncTask = new ImageAsyncTask();
                CheckAsyncTask checkAsyncTask = new CheckAsyncTask();
                PostAsyncTask postAsyncTask = new PostAsyncTask();
                searchAsyncTask.execute(food);
                imageAsyncTask.execute(food);
                checkAsyncTask.execute(food);
                postAsyncTask.execute();


            }
        });

        return dDietFragment;
    }

    private class SpinnerManipulation extends AsyncTask<Void,Void, List<String>>
    {
        protected List<String> doInBackground(Void... params)
        {
            return (ArrayList<String>) UrlHttp.getFoodNames(category);
        }

        protected void onPostExecute(List<String> food){
            ArrayAdapter<String> foodSubCategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,food);
            foodSubCategoryAdapter.notifyDataSetChanged();
            foodSubCategory.setAdapter(foodSubCategoryAdapter);
        }
    }

    private class SearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return UrlHttp.searchGoogle(params[0], new String[]{"num"}, new
                    String[]{"1"});

        }

        @Override
        protected void onPostExecute(String result) {
            String text = UrlHttp.getLink(result);
            String description = UrlHttp.getSnippet(result);
            tv.setText(description);
            Picasso.with(getContext()).load(text).into(googleImage);
        }
    }

    private class ImageAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return UrlHttp.searchGoogleImage(params[0], new String[]{"num"}, new
                    String[]{"1"});

        }

        @Override
        protected void onPostExecute(String result) {
            String text = UrlHttp.getLink(result);
            Picasso.with(getContext()).load(text).into(googleImage);
        }
    }

    private class CheckAsyncTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {

            String[] food = params[0].split(" ");
            String fName = "";
            for(String s : food)
                fName += s+"+";
            return UrlHttp.retrieveFoods(fName.substring(0,fName.length()-1));
        }

        @Override
        protected void onPostExecute(Integer s){

            retrieveFoodInfo obj = new retrieveFoodInfo();
            obj.execute(s);
        }
    }

    private class retrieveFoodInfo extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return UrlHttp.retrieveFInfo( params[0]);
        }

        @Override
        protected void onPostExecute(String foodData) {
            placeFoodInfo obj = new placeFoodInfo();
            obj.execute(foodData);
        }
    }

    private class placeFoodInfo extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {

            return UrlHttp.increaseFoods(params[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result == 204)
                ;
        }
    }


    private class PostAsyncTaskLocal extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String serving = localServings.getText().toString();
            Integer serve = Integer.parseInt(serving);

            JSONObject consumption = new JSONObject();
            try {
                consumption.put("consumptionId", UrlHttp.getConsumptionId());
                consumption.put("date", currDate);
                consumption.put("foodId", UrlHttp.getExistingFoodName(subCategory));
                consumption.put("qtyServings", serve);
                consumption.put("userId", UrlHttp.getUserDetail(user));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            UrlHttp.enterUserConsumption(consumption);
            return "";

        }

        @Override
        protected void onPostExecute(String response) {
        }
    }

    private class PostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String serving = servings.getText().toString();
            Integer serve = Integer.parseInt(serving);
            foodObject =  UrlHttp.retrieveFood(subCategory);

            JSONObject consumption = new JSONObject();
            try {
                consumption.put("consumptionId", UrlHttp.getConsumptionId());
                consumption.put("date", currDate);
                consumption.put("foodId", foodObject);
                consumption.put("qtyServings", serve);
                consumption.put("userId", UrlHttp.getUserDetail(user));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            UrlHttp.enterUserConsumption(consumption);
            return "";

        }

        @Override
        protected void onPostExecute(String response) {
        }
    }




}


