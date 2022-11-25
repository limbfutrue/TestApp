package lm.com.testapp.act;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import lm.com.testapp.BaseActivity;
import lm.com.testapp.R;

public class PDFDemoActivity extends BaseActivity implements TbsReaderView.ReaderCallback {
    private TbsReaderView tbsView;
    private FrameLayout frameLayout;
    private String tempPath = Environment.getExternalStorageDirectory() + "/tbsTempFilePath";
    private String pdfFilePath = Environment.getExternalStorageDirectory() + "/tbsPDFFile2.pdf";
    private PDFView pdfView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pdf_demo;
    }

    @Override
    public void initView() {
        super.initView();
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        tbsView = new TbsReaderView(this, this);
        frameLayout.addView(tbsView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                loadPdf();
            }
        });
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfView.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                showPdf();
            }
        });
    }

    private void loadPdf(){
        File bsTempFile = new File(tempPath);
        if (!bsTempFile.exists()){
            boolean mkdir = bsTempFile.mkdir();
            if (!mkdir){
                Log.d("callback", "onCreate: bsTempFile文件创建失败");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("filePath", pdfFilePath);
        bundle.putString("tempPath", tempPath);
        boolean result = tbsView.preOpen(parseFormat(pdfFilePath), true);
        if (result) {
            tbsView.openFile(bundle);
        } else {
            QbSdk.clearAllWebViewCache(this,true);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tbsView.onStop();
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    private void showPdf(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/pdfView.pdf");
                try{
                    pdfView.fromFile(pdfFile).defaultPage(0).swipeHorizontal(false).load();
                }catch (Exception e){
                }
            }
        });
    }

}
