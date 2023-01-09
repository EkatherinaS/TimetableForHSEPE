package org.hse.timetableforhsepe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.security.Permission;

import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View buttonStudent = findViewById(R.id.btnForStudents);
        View buttonProfessor = findViewById(R.id.btnForProfessors);
        View buttonSettings = findViewById(R.id.btnSettings);

        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudent();
            }
        });
        buttonProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfessor();
            }
        });
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettings();
            }
        });

        ImageView btnDemo = findViewById(R.id.imageView);
        btnDemo.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, DemoActivity.class));
        });
    }

    public void showStudent() {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }
    public void showProfessor() {
        Intent intent = new Intent(this, ProfessorActivity.class);
        startActivity(intent);
    }
    public void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}

