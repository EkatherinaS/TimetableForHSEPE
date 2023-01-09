package org.hse.timetableforhsepe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ProfessorActivity extends AppCompatActivity {
    private TextView time;
    private TextView status;
    private TextView subject;
    private TextView cabinet;
    private TextView corp;
    private TextView professor;
    public static final String TAG = "ProfessorActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        final Spinner spinner = findViewById(R.id.groupList);
        List<StudentActivity.Group> groups = new ArrayList<>();
        initGroupList(groups);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                Object item = adapter.getItem(selectedItemPosition);
                Log.d(TAG, "selectedItem: " + item);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        time = findViewById(R.id.time);
        initTime();

        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        professor = findViewById(R.id.professor);

        FloatingActionButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfessorActivity.this.finish();
            }
        });

        initData();
    }

    private void initGroupList(List<StudentActivity.Group> groups) {
        Random rand = new Random();
        String professor = "Преподаватель ";
        for (int i = 0; i < rand.nextInt(10) + 1; i++) {
            groups.add(new StudentActivity.Group(i, professor + (i + 1)));
        }
    }

    private void initTime() {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String result = simpleDateFormat.format(currentTime).toString() + ", " + StringUtils.capitalize(getDayOfWeek().toString());
        time.setText(result);
    }

    private String getDayOfWeek() {

        LocalDate date = LocalDate.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.println("dayOfWeek.toString()" + dayOfWeek.toString());
        Locale localeRu = new Locale("ru", "RU");

        return dayOfWeek.getDisplayName(TextStyle.FULL, localeRu);
    }

    private void initData() {
        status.setText("Нет пар");
        subject.setText("Дисциплина");
        cabinet.setText("Кабинет");
        corp.setText("Корпус");
        professor.setText("Преподаватель");
    }
}
