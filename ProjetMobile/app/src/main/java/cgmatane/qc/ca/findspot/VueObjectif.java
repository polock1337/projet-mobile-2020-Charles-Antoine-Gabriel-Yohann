package cgmatane.qc.ca.findspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import cgmatane.qc.ca.findspot.Utilitees.*;

public class VueObjectif extends AppCompatActivity
{

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button CaptureButton;
    ImageView imageViewPhoto;
    Uri imageUri;


    protected Intent intentionNaviguerVueAccueil;
    protected Intent intentionNaviguerVueLocalisation;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_objectif);

        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        CaptureButton = findViewById(R.id.captureImageBoutton);

        Button retourVueListeObjectif = (Button)findViewById(R.id.retourVueListeObjectif);

        //intentionNaviguerVueAccueil = new Intent(this, VueAccueil.class);
        CaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {

                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else{
                        openCamera();
                    }
                }
                else{
                    openCamera();
                }
            }
        });

        retourVueListeObjectif.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
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

        View viewRoot = (View)findViewById(R.id.viewRootObjectif);
        viewRoot.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void surSwipeDessus() {
                //Toast.makeText(getApplicationContext(), "swipe dessus", Toast.LENGTH_SHORT).show();
            }
            public void surSwipeDroit() {
                //Toast.makeText(getApplicationContext(), "swipe droit", Toast.LENGTH_SHORT).show();
            }
            public void surSwipeGauche() {
                //Toast.makeText(getApplicationContext(), "swipe gauche", Toast.LENGTH_SHORT).show();
                startActivity(intentionNaviguerVueLocalisation);
            }
            public void surSwipeDessous() {
                //Toast.makeText(getApplicationContext(), "swipe dessous", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void naviguerRetourListeObjectif()
    {
        this.finish();
    }

    private void openCamera(){
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
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_DENIED) {
                    openCamera();
                }
                else{
                    Toast.makeText(this, "Permission refuser...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //C'est rouge mais ça fonctionne bien
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            imageViewPhoto.setImageURI(imageUri);
        }
    }
}