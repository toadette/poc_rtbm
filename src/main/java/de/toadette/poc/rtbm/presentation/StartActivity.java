package de.toadette.poc.rtbm.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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

import javax.inject.Inject;

import de.toadette.poc.rtbm.R;
import de.toadette.poc.rtbm.domain.model.vip.Vip;
import de.toadette.poc.rtbm.domain.model.vip.VipNotFoundException;
import de.toadette.poc.rtbm.domain.model.vip.VipRepository;


public class StartActivity extends FragmentActivity {


    @Inject
    VipRepository vipRepository;
    private MapView mapView;
    private Location mLastLocation;
    private MapboxMap mapboxMap;
    private MapboxMap map;
    LocationServices locationServices;
    private static final int PERMISSIONS_LOCATION = 0;
    FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        locationServices = LocationServices.getLocationServices(StartActivity.this);

        MapboxAccountManager.start(this, getString(R.string.access_token));
        ((RtbmApplication) getApplication()).inject(this);
        init();
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                StartActivity.this.map=mapboxMap;
                double latitude = 53.075804;
                double longitude = 8.807184;
                map.setCameraPosition(new CameraPosition.Builder()
                        .target(new LatLng(latitude,longitude))
                        .zoom(16)
                        .build());
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title("Hello World!")
                        .snippet("Welcome to my marker."));
//                double latitude = 53.075804;
//                double longitude = 8.807184;
//                if (ActivityCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                            mGoogleApiClient);
//                }else {
//                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                            mGoogleApiClient);
//                }
//                if (mLastLocation != null) {
//                    latitude=mLastLocation.getLatitude();
//                    longitude=mLastLocation.getLongitude();
//                }
//                mapboxMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(latitude, longitude))
//                        .title("Hello World!")
//                        .snippet("Welcome to my marker."));

            }

        });
        floatingActionButton = (FloatingActionButton) findViewById(R.id.location_toggle_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null) {
//                    toggleGps(!map.isMyLocationEnabled());
                    setLocation();
                }
            }
        });
    }
private void setLocation(){
    Location lastLocation1 = com.mapzen.android.lost.api.LocationServices.FusedLocationApi.getLastLocation();
    Location lastLocation = locationServices.getLastLocation();
    map.setCameraPosition(new CameraPosition.Builder()
            .target(new LatLng(lastLocation))
            .zoom(16)
            .build());
    map.addMarker(new MarkerOptions()
            .position(new LatLng(lastLocation))
            .title("Hello World!")
            .snippet("Welcome to my marker."));
}
    private void init() {
//        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
//        TextView postCountTextView = (TextView) findViewById(R.id.postsCountTextView);
//        try {
//            Vip vipByUserName = vipRepository.getVipByUserId(1);
//            userNameTextView.setText(vipByUserName.getUsername());
//            postCountTextView.setText(String.valueOf(vipByUserName.getPostsCount()));
//        } catch (VipNotFoundException e) {
//            userNameTextView.setText("");
//            postCountTextView.setText("");
//        }
    }

    public void startCardActivity(View view) {
        getApplication().startActivity(new Intent(this, CardActivity.class));
    }

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


    @UiThread
    public void toggleGps(boolean enableGps) {
        if (enableGps) {
            // Check if user has granted location permission
            if (!locationServices.areLocationPermissionsGranted()) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_LOCATION);
            } else {
                enableLocation(true);
            }
        } else {
            enableLocation(false);
        }
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
            int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_LOCATION: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableLocation(true);
                }
            }
        }
    }

}