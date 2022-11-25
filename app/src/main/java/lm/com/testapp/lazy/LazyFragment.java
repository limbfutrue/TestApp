package lm.com.testapp.lazy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class LazyFragment extends Fragment {


    private static final String TAG = "LazyFragment";
    private View rootView;
    private boolean isCreateView;
    private boolean isFirstVisible = true;
    private int index = 0;
    private boolean currentVisibleState;

    public LazyFragment() {
    }

    public LazyFragment(int postion) {
        index = postion;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(getResLayoutId(), container, false);
        initView(rootView);
        isCreateView = true;
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isCreateView) {
            if (!currentVisibleState && isVisibleToUser) {//从不可见到可见
                dispatchUserVisibleHint(true);
            } else if (currentVisibleState && !isVisibleToUser) {//从可见到不可见
                dispatchUserVisibleHint(false);
            }
        }

    }

    private void dispatchUserVisibleHint(boolean visible) {
        if (visible && isParentInvisible()) {
            return;
        }

        if (currentVisibleState == visible) {
            return;
        }

        currentVisibleState = visible;

        if (visible) {
            if (isFirstVisible) {
                isFirstVisible = false;
                onFragmentFirstVisible();
            }
            onFragmentResume();
            dispatchChildVisibleState(true);
        } else {
            onFragmentPause();
            dispatchChildVisibleState(false);
        }
    }

    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof LazyFragment) {
            LazyFragment lazyFragment = (LazyFragment) parentFragment;
            return !lazyFragment.isSupportVisible();
        }
        return false;
    }

    private boolean isSupportVisible() {
        return currentVisibleState;
    }

    protected void onFragmentFirstVisible() {
    }

    protected abstract int getResLayoutId();

    protected abstract void initView(View rootView);

    /**
     * 处理fragment嵌套fragment的问题
     * @param visible
     */
    private void dispatchChildVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof LazyFragment && !fragment.isHidden() && fragment.getUserVisibleHint()){
                    ((LazyFragment) fragment).dispatchUserVisibleHint(visible);
                }
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstVisible){
            if (!isHidden() && !currentVisibleState && getUserVisibleHint()){
                dispatchUserVisibleHint(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (currentVisibleState && getUserVisibleHint()){
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isCreateView = false;
        isFirstVisible = false;
    }

    public void onFragmentResume() {
    }

    public void onFragmentPause() {
    }
}
