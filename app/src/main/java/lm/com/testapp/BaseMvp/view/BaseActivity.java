package lm.com.testapp.BaseMvp.view;

import android.app.Activity;
import android.os.Bundle;

import lm.com.testapp.BaseMvp.presenter.BasePresenter;

/**
 * Created by PSBC-26 on 2020/12/4.
 */

public abstract class BaseActivity<T extends BasePresenter,V extends IBaseView> extends Activity {

    protected T presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView((V)this);
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deatchView();
    }
}
