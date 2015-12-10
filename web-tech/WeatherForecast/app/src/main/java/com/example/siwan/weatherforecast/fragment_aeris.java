package com.example.siwan.weatherforecast;

import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.tiles.AerisTile;
import android.view.View;
import android.view.ViewGroup;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by siwan on 09-12-2015.
 */
public class fragment_aeris extends MapViewFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_aeris = inflater.inflate(R.layout.fragment_aeris, container, false);
        mapView = (AerisMapView)view_aeris.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapType.GOOGLE);
        MapActivity activity = (MapActivity) getActivity();
        mapView.moveToLocation(activity.getLatLong(), 9);
        mapView.addLayer(AerisTile.RADSAT);
        return view_aeris;
    }

}