package lm.com.testapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by PSBC-26 on 2021/7/6.
 */

public class ImageUtils {

    public static Bitmap getIamge(InputStream is){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 1;
        Bitmap bitmap = BitmapFactory.decodeStream(is,null,options);
        return bitmap;
    }

    /**
     * 生成缩略图
     * @param bitmap
     * @return
     */
    public static Bitmap createBitmapThumbnail(Bitmap bitmap,float newWidth, float newHeight){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //计算缩放比例
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;

        //取的想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);

        //得到新图
        Bitmap newBitmap = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        return newBitmap;

    }

    /**
     * 质量压缩
     * @param bitmap
     * @param sizeK
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap,int sizeK){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩  100为压缩比例  100代表不压缩  存入baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        //压缩比例
        int options = 90;
        //循环压缩  sizeK 压缩的目标大小
        while (baos.toByteArray().length / 1024 > sizeK){
            options -= 10;
            baos.reset();
            if (options < 0){
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,baos);
        }
        ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap1 = BitmapFactory.decodeStream(is);
        return bitmap1;
    }
}
