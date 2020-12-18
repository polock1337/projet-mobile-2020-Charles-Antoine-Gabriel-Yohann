package cgmatane.qc.ca.findspot;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;


public class VueLocalisation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap carteGeo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_localisation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        carteGeo = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (carteGeo != null) {
                carteGeo.setMyLocationEnabled(true);
            }
        }
        Bundle b = getIntent().getExtras();
        double lat = 0;
        double lng = 0;

        if(b != null)
        {
            lat = b.getDouble("lat");
            lng = b.getDouble("lng");
        }
        /*Changement da la position pour diffuculté*/
        Random random = new Random();
        double  randomLat = 0 + random.nextDouble() * (0.0005 - 0);
        double  randomLng = 0 + random.nextDouble() * (0.0005 - 0);



        carteGeo.addCircle(new CircleOptions()
                .center(new LatLng(lat+randomLat, lng+randomLng))
                .radius(100)
                .strokeColor(Color.BLACK)
                .fillColor(0x220000FF)
                .strokeWidth(3));

    }
}