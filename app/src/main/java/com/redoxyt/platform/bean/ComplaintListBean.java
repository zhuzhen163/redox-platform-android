package com.redoxyt.platform.bean;


import java.util.List;

public class ComplaintListBean {

    /**
     * complainClassId : 1
     * complainClassName : 投诉
     * children : [{"complainTypeId":1,"complainTypeName":"运费问题"},{"complainTypeId":2,"complainTypeName":"提现问题"},{"complainTypeId":3,"complainTypeName":"使用问题"},{"complainTypeId":4,"complainTypeName":"其它问题"}]
     */

    private int complainClassId;
    private String complainClassName;
    private List<ChildrenBean> children;

    public int getComplainClassId() {
        return complainClassId;
    }

    public void setComplainClassId(int complainClassId) {
        this.complainClassId = complainClassId;
    }

    public String getComplainClassName() {
        return complainClassName;
    }

    public void setComplainClassName(String complainClassName) {
        this.complainClassName = complainClassName;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * complainTypeId : 1
         * complainTypeName : 运费问题
         */

        private int complainTypeId;
        private String complainTypeName;

        public int getComplainTypeId() {
            return complainTypeId;
        }

        public void setComplainTypeId(int complainTypeId) {
            this.complainTypeId = complainTypeId;
        }

        public String getComplainTypeName() {
            return complainTypeName;
        }

        public void setComplainTypeName(String complainTypeName) {
            this.complainTypeName = complainTypeName;
        }
    }
}

