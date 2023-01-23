package org.hse.timetableforhsepe.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.hse.timetableforhsepe.R;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends ActionBarActivity {

    private TextView result;
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        number = findViewById(R.id.tvNumber);
        result = findViewById(R.id.lblResult);
        Button btnSum = findViewById(R.id.btnSum);
        Button btnMultiplyEven = findViewById(R.id.btnMultiplyEven);

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { clickButton(); }
        });

        btnMultiplyEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { clickButton2(); }
        });

        FloatingActionButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoActivity.this.finish();
            }
        });
    }

    private void clickButton() {
        String numberVal = number.getText().toString();
        if (numberVal.isEmpty()) { numberVal = "0"; }
        if((numberVal.toString()).length() > 5) {
            Toast.makeText(DemoActivity.this, R.string.demo_activity_warning_1, Toast.LENGTH_LONG).show();
        }
        else {
            int count = Integer.parseInt(numberVal);
            if (count > 10000) {
                Toast.makeText(DemoActivity.this, R.string.demo_activity_warning_1, Toast.LENGTH_LONG).show();
            }
            else {
                // init list
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    list.add(i + 1);
                }
                // Count all items in list
                int sum = list.stream().mapToInt(Integer::intValue).sum();
                result. setText (getString(R.string.demo_activity_result, sum));
            }
        }
    }

    private void clickButton2() {
        String numberVal = number.getText().toString();
        if (numberVal.isEmpty()) { numberVal = "0"; }
        if((numberVal.toString()).length() > 2) {
            Toast.makeText(DemoActivity.this, R.string.demo_activity_warning_2, Toast.LENGTH_LONG).show();
        }
        else {
            int count = Integer.parseInt(numberVal);
            if (count > 19) {
                Toast.makeText(DemoActivity.this, R.string.demo_activity_warning_2, Toast.LENGTH_LONG).show();
            }
            else {
                // init list
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    list.add(i + 1);
                }
                // Count all items in list
                int mul = list.stream().mapToInt(Integer::intValue).filter(x -> (x % 2 == 0)&&(x != 0)).reduce((x, y) -> x*y).orElse(0);
                result.setText(getString(R.string.demo_activity_result, mul));
            }
        }
    }











}