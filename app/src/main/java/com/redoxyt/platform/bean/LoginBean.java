package com.redoxyt.platform.bean;

/**
 * Created by zz.
 * description:
 */

public class LoginBean {

    /**
     * access_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRmxhZyI6bnVsbCwidXNlcl9uYW1lIjoiMTMzMDAwMDAyOTEiLCJzY29wZSI6WyJyZWFkIl0sImdyb3VwSWQiOjEsImV4cCI6MTU3OTI4MTkzMCwidXNlcklkIjo3LCJqdGkiOiJmMzZkNzE3MC01ZDYyLTRlNjYtYWExMy03MDQxZDgzMDM2NjMiLCJjbGllbnRfaWQiOiJhcHAifQ.GRQSSC9KfKMdaiwbXwl1HSYLTVRzbx9-hY1MUCrjFak
     * token_type : bearer
     * refresh_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRmxhZyI6bnVsbCwidXNlcl9uYW1lIjoiMTMzMDAwMDAyOTEiLCJzY29wZSI6WyJyZWFkIl0sImdyb3VwSWQiOjEsImF0aSI6ImYzNmQ3MTcwLTVkNjItNGU2Ni1hYTEzLTcwNDFkODMwMzY2MyIsImV4cCI6MTU4MTgzMDczMCwidXNlcklkIjo3LCJqdGkiOiIwZDI0OTk3OS1jYjFjLTQxZDUtOGYyMi0wMzY2NjU3MzViMjUiLCJjbGllbnRfaWQiOiJhcHAifQ.jxvMDMTgQ9tqWP9q_kpOBHAu6FKbMcKnkL0W7201fmQ
     * expires_in : 43005
     * scope : read
     * userFlag : null
     * groupId : 1
     * userId : 7
     * jti : f36d7170-5d62-4e66-aa13-7041d8303663
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int userFlag;
    private String groupId;
    private int userId;
    private String jti;
    private String realName;
    private String userMobile;
    private String userStatus;

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(int userFlag) {
        this.userFlag = userFlag;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
