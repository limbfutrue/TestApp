package lm.com.testapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import lm.com.testapp.fragment.HomeFragment;
import lm.com.testapp.fragment.MyFragment;

public class MainActivity extends BaseActivity {

    private RadioGroup mRgBtn;
    private HomeFragment homeFragment;
    private MyFragment myFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mRgBtn = (RadioGroup) findViewById(R.id.rg_home_bottom_tab);
        homeFragment = new HomeFragment();
        myFragment = new MyFragment();
        changeFragment(homeFragment);
    }

    @Override
    public void initAction() {
        mRgBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        changeFragment(homeFragment);
                        break;
                    case R.id.rb_my:
                        changeFragment(myFragment);
                        break;
                }
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_content, fragment);
        ft.commit();
    }

}
