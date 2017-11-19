package calendarapp.truongnh.com.calendarapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import calendarapp.truongnh.com.calendarapp.Utils.TextObjectUtils;
import calendarapp.truongnh.com.calendarapp.reminder.Reminder;

/**
 * Created by nguyentruong on 10/29/17.
 */

public class HomeListTaskAdapter extends BaseAdapter {

    List<Reminder> mReminders;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public HomeListTaskAdapter(List<Reminder> lstItems, Context mContext) {
        this.mReminders = lstItems;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mReminders.size();
    }

    @Override
    public Object getItem(int i) {
        return mReminders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.home_task_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txvTaskTime = (TextView) convertView.findViewById(R.id.txvTaskDate);
            viewHolder.txvTaskName = (TextView) convertView.findViewById(R.id.txvTaskTime);
            viewHolder.txvTaskNote = (TextView) convertView.findViewById(R.id.txvTaskNote);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Reminder data = mReminders.get(position);
        viewHolder.txvTaskTime.setText(TextObjectUtils.getSplit(data.getDateFrom())[2] +" " + TextObjectUtils.getSplit(data.getDateFrom())[1]);
        viewHolder.txvTaskName.setText(data.getTitle());
        viewHolder.txvTaskNote.setText(data.getTimeFrom());

        return convertView;
    }

    // class ViewHolder for AreaAdapter
    private static class ViewHolder {
        TextView txvTaskTime;
        TextView txvTaskName;
        TextView txvTaskNote;
    }
}
