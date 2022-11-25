package lm.com.testapp.utils;

import android.util.Log;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by PSBC-26 on 2021/12/29.
 */

public class ZipUtil {

    /**
     * 解压zip文件
     * @param zipPath
     */
    public static void unZip(String zipPath){
        try {
            ZipFile zipFile = new ZipFile(zipPath);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
        } catch (IOException e) {
            Log.e("ZipUtil","zipFile--IOException");
        }
    }
}
