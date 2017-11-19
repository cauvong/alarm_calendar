package calendarapp.truongnh.com.calendarapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import calendarapp.truongnh.com.calendarapp.Calendar.CalendarActivity;
import calendarapp.truongnh.com.calendarapp.reminder.Reminder;
import calendarapp.truongnh.com.calendarapp.reminder.ReminderDatabase;

/**
 * Created by 217REC10 on 10/27/17.
 */

public class TNHCalendarItemAdapter extends BaseAdapter {


    private final Context mContext;
    private final TNHDate[] books;
    private final CalendarActivity mActivity;

    private List<Reminder> mReminderList;
    private ReminderDatabase mReminderDatabase;


    public TNHCalendarItemAdapter(Context mContext, TNHDate[] books, CalendarActivity mActivity) {
        this.mContext = mContext;
        this.books = books;
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return books.length;
    }

    @Override
    public Object getItem(int i) {
        return books[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final TNHDate tnhDate = (TNHDate) getItem(i);

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.tnh_calendar_item, null);
        }

        if (tnhDate.getDay() != -1) {
            final TextView textView = (TextView) view.findViewById(R.id.gridSubviewText);
            textView.setText(String.valueOf(tnhDate.getDay()));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", new Locale("fa", "AF"));

            try {
                String strDate = String.valueOf(tnhDate.getYear())
                        + "/" + String.valueOf(tnhDate.getMonth()+1)
                        + "/" + String.valueOf(tnhDate.getDay());
                DateFormat dateFormatG = new SimpleDateFormat("yyyy/M/d", new Locale("en", "US"));
                Date dateG = dateFormatG.parse(strDate);

                String strSh = ShamsiCalleder.getCurrentShamsidate(strDate) + "T00:00:00Z";
                Date d = format.parse(strSh);

                DateFormat dateFormat1 = new SimpleDateFormat("d", new Locale("fa", "AF"));
                String formattedDate = dateFormat1.format(d);
                final TextView textView1 = (TextView) view.findViewById(R.id.gridSubviewText1);
                textView1.setText(formattedDate);

                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy MMM dd", new Locale("en", "US"));

                mReminderDatabase = new ReminderDatabase(mActivity);
                mReminderList = mReminderDatabase.getAllReminders(formatDate.format(dateG));

                ImageView imageView = (ImageView) view.findViewById(R.id.imgDot);
                if (mReminderList.size() > 0) {
                    imageView.setVisibility(ImageView.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

            final TNHSquareIMV squareImageView = (TNHSquareIMV) view.findViewById(R.id.imageView);

            Calendar calendar = Calendar.getInstance();

            int day = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH);

            if (day == tnhDate.getDay() && (month == tnhDate.getMonth())) {
                squareImageView.setBackgroundResource(R.color.cal_activity_current_color);
            }

            if (tnhDate.getDay() == mActivity.getCurrentDate().getDay() && (tnhDate.getMonth() == mActivity.getCurrentDate().getMonth())) {
                squareImageView.setBackgroundResource(R.color.cal_activity_check_color);
            }
        }


        return view;
    }
}
