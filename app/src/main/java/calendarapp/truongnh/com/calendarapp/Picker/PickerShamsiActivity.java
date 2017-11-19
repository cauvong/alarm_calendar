package calendarapp.truongnh.com.calendarapp.Picker;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import calendarapp.truongnh.com.calendarapp.R;
import calendarapp.truongnh.com.calendarapp.ShamsiCalleder;

public class PickerShamsiActivity extends AppCompatActivity {

    private int beginDay;
    private int beginMonth;
    private int beginYear;
    private int beginSDay;
    private int beginSMonth;
    private int beginSYear;

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


    Date datePicker;
    String pickerType;

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", new Locale("en", "US"));
    NumberFormat numberFormat = NumberFormat.getInstance(new Locale("fa", "AF"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_shamsi);

        datePicker = new Date();

        pickerType = getIntent().getStringExtra("pickerType");
        if ("PickerFrom".equals(pickerType)) {
            datePicker.setTime(getIntent().getLongExtra("dateFrom", -1));
        } else {
            datePicker.setTime(getIntent().getLongExtra("dateTo", -1));
        }

        SimpleDateFormat formatS = new SimpleDateFormat("yyyy/M/d", new Locale("en", "US"));
        String strDate = formatS.format(datePicker);
        ShamsiCalleder.SolarCalendar sc = ShamsiCalleder.getShamsidate(strDate);

        int dayOfMonth = getDayOfMonth(sc.month, sc.year);

        //----
        beginSYear = sc.year;
        beginSMonth = sc.month;
        beginSDay = sc.date;

        //-----
        currentYear = sc.year;
        currentMonth = sc.month;
        currentDay = sc.date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datePicker);

        beginYear = calendar.get(Calendar.YEAR);
        beginMonth = calendar.get(Calendar.MONTH) + 1;
        beginDay = calendar.get(Calendar.DATE);

        currentHour = calendar.get(Calendar.HOUR);
        currentMinute = calendar.get(Calendar.MINUTE);
        currentAMPM = calendar.get(Calendar.AM_PM) + 1;
        if (currentAMPM == 2) {
            currentHour = currentHour + 12;
        }



        dayPicker = (NumberPicker) findViewById(R.id.dayPicker);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        String[] displayValueDay = new String[31];
        for (int i = 0; i < 31; i++) {
            displayValueDay[i] = numberFormat.format(i+1);
        }
        dayPicker.setDisplayedValues(displayValueDay);
        setPickerDay();
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
        monthPicker.setDisplayedValues(new String[]{"حمل|وری", "ثور|غويی", "جوزا|غبرګولی", "سرطان|چنګاښ", "اسد|زمری",
                "سنبله|وږی", "میزان|تله", "عقرب|لړم", "قوس|ليندۍ", "جدی|مرغومی", "دلو|سلواغه", "حوت|كب"});
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

        String[] displayValueYear = new String[101];
        int iYear = currentYear - 50;
        for (int i = 0; i < 101 ; i++) {
            String numberS = numberFormat.format(iYear);
            displayValueYear[i] = numberS.replace("٬","");
            iYear ++;
        }
        Arrays.sort(displayValueYear);
        yearPicker.setDisplayedValues(displayValueYear);

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentYear = newVal;
                setPickerDay();
            }
        });


        hourPicker = (NumberPicker) findViewById(R.id.hourPicker);
        hourPicker.setMaxValue(23);
        hourPicker.setMinValue(0);

        String[] displayValueHour = new String[24];
        for (int i = 0; i < 24; i++) {
            displayValueHour[i] = numberFormat.format(i);
        }
        hourPicker.setDisplayedValues(displayValueHour);

        hourPicker.setValue(currentHour);
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    currentHour = newVal;
            }
        });

        minutePicker = (NumberPicker) findViewById(R.id.minutePicker);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(1);
        String[] displayValueMinute = new String[59];
        for (int i = 0; i < 59; i++) {
            displayValueMinute[i] = numberFormat.format(i+1);
        }
        minutePicker.setDisplayedValues(displayValueMinute);
        minutePicker.setValue(currentMinute);
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentMinute = newVal;
                setPickerDay();
            }
        });
    }

    private void setPickerDay() {
        int dayOfMonth = getDayOfMonth(currentMonth, currentYear);
        dayPicker.setMaxValue(dayOfMonth);
        if (currentDay > dayOfMonth) {
            currentDay = dayOfMonth;
        }
        dayPicker.setValue(currentDay);
    }

    @SuppressLint("DefaultLocale")
    public void onTapConfirm(View view) {

        beginDay = beginDay + (currentDay - beginSDay);
        beginMonth = beginMonth + (currentMonth - beginSMonth);
        beginYear = beginYear + (currentYear - beginSYear);

        String dateString = String.format("%d-%d-%d %d:%d:00",
                beginDay, beginMonth, beginYear, currentHour, currentMinute);
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

    public int getDayOfMonth(int month, int year) {
        if (month <= 6) {
            return 31;
        } else if (month < 11) {
            return 30;
        } else {
            if (isLeapYear(year)) {
                return 30;
            } else {
                return 29;
            }
        }
    }

    public boolean isLeapYear(int _year){
        boolean _leap;
        switch (_year % 33) {
            case 1:
                _leap = true;
                break;
            case 5:
                _leap = true;
                break;
            case 9:
                _leap = true;
                break;
            case 13:
                _leap = true;
                break;
            case 17:
                _leap = true;
                break;
            case 22:
                _leap = true;
                break;
            case 26:
                _leap = true;
                break;
            case 30:
                _leap = true;
                break;

            default:
                _leap = false;
                break;
        }
        return _leap;
    }

}
