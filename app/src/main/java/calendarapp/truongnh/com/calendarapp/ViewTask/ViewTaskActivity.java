package calendarapp.truongnh.com.calendarapp.ViewTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import calendarapp.truongnh.com.calendarapp.Alarm.AlarmActivity;
import calendarapp.truongnh.com.calendarapp.Alarm.AlarmRepeatActivity;
import calendarapp.truongnh.com.calendarapp.Calendar.CalendarActivity;
import calendarapp.truongnh.com.calendarapp.HomeActivity;
import calendarapp.truongnh.com.calendarapp.R;
import calendarapp.truongnh.com.calendarapp.constant.Contants;
import calendarapp.truongnh.com.calendarapp.reminder.AlarmReceiver;
import calendarapp.truongnh.com.calendarapp.reminder.Reminder;
import calendarapp.truongnh.com.calendarapp.reminder.ReminderDatabase;

public class ViewTaskActivity extends AppCompatActivity {

    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy MMM dd", new Locale("en", "US"));
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", new Locale("en", "US"));

    private TextView txtTaskName;
    private TextView txtDisplayDate;
    private TextView txtDisplayTime;

    private RelativeLayout rlCalPerson;
    private TextView txtCalPerson;
    private RelativeLayout rlCalNote;
    private TextView txtCalNote;
    private RelativeLayout rlCalReminder;
    private TextView txtCalReminder;
    private RelativeLayout rlCalRepeat;
    private TextView txtCalRepeat;

    Reminder reminder = new Reminder();
    ReminderDatabase rb;

    private String remind10minutes = "10 minutes before";
    private String remind30minutes = "30 minutes before";
    private String remind1Hour = "1 hour before";
    private String remind2Hours = "2 hours before";
    private String remind1Day = "1 day before";
    private String remind2Days = "2 days before";
    String[] items = new String[]{"DON'T REPEAT", "REPEAT DAILY", "REPEAT WEEKLY", "REPEAT MONTHLY", "REPEAT YEARLY"};

    String[] typeReminder;
    private Calendar mCalendar;

    private long mTimeRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        rb = new ReminderDatabase(getApplicationContext());
        mCalendar = Calendar.getInstance();
        if (getIntent() != null) {
            reminder = getIntent().getParcelableExtra(Contants.KEY_REMINDER);
            // Set up calender for creating the notification
            mCalendar.set(Calendar.MONTH, reminder.getMonth() - 1);
            mCalendar.set(Calendar.YEAR, reminder.getYear());
            mCalendar.set(Calendar.DAY_OF_MONTH, reminder.getDayOfMonth());
            mCalendar.set(Calendar.HOUR_OF_DAY, reminder.getHour());
            mCalendar.set(Calendar.MINUTE, reminder.getMinute());
            mCalendar.set(Calendar.SECOND, 0);
        }

        txtTaskName = (TextView) findViewById(R.id.txtTaskName);
        txtTaskName.setText(reminder.getTitle());

        txtDisplayDate = (TextView) findViewById(R.id.txtDisplayDate);
        txtDisplayDate.setText(reminder.getDateFrom() + " - " + reminder.getDateTo());

        txtDisplayTime = (TextView) findViewById(R.id.txtDisplayTime);
        txtDisplayTime.setText(reminder.getTimeFrom() + " - " + reminder.getTimeTo());

        rlCalPerson = (RelativeLayout) findViewById(R.id.rlCalPerson);
        txtCalPerson = (TextView) findViewById(R.id.txtCalPerson);
        rlCalNote = (RelativeLayout) findViewById(R.id.rlCalNote);
        txtCalNote = (TextView) findViewById(R.id.txtCalNote);
        rlCalReminder = (RelativeLayout) findViewById(R.id.rlCalReminder);
        txtCalReminder = (TextView) findViewById(R.id.txtCalReminder);
        rlCalRepeat = (RelativeLayout) findViewById(R.id.rlCalRepeat);
        txtCalRepeat = (TextView) findViewById(R.id.txtCalRepeat);

