package com.redoxyt.platform.bean;

/**
 * 车队回显
 */
public class FleetInfoBean {

    private TmsGroup tmsGroup;
    private TmsUserIdcard tmsUserIdcard;

    public TmsGroup getTmsGroup() {
        return tmsGroup;
    }

    public void setTmsGroup(TmsGroup tmsGroup) {
        this.tmsGroup = tmsGroup;
    }

    public TmsUserIdcard getTmsUserIdcard() {
        return tmsUserIdcard;
    }

    public void setTmsUserIdcard(TmsUserIdcard tmsUserIdcard) {
        this.tmsUserIdcard = tmsUserIdcard;
    }

    public class TmsUserIdcard {
        private String idcardRealname;//身份证姓名
        private String idcardCode;//身份证号
        private String idcardAddress;//身份证地址
        private String idcardFront;//身份证正面照片

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

    public class TmsGroup {
        private String groupContacter;
        private String groupAbbr;
        private String groupCompany;//企业名称
        private String groupTaxCode;//纳锐人识别号
        private String groupAddress;//企业地址
        private String groupLegal;//法人
        private String groupCapital;//注册资本
        private String groupCreateDate;//注册日期
        private String groupBusinessScope;//经营范围
        private String groupBusinessLicense;//营业执照上传路径

        public String getGroupContacter() {
            return groupContacter;
        }

        public void setGroupContacter(String groupContacter) {
            this.groupContacter = groupContacter;
        }

        public String getGroupAbbr() {
            return groupAbbr;
        }

        public void setGroupAbbr(String groupAbbr) {
            this.groupAbbr = groupAbbr;
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
    }
}
