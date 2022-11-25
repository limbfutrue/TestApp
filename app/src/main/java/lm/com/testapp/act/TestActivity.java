package lm.com.testapp.act;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lm.com.testapp.R;
import lm.com.testapp.utils.AudioStreamManager;

public class TestActivity extends Activity {

    private AudioStreamManager asm;
    private AudioTrack audioTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        asm = new AudioStreamManager();
        findViewById(R.id.tv_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "";
                playVoice(a);
            }
        });
    }


    /**
     * 文本转语音
     * @param textContent 文本内容
     */
    public void playVoice(String textContent){
        //请求接口，获取语音base64的wav数据
        final String path = Environment.getExternalStorageDirectory() + "/voice_play.pcm";
        File voiceFile = new File(path);
        if (voiceFile.exists()){
            voiceFile.delete();
        }
        byte[] decode = Base64.decode(textContent, Base64.NO_WRAP);
        asm.writeAudioDataToFile(decode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                playWavVoice(asm.getFileName());
            }
        }).start();
    }

    /**
     * 播放pcm音频文件
     * @param path
     */
    public void playWavVoice(final String path){
        //关闭上次音频
        if (audioTrack != null && audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING){
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
        }
        //重新开启一次新的音频读写播放
        int bufferSize = AudioTrack.getMinBufferSize(16000, AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT);
        if (audioTrack == null){
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
        }
        FileInputStream fis = null;
        try {
            audioTrack.play();
            fis = new FileInputStream(path);
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while((len = fis.read(buffer)) != -1){
                audioTrack.write(buffer,0,len);
            }
        } catch (Exception e){
            Log.e("playPCMVoice","连续点击读取造成音频流读写或者异常--Unable to retrieve AudioTask pointer for stop()");
        } finally {
            if (audioTrack != null){
                if (audioTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED){
                    audioTrack.stop();
                    audioTrack.release();
                    audioTrack = null;
                }
            }
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e){
                    Log.e("playPCMVoice","音频流关闭异常");
                }
            }
        }
    }
}
