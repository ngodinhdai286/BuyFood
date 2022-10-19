package hcmute.group6.buyfood;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hcmute.group6.buyfood.databinding.ActivityMapsBinding;

public class MapRestaurantActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    SupportMapFragment mapFragment;

    Button btnReturn;

    private void mapping() {
        btnReturn = findViewById(R.id.mapsRestaurantActivity_btnReturn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_map_restaurant);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapRes);
        mapping();

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String location = (String) getIntent().getSerializableExtra("location");
        geoLocate(location);
    }

    private void geoLocate(String location){
        Log.d("Notification", "geoLocate: geolocating");

        String searchString = location;

        Geocoder geocoder = new Geocoder(MapRestaurantActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e("Notification", "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d("Notification", "geoLocate: found a location: " + address.toString());
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    MarkerOptions options = new MarkerOptions().position(latLng)
                            .title("I am here");

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    googleMap.addMarker(options);
                }
            });

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }
}