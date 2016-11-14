package de.toadette.poc.rtbm.presentation;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationListener;
import com.mapbox.mapboxsdk.location.LocationServices;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import de.toadette.poc.rtbm.R;


public class MapActivity extends FragmentActivity {


    private static final int PERMISSIONS_LOCATION = 0;
    LocationServices locationServices;
    private MapView mapView;
    private MapboxMap map;
    private double latitude = 53.075804;
    private double longitude = 8.807184;
    private String name = "Default Marker";
    private String describtion = "Beschreibung";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapboxAccountManager.start(this, getString(R.string.access_token));
        ((RtbmApplication) getApplication()).inject(this);

        if (getIntent().getExtras().get("name") != null) {
            name = getIntent().getExtras().getString("name");
        }
        if (getIntent().getExtras().get("describtion") != null) {
            describtion = getIntent().getExtras().getString("describtion");
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(name + ": " + describtion);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                MapActivity.this.map = mapboxMap;
                map.setCameraPosition(new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(16)
                        .build());
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(name)
                        .snippet(describtion));

            }

        });
    }

//    private void setLocation() {
//        map.setCameraPosition(new CameraPosition.Builder()
//                .target(new LatLng(locationServices.getLastLocation()))
//                .zoom(16)
//                .build());
//        map.addMarker(new MarkerOptions()
//                .position(new LatLng(locationServices.getLastLocation()))
//                .title("Hello World!")
//                .snippet("Welcome to my marker."));
//    }


    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void enableLocation(boolean enabled) {
        if (enabled) {
            locationServices.addLocationListener(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        // Move the map camera to where the user location is
                        map.setCameraPosition(new CameraPosition.Builder()
                                .target(new LatLng(location))
                                .zoom(16)
                                .build());
                    }
                }
            });
        }
        // Enable or disable the location layer on the map
        map.setMyLocationEnabled(enabled);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_LOCATION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableLocation(true);
        }
    }

}