package lm.com.testapp.act;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;

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

import lm.com.testapp.BaseActivity;
import lm.com.testapp.R;
import lm.com.testapp.fingerprint.FingerPrintAuthDialog;
import lm.com.testapp.fingerprint.FingerprintUtils;
import lm.com.testapp.fingerprint.Stage;

/**
 * 指纹demo
 */
public class FingerDemoActivity extends BaseActivity {
    private FingerPrintAuthDialog fingerPrintAuthDialog;
    private Cipher mCipher;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private static final String KEY_NAME = "my_key";

    @Override
    public int getLayoutId() {
        return R.layout.activity_finger_demo;
    }

    @Override
    public void initView() {
        super.initView();
        //初始化指纹信息
        fingerInit();
        //点击验证是否指纹发生变化
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试中，必需点击执行这个方法才会检测出指纹变化，直接执行不能检测出，目前未找到原因
                buttonClick();
            }
        });
    }

    public void buttonClick(){
        if (initCipher()) {
            if (FingerprintUtils.isSurpportFingerprint(this)) {
                if (FingerprintUtils.hasEnrolledFingerprints(this)) {
                    fingerPrintAuthDialog = new FingerPrintAuthDialog(this);
                    fingerPrintAuthDialog.show();
                    fingerPrintAuthDialog.setOnFingerPrintListener(new FingerPrintAuthDialog.OnFingerPrintListener() {
                        @Override
                        public void onStatus(Stage stage) {
                            if (stage == Stage.SUCCESS) {
                                //TODO 指纹验证成功处理逻辑
                            }
                        }
                    });
                }
            }
        } else {
            //TODO 指纹变动处理逻辑
        }
    }

    private void fingerInit() {
        if (!createKey()) {
            Log.d("callback", "fingerInit: 指纹识别初始化失败");
            return;
        } else {
            initCipher();
        }

    }

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
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        } catch (NoSuchPaddingException e) {
        }
        return false;
    }

    @SuppressLint("NewApi")
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
            return false;
        } catch (KeyStoreException e) {
            return false;
        } catch (NoSuchProviderException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
