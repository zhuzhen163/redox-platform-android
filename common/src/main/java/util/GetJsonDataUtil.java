package util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class GetJsonDataUtil {


    public String getJson(Context context,String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(fileName));
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }

            inputStreamReader.close();
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

