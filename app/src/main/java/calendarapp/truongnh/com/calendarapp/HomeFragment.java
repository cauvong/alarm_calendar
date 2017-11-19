package calendarapp.truongnh.com.calendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import calendarapp.truongnh.com.calendarapp.ViewTask.ViewTaskActivity;
import calendarapp.truongnh.com.calendarapp.constant.Contants;
import calendarapp.truongnh.com.calendarapp.reminder.Reminder;
import calendarapp.truongnh.com.calendarapp.reminder.ReminderDatabase;

public class HomeFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;

    private List<Reminder> mReminderList;

    private ReminderDatabase mReminderDatabase;

    private ListView mListView;

    private Calendar mCalendar;


    // newInstance constructor for creating fragment with arguments
    public static HomeFragment newInstance(int page, String title) {
        HomeFragment fragmentFirst = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mListView = (ListView) view.findViewById(R.id.fragmentLitsView);

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy MMM dd", new Locale("en", "US"));
        Date date = new Date();
        Date newDate = new Date(date.getTime() + 24 * 60 * 60000 * (page - 50));

        mReminderDatabase = new ReminderDatabase(getActivity());
        mReminderList = mReminderDatabase.getAllReminders(formatDate.format(newDate));

        mCalendar = Calendar.getInstance();


        HomeListTaskAdapter adapter = new HomeListTaskAdapter(mReminderList, getContext());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reminder reminder = mReminderList.get(position);
                Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
                intent.putExtra(Contants.KEY_REMINDER, reminder);
                Log.d("TAGGGGG", reminder.getCalNote() + "" + reminder.getTitle());
                startActivity(intent);
            }
        });

        return view;

    }
}
