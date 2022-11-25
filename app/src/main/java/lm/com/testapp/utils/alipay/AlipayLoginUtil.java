package lm.com.testapp.utils.alipay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.OpenAuthTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PSBC-26 on 2021/4/13.
 */

public class AlipayLoginUtil {
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016063001569958";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088801865263025";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = System.currentTimeMillis()+"";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDpOtcepqf5M6cwLsF0ZlJURuKZzoaLgQ5Ujf7Cb9jR003zfyafD8v23wBo8ZM5b5T7ZCBXG2uh1i1tRz0b1bLF6uiVUnw7C7XEJnDPb7LKV/7qgqUGuUNle9nfenx6pMZi3EvAa+GQm8iQMT5qX+JHZ2u+uICtiBOnlIrDgU0Qw/gyjpvNPJ4qqoBYwtagde6V99jj9ThIok9yKheNqZ+QRlmyHDEHWbUuCHmBK+bjav7W60rXURHZIGQuNNKSgSO3XEpTDJ1Dr+DsOQYZmcslw72h3Xp5ixy5SeZkgmkHYY3CKEPJGer/rzdnYA6L6JCWgU/aL2rP0BOG72dF+s9FAgMBAAECggEBAKRAJgOWBrG/qLyMEP2ymcR0b102Txar+rWdko7ISf/rOfiD0WUvsMrZXeaKSs6mQFuSthviWbiNp0xQYT7bDp3Hw6h1gLvskUPkdon/9f/+XK4295V2n3o28Mcr8cJFjNlOn6KR1zcKHwM9SuGLSUZUFVMpiKWtMXpRaDArPWTR5gyZZkgfTWZI0CjkiOXv71BnbSIe4Jw1l3OUpy6/d8xik0psTcRjZaXK7jh5mm21NEkgmaRXNrSwPKm1AdGlK39PyazvpjQ76U5a5jQyWSSMrYNdYH4BiBg/5Hlo0zZqhQxyFdDHGGoTZNmfucUCvkeraoPpYnW0W4rqKuokzzkCgYEA+vs66LR/GFZXH77TBQRnVu1Pdp3V1EuIZRpF5bFQojJM5rJB6Y46Hp63DuLWHUgMWtHmJq7djd00AIWod1AKpBhjW7s/kBW7SliGS/Sk+41lohDWQN3vK65I+8y97NP815K7aRI7e6nSi8Ml1NpO5ZcKRuM+LEGx9qqB6ZeHpTMCgYEA7eS9jASCKkow12mCvVQUPjkIa8456R0BIoY9FMQuzINVpbwQUx4Ezwd6N7FFuCwM7XG9ldCQvZeUTQ/IyMe1x4lBdFoXvH+lVEJ+fFZoOq9U7A2+xoeHt8qscrB0OMH6HoBShHWL578jcMkyvaIiJEGmNrpq+unMl5+dqLMTyacCgYAYf39PiZbCTGMysRCt87EWlhFklaFymHDW1YqvEfC59Ej3V70BTCW2KW3a4R9fOlIiZ0ycyALOPyI9Ts6T3tc7DzpbkNpyePyQFDX/ogCcoT+jYxtWt5MJ43gyQ9jiDgsnC6aJagMuXfQ0OjcuIKUE2gphwvqlnT/+wMPvhh/yawKBgF5Ih+8UYlpMaSsWwCA1UIiQpgvEAiOnwSljtpWiY0He03UKIhYvEYzT09Z6xb0GiGEa8R6NOLBLgRVBqjwZGqxoc66ffkTQ+j940xSIUmrfznJ3yCJ62Ik5JDuYP5e3GWA5T3r782T31h3/P8L0nT9tXWjXXUrwmouFUUDpKHCFAoGBAI540L3M14yOeSPoKR0lePSLJLeXPnxERhcTrLpP0pkHrwu90V5Cw7KbgciRy2JXgVvhg7nmsagQn/YBfEZhLIgSu+n38Nq+weFm7eWaJUcJHpS1Chl+PzT6/uyGxXw8gQ88b6QYtEaCYg9xkizY3C97nAu7lBBln6my//5PMoQB";
    public static final String RSA_PRIVATE = "";
    public static final String RSA_PUBLICK_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6TrXHqan+TOnMC7BdGZSVEbimc6Gi4EOVI3+wm/Y0dNN838mnw/L9t8AaPGTOW+U+2QgVxtrodYtbUc9G9WyxerolVJ8Owu1xCZwz2+yylf+6oKlBrlDZXvZ33p8eqTGYtxLwGvhkJvIkDE+al/iR2drvriArYgTp5SKw4FNEMP4Mo6bzTyeKqqAWMLWoHXulffY4/U4SKJPcioXjamfkEZZshwxB1m1Lgh5gSvm42r+1utK11ER2SBkLjTSkoEjt1xKUwydQ6/g7DkGGZnLJcO9od16eYscuUnmZIJpB2GNwihDyRnq/683Z2AOi+iQloFP2i9qz9AThu9nRfrPRQIDAQAB";

    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_AUTH_FLAG = 2;

    /**
     * 支付宝账户授权业务示例
     */
    public static void authV2(final Activity context, final Handler mHandler) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            return;
        }

		/*
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
		 *
		 * authInfo 的获取必须来自服务端；
		 */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(context);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);
                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * 通用跳转授权业务的回调方法。
     * 此方法在支付宝 SDK 中为弱引用，故你的 App 需要以成员变量等方式保持对 Callback 的强引用，
     * 以免对象被回收。
     * 以局部变量保持对 Callback 的引用是不可行的。
     */
    static OpenAuthTask.Callback openAuthCallback = new OpenAuthTask.Callback() {
        @Override
        public void onResult(int i, String s, Bundle bundle) {

        }
    };


    /**
     * 通用跳转授权业务 Demo
     */
    public static void openAuthScheme(Context context) {

        // 传递给支付宝应用的业务参数
        final Map<String, String> bizParams = new HashMap<>();
        bizParams.put("url", "https://authweb.alipay.com/auth?auth_type=PURE_OAUTH_SDK&app_id="+PID+"&scope=auth_user&state=init");

        // 支付宝回跳到你的应用时使用的 Intent Scheme。请设置为不和其它应用冲突的值。
        // 如果不设置，将无法使用 H5 中间页的方法(OpenAuthTask.execute() 的最后一个参数)回跳至
        // 你的应用。
        //
        // 参见 AndroidManifest 中 <AlipayResultActivity> 的 android:scheme，此两处
        // 必须设置为相同的值。
        final String scheme = "__alipaysdkdemo__";

        // 唤起授权业务
        final OpenAuthTask task = new OpenAuthTask((Activity) context);
        task.execute(
                scheme,	// Intent Scheme
                OpenAuthTask.BizType.AccountAuth, // 业务类型
                bizParams, // 业务参数
                openAuthCallback, // 业务结果回调。注意：此回调必须被你的应用保持强引用
                false); // 是否需要在用户未安装支付宝 App 时，使用 H5 中间页中转
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("确定", null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    private static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    private static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder();
        for (String key: bundle.keySet()) {
            sb.append(key).append("=>").append(bundle.get(key)).append("\n");
        }
        return sb.toString();
    }
}
