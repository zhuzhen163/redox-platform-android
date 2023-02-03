package com.redoxyt.platform.uitl;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by zz.
 * description:
 */

public class TXTManager {

    /**
     * 保存动作数据
     * data 保存的内容
     * time 时间作为txt文件名
     */
    public static void saveAction(String data, String time) {
        //新建文件夹
        String folderName = "A-locationTXT";
        File sdCardDir = new File(Environment.getExternalStorageDirectory(), folderName);
        if (!sdCardDir.exists()) {
            if (!sdCardDir.mkdirs()) {
                try {
                    sdCardDir.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        String fileName = time + ".txt";

        //新建文件
        File saveFile = new File(sdCardDir, fileName);
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            writeData(data, false, saveFile);
        } catch (Exception e) {
        }

    }

    /**
     * @param content        写入内容
     * @param isClearContent 是否清楚原来内容 true 覆盖数据 false 累加内容
     */
//每次都重新写入
    public static void writeData(String content, Boolean isClearContent, File saveFile) {
        try {
            if (isClearContent) {
                final FileOutputStream outStream = new FileOutputStream(saveFile);
                try {
                    //内容写入文件
                    outStream.write(content.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //内容追加

                BufferedWriter out = null;
                try {
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile, true)));
                    out.write(content + "\r\n");
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
