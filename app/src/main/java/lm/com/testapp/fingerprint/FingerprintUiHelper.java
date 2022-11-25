package lm.com.testapp.fingerprint;

import android.annotation.TargetApi;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Log;

/**
 * 指纹识别回调
 *
 * @Description
 * @Author zqp(zqp@yitong.com.cn) on 2016/8/26 13:53
 */
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintUiHelper extends FingerprintManager.AuthenticationCallback {

    private final FingerprintManager mFingerprintManager;
    private CancellationSignal mCancellationSignal;
    private final Callback mCallback;
    boolean mSelfCancelled;

    public FingerprintUiHelper(FingerprintManager fingerprintManager, Callback callback) {
        mFingerprintManager = fingerprintManager;
        mCallback = callback;
    }


    /**
     * 开始监听
     *
     * @param cryptoObject
     */
    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        if (!isFingerprintAuthAvailable()) {
            return;
        }
        mCancellationSignal = new CancellationSignal();
        mSelfCancelled = false;
        mFingerprintManager.authenticate(cryptoObject, mCancellationSignal, 0 /* flags */, this, null);
    }

    public void stopListening() {
        if (mCancellationSignal != null) {
            mSelfCancelled = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }


    /**
     * 检测硬件是否支持和是否有指纹
     *
     * @return
     */
    public boolean isFingerprintAuthAvailable() {
        return mFingerprintManager.isHardwareDetected()
                && mFingerprintManager.hasEnrolledFingerprints();
    }


    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Log.i("onAuthenticationError", "认证错误。。。。。" + errString.toString());
        if (!mSelfCancelled) {
            mCallback.onError(errorCode, errString.toString());
        }

    }


    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Log.i("onAuthenticationHelp", helpString.toString());
        mCallback.onHelp(helpCode, helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        Log.i("onAuthenticationFailed", "认证失败。。。。。");
        mCallback.onFailed();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Log.i("AuthenticationSucceeded", "认证成功。。。。。");
        mCallback.onAuthenticated();
    }


    public interface Callback {

        void onHelp(int helpCode, CharSequence helpString);

        void onAuthenticated();

        void onError(int errorCode, String err);

        void onFailed();
    }

}
