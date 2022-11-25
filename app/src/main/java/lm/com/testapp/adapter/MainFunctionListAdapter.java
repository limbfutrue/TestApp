package lm.com.testapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lm.com.testapp.R;

/**
 * Created by PSBC-26 on 2021/1/6.
 */

public class MainFunctionListAdapter extends RecyclerView.Adapter<MainFunctionListAdapter.MyViewHolder> {
    private List<String> listData = new ArrayList<>();
    private Context context;
    private OnItemClickListener itemClickListener;
    public MainFunctionListAdapter(Context context, List<String> listData){
        this.context = context;
        this.listData = listData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_function_list,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTvFucntionName.setText(listData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null){
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0:listData.size();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvFucntionName;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTvFucntionName = itemView.findViewById(R.id.tv_item_function_name);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
