package lm.com.testapp.act;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import lm.com.testapp.BaseActivity;
import lm.com.testapp.R;
import lm.com.testapp.utils.ImageUtils;

public class CameraResultActivity extends BaseActivity {

    private ImageView photoShow;
    //缩放控制
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    //不同状态表示
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    //定义第一个按下的点，两只接触点的中点，以及初始的两指按下的距离
    private PointF startPoint = new PointF();
    private PointF midPoint = new PointF();
    private float oriDis = 1f;

    @Override
    public int getLayoutId() {
        return R.layout.activity_camera_result;
    }

    @Override
    public void initView() {
        super.initView();
        photoShow = (ImageView)findViewById(R.id.iv_photo_show);
        Button btShow = (Button) findViewById(R.id.bt_show);
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        String mFilePath = getIntent().getStringExtra("picPath");
        try{
            FileInputStream fis = new FileInputStream(mFilePath);
            Bitmap picBitmap = ImageUtils.getIamge(fis);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            picBitmap = Bitmap.createBitmap(picBitmap,0,0, picBitmap.getWidth(), picBitmap.getHeight(),matrix,true);
            photoShow.setImageBitmap(picBitmap);
            fixPhotoShowError();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initAction() {
        super.initAction();
        photoShow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView)v;
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    //单指
                    case MotionEvent.ACTION_DOWN:
                        matrix.set(view.getImageMatrix());
                        savedMatrix.set(matrix);
                        startPoint.set(event.getX(),event.getY());
                        mode = DRAG;
                        break;
                    //双指
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oriDis = distance(event);
                        if (oriDis > 10f){
                            savedMatrix.set(matrix);
                            midPoint = middle(event);
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        break;
                    //单指滑动事件
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG){
                            //是一个手指拖动
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - startPoint.x,event.getY()-startPoint.y);
                        } else if (mode == ZOOM){
                            //两个手指滑动
                            float newDist = distance(event);
                            if (newDist > 10f){
                                matrix.set(savedMatrix);
                                float scale = newDist / oriDis;
                                matrix.postScale(scale, scale,midPoint.x,midPoint.y);
                            }
                        }
                        break;

                }
                //设置IamgeView的Matrix
                view.setImageMatrix(matrix);
                return true;
            }
        });
    }

    private void fixPhotoShowError(){
        Matrix photomatrix = new Matrix();
        photomatrix.postScale(Float.parseFloat("0.4"),Float.parseFloat("0.4"));
        photoShow.setImageMatrix(photomatrix);
    }

    /**
     * 计算两个触摸点之间的距离
     * @param event
     * @return
     */
    private float distance(MotionEvent event){
        float x = event.getX(0) + event.getX(1);
        float y = event.getX(0) + event.getY(1);
        return Float.valueOf(String.valueOf(Math.sqrt(x * x + y * y)));
    }

    /**
     * 计算两个触摸点的中点
     * @param event
     * @return
     */
    private PointF middle(MotionEvent event){
        float x = event.getX(0) + event.getX(1);
        float y = event.getX(0) + event.getY(1);
        return new PointF(x / 2, y / 2);
    }

}
