package com.redoxyt.platform.bean;

/**
 * @author Sxw
 * @date 2019/12/18.
 * description：${xxxxx}
 */

public class InfoBean {


    /**
     * tmsUserDriver : {"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"beginTime":null,"endTime":null,"version":null,"userId":1,"groupId":0,"driverName":"高伟","driverLicence":"522115199806141518","driverModel":"C1","driverIssuedBy":"北京","driverStartDate":"2017-10-18","driverEndDate":"2027-10-18","driverCertification":"522115199806141518","driverLicenceImage":"/ImageData/driver_license/20191218/3_1576639717860.png","driverStatus":1,"driverAuditTime":"2019-12-18 11:29:58","driverAuditorId":1}
     * tmsUserBindBanks : {"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"beginTime":null,"endTime":null,"version":null,"bindId":1,"userId":1,"bindOwnerType":1,"bindBankId":1,"bindBankAccount":"6228480088141848874","bindBankMobile":null,"bindBankRealname":null,"bindBankIdcard":null,"bandBankStatus":1,"bankName":"农业银行"}
     * tmsUserCar : {"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"beginTime":null,"endTime":null,"version":null,"carId":1,"userId":1,"groupId":1,"carDrivingSsuedBy":"北京","carDrivingIssueDate":"2018-06-25","carDrivingRegistDate":"2018-05-03","carDrivingLicence":"322324711","carTransportLicence":"322324711","carCode":"冀C13958","carOwner":"昌黎佳通货物运输有限公司","carOwnerType":"企业","carProperty":"货运","carVin":"LZGJ1G842HX126853","carWeight":40000,"carModel":"重型半挂牵引车","carLength":7375,"carLoad":0,"carBrand":"陕汽牌","motCarPlatetypeCode":"Q11","motCarClassCode":null,"carEnergyType":"柴油","carIsAffiliated":1,"carOwnerStatement":"/ImageData/car_owners_call_statement/20191218/9_1576639768449.png","carCompanyStatement":"/ImageData/affiliate_company_statement/20191218/10_1576639777259.png","carStatus":1,"carDrivingImage":"/ImageData/driving_license/20191218/4_1576639700435.png","carImage":"/ImageData/vehicle_photo/20191218/8_1576639742076.png"}
     * tmsUserIdcard : {"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"beginTime":null,"endTime":null,"version":null,"userId":1,"groupId":0,"idcardRealname":"李业虹","idcardType":null,"idcardCode":"222403199407240247","idcardFront":"/ImageData/id_card/20191218/1_1576639731311.png","idcardReverse":null,"idcardStartDate":null,"idcardEndDate":null,"idcardAddress":"吉林省敦化市开发区双胜社区四组","idcardIssuedBy":null,"idcardRace":null,"idcardSex":null,"idcardArea":null,"idcardStatus":1}
     */

    private TmsUserDriverBean tmsUserDriver;
    private TmsUserBindBanksBean tmsUserBindBanks;
    private TmsUserCarBean tmsUserCar;
    private TmsUserIdcardBean tmsUserIdcard;
    private TmsUser tmsUser;
    private TmsUserCarTrailerBean tmsUserCarTrailer;

    public TmsUserCarTrailerBean getTmsUserCarTrailer() {
        return tmsUserCarTrailer;
    }

    public void setTmsUserCarTrailer(TmsUserCarTrailerBean tmsUserCarTrailer) {
        this.tmsUserCarTrailer = tmsUserCarTrailer;
    }

    public TmsUser getTmsUser() {
        return tmsUser;
    }

    public void setTmsUser(TmsUser tmsUser) {
        this.tmsUser = tmsUser;
    }

    public TmsUserDriverBean getTmsUserDriver() {
        return tmsUserDriver;
    }

    public void setTmsUserDriver(TmsUserDriverBean tmsUserDriver) {
        this.tmsUserDriver = tmsUserDriver;
    }

    public TmsUserBindBanksBean getTmsUserBindBanks() {
        return tmsUserBindBanks;
    }

