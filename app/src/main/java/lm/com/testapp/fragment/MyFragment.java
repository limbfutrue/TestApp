package lm.com.testapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import lm.com.testapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    private View rootView = null;
    private TextView tv;
    private Button bt;

    public MyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my, container, false);
        tv = rootView.findViewById(R.id.tv);
        bt = rootView.findViewById(R.id.bt);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
