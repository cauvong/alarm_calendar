package calendarapp.truongnh.com.calendarapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by nguyentruong on 10/29/17.
 */

public class HomeFragmentPageAdapter extends FragmentPagerAdapter {

    public HomeFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return HomeFragment.newInstance(position, "Page #" + position);
    }

    @Override
    public int getCount() {
        return 101;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
