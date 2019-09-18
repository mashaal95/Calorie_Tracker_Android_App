package com.example.calorie_tracker_final_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GMapsFragment extends Fragment {
    View vGMaps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        vGMaps = inflater.inflate(R.layout.fragment_gmaps, container,false);
        Intent intent = new Intent(getContext(),GMapsActivity.class);
        startActivity(intent);
        return vGMaps;
    }
}
