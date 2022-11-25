package lm.com.testapp.lazy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PSBC-26 on 2022/3/7.
 */

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fList = new ArrayList<>();

    public MyViewPagerAdapter(FragmentManager fm,List<Fragment> fList) {
        super(fm);
        this.fList = fList;
    }

    @Override
    public Fragment getItem(int position) {
        return fList.get(position);
    }

    @Override
    public int getCount() {
        return fList.size();
    }
}
