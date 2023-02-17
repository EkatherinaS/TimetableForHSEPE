package org.hse.timetableforhsepe.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import org.hse.timetableforhsepe.BuildConfig;
import org.hse.timetableforhsepe.model.Converters;
import org.hse.timetableforhsepe.model.PreferenceManager;
import org.hse.timetableforhsepe.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends ActionBarActivity implements SensorEventListener {
    private static final String TAG = "Settings";
    private static int PERMISSION_REQUEST_CODE = 1;
    private static int REQUEST_IMAGE_CAPTURE = 1;
    private SensorManager sensorManager;
    private Sensor light;
    private TextView sensorLight;
    private PreferenceManager preferenceManager;
    private String profileNameCurrent;
    private String photoPATHCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorLight = findViewById(R.id.tvLuxAmount);

        preferenceManager = new PreferenceManager(this);

        profileNameCurrent = preferenceManager.getProfileName();
        photoPATHCurrent = preferenceManager.getPhotoPATH();
        if (photoPATHCurrent.isEmpty()) {
            photoPATHCurrent = "";
        }

        Log.i(TAG, photoPATHCurrent);

        Button takePhoto = findViewById(R.id.btnTakePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        ListView sensorsList = findViewById(R.id.sensorsList);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listSensor = sensorManager.getSensorList(Sensor.TYPE_ALL);

        List<String> listSensorType = new ArrayList<>();
        for (int i = 0; i < listSensor.size(); i++) {
            listSensorType.add(listSensor.get(i).getName());
        }

        sensorsList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listSensorType));


        Button saveInfo = findViewById(R.id.btnSave);
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.savePhotoPATH(photoPATHCurrent);
                preferenceManager.saveProfileName(profileNameCurrent);
                SettingsActivity.this.finish();
            }
        });

        TextView profileName = findViewById(R.id.editTextTextPersonName);
        profileName.setText(profileNameCurrent);
        profileName.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus){
                if (hasFocus) {
                    profileName.setText("");
                }
                else {
                    if (profileName.getText().toString().equals("")) {
                        profileName.setText(preferenceManager.getProfileName());
                    }
                    else {
                        profileNameCurrent = profileName.getText().toString();
                    }
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String PERMISSION = Manifest.permission.CAMERA;
        if (ActivityCompat.checkSelfPermission(this, PERMISSION) ==
                PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void checkPermission() {
        String PERMISSION = Manifest.permission.CAMERA;
        if (ActivityCompat.checkSelfPermission(this, PERMISSION) ==
                PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION)){
            showExplanation(getResources().getString(R.string.permissionText),
                    getResources().getString(R.string.permissionCamera),
                    PERMISSION, PERMISSION_REQUEST_CODE);
        } else{
            requestPermissions( new String[] {PERMISSION}, PERMISSION_REQUEST_CODE);
        }
    }

    public void showExplanation(String textGeneral, String textExplanation, String permission, int permissionCode ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(textGeneral)
                .setMessage(textExplanation)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions( new String[] {permission}, permissionCode);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SettingsActivity.this,
                                    textGeneral + "\n" + textExplanation,
                                        Toast.LENGTH_LONG).show();
                    }
                })
                .setCancelable(true);
        builder.create().show();
    }




    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        sensorLight.setText(getString(R.string.currentLux, lux));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setProfileImage(photoPATHCurrent);
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "Create file", ex);
            }

            if (photoFile != null) {
                photoPATHCurrent = photoFile.getAbsolutePath();
                Log.e(TAG, photoPATHCurrent);
                Uri photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = Converters.dateToTimestamp(new Date()).toString();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String path = storageDir + "/" + R.string.image_name + timeStamp + ".jpg";
        File image = new File(path);
        image.createNewFile();
        return image;
    }

    public void setProfileImage(String path) {
        File f = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if(f.exists() && !f.isDirectory()) {
            try {
                ExifInterface ei = new ExifInterface(path);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        bitmap = rotateImage(bitmap, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        bitmap = rotateImage(bitmap, 180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        bitmap = rotateImage(bitmap, 270);
                        break;
                }
            }
            catch (IOException ex) {
                Log.e(TAG, "", ex);
            }
            ImageView profileImage = findViewById(R.id.imgProfile);
            profileImage.setImageBitmap(bitmap);
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
