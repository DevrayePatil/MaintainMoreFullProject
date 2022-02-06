package com.example.maintainmore;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.SearchView;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.maintainmore.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);

        com.example.maintainmore.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        binding.buttonChangeLocation.setOnClickListener(view -> ChangeLocation());
        binding.floatingDirectionButton.setOnClickListener(view -> DestinationDirection());

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String location = binding.searchView.getQuery().toString();
                List<Address> addressList = null;

                Geocoder geocoder = new Geocoder(MapsActivity.this);

                try {
                    addressList = geocoder.getFromLocationName(location,1);
                }
                catch (IOException e){
                    e.printStackTrace();
                }

                assert addressList != null;
                Address address = addressList.get(0);

                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                map.addMarker(new MarkerOptions().position(latLng).title(location));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void DestinationDirection() {
        Toast.makeText(this, "Direction clicked", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=19.1383, 77.3210&mode=l"));
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }


    private void ChangeLocation() {

        String location = "Mumbai";
        List<Address> addressList = null;

        Geocoder geocoder = new Geocoder(MapsActivity.this);

        try {
            addressList = geocoder.getFromLocationName(location,1);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        assert addressList != null;
        Address address = addressList.get(0);

        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

        map.addMarker(new MarkerOptions().position(latLng).title(location));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

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
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;


//d        LatLng Nanded = new LatLng(19.1383, 77.3210);
// e       googleMap.addMarker(new MarkerOptions().position(Nanded).title("Marker in Nanded"));
//  f      googleMap.moveCamera(CameraUpdateFactory.newLatLng(Nanded));
//   a     float zoomLevel = 16.0f; //This goes up to 21
//    u    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Nanded, zoomLevel));
    }
}