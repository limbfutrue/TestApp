package lm.com.testapp.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by PSBC-26 on 2021/3/9.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;
    public CustomLinearLayoutManager(Context context) {
        super(context);
    }
    public void setScrollEnabled(boolean flag){
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
