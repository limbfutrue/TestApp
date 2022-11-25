package lm.com.testapp.fingerprint;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 指纹辅助类
 *
 * @Description
 * @Author zqp(zqp@yitong.com.cn) on 2016/8/25 20:39
 */
public class FingerprintUtils {

    public static boolean isSurpportFingerprint(Context context) {
        if(!extitsFingerprintAPI() || !isSdkVersion23()){
            return false;
        }
        if(!hasHardwareDetected(context)){
            return false;
        }
        return true;
    }




    private static boolean extitsFingerprintAPI() {
        try {
            Class fClass = Class.forName("android.hardware.fingerprint.FingerprintManager");
            Log.i("FingerprintUtils", "系统为 >= 6.0系统");
            return true;
        } catch (ClassNotFoundException e) {
//            //e.printStackTrace();
        }
        return false;
    }


    private static boolean isSdkVersion23() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(Build.VERSION.SDK_INT);
            Log.i("FingerprintUtils", "API 版本" + sdkVersion);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }

        return sdkVersion >= 23 ? true : false;
    }

    /**
     * 当前系统锁屏是否有密码
     *
     * @param context
     * @return
     */
    public static boolean hasScreenLockPwd(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.isKeyguardSecure();
    }

    /**
     * 检测硬件是否支持指纹
     *
     * @param context
     * @return
     */
    private static boolean hasHardwareDetected(Context context) {
        boolean hardwareFlg = false;
        try {
            FingerprintManager mFingerprintManager = (FingerprintManager)
                    context.getSystemService(Context.FINGERPRINT_SERVICE);
            hardwareFlg = mFingerprintManager.isHardwareDetected();
        } catch (Exception e) {

        }
        return hardwareFlg;
    }

    /**
     * 检测是否设置了指纹
     * @param context
     * @return
     */
    public static boolean hasEnrolledFingerprints(Context context) {
        boolean encrolledFlg = false;
        try {
            FingerprintManager mFingerprintManager = (FingerprintManager)
                    context.getSystemService(Context.FINGERPRINT_SERVICE);
            encrolledFlg = mFingerprintManager.hasEnrolledFingerprints();
        } catch (Exception e) {

        }
        return encrolledFlg;
    }

    public static void showHasNotSetFingerDialog(Activity activity, final OKCallback callback){
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage("您尚未设置指纹功能，请在手机设置中开启设置指纹")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callback.onOk();
                    }
                }).create();
        alertDialog.show();
    }

    public static void showHasNotSetFingerDialog(Activity activity){
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage("您尚未设置指纹功能，请在手机设置中开启设置指纹")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    public interface OKCallback{
        public void onOk();
    }

    /**
     * 指纹变动--获取最新指纹信息
     * @param context
     * @return
     */
    public static String getFingerInfo(Context context){
        String newFingerContent = "";//最新指纹库信息
        if (isSurpportFingerprint(context) && hasEnrolledFingerprints(context)) {
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            try {
                Method method = FingerprintManager.class.getDeclaredMethod("getEnrolledFingerprints");
                Object obj = method.invoke(fingerprintManager);
                if (obj != null) {
                    Class<?> clazz = Class.forName("android.hardware.fingerprint.Fingerprint");
                    Method getName = clazz.getDeclaredMethod("getName");
                    Method getFingerId = clazz.getDeclaredMethod("getFingerId");
                    Method getGroupId = clazz.getDeclaredMethod("getGroupId");
                    Method getDeviceId = clazz.getDeclaredMethod("getDeviceId");
                    for (int i = 0; i < ((List) obj).size(); i++) {
                        Object item = ((List) obj).get(i);
                        if (item == null) {
                            continue;
                        }
                        String info = String.valueOf(getName.invoke(item)) +
                                String.valueOf(getGroupId.invoke(item)) +
                                String.valueOf(getFingerId.invoke(item)) +
                                String.valueOf(getDeviceId.invoke(item));
                        newFingerContent += info;
                    }
                    newFingerContent = newFingerContent.replace(" ", "");
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return newFingerContent;
    }
}
