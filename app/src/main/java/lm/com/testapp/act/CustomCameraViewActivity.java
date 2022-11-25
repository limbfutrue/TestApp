package lm.com.testapp.act;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lm.com.testapp.R;

public class CustomCameraViewActivity extends Activity implements SurfaceHolder.Callback,View.OnClickListener{

    private static final int REQUEST_CODE_READ = 0x0001;
    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private int cameraPositon = 0;//1调用前置摄像头，0后置摄像头
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String mFilePath = Environment.getExternalStorageDirectory().getPath();
            //保存图片的文件名
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date(System.currentTimeMillis());
            mFilePath = mFilePath + "/" + simpleDateFormat.format(date) + "pictrue.jpg";
            File tempFile = new File(mFilePath);
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(data);
                fos.close();
                Intent intent = new Intent(CustomCameraViewActivity.this,CameraResultActivity.class);
                intent.putExtra("picPath",mFilePath);
                startActivity(intent);
            } catch (IOException e){
            }
        }
    };
    private Button mBtCancel;
    private Button mBtTakenPhoto;
    private Button mBtSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera_view);
        initView();
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera,mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //停止预览界面
        mCamera.stopPreview();
        //重新设置预览界面
        setStartPreview(mCamera,mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    private void initView(){
        mBtCancel = (Button)findViewById(R.id.bt_cancel);
        mBtTakenPhoto = (Button)findViewById(R.id.bt_taken_photo);
        mBtSwitch = (Button)findViewById(R.id.bt_switch);
        mPreview = (SurfaceView)findViewById(R.id.preview);
        mPreview.setOnClickListener(this);
        mBtSwitch.setOnClickListener(this);
        mBtCancel.setOnClickListener(this);
        mBtTakenPhoto.setOnClickListener(this);
    }

    /**
     * 拍照
     */
    public void takePhoto(){
        Camera.Parameters parameters = mCamera.getParameters();
        //获取支持预览的尺寸
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size previewSize = supportedPreviewSizes.get(0);
        //设置照片大小
        parameters.setPreviewSize(previewSize.width,previewSize.height);
        mCamera.setParameters(parameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback(){
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success){
                    mCamera.takePicture(null,null,mPictureCallback);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        releaseCamera();
        if (mCamera == null){
            mCamera = getCamera(cameraPositon);
            if (mHolder != null){
                //开启预览界面
                setStartPreview(mCamera,mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * 打开相机
     * @param cameraId
     * @return
     */
    private Camera getCamera(int cameraId){
        Camera camera;
        try {
            camera = Camera.open(cameraId);
        } catch (Exception e){
            camera = null;
        }

        return camera;
    }

    /**
     * 设置开始预览
     * @param camera
     * @param holder
     */
    private void setStartPreview(Camera camera,SurfaceHolder holder){
        try{
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (Exception e){}
    }

    /**
     * 释放资源
     */
    private void releaseCamera(){
        if (mCamera != null){
            //停止预览
            mCamera.stopPreview();
            //预览返回值为null
            mCamera.setPreviewCallback(null);
            //释放摄像头
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.preview:
                mCamera.autoFocus(null);
                break;
            case R.id.bt_cancel:
                finish();
                break;
            case R.id.bt_taken_photo:
                takePhoto();
                break;
            case R.id.bt_switch:
                changeCamera();
                break;
        }
    }

    /**
     * 切换摄像头
     */
    public void changeCamera(){
        //切换前后摄像头
        int cameraCount;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        if (cameraCount > 1){
            //得到每一个摄像头的信息
            Camera.getCameraInfo(cameraPositon,cameraInfo);
            if (cameraPositon == 1){
                releasCameraPamara(0);
                cameraPositon = 0;
            } else {
                if (cameraPositon == 0){
                    releasCameraPamara(1);
                    cameraPositon = 1;
                }
            }
        }
    }

    /**
     * 重置摄像头参数属性
     * @param i
     */
    private void releasCameraPamara(int i){
        //停掉原来摄像头预览
        mCamera.stopPreview();
        //释放资源
        mCamera.release();
        //取消原来摄像头
        mCamera = null;
        //打开当前选中的摄像头
        mCamera = Camera.open(i);
        try{
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e){
            e.printStackTrace();
        }
        mCamera.setDisplayOrientation(90);
        //开始预览
        mCamera.startPreview();
    }



    private boolean isOpenCameraPermission(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_CODE_READ);
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean isReadStoragePermissionGranted(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_READ);
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean isWriteStoragePermissionGranted(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_READ);
                return false;
            }
        } else {
            return true;
        }
    }

}