    public void setTmsUserBindBanks(TmsUserBindBanksBean tmsUserBindBanks) {
        this.tmsUserBindBanks = tmsUserBindBanks;
    }

    public TmsUserCarBean getTmsUserCar() {
        return tmsUserCar;
    }

    public void setTmsUserCar(TmsUserCarBean tmsUserCar) {
        this.tmsUserCar = tmsUserCar;
    }

    public TmsUserIdcardBean getTmsUserIdcard() {
        return tmsUserIdcard;
    }

    public void setTmsUserIdcard(TmsUserIdcardBean tmsUserIdcard) {
        this.tmsUserIdcard = tmsUserIdcard;
    }

    public static class TmsUserDriverBean {
        /**
         * createUserId : null
         * createTime : null
         * updateUserId : null
         * updateTime : null
         * beginTime : null
         * endTime : null
         * version : null
         * userId : 1
         * groupId : 0
         * driverName : 高伟
         * driverLicence : 522115199806141518
         * driverModel : C1
         * driverIssuedBy : 北京
         * driverStartDate : 2017-10-18
         * driverEndDate : 2027-10-18
         * driverCertification : 522115199806141518
         * driverLicenceImage : /ImageData/driver_license/20191218/3_1576639717860.png
         * driverStatus : 1
         * driverAuditTime : 2019-12-18 11:29:58
         * driverAuditorId : 1
         */

        private int userId;
        private int groupId;
        private String driverName;
        private String driverLicence;
        private String driverModel;
        private String driverIssuedBy;
        private String driverStartDate;
        private String driverEndDate;
        private String driverCertification;
        private String driverLicenceImage;
        private int driverStatus;
        private String driverAuditTime;
        private int driverAuditorId;
        private String driverCertificationImage;

        public String getDriverCertificationImage() {
            return driverCertificationImage;
        }

        public void setDriverCertificationImage(String driverCertificationImage) {
            this.driverCertificationImage = driverCertificationImage;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverLicence() {
            return driverLicence;
        }

        public void setDriverLicence(String driverLicence) {
            this.driverLicence = driverLicence;
        }

        public String getDriverModel() {
            return driverModel;
        }

        public void setDriverModel(String driverModel) {
            this.driverModel = driverModel;
        }

        public String getDriverIssuedBy() {
            return driverIssuedBy;
        }

        public void setDriverIssuedBy(String driverIssuedBy) {
            this.driverIssuedBy = driverIssuedBy;
        }

        public String getDriverStartDate() {
            return driverStartDate;
        }

        public void setDriverStartDate(String driverStartDate) {
            this.driverStartDate = driverStartDate;
        }

        public String getDriverEndDate() {
            return driverEndDate;
        }

        public void setDriverEndDate(String driverEndDate) {
            this.driverEndDate = driverEndDate;
        }

        public String getDriverCertification() {
            return driverCertification;
        }

        public void setDriverCertification(String driverCertification) {
            this.driverCertification = driverCertification;
        }

        public String getDriverLicenceImage() {
            return driverLicenceImage;
        }

        public void setDriverLicenceImage(String driverLicenceImage) {
            this.driverLicenceImage = driverLicenceImage;
        }

        public int getDriverStatus() {
            return driverStatus;
        }

        public void setDriverStatus(int driverStatus) {
            this.driverStatus = driverStatus;
        }

        public String getDriverAuditTime() {
            return driverAuditTime;
        }

        public void setDriverAuditTime(String driverAuditTime) {
            this.driverAuditTime = driverAuditTime;
        }

        public int getDriverAuditorId() {
            return driverAuditorId;
        }

        public void setDriverAuditorId(int driverAuditorId) {
            this.driverAuditorId = driverAuditorId;
        }
    }

    public static class TmsUserBindBanksBean {
        /**
         * createUserId : null
         * createTime : null
         * updateUserId : null
         * updateTime : null
         * beginTime : null
         * endTime : null
         * version : null
         * bindId : 1
         * userId : 1
         * bindOwnerType : 1
         * bindBankId : 1
         * bindBankAccount : 6228480088141848874
         * bindBankMobile : null
         * bindBankRealname : null
         * bindBankIdcard : null
         * bandBankStatus : 1
         * bankName : 农业银行
         */

