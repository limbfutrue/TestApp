package lm.com.testapp.mvp.model;

import java.util.ArrayList;
import java.util.List;

import lm.com.testapp.BaseMvp.model.IBaseModel;
import lm.com.testapp.entity.Person;

/**
 * Created by PSBC-26 on 2020/12/4.
 */

public class PersonModel implements IBaseModel {
    OnLoadListener onLoadListener;
    @Override
    public void showPersonView(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
        onLoadListener.onComplete(getData());
    }

    @Override
    public void showGoodsVIew(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
        onLoadListener.onComplete(getData());
    }

    /**
     * 获取数据
     * @return
     */
    private List<Person> getData(){
        if (true){
            return new ArrayList<>();
        } else {
            onLoadListener.onError("error_msg");
        }
        return null;
    }
}
