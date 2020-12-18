package cgmatane.qc.ca.findspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import cgmatane.qc.ca.findspot.Donnee.ObjectifDAO;
import cgmatane.qc.ca.findspot.Modele.Objectif;
import cgmatane.qc.ca.findspot.Utilitees.*;

public class VueObjectif extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button CaptureButton;
    ImageView imageViewPhoto;
    Uri imageUri;
    private String id ;
    protected Objectif objectif;
    TextView nomLieuObjectif;
    protected Intent intentionNaviguerVueAccueil;
    protected Intent intentionNaviguerVueLocalisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_objectif);


        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        CaptureButton = findViewById(R.id.captureImageBoutton);

        Button retourVueListeObjectif = (Button) findViewById(R.id.retourVueListeObjectif);
        nomLieuObjectif = (TextView) findViewById(R.id.nomLieuObjectif);
        checkPermission();
        setObjectifInfo();
        //intentionNaviguerVueAccueil = new Intent(this, VueAccueil.class);
        CaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {

                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });

        retourVueListeObjectif.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO : coder !

                       /* Toast message = Toast.makeText(

                                getApplicationContext(),
                                "vue Ecran Accueil",
                                Toast.LENGTH_LONG);

                        message.show();*/

                        naviguerRetourListeObjectif();
                        //startActivityForResult(intentionNaviguerProfit, ACTIVITE_PROFIT);
                    }
                }
        );

        intentionNaviguerVueLocalisation = new Intent(getApplicationContext(), VueLocalisation.class);

        View viewRoot = (View) findViewById(R.id.viewRootObjectif);
        viewRoot.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void surSwipeDessus() {
                //Toast.makeText(getApplicationContext(), "swipe dessus", Toast.LENGTH_SHORT).show();
            }

            public void surSwipeDroit() {
                //Toast.makeText(getApplicationContext(), "swipe droit", Toast.LENGTH_SHORT).show();
            }

            public void surSwipeGauche() {
                //Toast.makeText(getApplicationContext(), "swipe gauche", Toast.LENGTH_SHORT).show();
                Bundle b = new Bundle();
                b.putDouble("lat",objectif.getLocalisation().getLatitude());
                b.putDouble("lng",objectif.getLocalisation().getLongitude());
                intentionNaviguerVueLocalisation.putExtras(b);
                startActivity(intentionNaviguerVueLocalisation);
            }

            public void surSwipeDessous() {
                //Toast.makeText(getApplicationContext(), "swipe dessous", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setObjectifInfo()
    {
        Bundle b = getIntent().getExtras();
        String value = "";
        if(b != null)
            value = b.getString("id");
        id = value;

        for(Objectif objectif:ObjectifDAO.getInstance().getListeObjectif())
        {
            if(objectif.getId().equals(id))
            {
                nomLieuObjectif.setText(objectif.getNom());
                this.objectif =objectif;
            }
        }
        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference().child(objectif.getReference());
        final File fichierLocal;
        try {
            fichierLocal = File.createTempFile("photoObjectif","jpg");
            storageRef.getFile(fichierLocal)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(fichierLocal.getAbsolutePath());
                            imageViewPhoto.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Image a fait une erreur");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void checkPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
    }

    public void naviguerRetourListeObjectif() {
        this.finish();
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Nouvelle Photo");
        values.put(MediaStore.Images.Media.DESCRIPTION, "De la camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_DENIED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission refuser...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //C'est rouge mais ça fonctionne bien
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            GPSTracker gps = new GPSTracker(this);

            // Check if GPS enabled
            if(gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                double lat2 = gps.getLatitude();
                double lng2 = gps.getLongitude();
                double lat1 =  objectif.getLocalisation().getLatitude();
                double lng1 = objectif.getLocalisation().getLongitude();
                // lat1 and lng1 are the values of a previously stored location
                if (distance(lat1, lng1, lat2, lng2) < 0.0621371) { // if distance < 0.1 miles we take locations as equal
                    Toast.makeText(getApplicationContext(), "Vous avez les points!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Pas de points!", Toast.LENGTH_LONG).show();
                }
                // \n is for new line
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }
    }
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }


}