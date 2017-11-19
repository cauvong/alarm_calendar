package calendarapp.truongnh.com.calendarapp.AddTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import calendarapp.truongnh.com.calendarapp.HomeActivity;
import calendarapp.truongnh.com.calendarapp.Picker.PickerActivity;
import calendarapp.truongnh.com.calendarapp.Picker.PickerShamsiActivity;
import calendarapp.truongnh.com.calendarapp.R;
import calendarapp.truongnh.com.calendarapp.ShamsiCalleder;
import calendarapp.truongnh.com.calendarapp.ViewTask.ViewTaskActivity;
import calendarapp.truongnh.com.calendarapp.constant.Contants;
import calendarapp.truongnh.com.calendarapp.reminder.AlarmReceiver;
import calendarapp.truongnh.com.calendarapp.reminder.Reminder;
import calendarapp.truongnh.com.calendarapp.reminder.ReminderDatabase;

public class AddTaskActivity extends AppCompatActivity {

    TextView txtDateFrom;
    TextView txtTimeFrom;

    TextView txtDateSFrom;
    TextView txtTimeSFrom;

    TextView txtDateTo;
    TextView txtTimeTo;

    TextView txtDateSTo;
    TextView txtTimeSTo;

    EditText mEdtTitle;


    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy MMM dd", new Locale("en", "US"));
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", new Locale("en", "US"));
    SimpleDateFormat formatDateS = new SimpleDateFormat("yyyy MMM dd", new Locale("fa", "AF"));
    SimpleDateFormat formatTimeS = new SimpleDateFormat("HH:mm", new Locale("fa", "AF"));
    SimpleDateFormat formatS = new SimpleDateFormat("yyyy/M/d", new Locale("en", "US"));
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", new Locale("fa", "AF"));


    Date dateFrom;
    Date dateTo;

    //todo Cuongnv31

    private Reminder reminder ;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private long mRepeatTime;
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeat;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;
    private String mDateTo;
    private String mTimeTo;
    private String mTypeReminder = "";

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_REPEAT = "repeat_key";
    private static final String KEY_REPEAT_NO = "repeat_no_key";
    private static final String KEY_REPEAT_TYPE = "repeat_type_key";
    private static final String KEY_ACTIVE = "active_key";

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;
    private static final long milYear = 31556952000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        txtDateFrom = (TextView) findViewById(R.id.txtDateFrom);
        txtTimeFrom = (TextView) findViewById(R.id.txtTimeFrom);

        txtDateSFrom = (TextView) findViewById(R.id.txtDateSFrom);
        txtTimeSFrom = (TextView) findViewById(R.id.txtTimeSFrom);

        txtDateTo = (TextView) findViewById(R.id.txtDateTo);
        txtTimeTo = (TextView) findViewById(R.id.txtTimeTo);

        txtDateSTo = (TextView) findViewById(R.id.txtDateSTo);
        txtTimeSTo = (TextView) findViewById(R.id.txtTimeSTo);

        mEdtTitle = (EditText) findViewById(R.id.editText);

        dateFrom = new Date();
        setupDisplayDateFrom();

        dateTo = new Date(dateFrom.getTime() + 60 * 60000);
        setupDisplayDateTo();