        private Object createUserId;
        private Object createTime;
        private Object updateUserId;
        private Object updateTime;
        private Object beginTime;
        private Object endTime;
        private Object version;
        private int bindId;
        private int userId;
        private int bindOwnerType;
        private int bindBankId;
        private String bindBankAccount;
        private String bindBankMobile;
        private String bindBankRealname;
        private String bindBankIdcard;
        private int bandBankStatus;
        private String bankName;
        private String bindBankImg;

        public String getBindBankImg() {
            return bindBankImg;
        }

        public void setBindBankImg(String bindBankImg) {
            this.bindBankImg = bindBankImg;
        }

        public Object getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Object createUserId) {
            this.createUserId = createUserId;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(Object updateUserId) {
            this.updateUserId = updateUserId;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public int getBindId() {
            return bindId;
        }

        public void setBindId(int bindId) {
            this.bindId = bindId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getBindOwnerType() {
            return bindOwnerType;
        }

        public void setBindOwnerType(int bindOwnerType) {
            this.bindOwnerType = bindOwnerType;
        }

        public int getBindBankId() {
            return bindBankId;
        }

        public void setBindBankId(int bindBankId) {
            this.bindBankId = bindBankId;
        }

        public String getBindBankAccount() {
            return bindBankAccount;
        }

        public void setBindBankAccount(String bindBankAccount) {
            this.bindBankAccount = bindBankAccount;
        }

        public String getBindBankMobile() {
            return bindBankMobile;
        }

        public void setBindBankMobile(String bindBankMobile) {
            this.bindBankMobile = bindBankMobile;
        }

        public String getBindBankRealname() {
            return bindBankRealname;
        }

        public void setBindBankRealname(String bindBankRealname) {
            this.bindBankRealname = bindBankRealname;
        }

        public String getBindBankIdcard() {
            return bindBankIdcard;
        }

        public void setBindBankIdcard(String bindBankIdcard) {
            this.bindBankIdcard = bindBankIdcard;
        }

        public int getBandBankStatus() {
            return bandBankStatus;
        }

        public void setBandBankStatus(int bandBankStatus) {
            this.bandBankStatus = bandBankStatus;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }
    }

    public static class TmsUserCarTrailerBean{

        /**
         * createUserId : null
         * createTime : null
         * updateUserId : 138
         * updateTime : null
         * beginTime : null
         * endTime : null
         * version : null
         * trailerId : 6
         * carId : 1065
         * userId : 138
         * groupId : 156
         * trailerDrivingSsuedBy : 看看2
         * trailerDrivingIssueDate : 2018-11-12
         * trailerDrivingRegistDate : 2018-11-12
         * trailerDrivingLicence : 374428987572
         * trailerTransportLicence : 图2
         * trailerTransportLicenceImage : http://redox-test01.oss-cn-qingdao.aliyuncs.com/ImageData/affiliate_company_statement/20200206/10_1580986056459.png
         * trailerCode : 冀B12232
         * trailerOwner : 遵化宏日通物流有限公司2
         * trailerOwnerType : 企业2
         * trailerProperty : 货运2
         * trailerVin : LFWSRU9L8JAC296282
         * trailerWeight : 82
         * trailerModel : 李2
         * trailerLength : 82
         * trailerLoad : 82
         * trailerBrand : 解放牌CA4251P1K15T1NE2
         * motTrailerPlatetypeCode : 李2
         * motTrailerClassCode : 112
         * trailerEnergyType : null
         * trailerEnergyTypeName : null
         * trailerStatus : 2
         * trailerDrivingImage : http://redox-test01.oss-cn-qingdao.aliyuncs.com/ImageData/driving_license/20200206/4_1580985847229.png
         * trailerGabarite : null
         * exceptionReason : 不通过
         * trailerStatusName : 审核不通过
         * carAuditTime : 2020-03-26 17:25:57
         */

        private int trailerId;
        private int carId;
        private int userId;
        private int groupId;
        private String trailerDrivingSsuedBy;
        private String trailerDrivingIssueDate;
        private String trailerDrivingRegistDate;
        private String trailerDrivingLicence;
        private String trailerTransportLicence;
        private String trailerTransportLicenceImage;
        private String trailerCode;
        private String trailerOwner;
        private String trailerOwnerType;
        private String trailerProperty;
        private String trailerVin;
        private String trailerWeight;
        private String trailerModel;
        private String trailerLength;
        private String trailerLoad;
        private String trailerBrand;
        private String motTrailerPlatetypeCode;
        private String motTrailerClassCode;
        private int trailerStatus;
        private String trailerDrivingImage;
        private String trailerGabarite;
        private String exceptionReason;
        private String trailerStatusName;
        private String carAuditTime;

        private String motTrailerClassCodeName;//车辆类型
        private String motTrailerPlatetypeCodeName;
        private String trailerDrivingAttachedImage;//挂车行驶证附页图片

        public String getTrailerDrivingAttachedImage() {
            return trailerDrivingAttachedImage;
        }

        public void setTrailerDrivingAttachedImage(String trailerDrivingAttachedImage) {
            this.trailerDrivingAttachedImage = trailerDrivingAttachedImage;
        }

        public String getMotTrailerPlatetypeCodeName() {
            return motTrailerPlatetypeCodeName;
        }

        public void setMotTrailerPlatetypeCodeName(String motTrailerPlatetypeCodeName) {
            this.motTrailerPlatetypeCodeName = motTrailerPlatetypeCodeName;
        }

        public String getMotTrailerClassCodeName() {
            return motTrailerClassCodeName;
        }

        public void setMotTrailerClassCodeName(String motTrailerClassCodeName) {
            this.motTrailerClassCodeName = motTrailerClassCodeName;
        }

        public int getTrailerId() {
            return trailerId;
        }

        public void setTrailerId(int trailerId) {
            this.trailerId = trailerId;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getTrailerDrivingSsuedBy() {
            return trailerDrivingSsuedBy;
        }

        public void setTrailerDrivingSsuedBy(String trailerDrivingSsuedBy) {
            this.trailerDrivingSsuedBy = trailerDrivingSsuedBy;
        }

        public String getTrailerDrivingIssueDate() {
            return trailerDrivingIssueDate;
        }

        public void setTrailerDrivingIssueDate(String trailerDrivingIssueDate) {
            this.trailerDrivingIssueDate = trailerDrivingIssueDate;
        }

        public String getTrailerDrivingRegistDate() {
            return trailerDrivingRegistDate;
        }

        public void setTrailerDrivingRegistDate(String trailerDrivingRegistDate) {
            this.trailerDrivingRegistDate = trailerDrivingRegistDate;
        }

        public String getTrailerDrivingLicence() {
            return trailerDrivingLicence;
        }

        public void setTrailerDrivingLicence(String trailerDrivingLicence) {
            this.trailerDrivingLicence = trailerDrivingLicence;
        }

        public String getTrailerTransportLicence() {
            return trailerTransportLicence;
        }

        public void setTrailerTransportLicence(String trailerTransportLicence) {
            this.trailerTransportLicence = trailerTransportLicence;
        }

        public String getTrailerTransportLicenceImage() {
            return trailerTransportLicenceImage;
        }

        public void setTrailerTransportLicenceImage(String trailerTransportLicenceImage) {
            this.trailerTransportLicenceImage = trailerTransportLicenceImage;
        }

        public String getTrailerCode() {
            return trailerCode;
        }

        public void setTrailerCode(String trailerCode) {
            this.trailerCode = trailerCode;
        }

        public String getTrailerOwner() {
            return trailerOwner;
        }

        public void setTrailerOwner(String trailerOwner) {
            this.trailerOwner = trailerOwner;
        }

        public String getTrailerOwnerType() {
            return trailerOwnerType;
        }

        public void setTrailerOwnerType(String trailerOwnerType) {
            this.trailerOwnerType = trailerOwnerType;
        }

        public String getTrailerProperty() {
            return trailerProperty;
        }

        public void setTrailerProperty(String trailerProperty) {
            this.trailerProperty = trailerProperty;
        }

        public String getTrailerVin() {
            return trailerVin;
        }

        public void setTrailerVin(String trailerVin) {
            this.trailerVin = trailerVin;
        }


        public String getTrailerModel() {
            return trailerModel;
        }

        public void setTrailerModel(String trailerModel) {
            this.trailerModel = trailerModel;
        }

        public String getTrailerBrand() {
            return trailerBrand;
        }

        public void setTrailerBrand(String trailerBrand) {
            this.trailerBrand = trailerBrand;
        }

        public String getMotTrailerPlatetypeCode() {
            return motTrailerPlatetypeCode;
        }

        public void setMotTrailerPlatetypeCode(String motTrailerPlatetypeCode) {
            this.motTrailerPlatetypeCode = motTrailerPlatetypeCode;
        }

        public String getMotTrailerClassCode() {
            return motTrailerClassCode;
        }

        public void setMotTrailerClassCode(String motTrailerClassCode) {
            this.motTrailerClassCode = motTrailerClassCode;
        }

        public String getTrailerWeight() {
            return trailerWeight;
        }

        public void setTrailerWeight(String trailerWeight) {
            this.trailerWeight = trailerWeight;
        }

        public String getTrailerLength() {
            return trailerLength;
        }

        public void setTrailerLength(String trailerLength) {
            this.trailerLength = trailerLength;
        }

        public String getTrailerLoad() {
            return trailerLoad;
        }

        public void setTrailerLoad(String trailerLoad) {
            this.trailerLoad = trailerLoad;
        }

        public int getTrailerStatus() {
            return trailerStatus;
        }

        public void setTrailerStatus(int trailerStatus) {
            this.trailerStatus = trailerStatus;
        }

        public String getTrailerDrivingImage() {
            return trailerDrivingImage;
        }

        public void setTrailerDrivingImage(String trailerDrivingImage) {
            this.trailerDrivingImage = trailerDrivingImage;
        }

        public String getTrailerGabarite() {
            return trailerGabarite;
        }

        public void setTrailerGabarite(String trailerGabarite) {
            this.trailerGabarite = trailerGabarite;
        }

        public String getExceptionReason() {
            return exceptionReason;
        }

        public void setExceptionReason(String exceptionReason) {
            this.exceptionReason = exceptionReason;
        }

        public String getTrailerStatusName() {
            return trailerStatusName;
        }

        public void setTrailerStatusName(String trailerStatusName) {
            this.trailerStatusName = trailerStatusName;
        }

        public String getCarAuditTime() {
            return carAuditTime;
        }

        public void setCarAuditTime(String carAuditTime) {
            this.carAuditTime = carAuditTime;
        }
    }

    public static class TmsUserCarBean {
        /**
         * createUserId : null
         * createTime : null
         * updateUserId : null
         * updateTime : null
         * beginTime : null
         * endTime : null
         * version : null
         * carId : 1
         * userId : 1
         * groupId : 1
         * carDrivingSsuedBy : 北京
         * carDrivingIssueDate : 2018-06-25
         * carDrivingRegistDate : 2018-05-03
         * carDrivingLicence : 322324711
         * carTransportLicence : 322324711
         * carCode : 冀C13958
         * carOwner : 昌黎佳通货物运输有限公司
         * carOwnerType : 企业
         * carProperty : 货运
         * carVin : LZGJ1G842HX126853
         * carWeight : 40000.0
         * carModel : 重型半挂牵引车
         * carLength : 7375.0
         * carLoad : 0.0
         * carBrand : 陕汽牌
         * motCarPlatetypeCode : Q11
         * motCarClassCode : null
         * carEnergyType : 柴油
         * carIsAffiliated : 1
         * carOwnerStatement : /ImageData/car_owners_call_statement/20191218/9_1576639768449.png
         * carCompanyStatement : /ImageData/affiliate_company_statement/20191218/10_1576639777259.png
         * carStatus : 1
         * carDrivingImage : /ImageData/driving_license/20191218/4_1576639700435.png
         * carImage : /ImageData/vehicle_photo/20191218/8_1576639742076.png
         */

        private int carId;
        private int userId;
        private int groupId;
        private String carDrivingSsuedBy;
        private String carDrivingIssueDate;
        private String carDrivingRegistDate;
        private String carDrivingLicence;
        private String carTransportLicence;
        private String carCode;
        private String carOwner;
        private String carOwnerType;
        private String carProperty;
        private String carVin;
        private String carWeight;
        private String carModel;
        private String carLength;
        private String carLoad;
        private String carBrand;
        private String motCarPlatetypeCode;
        private String motCarClassCode;
        private String motCarClassCodeName;
        private String carEnergyType;
        private String carEnergyTypeName;
        private int carIsAffiliated;
        private String carOwnerStatement;
        private String carCompanyStatement;
        private int carStatus;
        private String carDrivingImage;
        private String carImage;
        private int carIsTrailer;
        private String motCarPlatetypeCodeName;//车牌分类（车牌颜色）
        private String carTransportLicenceImage;
        private String carGabarite;
        private String drivingLicenseFront;//车头行驶证正页

        private String carCompanyTaxCode;//纳税人识别号
        private String carCompanyCertificateCode;//道路运输经营许可证号
        private String carCompanyBusinessLicense;//车辆所属企业营业执照上传路径
        private String carCompanyCertificateImg;//道路运输经营许可证上传路径
        private String carDrivingAttachedImage;//车头行驶证附页图片

        public String getCarDrivingAttachedImage() {
            return carDrivingAttachedImage;
        }

        public void setCarDrivingAttachedImage(String carDrivingAttachedImage) {
            this.carDrivingAttachedImage = carDrivingAttachedImage;
        }

        public String getDrivingLicenseFront() {
            return drivingLicenseFront;
        }

        public void setDrivingLicenseFront(String drivingLicenseFront) {
            this.drivingLicenseFront = drivingLicenseFront;
        }

        public String getCarCompanyTaxCode() {
            return carCompanyTaxCode;
        }

        public void setCarCompanyTaxCode(String carCompanyTaxCode) {
            this.carCompanyTaxCode = carCompanyTaxCode;
        }

        public String getCarCompanyCertificateCode() {
            return carCompanyCertificateCode;
        }

        public void setCarCompanyCertificateCode(String carCompanyCertificateCode) {
            this.carCompanyCertificateCode = carCompanyCertificateCode;
        }

        public String getCarCompanyBusinessLicense() {
            return carCompanyBusinessLicense;
        }

        public void setCarCompanyBusinessLicense(String carCompanyBusinessLicense) {
            this.carCompanyBusinessLicense = carCompanyBusinessLicense;
        }

        public String getCarCompanyCertificateImg() {
            return carCompanyCertificateImg;
        }

        public void setCarCompanyCertificateImg(String carCompanyCertificateImg) {
            this.carCompanyCertificateImg = carCompanyCertificateImg;
        }

        public String getCarGabarite() {
            return carGabarite;
        }

        public void setCarGabarite(String carGabarite) {
            this.carGabarite = carGabarite;
        }

        public String getCarTransportLicenceImage() {
            return carTransportLicenceImage;
        }

        public void setCarTransportLicenceImage(String carTransportLicenceImage) {
            this.carTransportLicenceImage = carTransportLicenceImage;
        }

        public int getCarIsTrailer() {
            return carIsTrailer;
        }

        public void setCarIsTrailer(int carIsTrailer) {
            this.carIsTrailer = carIsTrailer;
        }

        public String getMotCarPlatetypeCodeName() {
            return motCarPlatetypeCodeName;
        }

        public void setMotCarPlatetypeCodeName(String motCarPlatetypeCodeName) {
            this.motCarPlatetypeCodeName = motCarPlatetypeCodeName;
        }

        public String getMotCarClassCodeName() {
            return motCarClassCodeName;
        }

        public void setMotCarClassCodeName(String motCarClassCodeName) {
            this.motCarClassCodeName = motCarClassCodeName;
        }

        public String getCarEnergyTypeName() {
            return carEnergyTypeName;
        }

        public void setCarEnergyTypeName(String carEnergyTypeName) {
            this.carEnergyTypeName = carEnergyTypeName;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getCarDrivingSsuedBy() {
            return carDrivingSsuedBy;
        }

        public void setCarDrivingSsuedBy(String carDrivingSsuedBy) {
            this.carDrivingSsuedBy = carDrivingSsuedBy;
        }

        public String getCarDrivingIssueDate() {
            return carDrivingIssueDate;
        }

        public void setCarDrivingIssueDate(String carDrivingIssueDate) {
            this.carDrivingIssueDate = carDrivingIssueDate;
        }

        public String getCarDrivingRegistDate() {
            return carDrivingRegistDate;
        }

        public void setCarDrivingRegistDate(String carDrivingRegistDate) {
            this.carDrivingRegistDate = carDrivingRegistDate;
        }

        public String getCarDrivingLicence() {
            return carDrivingLicence;
        }

        public void setCarDrivingLicence(String carDrivingLicence) {
            this.carDrivingLicence = carDrivingLicence;
        }

        public String getCarTransportLicence() {
            return carTransportLicence;
        }

        public void setCarTransportLicence(String carTransportLicence) {
            this.carTransportLicence = carTransportLicence;
        }

        public String getCarCode() {
            return carCode;
        }

        public void setCarCode(String carCode) {
            this.carCode = carCode;
        }

        public String getCarOwner() {
            return carOwner;
        }

        public void setCarOwner(String carOwner) {
            this.carOwner = carOwner;
        }

        public String getCarOwnerType() {
            return carOwnerType;
        }

        public void setCarOwnerType(String carOwnerType) {
            this.carOwnerType = carOwnerType;
        }

        public String getCarProperty() {
            return carProperty;
        }

        public void setCarProperty(String carProperty) {
            this.carProperty = carProperty;
        }

        public String getCarVin() {
            return carVin;
        }

        public void setCarVin(String carVin) {
            this.carVin = carVin;
        }

        public String getCarWeight() {
            return carWeight;
        }

        public void setCarWeight(String carWeight) {
            this.carWeight = carWeight;
        }

        public String getCarModel() {
            return carModel;
        }

        public void setCarModel(String carModel) {
            this.carModel = carModel;
        }

        public String getCarLength() {
            return carLength;
        }

        public void setCarLength(String carLength) {
            this.carLength = carLength;
        }

        public String getCarLoad() {
            return carLoad;
        }

        public void setCarLoad(String carLoad) {
            this.carLoad = carLoad;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public String getMotCarPlatetypeCode() {
            return motCarPlatetypeCode;
        }

        public void setMotCarPlatetypeCode(String motCarPlatetypeCode) {
            this.motCarPlatetypeCode = motCarPlatetypeCode;
        }

        public String getMotCarClassCode() {
            return motCarClassCode;
        }

        public void setMotCarClassCode(String motCarClassCode) {
            this.motCarClassCode = motCarClassCode;
        }

        public String getCarEnergyType() {
            return carEnergyType;
        }

        public void setCarEnergyType(String carEnergyType) {
            this.carEnergyType = carEnergyType;
        }

        public int getCarIsAffiliated() {
            return carIsAffiliated;
        }

        public void setCarIsAffiliated(int carIsAffiliated) {
            this.carIsAffiliated = carIsAffiliated;
        }

        public String getCarOwnerStatement() {
            return carOwnerStatement;
        }

        public void setCarOwnerStatement(String carOwnerStatement) {
            this.carOwnerStatement = carOwnerStatement;
        }

        public String getCarCompanyStatement() {
            return carCompanyStatement;
        }

        public void setCarCompanyStatement(String carCompanyStatement) {
            this.carCompanyStatement = carCompanyStatement;
        }

        public int getCarStatus() {
            return carStatus;
        }

        public void setCarStatus(int carStatus) {
            this.carStatus = carStatus;
        }

        public String getCarDrivingImage() {
            return carDrivingImage;
        }

        public void setCarDrivingImage(String carDrivingImage) {
            this.carDrivingImage = carDrivingImage;
        }

        public String getCarImage() {
            return carImage;
        }

        public void setCarImage(String carImage) {
            this.carImage = carImage;
        }
    }

    public static class TmsUser{
        private String userStatus;

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }
    }

    public static class TmsUserIdcardBean {
        /**
         * createUserId : null
         * createTime : null
         * updateUserId : null
         * updateTime : null
         * beginTime : null
         * endTime : null
         * version : null
         * userId : 1
         * groupId : 0
         * idcardRealname : 李业虹
         * idcardType : null
         * idcardCode : 222403199407240247
         * idcardFront : /ImageData/id_card/20191218/1_1576639731311.png
         * idcardReverse : null
         * idcardStartDate : null
         * idcardEndDate : null
         * idcardAddress : 吉林省敦化市开发区双胜社区四组
         * idcardIssuedBy : null
         * idcardRace : null
         * idcardSex : null
         * idcardArea : null
         * idcardStatus : 1
         */

        private Object createUserId;
        private Object createTime;
        private Object updateUserId;
        private Object updateTime;
        private Object beginTime;
        private Object endTime;
        private Object version;
        private int userId;
        private int groupId;
        private String idcardRealname;
        private Object idcardType;
        private String idcardCode;
        private String idcardFront;
        private Object idcardReverse;
        private Object idcardStartDate;
        private Object idcardEndDate;
        private String idcardAddress;
        private Object idcardIssuedBy;
        private Object idcardRace;
        private Object idcardSex;
        private Object idcardArea;
        private int idcardStatus;

        public Object getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Object createUserId) {
            this.createUserId = createUserId;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(Object updateUserId) {
            this.updateUserId = updateUserId;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getIdcardRealname() {
            return idcardRealname;
        }

        public void setIdcardRealname(String idcardRealname) {
            this.idcardRealname = idcardRealname;
        }

        public Object getIdcardType() {
            return idcardType;
        }

        public void setIdcardType(Object idcardType) {
            this.idcardType = idcardType;
        }

        public String getIdcardCode() {
            return idcardCode;
        }

        public void setIdcardCode(String idcardCode) {
            this.idcardCode = idcardCode;
        }

        public String getIdcardFront() {
            return idcardFront;
        }

        public void setIdcardFront(String idcardFront) {
            this.idcardFront = idcardFront;
        }

        public Object getIdcardReverse() {
            return idcardReverse;
        }

        public void setIdcardReverse(Object idcardReverse) {
            this.idcardReverse = idcardReverse;
        }

        public Object getIdcardStartDate() {
            return idcardStartDate;
        }

        public void setIdcardStartDate(Object idcardStartDate) {
            this.idcardStartDate = idcardStartDate;
        }

        public Object getIdcardEndDate() {
            return idcardEndDate;
        }

        public void setIdcardEndDate(Object idcardEndDate) {
            this.idcardEndDate = idcardEndDate;
        }

        public String getIdcardAddress() {
            return idcardAddress;
        }

        public void setIdcardAddress(String idcardAddress) {
            this.idcardAddress = idcardAddress;
        }

        public Object getIdcardIssuedBy() {
            return idcardIssuedBy;
        }

        public void setIdcardIssuedBy(Object idcardIssuedBy) {
            this.idcardIssuedBy = idcardIssuedBy;
        }

        public Object getIdcardRace() {
            return idcardRace;
        }

        public void setIdcardRace(Object idcardRace) {
            this.idcardRace = idcardRace;
        }

        public Object getIdcardSex() {
            return idcardSex;
        }

        public void setIdcardSex(Object idcardSex) {
            this.idcardSex = idcardSex;
        }

        public Object getIdcardArea() {
            return idcardArea;
        }

        public void setIdcardArea(Object idcardArea) {
            this.idcardArea = idcardArea;
        }

        public int getIdcardStatus() {
            return idcardStatus;
        }

        public void setIdcardStatus(int idcardStatus) {
            this.idcardStatus = idcardStatus;
        }
    }
}