        setupDisplay();

    }

    private void setupDisplay() {

        if (reminder.getCalPerson().isEmpty()) {
            rlCalPerson.setVisibility(View.GONE);
        } else {
            rlCalPerson.setVisibility(View.VISIBLE);
            txtCalPerson.setText(reminder.getCalPerson());
        }

        if (reminder.getCalNote().isEmpty()) {
            rlCalNote.setVisibility(View.GONE);
        } else {
            rlCalNote.setVisibility(View.VISIBLE);
            txtCalNote.setText(reminder.getCalNote());
        }

        displayRemind();

        displayRepeat();

    }

    public void gotoAlarmActivity(View view) {
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.putExtra("calReminder", reminder.getTypeReminder());
        intent.putExtra("alarmType", "remind");
        startActivityForResult(intent, 1);
    }

    public void gotoAlarmRepeatActivity(View view) {
        Intent intent = new Intent(this, AlarmRepeatActivity.class);
        intent.putExtra("calRepeat", reminder.getRepeatNo());
        intent.putExtra("alarmType", "repeat");
        startActivityForResult(intent, 1);
    }

    public void openAlerTypePerson(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Who else is coming?");
        final EditText editText = new EditText(this);
        editText.setText(reminder.getCalPerson());
        builder1.setView(editText);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reminder.setCalPerson(editText.getText().toString().trim());

                        rb.updateReminder(reminder);
                        Log.d("TAIIIII", rb.getAllReminders().get(0).getCalPerson() + "");
                        setupDisplay();

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void openAlerTypeNote(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Note?");
        final EditText editText = new EditText(this);
        editText.setText(reminder.getCalNote());
        builder1.setView(editText);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        reminder.setCalNote(editText.getText().toString().trim());
                        rb.updateReminder(reminder);
                        setupDisplay();

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void onClickAddButton(View view) {
        setAlarmRemind();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickDeleteButton(View view) {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure to delete it?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteTask();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void deleteTask() {
        ReminderDatabase rb = new ReminderDatabase(this);
        rb.deleteReminder(reminder);
        new AlarmReceiver().cancelAlarm(this, reminder.getId());
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            String alarmType = data.getStringExtra("alarmType");

            if (alarmType.equals("remind")) {
                String calReminder = data.getStringExtra("calReminder");
                Log.d("TAHHHHHHH", calReminder);
                reminder.setTypeReminder(calReminder);
                rb.updateReminder(reminder);
                displayRemind();
            } else {
                String calRepeat = data.getStringExtra("calRepeat");
                reminder.setRepeatNo(calRepeat);
                rb.updateReminder(reminder);
                displayRepeat();
            }
        }
    }

    private void displayRemind() {

        String itemsSelected = reminder.getTypeReminder();
        String remind;

        if (itemsSelected.isEmpty()) {
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

        if (remind.equals("")) {
            rlCalReminder.setVisibility(RelativeLayout.GONE);
        } else {
            rlCalReminder.setVisibility(RelativeLayout.VISIBLE);
            txtCalReminder.setText(remind);
        }
    }

    private void displayRepeat() {
        String itemsSelected = reminder.getRepeatNo();
        String repeat = "";
        if (itemsSelected.equals("0")) {
            repeat = items[0];
        }
        if (itemsSelected.equals("1")) {
            repeat = items[1];
        }
        if (itemsSelected.equals("2")) {
            repeat = items[2];
        }
        if (itemsSelected.equals("3")) {
            repeat = items[3];
        }
        if (itemsSelected.equals("4")) {
            repeat = items[4];
        }

        if (repeat.equals("")) {
            rlCalRepeat.setVisibility(RelativeLayout.GONE);
        } else {
            rlCalRepeat.setVisibility(RelativeLayout.VISIBLE);
            txtCalRepeat.setText(repeat);
        }
    }

    private void setAlarmRemind() {
        Toast.makeText(this, "set alarm", Toast.LENGTH_SHORT).show();
        String itemsSelected = reminder.getTypeReminder();
        String itemsSelectedRepeat = reminder.getRepeatNo();

        if (!itemsSelected.isEmpty()) {
            if (itemsSelectedRepeat.equals("0")) {
                setAlarmType(itemsSelected);
            } else {
                if (itemsSelectedRepeat.equals("1")) {
                    mTimeRepeat = Contants.milDay;
                }
                if (itemsSelectedRepeat.equals("2")) {
                    mTimeRepeat = Contants.milWeek;
                }
                if (itemsSelectedRepeat.equals("3")) {
                    mTimeRepeat = Contants.milMonth;
                }
                if (itemsSelectedRepeat.equals("4")) {
                    mTimeRepeat = Contants.milYear;
                }
                setAlarmTypeRepeat(itemsSelected);
            }
        }
    }

    private void setAlarmType(String type) {
        // Set up calender for creating the notification
        if (type.contains("0")) {
            new AlarmReceiver().setAlarmBefore10(getApplicationContext(), mCalendar, reminder.getId() * 100, reminder);
        }
        if (type.contains("1")) {
            new AlarmReceiver().setAlarmBefore30(getApplicationContext(), mCalendar, reminder.getId() * 101, reminder);
        }
        if (type.contains("2")) {
            new AlarmReceiver().setAlarmBefore1Hour(getApplicationContext(), mCalendar, reminder.getId() * 102, reminder);
        }
        if (type.contains("3")) {
            new AlarmReceiver().setAlarmBefore2Hours(getApplicationContext(), mCalendar, reminder.getId() * 103, reminder);
        }
        if (type.contains("4")) {
            new AlarmReceiver().setAlarmBefore1Day(getApplicationContext(), mCalendar, reminder.getId() * 104, reminder);
        }
        if (type.contains("5")) {
            new AlarmReceiver().setAlarmBefore2Days(getApplicationContext(), mCalendar, reminder.getId() * 105, reminder);
        }
    }

    private void setAlarmTypeRepeat(String type) {
        // Set up calender for creating the notification
        if (type.contains("0")) {
            new AlarmReceiver().setAlarmBeforeRepeat10(getApplicationContext(), mCalendar, reminder.getId() * 100, reminder, mTimeRepeat);
        }
        if (type.contains("1")) {
            new AlarmReceiver().setAlarmBeforeRepeat30(getApplicationContext(), mCalendar, reminder.getId() * 101, reminder, mTimeRepeat);
        }
        if (type.contains("2")) {
            new AlarmReceiver().setAlarmBeforeRepeat1Hour(getApplicationContext(), mCalendar, reminder.getId() * 102, reminder, mTimeRepeat);
        }
        if (type.contains("3")) {
            new AlarmReceiver().setAlarmBeforeRepeat2Hours(getApplicationContext(), mCalendar, reminder.getId() * 103, reminder, mTimeRepeat);
        }
        if (type.contains("4")) {
            new AlarmReceiver().setAlarmBeforeRepeat1Day(getApplicationContext(), mCalendar, reminder.getId() * 104, reminder, mTimeRepeat);
        }
        if (type.contains("5")) {
            new AlarmReceiver().setAlarmBeforeRepeat2Days(getApplicationContext(), mCalendar, reminder.getId() * 105, reminder, mTimeRepeat);
        }
    }
}
