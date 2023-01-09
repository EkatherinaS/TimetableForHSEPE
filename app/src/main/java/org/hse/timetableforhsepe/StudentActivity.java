package org.hse.timetableforhsepe;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Random;

public class StudentActivity extends AppCompatActivity {

    private TextView time;
    private TextView status;
    private TextView subject;
    private TextView cabinet;
    private TextView corp;
    private TextView professor;
    public static final String TAG = "StudentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        final Spinner spinner = findViewById(R.id.groupList);

        List<Group> groups = new ArrayList<>();
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
                StudentActivity.this.finish();
            }
        });

        initData();
    }

    private void initGroupList(List<Group> groups) {
        String[] courses = new String[] { "ПИ", "БИ", "УБ", "РИС" };
        String[] years = new String[] { "19", "20", "21", "22" };
        String[] groupNum = new String[] { "1", "2", "3" };

        Random rand = new Random();
        String groupName;
        for (int i = 0; i < rand.nextInt(10) + 1; i++) {
            groupName = courses[rand.nextInt(4)] + "-" +
                        years[rand.nextInt(4)] + "-" +
                        groupNum[rand.nextInt(3)];
            groups.add(new Group(i, groupName));
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

    static class Group {

        private Integer id;
        private String name;

        public Group(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
