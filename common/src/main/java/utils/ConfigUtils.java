package utils;

import android.text.TextUtils;

/**
 * Created by zz on 2020/1/6.
 */

public class ConfigUtils {

    public static final String TOKEN="token";
    public static void saveToken(String token){
        SpUtils.put(TOKEN,token);
    }
    public static String getToken(){
        return (String)SpUtils.get(TOKEN,"");
    }

    public static final String REFRESH_TOKEN="refresh_token";
    public static void saveRefreshToken(String token){
        SpUtils.put(REFRESH_TOKEN,token);
    }
    public static String getRefreshToken(){
        return (String)SpUtils.get(REFRESH_TOKEN,"");
    }

    public static final String USERNAME="username";
    public static void saveUserName(String userName){
        if (!TextUtils.isEmpty(userName))
        SpUtils.put(USERNAME,userName);
    }
    public static String getUserName(){
        return (String)SpUtils.get(USERNAME,"");
    }

    public static final String USERMOBILE="userMobile";
    public static void saveUserMoble(String userName){
        if (!TextUtils.isEmpty(userName))
        SpUtils.put(USERMOBILE,userName);
    }
    public static String getUserMoble(){
        return (String)SpUtils.get(USERMOBILE,"");
    }

    public static final String USERID="userId";
    public static void saveUserId(String id){
        SpUtils.put(USERID,id);
    }
    public static String getUserId(){
        return (String)SpUtils.get(USERID,"");
    }

    public static final String USERFLAG="userFlag";
    public static void saveUserFlag(int userFlag){
        SpUtils.put(USERFLAG,userFlag);
    }
    public static int getUserFlag(){
        return (int)SpUtils.get(USERFLAG,3);
    }

    public static final String GROUPID="groupId";
    public static void saveGroupId(String id){
        if (!TextUtils.isEmpty(id))
        SpUtils.put(GROUPID,id);
    }
    public static String getGroupId(){
        return (String)SpUtils.get(GROUPID,"");
    }

    public static final String CARID="carId";
    public static void saveCarId(String id){
        SpUtils.put(CARID,id);
    }
    public static String getCarId(){
        return (String)SpUtils.get(CARID,"");
    }

    public static final String CARCODE="carCode";
    public static void saveCarCode(String code){
        SpUtils.put(CARCODE,code);
    }
    public static String getCarCode(){
        return (String)SpUtils.get(CARCODE,"");
    }

    public static final String PASSWORK="passwork";
    public static void savePassword(String passwork){
        SpUtils.put(PASSWORK,passwork);
    }
    public static String getPassword(){
        return (String)SpUtils.get(PASSWORK,"");
    }

    //身份证号
    public static final String IDCARD="idcard";
    public static void saveIdCard(String idcard){
        SpUtils.put(IDCARD,idcard);
    }
    public static String getIdCard(){
        return (String)SpUtils.get(IDCARD,"");
    }

    //金额展示隐藏
    public static final String BALANCE="balance";
    public static void saveBalance(boolean isShow){
        SpUtils.put(BALANCE,isShow);
    }
    public static boolean getBalance(){
        return (boolean)SpUtils.get(BALANCE,false);
    }

    //位置上报时间
    public static final String LOCATION_TIME="location_time";
    public static void saveLocTime(int time){
        SpUtils.put(LOCATION_TIME,time);
    }
    public static int getLocTime(){
        return (int)SpUtils.get(LOCATION_TIME,900000);
//        return (int)SpUtils.get(LOCATION_TIME,10000);
    }

    //纬度
    public static final String LOCATION_LAT="location_lat";
    public static void saveLocLat(String lat){
        SpUtils.put(LOCATION_LAT,lat);
    }
    public static String getLocLat(){
        return (String)SpUtils.get(LOCATION_LAT,"0");
    }

    //精度
    public static final String LOCATION_LON="location_lon";
    public static void saveLocLon(String lon){
        SpUtils.put(LOCATION_LON,lon);
    }
    public static String getLocLon(){
        return (String)SpUtils.get(LOCATION_LON,"0");
    }

    //用户状态
    public static final String USERSTATUS="userStatus";
    public static void saveUserStatus(String status){
        SpUtils.put(USERSTATUS,status);
    }
    public static String getUserStatus(){
        return (String) SpUtils.get(USERSTATUS,"0");
    }

    //用户设置过密码
    public static final String USERPAYPWD="userPayPwd";
    public static void saveUserPayPwd(boolean userPayPwd){
        SpUtils.put(USERPAYPWD,userPayPwd);
    }
    public static boolean getUserPayPwd(){
        return (boolean) SpUtils.get(USERPAYPWD,false);
    }

    //是否可以使用相机
    public static final String PHOTO="usePhoto";
    public static void saveUsePhoto(boolean photo){
        SpUtils.put(PHOTO,photo);
    }
    public static boolean getUsePhoto(){
        return (boolean) SpUtils.get(PHOTO,true);
    }

    //是否首次登陆
    public static final String FIRSTLOGIN="firstLogin";
    public static void saveFirstLogin(boolean first){
        SpUtils.put(FIRSTLOGIN,first);
    }
    public static boolean getFirstLogin(){
        return (boolean) SpUtils.get(FIRSTLOGIN,true);
    }

    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";

    public static void saveSearch(String input) {
        SpUtils.put(KEY_SEARCH_HISTORY_KEYWORD, input);
    }

    public static String getSearch() {
        return (String) SpUtils.get(KEY_SEARCH_HISTORY_KEYWORD, "");
    }

    //预约码
    public static final String RESERVECODE = "reserveCode";
    public static void saveReserveCode(String code) {
        if (code != null)
        SpUtils.put(RESERVECODE, code);
    }

    public static String getReserveCode() {
        return (String) SpUtils.get(RESERVECODE, "");
    }

    //仓库id
    public static final String WAREHOUSEID = "warehouseId";
    public static void saveWarehouseId(String id) {
        if (id != null)
            SpUtils.put(WAREHOUSEID, id);
    }

    public static String getWarehouseId() {
        return (String) SpUtils.get(WAREHOUSEID, "");
    }

    //车队名称
    public static final String GROUPABBR = "groupAbbr";
    public static void saveGroupAbbr(String abbr) {
        if (abbr != null)
            SpUtils.put(GROUPABBR, abbr);
    }

    public static String getGroupAbbr() {
        return (String) SpUtils.get(GROUPABBR, "");
    }
}
