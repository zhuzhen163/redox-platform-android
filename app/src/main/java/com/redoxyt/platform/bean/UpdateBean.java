package com.redoxyt.platform.bean;

public class UpdateBean {

    private String package_url;
    private String package_desc;
    private String package_version;
    private String package_flag;

    public String getPackage_flag() {
        return package_flag;
    }

    public void setPackage_flag(String package_flag) {
        this.package_flag = package_flag;
    }

    public String getPackage_url() {
        return package_url;
    }

    public void setPackage_url(String package_url) {
        this.package_url = package_url;
    }

    public String getPackage_desc() {
        return package_desc;
    }

    public void setPackage_desc(String package_desc) {
        this.package_desc = package_desc;
    }

    public String getPackage_version() {
        return package_version;
    }

    public void setPackage_version(String package_version) {
        this.package_version = package_version;
    }
}
