package calendarapp.truongnh.com.calendarapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import calendarapp.truongnh.com.calendarapp.AddTask.AddTaskActivity;
import calendarapp.truongnh.com.calendarapp.Calendar.CalendarActivity;
import calendarapp.truongnh.com.calendarapp.constant.Contants;


public class HomeActivity extends AppCompatActivity{

    List<Integer> lstImage = new ArrayList<>();

    ViewPager viewpager;
    HomeFragmentPageAdapter adapterViewPager;
    FragmentBar firstFragment;

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
        setContentView(R.layout.activity_home);


        txvGregorian = (TextView) findViewById(R.id.txvGregorian);
        txvDateGregorian = (TextView) findViewById(R.id.txvDateGregorian);
        txvShamsi = (TextView) findViewById(R.id.txvShamsi);
        txvDateShamsi = (TextView) findViewById(R.id.txvDateShamsi);

        setupDisplayWithDate(new Date());

        viewpager = (ViewPager) findViewById(R.id.home_gallery);
        adapterViewPager = new HomeFragmentPageAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapterViewPager);
        viewpager.setCurrentItem(50, false);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                Date date = new Date();
                Date newDate = new Date(date.getTime() + 24 * 60 * 60000 * (position - 50));
                setupDisplayWithDate(newDate);

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

    /** Called when the user taps the Send button */
    public void gotoCalendar(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    /** Called when the user taps add task button */
    public void gotoAddTask(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

}
