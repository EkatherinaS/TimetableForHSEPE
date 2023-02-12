package org.hse.timetableforhsepe.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.hse.timetableforhsepe.R;
import org.hse.timetableforhsepe.view_model.HseDao;
import org.hse.timetableforhsepe.view_model.HseRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View buttonStudent = findViewById(R.id.btnForStudents);
        View buttonTeacher = findViewById(R.id.btnForProfessors);
        View buttonSettings = findViewById(R.id.btnSettings);

        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudent();
            }
        });
        buttonTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTeacher();
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
            startActivity(new Intent(this, DemoActivity.class));
        });
    }

    public void showStudent() {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }
    public void showTeacher() {
        Intent intent = new Intent(this, TeacherActivity.class);
        startActivity(intent);
    }
    public void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}

