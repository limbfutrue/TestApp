package lm.com.testapp.lazy;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import lm.com.testapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends LazyFragment {


    public Fragment3() {
    }


    @Override
    protected int getResLayoutId() {
        return R.layout.fragment_fragment1;
    }

    @Override
    protected void initView(View rootView) {
        Log.d("11111111","f3  onCreate");
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Log.d("callback","fragment3数据加载");
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        Log.d("callback","fragment3停止加载");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("11111111","f3  onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("11111111","f3  onDestroyView");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("11111111","f3  onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("11111111","f3  onDestroy");
    }
}
