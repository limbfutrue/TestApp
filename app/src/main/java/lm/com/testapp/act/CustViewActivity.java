package lm.com.testapp.act;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import lm.com.testapp.BaseActivity;
import lm.com.testapp.R;
import lm.com.testapp.utils.Client;
import test.ALbm;
import test.ALbmUtils;

public class CustViewActivity extends BaseActivity implements Client.IProgressListener {

    private static final int REQUEST_CODE = 0x0001;
    private Button bt_send;
    private Client client;
    private String path;
    private Button bt_select_file;
    private TextView tv_show_file_path;
    private String host = "20.2.198.126";
    private int port = 8888;
    private EditText et_name;
    private LinearLayout ll_ip_layout;
    private Button bt_update_ip;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            path = "";
            tv_show_file_path.setText(path);
        }
    };
    private ProgressBar p_progress;
    private TextView tv_progress_text;

    @ALbm("name1")
    private String testName;
    @ALbm("name2")
    private String name2;
    @Override
    public int getLayoutId() {
        return R.layout.activity_cust_view;
    }

    @Override
    public void initView() {
        super.initView();
        ALbmUtils.init(this);
//        OnClickUtils.initOnClick(this);
        Log.d("callback", "initView: " + testName + name2);
        bt_send = (Button) findViewById(R.id.bt_send);
        bt_select_file = (Button) findViewById(R.id.bt_select_file);
        tv_show_file_path = (TextView) findViewById(R.id.tv_show_file_path);
        tv_progress_text = (TextView) findViewById(R.id.tv_progress_text);
        et_name = (EditText) findViewById(R.id.et_ip);
        ll_ip_layout = (LinearLayout) findViewById(R.id.ll_ip_layout);
        bt_update_ip = (Button) findViewById(R.id.bt_update_ip);
        p_progress = (ProgressBar) findViewById(R.id.p_progress);
        client = new Client();

        //上传文件
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(path)){
                    Toast.makeText(CustViewActivity.this,"请先选择文件",Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int isSuccess = client.sendFile(host,port,path,CustViewActivity.this);
                        Looper.prepare();
                        if (isSuccess == Client.RESULT_SUCCESS_CODE){
                            Toast.makeText(CustViewActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(0);
                        } else if (isSuccess == Client.RESULT_FAIL_CODE){
                            Toast.makeText(CustViewActivity.this,"服务器未开启，请联系李保明",Toast.LENGTH_SHORT).show();
                        } else if (isSuccess == Client.ERROR_CODE){
                            Toast.makeText(CustViewActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();

                    }
                }).start();
            }
        });

        //选择上传文件
        bt_select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        //长按显示修改域名功能
        bt_send.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ll_ip_layout.setVisibility(View.VISIBLE);
                return false;
            }
        });


        //修改ip
        bt_update_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_name.getText().toString())){
                    Toast.makeText(CustViewActivity.this, "请输入域名IP", Toast.LENGTH_SHORT).show();
                    return;
                }
                host = et_name.getText().toString();
                Toast.makeText(CustViewActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                ll_ip_layout.setVisibility(View.GONE);
            }
        });
    }

//    @OnClick({R.id.bt_update_ip,R.id.bt_send})
//    private void onClickEvent(View view){
//        switch (view.getId()){
//            case R.id.bt_update_ip:
//                if (TextUtils.isEmpty(et_name.getText().toString())){
//                    Toast.makeText(CustViewActivity.this, "请输入域名IP", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                host = et_name.getText().toString();
//                Toast.makeText(CustViewActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                ll_ip_layout.setVisibility(View.GONE);
//                break;
//            case R.id.bt_send:
//                if(TextUtils.isEmpty(path)){
//                    Toast.makeText(CustViewActivity.this,"请先选择文件",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        int isSuccess = client.sendFile(host,port,path,CustViewActivity.this);
//                        Looper.prepare();
//                        if (isSuccess == Client.RESULT_SUCCESS_CODE){
//                            Toast.makeText(CustViewActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
//                            handler.sendEmptyMessage(0);
//                        } else if (isSuccess == Client.RESULT_FAIL_CODE){
//                            Toast.makeText(CustViewActivity.this,"服务器未开启，请联系李保明",Toast.LENGTH_SHORT).show();
//                        } else if (isSuccess == Client.ERROR_CODE){
//                            Toast.makeText(CustViewActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
//                        }
//                        Looper.loop();
//
//                    }
//                }).start();
//                break;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        upLoadFile(data);
    }

    /**
     * 选择文件
     * @param data
     */
    private void upLoadFile(Intent data) {
        try {
            Uri uri = data.getData();
            ContentResolver resolver = getContentResolver();
            Cursor cursor = resolver.query(uri,null,null,null,null);
            if (cursor == null){
                //普通文本
                path = uri.getPath();
                tv_show_file_path.setText(path);
                return;
            }
            if (cursor.moveToFirst()){
                //多媒体文件
                path = cursor.getString(cursor.getColumnIndex("_data"));
                tv_show_file_path.setText(path);
            }
            cursor.close();
        } catch (Exception e){
            Toast.makeText(this, "请不要从最近中选择文件，请从文件管理目录下选择", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onProgress(final double p) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                p_progress.setProgress((int) p);
                tv_progress_text.setText("上传进度  " + p + "%");
            }
        });
    }
}
