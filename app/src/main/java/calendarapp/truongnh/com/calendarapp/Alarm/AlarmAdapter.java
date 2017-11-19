package calendarapp.truongnh.com.calendarapp.Alarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import calendarapp.truongnh.com.calendarapp.R;
import calendarapp.truongnh.com.calendarapp.TNHSquareIMV;

/**
 * Created by nguyentruong on 11/8/17.
 */

public class AlarmAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] items;
    private final AlarmActivity mActivity;

    public AlarmAdapter(Context mContext, String[] items, AlarmActivity mActivity) {
        this.mContext = mContext;
        this.items = items;
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return (String)items[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.alarm_item, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.lblAlarm);
        String textString = (String) getItem(position);
        textView.setText(textString);

        TNHSquareIMV tnhSquareIMV = (TNHSquareIMV) convertView.findViewById(R.id.imageView);

        if (position < 6) {
            if (mActivity.getItemsSelected().contains(String.valueOf(position))) {
                tnhSquareIMV.setBackgroundResource(R.color.alarm_activity_check_color);
            } else {
                tnhSquareIMV.setBackgroundResource(R.color.alarm_activity_normal_color);
            }
        } else {
            if (position == 6) {
                if (mActivity.getHaveDateAlarm()) {
                    tnhSquareIMV.setBackgroundResource(R.color.alarm_activity_check_color);
                } else {
                    tnhSquareIMV.setBackgroundResource(R.color.alarm_activity_normal_color);
                }
            } else {
                tnhSquareIMV.setBackgroundResource(R.color.alarm_activity_normal_color);
            }
        }

        return convertView;
    }
}
