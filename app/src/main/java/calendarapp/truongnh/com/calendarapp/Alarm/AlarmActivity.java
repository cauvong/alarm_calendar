package calendarapp.truongnh.com.calendarapp.Alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import calendarapp.truongnh.com.calendarapp.Picker.PickerActivity;
import calendarapp.truongnh.com.calendarapp.R;

public class AlarmActivity extends AppCompatActivity {

    String itemsSelected;

    String remind = "Remind me";
    private String remind10minutes = "10 minutes before";
    private String remind30minutes = "30 minutes before";
    private String remind1Hour = "1 hour before";
    private String remind2Hours = "2 hours before";
    private String remind1Day = "1 day before";
    private String remind2Days = "2 days before";

    private String reminderAlarm = "";

    Date dateAlarm;
    Boolean isHaveDateAlarm = false;

    TextView textView;
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy MMM dd", new Locale("en", "US"));
    GridView gridView;
    AlarmAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        itemsSelected = getIntent().getStringExtra("calReminder");
        dateAlarm = new Date();

        String[] items = new String[]{"10\nMunites", "30\nMinutes", "1\nHour", "2\nHours", "1\nDay", "2\nDay", "Custom", "Clear"};
        alarmAdapter = new AlarmAdapter(this, items, this);

        gridView = (GridView) findViewById(R.id.alarmGridView);
        gridView.setAdapter(alarmAdapter);

        textView = (TextView) findViewById(R.id.txtAlarm);

        displayRemind();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) gridView.getItemAtPosition(i);

                if (item.equals("Custom")) {
                    gotoPickerDateFrom();
                    return;
                } else if (item.equals("Clear")) {
                    itemsSelected = "";
                    isHaveDateAlarm = false;
                } else if (itemsSelected.contains(String.valueOf(i))) {
                    itemsSelected = itemsSelected.replace(String.valueOf(i), "");
                } else {
                    itemsSelected += String.valueOf(i);
                }

                displayRemind();

                gridView.setAdapter(alarmAdapter);
            }
        });
    }

    private void displayRemind() {
        if (itemsSelected.equals("")) {
            remind = "";
        } else {
            remind = "Remind me ";
            if (itemsSelected.contains("0")) {
                remind += remind10minutes + ", ";
            }
            if (itemsSelected.contains("1")) {
                remind += remind30minutes + ", ";
            }
            if (itemsSelected.contains("2")) {
                remind += remind1Hour + ", ";
            }
            if (itemsSelected.contains("3")) {
                remind += remind2Hours + ", ";
            }
            if (itemsSelected.contains("4")) {
                remind += remind1Day + ", ";
            }
            if (itemsSelected.contains("5")) {
                remind += remind2Days + ", ";
            }
        }

        if (isHaveDateAlarm) {
            if (remind != "") {
                remind += "at" + formatDate.format(dateAlarm);
            } else {
                remind = "Remind me at " + formatDate.format(dateAlarm);
            }
        }

        textView.setText(remind);
    }

    public String getItemsSelected() {
        return itemsSelected;
    }

    public Boolean getHaveDateAlarm() {
        return isHaveDateAlarm;
    }

    /** Called when the user taps add task button */
    public void gotoPickerDateFrom() {
        Intent intent = new Intent(this, PickerActivity.class);
        intent.putExtra("pickerType", "PickerFrom");
        intent.putExtra("dateFrom", dateAlarm.getTime());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String pickerType = data.getStringExtra("pickerType");

            if ("PickerFrom".equals(pickerType)) {
                dateAlarm.setTime(data.getLongExtra("dateFrom", -1));
                isHaveDateAlarm = true;
                displayRemind();
                gridView.setAdapter(alarmAdapter);
            }
        }
    }

    public void onTapConfirm(View view) {
        getIntent().putExtra("calReminder", itemsSelected);
        setResult(RESULT_OK, getIntent());
        super.onBackPressed();
    }
}
