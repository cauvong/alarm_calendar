package calendarapp.truongnh.com.calendarapp.Calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import calendarapp.truongnh.com.calendarapp.AddTask.AddTaskActivity;
import calendarapp.truongnh.com.calendarapp.HomeListTaskAdapter;
import calendarapp.truongnh.com.calendarapp.R;
import calendarapp.truongnh.com.calendarapp.ShamsiCalleder;
import calendarapp.truongnh.com.calendarapp.TNHCalendarAdapter;
import calendarapp.truongnh.com.calendarapp.TNHDate;
import calendarapp.truongnh.com.calendarapp.ViewTask.ViewTaskActivity;
import calendarapp.truongnh.com.calendarapp.constant.Contants;
import calendarapp.truongnh.com.calendarapp.reminder.Reminder;
import calendarapp.truongnh.com.calendarapp.reminder.ReminderDatabase;

public class CalendarActivity extends AppCompatActivity {

    private TNHDate currentDate;
    TNHCalendarAdapter mAdapterViewPager;

    private List<Reminder> mReminderList;
    private ReminderDatabase mReminderDatabase;
    private ListView mListView;


    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy MMMM dd", new Locale("en", "US"));
    SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("EEEE", new Locale("en", "US"));
    SimpleDateFormat formatDateS = new SimpleDateFormat("yyyy MMMM dd", new Locale("fa", "AF"));
    SimpleDateFormat formatDayOfWeekS = new SimpleDateFormat("EEEE", new Locale("fa", "AF"));
    SimpleDateFormat formatS = new SimpleDateFormat("yyyy/M/d", new Locale("en", "US"));
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", new Locale("fa", "AF"));

    TextView txvGregorian;
    TextView txvDateGregorian;
    TextView txvShamsi;
    TextView txvDateShamsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mListView = (ListView) findViewById(R.id.calListView);
        txvGregorian = (TextView) findViewById(R.id.txvGregorian);
        txvDateGregorian = (TextView) findViewById(R.id.txvDateGregorian);
        txvShamsi = (TextView) findViewById(R.id.txvShamsi);
        txvDateShamsi = (TextView) findViewById(R.id.txvDateShamsi);

        Date date = new Date();
        showListView(date);
        setupDisplayWithDate(date);

        mAdapterViewPager = new TNHCalendarAdapter(getSupportFragmentManager());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.home_viewPage);
        viewPager.setAdapter(mAdapterViewPager);

        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = (int) ((getResources().getDisplayMetrics().widthPixels/7.0*6)/10*8);
        viewPager.setLayoutParams(params);
        viewPager.setCurrentItem(6);

        Calendar calendar = Calendar.getInstance();
        currentDate = new TNHDate(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = mAdapterViewPager.getFragment(position);
                if (fragment != null) {
                    fragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void setupDisplayWithDate(Date date) {
        txvDateGregorian.setText(formatDate.format(date));
        txvGregorian.setText(formatDayOfWeek.format(date));

        String strDate = formatS.format(date);
        String strSh = ShamsiCalleder.getCurrentShamsidate(strDate) + "T00:00:00Z";
        try {
            Date dateSFrom = format.parse(strSh);
            txvDateShamsi.setText(formatDateS.format(dateSFrom));
            txvShamsi.setText(formatDayOfWeekS.format(dateSFrom));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void showListView(Date date) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy MMM dd", new Locale("en", "US"));
        mReminderDatabase = new ReminderDatabase(this);
        mReminderList = mReminderDatabase.getAllReminders(formatDate.format(date));

        HomeListTaskAdapter adapter = new HomeListTaskAdapter(mReminderList, this);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reminder reminder = mReminderList.get(position);
                gotoViewTask(reminder);
            }
        });
    }

    public TNHDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(TNHDate currentDate) {
        this.currentDate = currentDate;
    }

    /** Called when the user taps the Send button */
    public void gotoHome(View view) {
        super.onBackPressed();
    }

    public void gotoAddTask(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    public void gotoViewTask(Reminder reminder) {
        Intent intent = new Intent(this, ViewTaskActivity.class);
        intent.putExtra(Contants.KEY_REMINDER, reminder);
        startActivity(intent);
    }

}
