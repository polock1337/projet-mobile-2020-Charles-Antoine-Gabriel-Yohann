package ca.qc.cgmatane.preuvedeconceptcapteur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    Button mCaptureButton;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.imageView);
        mCaptureButton = findViewById(R.id.captureImageBoutton);

        mCaptureButton.setOnClickListener(new View.OnClickListener() {
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

    }
    private void openCamera(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}