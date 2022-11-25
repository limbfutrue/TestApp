package lm.com.testapp.mvp.presenter;

import java.util.List;

import lm.com.testapp.BaseMvp.model.IBaseModel;
import lm.com.testapp.BaseMvp.presenter.BasePresenter;
import lm.com.testapp.entity.Goods;
import lm.com.testapp.entity.Person;
import lm.com.testapp.mvp.model.PersonModel;
import lm.com.testapp.mvp.view.IPersonView;

/**
 * Created by PSBC-26 on 2020/12/4.
 */

public class PersonPresenter<T extends IPersonView> extends BasePresenter{
    private IBaseModel iPersonModel = new PersonModel();

    public void fetchPersonData(){
        if (iPersonModel != null &&  iBaseView != null){
            iPersonModel.showPersonView(new IBaseModel.OnLoadListener<Person>() {
                @Override
                public void onComplete(List<Person> personData) {
                    ((T)iBaseView.get()).showData(personData);
                }

                @Override
                public void onError(String msg) {
                    ((T)iBaseView.get()).showErrorMessge(msg);
                }
            });
        }
    }

    public void fetchGoodsData(){
        if (iPersonModel != null &&  iBaseView != null){
            iPersonModel.showGoodsVIew(new IBaseModel.OnLoadListener<Goods>() {
                @Override
                public void onComplete(List<Goods> goodsData) {
                    ((T)iBaseView.get()).showGoodsData(goodsData);
                }

                @Override
                public void onError(String msg) {
                    ((T)iBaseView.get()).showErrorMessge(msg);
                }
            });
        }
    }
}
