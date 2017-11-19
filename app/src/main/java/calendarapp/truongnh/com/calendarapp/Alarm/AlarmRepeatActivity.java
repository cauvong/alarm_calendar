package calendarapp.truongnh.com.calendarapp.Alarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import calendarapp.truongnh.com.calendarapp.Picker.PickerActivity;
import calendarapp.truongnh.com.calendarapp.R;

public class AlarmRepeatActivity extends AppCompatActivity {

    String itemsSelected;
    String remind;
    String[] items = new String[]{"DON'T REPEAT", "REPEAT DAILY", "REPEAT WEEKLY", "REPEAT MONTHLY", "REPEAT YEARLY"};

    TextView textView;
    GridView gridView;
    AlarmRepeatAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_repeat);

        itemsSelected = getIntent().getStringExtra("calRepeat");

        alarmAdapter = new AlarmRepeatAdapter(this, items, this);

        gridView = (GridView) findViewById(R.id.alarmGridView);
        gridView.setAdapter(alarmAdapter);

        textView = (TextView) findViewById(R.id.txtAlarm);
        displayRemind();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) gridView.getItemAtPosition(i);

                itemsSelected = String.valueOf(i);

                displayRemind();

                gridView.setAdapter(alarmAdapter);
            }
        });
    }

    private void displayRemind() {
        if (itemsSelected.equals("0")) {
            remind = items[0];
        }
        if (itemsSelected.equals("1")) {
            remind = items[1];
        }
        if (itemsSelected.equals("2")) {
            remind = items[2];
        }
        if (itemsSelected.equals("3")) {
            remind = items[3];
        }
        if (itemsSelected.equals("4")) {
            remind = items[4];
        }

        textView.setText(remind);
    }

    public String getItemsSelected() {
        return itemsSelected;
    }

    public void onTapConfirm(View view) {
        getIntent().putExtra("calRepeat", itemsSelected);
        setResult(RESULT_OK, getIntent());
        super.onBackPressed();
    }
}
