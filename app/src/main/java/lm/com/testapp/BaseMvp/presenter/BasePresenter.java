package lm.com.testapp.BaseMvp.presenter;

import java.lang.ref.WeakReference;

import lm.com.testapp.BaseMvp.view.IBaseView;

/**
 * Created by PSBC-26 on 2020/12/4.
 */

public class BasePresenter<T extends IBaseView>{
    public WeakReference<T> iBaseView;

    /**
     * 绑定
     * @param view
     */
    public void attachView(T view){
        iBaseView = new WeakReference<T>(view);
    }

    /**
     * 解绑
     */
    public void deatchView(){
        if (iBaseView != null){
            iBaseView.clear();
            iBaseView = null;
        }
    }
}
