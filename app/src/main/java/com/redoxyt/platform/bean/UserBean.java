package com.redoxyt.platform.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 */
public class UserBean implements Serializable{

    private String userMobile;
    private String userName;
    private String userLoginPwd;
    private String smsCode;
    private String groupAbbr;//车队名称
    private String groupContacter;//联系人

    private String groupCompany;//企业名称
    private String groupTaxCode;//纳锐人识别号
    private String groupAddress;//企业地址
    private String groupLegal;//法人
    private String groupCapital;//注册资本
    private String groupCreateDate;//注册日期
    private String groupBusinessScope;//经营范围
    private String groupBusinessLicense;//营业执照上传路径

    private String idcardRealname;//身份证姓名
    private String idcardCode;//身份证号
    private String idcardAddress;//身份证地址
    private String idcardFront;//身份证正面照片

    private String userStatus;//
    private String userStatusName;
    private String userId;
    private String flag;

    private String access_token;
    private String refresh_token;
    private int userFlag;
    private int groupId;
    private String realName;

    private TmsBean tmsUserExt;
    private List<String> platformIdList;

    public List<String> getPlatformIdList() {
        return platformIdList;
    }

    public void setPlatformIdList(List<String> platformIdList) {
        this.platformIdList = platformIdList;
    }

    public TmsBean getTmsUserExt() {
        return tmsUserExt;
    }

    public void setTmsUserExt(TmsBean tmsUserExt) {
        this.tmsUserExt = tmsUserExt;
    }

    public class TmsBean implements Serializable{
        private String platformWarehouseCode;
        private String platformWarehouseName;
        private String groupId;
        private String groupAbbr;

        public String getGroupAbbr() {
            return groupAbbr;
        }

        public void setGroupAbbr(String groupAbbr) {
            this.groupAbbr = groupAbbr;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getPlatformWarehouseCode() {
            return platformWarehouseCode;
        }

        public void setPlatformWarehouseCode(String platformWarehouseCode) {
            this.platformWarehouseCode = platformWarehouseCode;
        }

        public String getPlatformWarehouseName() {
            return platformWarehouseName;
        }

        public void setPlatformWarehouseName(String platformWarehouseName) {
            this.platformWarehouseName = platformWarehouseName;
        }
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(int userFlag) {
        this.userFlag = userFlag;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserStatusName() {
        return userStatusName;
    }

    public void setUserStatusName(String userStatusName) {
        this.userStatusName = userStatusName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLoginPwd() {
        return userLoginPwd;
    }

    public void setUserLoginPwd(String userLoginPwd) {
        this.userLoginPwd = userLoginPwd;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getGroupAbbr() {
        return groupAbbr;
    }

    public void setGroupAbbr(String groupAbbr) {
        this.groupAbbr = groupAbbr;
    }

    public String getGroupContacter() {
        return groupContacter;
    }

    public void setGroupContacter(String groupContacter) {
        this.groupContacter = groupContacter;
    }

    public String getGroupCompany() {
        return groupCompany;
    }

    public void setGroupCompany(String groupCompany) {
        this.groupCompany = groupCompany;
    }

    public String getGroupTaxCode() {
        return groupTaxCode;
    }

    public void setGroupTaxCode(String groupTaxCode) {
        this.groupTaxCode = groupTaxCode;
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public String getGroupLegal() {
        return groupLegal;
    }

    public void setGroupLegal(String groupLegal) {
        this.groupLegal = groupLegal;
    }

    public String getGroupCapital() {
        return groupCapital;
    }

    public void setGroupCapital(String groupCapital) {
        this.groupCapital = groupCapital;
    }

    public String getGroupCreateDate() {
        return groupCreateDate;
    }

    public void setGroupCreateDate(String groupCreateDate) {
        this.groupCreateDate = groupCreateDate;
    }

    public String getGroupBusinessScope() {
        return groupBusinessScope;
    }

    public void setGroupBusinessScope(String groupBusinessScope) {
        this.groupBusinessScope = groupBusinessScope;
    }

    public String getGroupBusinessLicense() {
        return groupBusinessLicense;
    }

    public void setGroupBusinessLicense(String groupBusinessLicense) {
        this.groupBusinessLicense = groupBusinessLicense;
    }

    public String getIdcardRealname() {
        return idcardRealname;
    }

    public void setIdcardRealname(String idcardRealname) {
        this.idcardRealname = idcardRealname;
    }

    public String getIdcardCode() {
        return idcardCode;
    }

    public void setIdcardCode(String idcardCode) {
        this.idcardCode = idcardCode;
    }

    public String getIdcardAddress() {
        return idcardAddress;
    }

    public void setIdcardAddress(String idcardAddress) {
        this.idcardAddress = idcardAddress;
    }

    public String getIdcardFront() {
        return idcardFront;
    }

    public void setIdcardFront(String idcardFront) {
        this.idcardFront = idcardFront;
    }
}
