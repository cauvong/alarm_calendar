package calendarapp.truongnh.com.calendarapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import calendarapp.truongnh.com.calendarapp.Calendar.CalendarActivity;
import calendarapp.truongnh.com.calendarapp.reminder.ReminderDatabase;

public class TNHCalendarFragment extends Fragment {

    private Calendar calendar;
    GridView gridView;
    TNHCalendarItemAdapter tnhCalendarItemAdapter;

    private int position;

    public TNHCalendarFragment() {
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_tnhcalendar, container, false);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, position);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, -dayOfMonth+1);

        int monthMaxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

//        String weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
//        TextView textView = (TextView) view.findViewById(R.id.dayTextView);
//        textView.setText(weekday);

        final TNHDate[] tnhDates = new TNHDate[42];
        for (int i  = 0; i < 42; i++) {
            if ((i >= dayOfWeek) && (i < dayOfWeek + monthMaxDays)) {
                tnhDates[i] = new TNHDate((i-dayOfWeek+1),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
            } else {
                tnhDates[i] = new TNHDate(-1,0,0);
            }
        }

        final CalendarActivity activity = (CalendarActivity) getActivity();

        gridView = (GridView) view.findViewById(R.id.tnhcalendar_gridview);
        tnhCalendarItemAdapter = new TNHCalendarItemAdapter(getContext(), tnhDates, activity);
        gridView.setAdapter(tnhCalendarItemAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TNHDate tnhDate = (TNHDate) gridView.getItemAtPosition(i);

                if (tnhDate.getDay() != -1) {
                    activity.setCurrentDate(tnhDate);
                    gridView.setAdapter(tnhCalendarItemAdapter);

                    try {
                        String strDate = String.valueOf(tnhDate.getYear())
                                + "/" + String.valueOf(tnhDate.getMonth()+1)
                                + "/" + String.valueOf(tnhDate.getDay());
                        DateFormat dateFormatG = new SimpleDateFormat("yyyy/M/d", new Locale("en", "US"));
                        Date dateG = dateFormatG.parse(strDate);
                        activity.showListView(dateG);
                        activity.setupDisplayWithDate(dateG);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(getContext(), ShamsiCalleder.getCurrentShamsidate("2017/10/29"), Toast.LENGTH_SHORT).show();
                }

            }

        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        gridView.setAdapter(tnhCalendarItemAdapter);
    }
}
