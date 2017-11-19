package calendarapp.truongnh.com.calendarapp.Picker;

import calendarapp.truongnh.com.calendarapp.R;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PickerActivity extends AppCompatActivity {

    private int currentDay;
    private int currentMonth;
    private int currentYear;

    private int currentHour;
    private int currentMinute;
    private int currentAMPM;

    private NumberPicker dayPicker;
    private NumberPicker monthPicker;
    private NumberPicker yearPicker;

    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private NumberPicker ampmPicker;


    Date datePicker;
    String pickerType;

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", new Locale("en", "US"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        datePicker = new Date();

        pickerType = getIntent().getStringExtra("pickerType");
        if ("PickerFrom".equals(pickerType)) {
            datePicker.setTime(getIntent().getLongExtra("dateFrom", -1));
        } else {
            datePicker.setTime(getIntent().getLongExtra("dateTo", -1));
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datePicker);

        int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DATE);

        currentHour = calendar.get(Calendar.HOUR);
        currentMinute = calendar.get(Calendar.MINUTE);
        currentAMPM = calendar.get(Calendar.AM_PM) + 1;



        dayPicker = (NumberPicker) findViewById(R.id.dayPicker);
        dayPicker.setMaxValue(dayOfMonth);
        dayPicker.setMinValue(1);
        dayPicker.setValue(currentDay);
        dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentDay = newVal;
            }
        });

        monthPicker = (NumberPicker) findViewById(R.id.monthPicker);
        monthPicker.setMaxValue(12);
        monthPicker.setMinValue(1);
        monthPicker.setValue(currentMonth);
        monthPicker.setDisplayedValues(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentMonth = newVal;
                setPickerDay();
            }
        });

        yearPicker = (NumberPicker) findViewById(R.id.yearPicker);
        yearPicker.setMaxValue(currentYear + 50);
        yearPicker.setMinValue(currentYear - 50);
        yearPicker.setValue(currentYear);
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentYear = newVal;
                setPickerDay();
            }
        });


        hourPicker = (NumberPicker) findViewById(R.id.hourPicker);
        hourPicker.setMaxValue(12);
        hourPicker.setMinValue(1);
        hourPicker.setValue(currentHour);
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (currentAMPM == 2 && newVal == 12) {
                    currentHour = newVal;
                    currentAMPM = 1;
                    ampmPicker.setValue(currentAMPM);
                }

                setPickerDay();
            }
        });

        minutePicker = (NumberPicker) findViewById(R.id.minutePicker);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(1);
        minutePicker.setValue(currentMinute);
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentMinute = newVal;
                setPickerDay();
            }
        });

        ampmPicker = (NumberPicker) findViewById(R.id.ampmPicker);
        ampmPicker.setMaxValue(2);
        ampmPicker.setMinValue(1);
        ampmPicker.setValue(currentAMPM);
        ampmPicker.setDisplayedValues(new String[]{"am", "pm"});
        ampmPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentAMPM = newVal;
                setPickerDay();
            }
        });
    }

    private void setPickerDay() {
        int dayOfMonth = currentMonth == 2 ?
                28 + (currentYear % 4 == 0 ? 1:0) - (currentYear % 100 == 0 ? (currentYear % 400 == 0 ? 0 : 1) : 0) :
                31 - (currentMonth-1) % 7 % 2;
        if (currentDay > dayOfMonth) {
            currentDay = dayOfMonth;
        }
        dayPicker.setMaxValue(dayOfMonth);
    }

    @SuppressLint("DefaultLocale")
    public void onTapConfirm(View view) {
        String dateString = String.format("%d-%d-%d %d:%d:00",
                currentDay, currentMonth, currentYear,
                currentAMPM == 1 ? currentHour : currentHour + 12, currentMinute);
        try {
            datePicker = format.parse(dateString);
            getIntent().putExtra("pickerType", pickerType);
            if ("PickerFrom".equals(pickerType)) {
                getIntent().putExtra("dateFrom", datePicker.getTime());
            } else {
                getIntent().putExtra("dateTo", datePicker.getTime());
            }

            setResult(RESULT_OK, getIntent());
            super.onBackPressed();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onTapCancel(View view) {
        super.onBackPressed();
    }

}