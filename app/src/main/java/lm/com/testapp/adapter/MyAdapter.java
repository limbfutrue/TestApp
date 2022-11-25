package lm.com.testapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import lm.com.testapp.act.BlankFragment;

/**
 * Created by PSBC-26 on 2021/3/9.
 */

public class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new BlankFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "tab1";
            case 1:
                return "tab2";
            case 2:
                return "tab3";
            case 3:
                return "tab4";
            default:
                return null;
        }
    }
}
