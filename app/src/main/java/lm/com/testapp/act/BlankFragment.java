package lm.com.testapp.act;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lm.com.testapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    private View rootView;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        return rootView;
    }

}
