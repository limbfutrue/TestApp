package lm.com.testapp.BaseMvp.model;

import java.util.List;

/**
 *
 * 数据源，网络，数据库，线程，服务
 * Created by PSBC-26 on 2020/12/4.
 */

public interface IBaseModel {
    void showPersonView(OnLoadListener onLoadListener);
    void showGoodsVIew(OnLoadListener onLoadListener);
    interface OnLoadListener<T>{
        void onComplete(List<T> personData);
        void onError(String msg);
    }
}
