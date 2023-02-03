package util.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/5/27/027.
 * 筛选条公共方法
 */

public class GetCommonScreenData {
    public static List<HashMap<String, Object>> getCommonScreenData(String[] mScreenName, int[] mScreenIcon) {
        List<HashMap<String, Object>> mCommonScreenHash = new ArrayList<>();
        for (int i = 0; i < mScreenName.length; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("TYPE_NAME", mScreenName[i]);
            hashMap.put("TYPE_ICON", mScreenIcon[i]);
            hashMap.put("isClick", "false");
            mCommonScreenHash.add(hashMap);
        }
        return mCommonScreenHash;
    }
}
