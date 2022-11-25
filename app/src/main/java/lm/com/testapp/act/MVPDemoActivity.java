package lm.com.testapp.act;

import android.os.Bundle;
import android.util.Log;
import java.util.List;

import lm.com.testapp.BaseMvp.view.BaseActivity;
import lm.com.testapp.R;
import lm.com.testapp.entity.Goods;
import lm.com.testapp.entity.Person;
import lm.com.testapp.mvp.presenter.PersonPresenter;
import lm.com.testapp.mvp.view.IPersonView;

public class MVPDemoActivity extends BaseActivity<PersonPresenter, IPersonView> implements IPersonView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_demo);
        presenter.fetchPersonData();
        presenter.fetchGoodsData();
    }

    @Override
    protected PersonPresenter createPresenter() {
        return new PersonPresenter();
    }

    @Override
    public void showData(List<Person> personData) {
        Log.d("TagDebug", "showData");
    }

    @Override
    public void showGoodsData(List<Goods> goodsData) {
        Log.d("TagDebug", "showGoodsData");
    }

    @Override
    public void showErrorMessge(String msg) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
