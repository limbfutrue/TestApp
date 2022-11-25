package lm.com.testapp.utils;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import lm.com.testapp.act.AlipayLoginActivity;
import lm.com.testapp.act.CustViewActivity;
import lm.com.testapp.act.CustomCameraViewActivity;
import lm.com.testapp.act.FingerDemoActivity;
import lm.com.testapp.act.MVPDemoActivity;
import lm.com.testapp.act.PDFDemoActivity;
import lm.com.testapp.act.SecondFloorActivity;
import lm.com.testapp.act.TextViewFontAutoAdapterActivity;

/**
 * Created by PSBC-26 on 2021/6/9.
 */

public class DataUtils {
    public static List<String> getHomeListData(){
        List<String> listData = new ArrayList<>();
        listData.add("指纹");
        listData.add("PDF文件展示");
        listData.add("自适应字体");
        listData.add("MVP模式");
        listData.add("支付宝登录");
        listData.add("二楼");
        listData.add("自定义VIEW");
        listData.add("相机");
        return listData;
    }

    public static List<Intent> getIntent(Activity context){
        List<Intent> intentList = new ArrayList<>();
        intentList.add(new Intent(context, FingerDemoActivity.class));
        intentList.add(new Intent(context, PDFDemoActivity.class));
        intentList.add(new Intent(context, TextViewFontAutoAdapterActivity.class));
        intentList.add(new Intent(context, MVPDemoActivity.class));
        intentList.add(new Intent(context, AlipayLoginActivity.class));
        intentList.add(new Intent(context, SecondFloorActivity.class));
        intentList.add(new Intent(context, CustViewActivity.class));
        intentList.add(new Intent(context, CustomCameraViewActivity.class));
        return intentList;
    }
}
