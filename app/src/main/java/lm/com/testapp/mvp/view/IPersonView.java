package lm.com.testapp.mvp.view;

import java.util.List;

import lm.com.testapp.BaseMvp.view.IBaseView;
import lm.com.testapp.entity.Goods;
import lm.com.testapp.entity.Person;

/**
 * Created by PSBC-26 on 2020/12/4.
 */

public interface IPersonView extends IBaseView{
    void showData(List<Person> personData);
    void showGoodsData(List<Goods> goodsData);
}