        // Initialize default values
        mActive = "true";
        mRepeat = "false";
        mRepeatNo = Integer.toString(0);
        mRepeatType = "Hour";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

    }

    private void setupDisplayDateFrom() {
        String stringDateFrom = formatDate.format(dateFrom);
        String stringTimeFrom = formatTime.format(dateFrom);

        txtDateFrom.setText(stringDateFrom);
        txtTimeFrom.setText(stringTimeFrom);

        String strDate = formatS.format(dateFrom);
        Log.d("TABBBBBBBB", strDate);
        String strSh = ShamsiCalleder.getCurrentShamsidate(strDate) + "T00:00:00Z";
        try {
            Date dateSFrom = format.parse(strSh);

            String stringDateS = formatDateS.format(dateSFrom);
            String stringTimeS = formatTimeS.format(dateFrom);

            txtDateSFrom.setText(stringDateS);
            txtTimeSFrom.setText(stringTimeS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setupDisplayDateTo() {
        String stringDateTo = formatDate.format(dateTo);
        String stringTimeTo = formatTime.format(dateTo);

        mDateTo = stringDateTo;
        mTimeTo = stringTimeTo;

        txtDateTo.setText(stringDateTo);
        txtTimeTo.setText(stringTimeTo);

        String strDateTo = formatS.format(dateTo);
        String strShTo = ShamsiCalleder.getCurrentShamsidate(strDateTo) + "T00:00:00Z";
        try {
            Date dateSTo = format.parse(strShTo);

            String stringDateSTo = formatDateS.format(dateSTo);
            String stringTimeSTo = formatTimeS.format(dateTo);

            txtDateSTo.setText(stringDateSTo);
            txtTimeSTo.setText(stringTimeSTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /** Called when the user taps add task button */
    public void gotoViewTask(View view) {
        if (!mEdtTitle.getText().toString().trim().isEmpty()) {
            saveReminder();
            Intent intent = new Intent(this, ViewTaskActivity.class);
            intent.putExtra(Contants.KEY_REMINDER, reminder);
            intent.putExtra(Contants.KEY_CALENDAR, mCalendar);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Please type note!", Toast.LENGTH_SHORT).show();
        }

    }

    /** Called when the user taps add task button */
    public void gotoPickerDateFrom(View view) {
        Intent intent = new Intent(this, PickerActivity.class);
        intent.putExtra("pickerType", "PickerFrom");
        intent.putExtra("dateFrom", dateFrom.getTime());
        startActivityForResult(intent, 1);
    }

    /** Called when the user taps add task button */
    public void gotoPickerDateTo(View view) {
        Intent intent = new Intent(this, PickerActivity.class);
        intent.putExtra("pickerType", "PickerTo");
        intent.putExtra("dateTo", dateTo.getTime());
        startActivityForResult(intent, 1);
    }

    /** Called when the user taps add task button */
    public void gotoPickerDateSFrom(View view) {
        Intent intent = new Intent(this, PickerShamsiActivity.class);
        intent.putExtra("pickerType", "PickerFrom");
        intent.putExtra("dateFrom", dateFrom.getTime());
        startActivityForResult(intent, 1);
    }

    /** Called when the user taps add task button */
    public void gotoPickerDateSTo(View view) {
        Intent intent = new Intent(this, PickerShamsiActivity.class);
        intent.putExtra("pickerType", "PickerTo");
        intent.putExtra("dateTo", dateTo.getTime());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String pickerType = data.getStringExtra("pickerType");

            if ("PickerFrom".equals(pickerType)) {
                dateFrom.setTime(data.getLongExtra("dateFrom", -1));
                setupDisplayDateFrom();
            }

            if ("PickerTo".equals(pickerType)) {
                dateTo.setTime(data.getLongExtra("dateTo", -1));
                setupDisplayDateTo();
            }
        }
    }

    // On clicking the save button
    public void saveReminder() {
        mTime = txtTimeFrom.getText().toString().trim();
        Log.d("TABBBBBB", mTime + "");

        String[] itemTime = mTime.split(":");
        mTitle = mEdtTitle.getText().toString().trim();

        mDate = txtDateFrom.getText().toString();

        mHour = Integer.parseInt(itemTime[0]);
        mMinute = Integer.parseInt(itemTime[1]);
        // Create a new notification
        ReminderDatabase rb = new ReminderDatabase(this);

        // Creating Reminder
        reminder = new Reminder();
        reminder.setTitle(mTitle);
        reminder.setDateFrom(mDate);
        reminder.setTimeFrom(mTime);
        reminder.setRepeat(mRepeat);
        reminder.setRepeatNo(mRepeatNo);
        reminder.setRepeatType(mRepeatType);
        reminder.setActive(mActive);
        reminder.setDateTo(mDateTo);
        reminder.setTimeTo(mTimeTo);
        reminder.setTypeReminder("");
        reminder.setCalPerson("");
        reminder.setCalNote("");
        reminder.setYear(mYear);
        reminder.setMonth(mMonth);
        reminder.setDayOfMonth(mDay);
        reminder.setHour(mHour);
        reminder.setMinute(mMinute);
        reminder.setSecond(0);

        int ID = rb.addReminder(reminder);

        // Set up calender for creating the notification
        mCalendar.set(Calendar.MONTH, mMonth - 1);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        // Check repeat type
        if (mRepeatType.equals("Minute")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
        } else if (mRepeatType.equals("Hour")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
        } else if (mRepeatType.equals("Day")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
        } else if (mRepeatType.equals("Week")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
        } else if (mRepeatType.equals("Month")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
        } else if (mRepeatType.equals("Year")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milYear;
        }

        // Create a new notification
        new AlarmReceiver().setAlarm(getApplicationContext(), mCalendar, ID, reminder);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
