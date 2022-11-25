package lm.com.testapp.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import lm.com.testapp.R;

/**
 * 轮播图片的适配器
 *
 * @author Charley Chen 2015-1-7 下午4:15:34
 * @company Copyright (c) 2014 Shanghai P&C Information Technology Co., Ltd.
 */
public class DynamicBannersViewFlowAdapter extends YTBaseAdapter {

    private ArrayList<String> strList1;
    private ArrayList<String> strList2;
    private ArrayList<String> strList3;
    private boolean clickable = true;// 是否响应点击事件
    private String lastName = "";

    private long first = 0;
    private long second = 0;

    public DynamicBannersViewFlowAdapter(Activity activity) {
        this.context = activity;
        strList1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strList1.add("测试数据1111" + i);
        }

        strList2 = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            strList2.add("测试数据2222" + i);
        }

        strList3 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            strList3.add("测试数据333" + i);
        }
    }

    public DynamicBannersViewFlowAdapter(Activity activity, boolean clickable) {
        this.context = activity;
        this.clickable = clickable;
    }

    @Override
    public int getCount() {
        // 返回很大的值使得getView中的position不断增大来实现
//        if (items.size() == 1) {
//            return 1;
//        } else {
//            return Integer.MAX_VALUE;
//        }
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position % items.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_layout, null);
            RecyclerView listView = convertView.findViewById(R.id.lv_list);
            listView.setLayoutManager(new LinearLayoutManager(context));
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            if (position == 0) {
                listView.setAdapter(new RecycleViewListAdapter(context, strList1));
            } else if (position == 1){
                listView.setAdapter(new RecycleViewListAdapter(context, strList2));
            } else if (position == 2){
                listView.setAdapter(new RecycleViewListAdapter(context, strList3));
            }
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 防重复点击
                second = System.currentTimeMillis();
                if (second - first < 1000) {
                    first = second;
                    return;
                }
                first = second;

            }
        });

        return convertView;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastMenuName(String lastName) {
        this.lastName = lastName;
    }

    public class ViewHolder {
        public ImageView imageView;
    }
}
