package calendarapp.truongnh.com.calendarapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import calendarapp.truongnh.com.calendarapp.TNHCalendarFragment;

/**
 * Created by 217REC10 on 10/30/17.
 */

public class TNHCalendarAdapter extends FragmentPagerAdapter {

    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;

    public TNHCalendarAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
    }

    @Override
    public Fragment getItem(int position) {
        TNHCalendarFragment tnhCalendarFragment = new TNHCalendarFragment();
        switch (position) {
            case 0:
                tnhCalendarFragment.setPosition(-6);
                break;
            case 1:
                tnhCalendarFragment.setPosition(-5);
                break;
            case 2:
                tnhCalendarFragment.setPosition(-4);
                break;
            case 3:
                tnhCalendarFragment.setPosition(-3);
                break;
            case 4:
                tnhCalendarFragment.setPosition(-2);
                break;
            case 5:
                tnhCalendarFragment.setPosition(-1);
                break;
            case 6:
                tnhCalendarFragment.setPosition(0);
                break;
            case 7:
                tnhCalendarFragment.setPosition(1);
                break;
            case 8:
                tnhCalendarFragment.setPosition(2);
                break;
            case 9:
                tnhCalendarFragment.setPosition(3);
                break;
            case 10:
                tnhCalendarFragment.setPosition(4);
                break;
            case 11:
                tnhCalendarFragment.setPosition(5);
                break;
            default:
                tnhCalendarFragment.setPosition(6);
                break;
        }

        return tnhCalendarFragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            String tag = fragment.getTag();
            mFragmentTags.put(position, tag);
        }
        return object;
    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        String tag = mFragmentTags.get(position);
        if (tag != null) {
            fragment = mFragmentManager.findFragmentByTag(tag);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 13;
    }
}
