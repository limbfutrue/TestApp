package lm.com.testapp.fingerprint;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import lm.com.testapp.R;

/**
 * @Description 指纹登录认证对话框
 */
public class FingerPrintAuthDialog extends ProgressDialog implements FingerprintUiHelper.Callback {

    private TextView tvDialogConfirm;
    private TextView tvDialogConfirmPsd;
    private TextView sc_dialog_msg_txt;
    private LinearLayout dialog_sure_ll;
    private String msg;
    private String sure_txt;
    private OnFingerPrintListener onFingerPrintListener;
    private FingerprintUiHelper mFingerprintUiHelper;
    private FingerprintManager.CryptoObject mCryptoObject;
    private FingerprintManager mFingerprintManager;
    private Cipher mCipher;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private static final String KEY_NAME = "my_key";
    private Context context;
    private Animation anim;
    private boolean isLeft = false;
    private String isVipLogin = "N";//"N"不是vip登录"Y"是vip登录
    private ImageView ivFingerIcon;
    private View v_line;
    private String isOpenActiveFinger = "Y";//是否是开通指纹登录弹出的弹框

    public FingerPrintAuthDialog(Context context) {
        super(context);
        this.context = context;
    }

    public FingerPrintAuthDialog(Context context, String isVipLogin) {
        super(context);
        this.context = context;
        this.isVipLogin = isVipLogin;
    }

    public FingerPrintAuthDialog(Context context, boolean isLeft) {
        super(context);
        this.isLeft = isLeft;
        this.context = context;
    }

    public FingerPrintAuthDialog(Context ctx, int theme) {
        super(ctx, theme);
        this.context = ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finger_dialog_sure);
        anim = AnimationUtils.loadAnimation(context, R.anim.finger_shake);
        sc_dialog_msg_txt = (TextView) findViewById(R.id.sc_dialog_msg_txt_finnger);
        dialog_sure_ll = (LinearLayout) findViewById(R.id.dialog_sure_ll_finnger);
        tvDialogConfirm = (TextView) findViewById(R.id.tvDialogConfirm_finnger);
        tvDialogConfirmPsd = (TextView) findViewById(R.id.tvDialogConfirm_psd);
        v_line = (View) findViewById(R.id.v_line);
        ivFingerIcon = (ImageView) findViewById(R.id.ivFingerIcon);
        ivFingerIcon.setImageResource(R.mipmap.fingerprint_icon);
        sc_dialog_msg_txt.setText("请验证指纹");
        if (sure_txt != null) {
            tvDialogConfirm.setText(sure_txt);
        }
        if (!isLeft) {
            sc_dialog_msg_txt.setGravity(Gravity.CENTER);
        }
        tvDialogConfirm.setOnClickListener(new ClickListener());
        tvDialogConfirmPsd.setOnClickListener(new ClickListener());
        setCanceledOnTouchOutside(false);
        fingerInit();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void fingerInit() {
        if (!createKey()) {
            Log.d("callback", "fingerInit: 指纹识别初始化失败");
            dismiss();
            return;
        }
        if (initCipher()) {
            mCryptoObject = new FingerprintManager.CryptoObject(mCipher);
        }
        mFingerprintManager = context.getSystemService(FingerprintManager.class);
        mFingerprintUiHelper = new FingerprintUiHelper(mFingerprintManager, this);
        if (mFingerprintUiHelper != null) {
            mFingerprintUiHelper.startListening(mCryptoObject);
        }
    }

    /**
     * @Description 注入监听事件
     */
    public interface OnFingerPrintListener {
        public void onStatus(Stage stage);
    }

    public void setOnFingerPrintListener(OnFingerPrintListener onFingerPrintListener) {
        this.onFingerPrintListener = onFingerPrintListener;
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.tvDialogConfirm_finnger:
                    dismiss();
                    break;
                case R.id.tvDialogConfirm_psd:
                    dismiss();
                    break;
            }
        }
    }

    ;

    @TargetApi(Build.VERSION_CODES.M)
    public boolean initCipher() {
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            Log.d("callback", "createKey: KeyPermanentlyInvalidatedException");
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            Log.d("callback", "createKey: KeyStoreException | CertificateException\n" +
                    "                | UnrecoverableKeyException | IOException\n" +
                    "                | NoSuchAlgorithmException | InvalidKeyException");
            throw new RuntimeException("Failed to init Cipher", e);
        } catch (NoSuchPaddingException e) {
            Log.d("callback", "createKey: NoSuchPaddingException");
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean createKey() {
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            mKeyStore.load(null);

            mKeyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            mKeyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT
                            | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7).build());

            mKeyGenerator.generateKey();
            return true;
        } catch (IllegalStateException e) {
            Log.d("callback", "createKey: IllegalStateException");
            return false;
        } catch (KeyStoreException e) {
            Log.d("callback", "createKey: KeyStoreException");
            return false;
        } catch (NoSuchProviderException e) {
            Log.d("callback", "createKey: NoSuchProviderException");
            return false;
        } catch (Exception e) {
            Log.d("callback", "createKey: Exception");
            return false;
        }
    }

    /**
     * @Description msg注册
     */
    public void setTextMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @Description 控制显示 确定按钮 和 取消按钮 默认显示确定 和取消
     */
    public void changeSure(String sure) {
        if (sure != null) {
            this.sure_txt = sure;
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        if (mFingerprintUiHelper != null) {
            mFingerprintUiHelper.stopListening();
        }
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onHelp(int helpCode, CharSequence helpString) {
//		sc_dialog_msg_txt.setText(helpString);
    }

    @Override
    public void onAuthenticated() {
        sc_dialog_msg_txt.clearAnimation();
        sc_dialog_msg_txt.setText("验证通过");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                onFingerPrintListener.onStatus(Stage.SUCCESS);
                dismiss();
            }
        }, 500);
    }

    @Override
    public void onError(int errorCode, String err) {
        if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
            sc_dialog_msg_txt.setText(err);
            if (anim != null) {
                sc_dialog_msg_txt.startAnimation(anim);
            }
        } else {
            dismiss();
        }
    }

    @Override
    public void onFailed() {
        if (isOpenActiveFinger.equals("N")) {
            tvDialogConfirmPsd.setVisibility(View.VISIBLE);
            v_line.setVisibility(View.VISIBLE);
        }
        sc_dialog_msg_txt.setText("再试一次");
        if (anim != null) {
            sc_dialog_msg_txt.startAnimation(anim);
        }
    }

    public String getIsOpenActiveFinger() {
        return isOpenActiveFinger;
    }

    public void setIsOpenActiveFinger(String isOpenActiveFinger) {
        this.isOpenActiveFinger = isOpenActiveFinger;
    }
}
