package org.hse.timetableforhsepe;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ScheduleActivity extends ActionBarActivity {

    public static String ARG_ID = "ARG_ID";
    public static String ARG_TYPE = "ARG_TYPE";
    public static String ARG_MODE = "ARG_MODE";
    public static String ARG_NAME = "ARG_NAME";
    private static final int DEFAULT_ID = 0;

    private ItemAdapter adapter;
    private BaseActivity.ScheduleType type;
    private BaseActivity.ScheduleMode mode;
    private int id;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        type = (BaseActivity.ScheduleType) getIntent().getSerializableExtra(ARG_TYPE);
        mode = (BaseActivity.ScheduleMode) getIntent().getSerializableExtra(ARG_MODE);
        id = getIntent().getIntExtra(ARG_ID, DEFAULT_ID);
        name = getIntent().getStringExtra(ARG_NAME);

        TextView title = findViewById(R.id.title);
        title.setText(name);

        RecyclerView recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.adapter = new ItemAdapter(this::onScheduleItemClick);
        recyclerView.setAdapter(this.adapter);

        initData();
    }

    private void onScheduleItemClick(ScheduleItem scheduleItem) {
        ;
    }

    protected void initData() {
        List<ScheduleItem> list = new ArrayList<>();

        ScheduleItemHeader header = new ScheduleItemHeader();
        header.setTitle("Понедельник, 28 января");
        list.add(header);

        ScheduleItem item = new ScheduleItem();
        item.setStart("10:00");
        item.setEnd("11:00");
        item.setType("Практическое занятие");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 503, Кочновский пр-д, д.3");
        item.setProfessor("Пред. Гущим Михаил Иванович");
        list.add(item);

        item = new ScheduleItem();
        item.setStart("12:00");
        item.setEnd("13:00");
        item.setType("Практическое занятие");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 503, Кочновский пр-д, д.3");
        item.setProfessor("Пред. Гущим Михаил Иванович");
        list.add(item);

        this.adapter.setDataList(list);
    }
}
