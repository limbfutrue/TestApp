package lm.com.testapp.lazy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import lm.com.testapp.R;

public class LazyActivity extends AppCompatActivity {

    private ViewPager mVp;
    private RadioGroup mRg;
    private List<Fragment> fList = new ArrayList<>();
    private MyViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy);
        mVp = (ViewPager) findViewById(R.id.vp);
        mRg = (RadioGroup) findViewById(R.id.rg);


        fList.add(new Fragment1());
        fList.add(new Fragment2());
        fList.add(new Fragment3());
        fList.add(new Fragment4());
        fList.add(new Fragment5());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();

        adapter = new MyViewPagerAdapter(fm,fList);
        mVp.setOffscreenPageLimit(1);
        mVp.setAdapter(adapter);
        mVp.setCurrentItem(0);

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        mVp.setCurrentItem(0);
                        break;
                    case R.id.rb2:
                        mVp.setCurrentItem(1);
                        break;
                    case R.id.rb3:
                        mVp.setCurrentItem(2);
                        break;
                    case R.id.rb4:
                        mVp.setCurrentItem(3);
                        break;
                    case R.id.rb5:
                        mVp.setCurrentItem(4);
                        break;
                }
            }
        });

        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton)mRg.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
