package com.example.calorie_tracker_final_v2;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.calorie_tracker_final_v2.LoginActivity.pref;


public class GMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    public static String retrievedAddress = "";
    public String getRetrievedAddress="";
    EditText editText;
    Button button;




    static LatLng coordinates;
    static String locator;
    static List<JSONObject> fitnessAreas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps);
        fitnessAreas = new ArrayList<>();
        //   button.setOnClickListener(new View.OnClickListener() {

        SharedPreferences sharedPreferences = getSharedPreferences(pref,MODE_PRIVATE);
        getRetrievedAddress = sharedPreferences.getString("address","");


        locator = getRetrievedAddress;
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            Address address = new Address(Locale.getDefault());
            if (geocoder.getFromLocationName(locator, 1).size() > 0)
                address = geocoder.getFromLocationName(locator, 1).get(0);
            coordinates = new LatLng(address.getLatitude(), address.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //coordinates = new LatLng(-32.1133,12.2221);
        Check c = new Check();
        c.execute();


    }
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.



    private class Check extends AsyncTask<Void, Void, List<JSONObject>> {
        @Override
        protected List<JSONObject> doInBackground(Void... params) {
            List<JSONObject> data = UrlHttp.addUser(Double.toString(coordinates.latitude),Double.toString(coordinates.longitude));
            return data;
        }

        @Override
        protected void onPostExecute(List<JSONObject> data) {
            fitnessAreas = data;
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(GMapsActivity.this);
        }
    }

    public void markers(GoogleMap gMap) throws JSONException {
        gMap.clear();
        gMap.addMarker(new MarkerOptions().position(coordinates).title("Marker in " + locator).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        for(JSONObject o : fitnessAreas) {
            LatLng pos = new LatLng(o.getDouble("latitude"),o.getDouble("longitude"));
            gMap.addMarker(new MarkerOptions().position(pos).title(o.getString("name")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {


        gMap = googleMap;
        gMap.clear();
        gMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        gMap.setMinZoomPreference(12);
        gMap.setMaxZoomPreference(12);
        try {
            markers(gMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}

