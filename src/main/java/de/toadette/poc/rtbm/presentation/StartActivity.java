package de.toadette.poc.rtbm.presentation;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;

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
import de.toadette.poc.rtbm.domain.model.vip.VipRepository;


public class StartActivity extends FragmentActivity {


    private static final int PERMISSIONS_LOCATION = 0;
    @Inject
    VipRepository vipRepository;
    LocationServices locationServices;
    FloatingActionButton floatingActionButton;
    private MapView mapView;
    private MapboxMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        MapboxAccountManager.start(this, getString(R.string.access_token));
        ((RtbmApplication) getApplication()).inject(this);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                StartActivity.this.map = mapboxMap;
                double latitude = 53.075804;
                double longitude = 8.807184;
                map.setCameraPosition(new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(16)
                        .build());
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title("Hello World!")
                        .snippet("Welcome to my marker."));

            }

        });
        floatingActionButton = (FloatingActionButton) findViewById(R.id.location_toggle_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null) {
                    setLocation();
                }
            }
        });
    }

    private void setLocation() {
        map.setCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(locationServices.getLastLocation()))
                .zoom(16)
                .build());
        map.addMarker(new MarkerOptions()
                .position(new LatLng(locationServices.getLastLocation()))
                .title("Hello World!")
                .snippet("Welcome to my marker."));
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