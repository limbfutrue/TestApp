package lm.com.testapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lm.com.testapp.R;

/**
 * Created by PSBC-26 on 2021/3/9.
 */

public class RecycleViewListAdapter extends RecyclerView.Adapter<RecycleViewListAdapter.MyViewHolder> {
    private Context context;
    private List<String> list;

    public RecycleViewListAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvText.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvText;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTvText = itemView.findViewById(R.id.tv_text);
        }


    }
}
