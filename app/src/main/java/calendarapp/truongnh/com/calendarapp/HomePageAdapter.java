package calendarapp.truongnh.com.calendarapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by nguyentruong on 10/29/17.
 */

public class HomePageAdapter extends PagerAdapter {

    List<Integer> listImages;
    Context mContex;
    LayoutInflater layoutInflater;

    public HomePageAdapter(List<Integer> listImages, Context mContex) {
        this.listImages = listImages;
        this.mContex = mContex;
        layoutInflater = LayoutInflater.from(mContex);
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.home_page, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imvHomePage);
        imageView.setImageResource(listImages.get(position));
        container.addView(view);
        return view;
    }


}
