package com.fe.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fe.home.annotaiton.InitClickListener;
import com.fe.home.annotaiton.InitResId;
import com.fe.home.annotaiton.ResIdAnnoUtils;

public class Home_MainActivity extends AppCompatActivity {

    private static final String TAG = "Home_MainActivity";
    @InitResId(R.id.tv1)
    private TextView tv1;
    @InitResId(R.id.tv2)
    private TextView tv2;
    @InitResId(R.id.tv3)
    private TextView tv3;
    @InitResId(R.id.tv4)
    private TextView tv4;
    @InitResId(R.id.tv5)
    private TextView tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResIdAnnoUtils.init(this);
        setText(tv1,"tv1");
        setText(tv2,"tv2");
        setText(tv3,"tv3");
        setText(tv4,"tv4");
        setText(tv5,"tv5");
    }

    public void setText(TextView tv, String text){
        try{
            tv.setText(text);
        }catch (Exception e){
            Log.d(TAG, "setText: tv == null");
        }
    }

    @InitClickListener({R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5,})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv2:
                Toast.makeText(this, "tv2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv3:
                Toast.makeText(this, "tv3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv4:
                Toast.makeText(this, "tv4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv5:
                Toast.makeText(this, "tv5", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
