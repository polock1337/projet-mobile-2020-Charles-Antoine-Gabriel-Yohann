package cgmatane.qc.ca.findspot;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cgmatane.qc.ca.findspot.R;

public class VueLocalisation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap carteGeo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_localisation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        carteGeo = googleMap;

        Bundle b = getIntent().getExtras();
        double lat = 0;
        double lng = 0;
        if(b != null)
        {
            lat = b.getDouble("lat");
            lng = b.getDouble("lng");
        }




        Circle cercle = carteGeo.addCircle(new CircleOptions()
                .center(new LatLng(lat, lng))
                .radius(100)
                .strokeColor(Color.BLACK)
                .fillColor(0x220000FF)
                .strokeWidth(3));

    }
}