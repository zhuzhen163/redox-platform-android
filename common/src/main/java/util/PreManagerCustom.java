package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 登录保存机构信息
 */
public class PreManagerCustom {
    private static PreManagerCustom preManager;
    private SharedPreferences mShare;

    private PreManagerCustom(Context context) {
        mShare = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized PreManagerCustom instance(Context context) {
        if (preManager == null)
            preManager = new PreManagerCustom(context);
        return preManager;
    }

    public String getUserId() {
        return mShare.getString("userId", "");
    }

    public void setUserId(String userId) {
        mShare.edit().putString("userId", userId).commit();
    }

    //天然气
    public int getFEEDER_PART_ID() {
        return mShare.getInt("FEEDER_PART_ID", -1);
    }

    public void setFEEDER_PART_ID(int FEEDER_PART_ID) {
        mShare.edit().putInt("FEEDER_PART_ID", FEEDER_PART_ID).commit();
    }

    //油
    public int getGASMAN_PART_ID() {
        return mShare.getInt("GASMAN_PART_ID", -1);
    }

    public void setGASMAN_PART_ID(int GASMAN_PART_ID) {
        mShare.edit().putInt("GASMAN_PART_ID", GASMAN_PART_ID).commit();
    }

    public void setIsLogin(boolean isLogin) {
        mShare.edit().putBoolean("isLogin", isLogin).commit();
    }

    public boolean getIsLogin() {
        return mShare.getBoolean("isLogin", false);
    }


    public String getDriverId() {
        return mShare.getString("driverId", "");
    }

    public void setDriverId(String drIverId) {
        mShare.edit().putString("driverId", drIverId).commit();
    }


    public void setDriveName(String driverName) {
        mShare.edit().putString("driverName", driverName).commit();
    }

    public String getDriveName() {
        return mShare.getString("driverName", "");
    }

    public String getUserName() {
        return mShare.getString("userName", "");
    }

    public void setUserName(String userName) {
        mShare.edit().putString("userName", userName).commit();
    }

    public String getUserAliAs() {
        return mShare.getString("userAliAs", "");
    }

    public void setUserAliAs(String userAliAs) {
        mShare.edit().putString("userAliAs", userAliAs).commit();
    }

    public void setCarId(int carId) {
        mShare.edit().putInt("carId", carId).commit();
    }

    public int getCarId() {
        return mShare.getInt("carId", -1);
    }

    public void setCarCode(String carCode) {
        mShare.edit().putString("carCode", carCode).commit();
    }

    public String getCarCode() {
        return mShare.getString("carCode", "");
    }

    public void setUserPhone(String userPhone) {
        mShare.edit().putString("userPhone", userPhone).commit();
    }

    public String getUserPhone() {
        return mShare.getString("userPhone", "");
    }

    public String getNextSecret() {
        return mShare.getString("NextSecret", "");
    }

    public void setnextSecret(String extSecret) {
        mShare.edit().putString("carPhone", extSecret).commit();
    }


    /**
     * 头像
     *
     * @param userLogo
     */
    public void setHeadImg(String userLogo) {
        mShare.edit().putString("userLogo", userLogo).commit();
    }

    public String getHeadImg() {
        return mShare.getString("userLogo", "");
    }

    /**
     * 作业区id
     *
     * @param groupId
     */
    public void setGroupID(String groupId) {
        mShare.edit().putString("groupId", groupId).commit();
    }

    public String getGroupID() {
        return mShare.getString("groupId", "");
    }

    public String getCarGroupID() {
        return mShare.getString("CarGroupId", "");
    }

    /**
     * 车作业区id
     *
     * @param groupId
     */
    public void setCarGroupID(String groupId) {
        mShare.edit().putString("CarGroupId", groupId).commit();
    }

    /**
     * 汽车类型
     * 1 oil  2  gas
     *
     * @param carOilGasType
     */
    public void setCarOilGasType(String carOilGasType) {
        mShare.edit().putString("carOilGasType", carOilGasType).commit();
    }

    public String getCarOilGasType() {
        return mShare.getString("carOilGasType", "");
    }


    /**
     * @param role 1超级管理员 2司机 3控制中心 4货官员
     */
    public void setRoleId(String role) {
        mShare.edit().putString("role", role).commit();

    }


    public String getRoleId() {
        return mShare.getString("role", "");
    }

    public void clearUserInfo() {
        setCarCode("");
        setUserPhone("");
        setGroupID("");
        setUserId("");
        setUserName("");
        setCarId(-1);
        setnextSecret("");
        setRoleId("");
        setIsLogin(false);
        setDriverId("");
        setDriveName("");
        setFEEDER_PART_ID(-1);
        setFEEDER_PART_ID(-1);
        setCarGroupID("");
        setCarOilGasType("");
        setHeadImg("");
    }

}
