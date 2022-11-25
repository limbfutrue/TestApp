package lm.com.testapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lm.com.testapp.R;
import lm.com.testapp.adapter.MainFunctionListAdapter;
import lm.com.testapp.utils.DataUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View rootView = null;
    private RecyclerView mRcFounctionList;
    private MainFunctionListAdapter functionListAdapter;
    private List<Intent> intentList;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initData();
        initListener();
        return rootView;
    }
    private void initView() {
        mRcFounctionList = (RecyclerView) rootView.findViewById(R.id.rc_founction_list);
    }

    private void initData() {
        intentList = DataUtils.getIntent(getActivity());
        GridLayoutManager mainManager = new GridLayoutManager(getActivity(),4);
        mRcFounctionList.setLayoutManager(mainManager);
        functionListAdapter = new MainFunctionListAdapter(getActivity(), DataUtils.getHomeListData());
        mRcFounctionList.setAdapter(functionListAdapter);
    }

    private void initListener() {
        functionListAdapter.setItemClickListener(new MainFunctionListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(intentList.get(position));
            }
        });
    }
}
