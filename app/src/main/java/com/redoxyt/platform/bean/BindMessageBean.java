package com.redoxyt.platform.bean;


import java.io.Serializable;

/**
 * 信息认证绑定
 */

public class BindMessageBean implements Serializable{
    private String bindBankName;
    private String bindBankAccount;
    private String bindBankId;
    private String bindBankImg;
    private String bindBankAddress;

    private String carDrivingImage;
    private String drivingLicenseFront;//车头行驶证正页
    private String carIsAffiliated;
    private String driverLicenceImage;
    private String idcardFront;
    private String carImage;
    private String carId;
    private String carOwnerStatement;
    private String carCompanyStatement;

    private String idcardRealname;//车主姓名
    private String idcardCode;//身份证号
    private String idcardAddress;//所属地区
    private String bindBankMobile;//手机号
    private String carEnergyTypeName;
    private String carEnergyType;

    private int trailerId;
    private String ransportQualificationLicenseImg;//道路运输证从业资格证
    private String transportLicenseImg;//道路运输证
    private String driverCertificationImage;

    private String groupCompany;//企业名称
    private String groupTaxCode;//纳税人识别号
    private String businessLicenseImg;//营业执照图片路径
    private String groupAddress;//营业执照地址
    private String groupLegal;//营业执照
    private String groupCapital;//营业执照
    private String groupCreateDate;//营业执照
    private String groupBusinessScope;//营业执照

    private String transportBusinessLicense;//道路运输经营许可证图片
    private String drivingLicenseAttache;//车头行驶证附页图片
    private String trailerLicenseAttached;//挂车行驶证附页图片
    private String trailerLicenseFront;//挂车行驶证正业
    private String vehicleCheckUrl;//入场检查url

    public String getGroupBusinessScope() {
        return groupBusinessScope;
    }

    public void setGroupBusinessScope(String groupBusinessScope) {
        this.groupBusinessScope = groupBusinessScope;
    }

    public String getGroupCreateDate() {
        return groupCreateDate;
    }

    public void setGroupCreateDate(String groupCreateDate) {
        this.groupCreateDate = groupCreateDate;
    }

    public String getGroupCapital() {
        return groupCapital;
    }

    public void setGroupCapital(String groupCapital) {
        this.groupCapital = groupCapital;
    }

    public String getGroupLegal() {
        return groupLegal;
    }

    public void setGroupLegal(String groupLegal) {
        this.groupLegal = groupLegal;
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public String getVehicleCheckUrl() {
        return vehicleCheckUrl;
    }

    public void setVehicleCheckUrl(String vehicleCheckUrl) {
        this.vehicleCheckUrl = vehicleCheckUrl;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getTrailerLicenseFront() {
        return trailerLicenseFront;
    }

    public void setTrailerLicenseFront(String trailerLicenseFront) {
        this.trailerLicenseFront = trailerLicenseFront;
    }

    public String getDrivingLicenseAttache() {
        return drivingLicenseAttache;
    }

    public void setDrivingLicenseAttache(String drivingLicenseAttache) {
        this.drivingLicenseAttache = drivingLicenseAttache;
    }

    public String getTrailerLicenseAttached() {
        return trailerLicenseAttached;
    }

    public void setTrailerLicenseAttached(String trailerLicenseAttached) {
        this.trailerLicenseAttached = trailerLicenseAttached;
    }

    public String getBindBankAddress() {
        return bindBankAddress;
    }

    public void setBindBankAddress(String bindBankAddress) {
        this.bindBankAddress = bindBankAddress;
    }

    public String getDrivingLicenseFront() {
        return drivingLicenseFront;
    }

    public void setDrivingLicenseFront(String drivingLicenseFront) {
        this.drivingLicenseFront = drivingLicenseFront;
    }

    public int getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(int trailerId) {
        this.trailerId = trailerId;
    }

    public String getTransportBusinessLicense() {
        return transportBusinessLicense;
    }

    public void setTransportBusinessLicense(String transportBusinessLicense) {
        this.transportBusinessLicense = transportBusinessLicense;
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

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }

    public String getDriverCertificationImage() {
        return driverCertificationImage;
    }

    public void setDriverCertificationImage(String driverCertificationImage) {
        this.driverCertificationImage = driverCertificationImage;
    }

    /**
     * 挂车行驶证
     * trailerProperty : 货运
     * trailerDrivingRegistDate : 2019-01-15
     * trailerGabarite : 3000X2550X3550mm
     * trailerBrand : 林威级亚牌XCD9400CCY
     * trailerLength :
     * trailerOwner : 张掖市长帆物流有限公司
     * trailerDrivingSsuedBy :
     * trailerIsTrailer : 0
     * trailerTransportLicence :
     * trailerDrivingIssueDate : 2019-01-15
     * trailerOwnerType : 企业
     * motTrailerClassCode : 111
     * motTrailerPlatetypeCode : 1
     * trailerDriverLicence :
     * trailerVin : LA9940C3XK0XCD013
     * trailerWeight : 40000kg
     * trailerLoad : 33200kg
     * trailerDrivingImage : http://redox-test01.oss-cn-qingdao.aliyuncs.com/ImageData/trailer_license/20200327/14_1585279820924.jpg
     * trailerModel :
     * trailerCode : 甘G2272挂
     */

    private String trailerProperty;
    private String trailerDrivingRegistDate;
    private String trailerGabarite;
    private String trailerBrand;
    private String trailerLength;
    private String trailerOwner;
    private String trailerDrivingSsuedBy;
    private int trailerIsTrailer;
    private String trailerTransportLicence;
    private String trailerDrivingIssueDate;
    private String trailerOwnerType;
    private String motTrailerClassCode;
    private String motTrailerPlatetypeCode;
    private String trailerDriverLicence;
    private String trailerVin;
    private String trailerWeight;
    private String trailerLoad;
    private String trailerDrivingImage;
    private String trailerModel;
    private String trailerCode;
    private String motTrailerClassCodeName;//车辆类型
    private String motTrailerPlatetypeCodeName;

    public String getMotTrailerClassCodeName() {
        return motTrailerClassCodeName;
    }

    public void setMotTrailerClassCodeName(String motTrailerClassCodeName) {
        this.motTrailerClassCodeName = motTrailerClassCodeName;
    }

    public String getMotTrailerPlatetypeCodeName() {
        return motTrailerPlatetypeCodeName;
    }

    public void setMotTrailerPlatetypeCodeName(String motTrailerPlatetypeCodeName) {
        this.motTrailerPlatetypeCodeName = motTrailerPlatetypeCodeName;
    }

    public String getTransportLicenseImg() {
        return transportLicenseImg;
    }

    public void setTransportLicenseImg(String transportLicenseImg) {
        this.transportLicenseImg = transportLicenseImg;
    }

    public String getRansportQualificationLicenseImg() {
        return ransportQualificationLicenseImg;
    }

    public void setRansportQualificationLicenseImg(String ransportQualificationLicenseImg) {
        this.ransportQualificationLicenseImg = ransportQualificationLicenseImg;
    }

    public String getCarEnergyTypeName() {
        return carEnergyTypeName;
    }

    public void setCarEnergyTypeName(String carEnergyTypeName) {
        this.carEnergyTypeName = carEnergyTypeName;
    }

    public String getCarEnergyType() {
        return carEnergyType;
    }

    public void setCarEnergyType(String carEnergyType) {
        this.carEnergyType = carEnergyType;
    }

    public String getBindBankMobile() {
        return bindBankMobile;
    }

    public void setBindBankMobile(String bindBankMobile) {
        this.bindBankMobile = bindBankMobile;
    }

    //驾驶证
    private String driverName;//驾驶员姓名
    private String driverLicence;//驾驶证编号
    private String driverModel;//准驾车型
    private String driverStartDate;//驾驶证有效期自
    private String driverEndDate;//驾驶证有效期至
    private String driverCertification;//从业资格证号;

    //行驶证
    private String carCode;//承运车牌号码
    private String carOwner;//所有人
    private String carProperty;//使用性质
    private String carVin;//车辆识别代号
    private String carDrivingRegistDate;//注册日期
    private String carDrivingIssueDate;//发证日期
    private String carDrivingSsuedBy;//发证机关
    private String driverIssuedBy;//发证机关
    private String carWeight;//总质量
    private String carTransportLicence;//道路运输证号
    private String carDrivingLicence;//行驶证号
    private String carModel;//承运车型
    private String carLength;//承运车长
    private String carGabarite;//外扩尺寸
    private String carLoad;//承运载重（核定载质量）
    private String carBrand;//承运车辆品牌
    private String carOwnerType;//车辆所有人类型
    private String motCarClassCode;//车辆类型代码
    private String motCarClassCodeName;//车辆类型
    private int carIsTrailer;//是否上传挂车（0-不上传，1-上传
    private String motCarPlatetypeCode;//车牌分类代码（车牌颜色）
    private String motCarPlatetypeCodeName;//车牌分类（车牌颜色）

    public String getDriverIssuedBy() {
        return driverIssuedBy;
    }

    public void setDriverIssuedBy(String driverIssuedBy) {
        this.driverIssuedBy = driverIssuedBy;
    }

    public String getMotCarClassCodeName() {
        return motCarClassCodeName;
    }

    public void setMotCarClassCodeName(String motCarClassCodeName) {
        this.motCarClassCodeName = motCarClassCodeName;
    }

    public String getMotCarPlatetypeCode() {
        return motCarPlatetypeCode;
    }

    public void setMotCarPlatetypeCode(String motCarPlatetypeCode) {
        this.motCarPlatetypeCode = motCarPlatetypeCode;
    }

    public String getMotCarPlatetypeCodeName() {
        return motCarPlatetypeCodeName;
    }

    public void setMotCarPlatetypeCodeName(String motCarPlatetypeCodeName) {
        this.motCarPlatetypeCodeName = motCarPlatetypeCodeName;
    }

    public String getMotCarClassCode() {
        return motCarClassCode;
    }

    public void setMotCarClassCode(String motCarClassCode) {
        this.motCarClassCode = motCarClassCode;
    }

    public String getCarGabarite() {
        return carGabarite;
    }

    public void setCarGabarite(String carGabarite) {
        this.carGabarite = carGabarite;
    }

    public int getCarIsTrailer() {
        return carIsTrailer;
    }

    public void setCarIsTrailer(int carIsTrailer) {
        this.carIsTrailer = carIsTrailer;
    }
    //    private String tractionMass;//准牵引总质量

//    public String getTractionMass() {
//        return tractionMass;
//    }
//
//    public void setTractionMass(String tractionMass) {
//        this.tractionMass = tractionMass;
//    }

    public String getCarDrivingSsuedBy() {
        return carDrivingSsuedBy;
    }

    public void setCarDrivingSsuedBy(String carDrivingSsuedBy) {
        this.carDrivingSsuedBy = carDrivingSsuedBy;
    }

    public String getBindBankImg() {
        return bindBankImg;
    }

    public void setBindBankImg(String bindBankImg) {
        this.bindBankImg = bindBankImg;
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

    public String getCarDrivingRegistDate() {
        return carDrivingRegistDate;
    }

    public void setCarDrivingRegistDate(String carDrivingRegistDate) {
        this.carDrivingRegistDate = carDrivingRegistDate;
    }

    public String getCarDrivingIssueDate() {
        return carDrivingIssueDate;
    }

    public void setCarDrivingIssueDate(String carDrivingIssueDate) {
        this.carDrivingIssueDate = carDrivingIssueDate;
    }

    public String getCarWeight() {
        return carWeight;
    }

    public void setCarWeight(String carWeight) {
        this.carWeight = carWeight;
    }

    public String getCarTransportLicence() {
        return carTransportLicence;
    }

    public void setCarTransportLicence(String carTransportLicence) {
        this.carTransportLicence = carTransportLicence;
    }

    public String getCarDrivingLicence() {
        return carDrivingLicence;
    }

    public void setCarDrivingLicence(String carDrivingLicence) {
        this.carDrivingLicence = carDrivingLicence;
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

    public String getCarOwnerType() {
        return carOwnerType;
    }

    public void setCarOwnerType(String carOwnerType) {
        this.carOwnerType = carOwnerType;
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

    public String getCarDrivingImage() {
        return carDrivingImage;
    }

    public void setCarDrivingImage(String carDrivingImage) {
        this.carDrivingImage = carDrivingImage;
    }

    public String getCarIsAffiliated() {
        return carIsAffiliated;
    }

    public void setCarIsAffiliated(String carIsAffiliated) {
        this.carIsAffiliated = carIsAffiliated;
    }

    public String getDriverLicenceImage() {
        return driverLicenceImage;
    }

    public void setDriverLicenceImage(String driverLicenceImage) {
        this.driverLicenceImage = driverLicenceImage;
    }

    public String getIdcardFront() {
        return idcardFront;
    }

    public void setIdcardFront(String idcardFront) {
        this.idcardFront = idcardFront;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
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

    public String getBindBankName() {
        return bindBankName;
    }

    public void setBindBankName(String bindBankName) {
        this.bindBankName = bindBankName;
    }

    public String getBindBankAccount() {
        return bindBankAccount;
    }

    public void setBindBankAccount(String bindBankAccount) {
        this.bindBankAccount = bindBankAccount;
    }

    public String getBindBankId() {
        return bindBankId;
    }

    public void setBindBankId(String bindBankId) {
        this.bindBankId = bindBankId;
    }


    public String getTrailerProperty() {
        return trailerProperty;
    }

    public void setTrailerProperty(String trailerProperty) {
        this.trailerProperty = trailerProperty;
    }

    public String getTrailerDrivingRegistDate() {
        return trailerDrivingRegistDate;
    }

    public void setTrailerDrivingRegistDate(String trailerDrivingRegistDate) {
        this.trailerDrivingRegistDate = trailerDrivingRegistDate;
    }

    public String getTrailerGabarite() {
        return trailerGabarite;
    }

    public void setTrailerGabarite(String trailerGabarite) {
        this.trailerGabarite = trailerGabarite;
    }

    public String getTrailerBrand() {
        return trailerBrand;
    }

    public void setTrailerBrand(String trailerBrand) {
        this.trailerBrand = trailerBrand;
    }

    public String getTrailerLength() {
        return trailerLength;
    }

    public void setTrailerLength(String trailerLength) {
        this.trailerLength = trailerLength;
    }

    public String getTrailerOwner() {
        return trailerOwner;
    }

    public void setTrailerOwner(String trailerOwner) {
        this.trailerOwner = trailerOwner;
    }

    public String getTrailerDrivingSsuedBy() {
        return trailerDrivingSsuedBy;
    }

    public void setTrailerDrivingSsuedBy(String trailerDrivingSsuedBy) {
        this.trailerDrivingSsuedBy = trailerDrivingSsuedBy;
    }

    public int getTrailerIsTrailer() {
        return trailerIsTrailer;
    }

    public void setTrailerIsTrailer(int trailerIsTrailer) {
        this.trailerIsTrailer = trailerIsTrailer;
    }

    public String getTrailerTransportLicence() {
        return trailerTransportLicence;
    }

    public void setTrailerTransportLicence(String trailerTransportLicence) {
        this.trailerTransportLicence = trailerTransportLicence;
    }

    public String getTrailerDrivingIssueDate() {
        return trailerDrivingIssueDate;
    }

    public void setTrailerDrivingIssueDate(String trailerDrivingIssueDate) {
        this.trailerDrivingIssueDate = trailerDrivingIssueDate;
    }

    public String getTrailerOwnerType() {
        return trailerOwnerType;
    }

    public void setTrailerOwnerType(String trailerOwnerType) {
        this.trailerOwnerType = trailerOwnerType;
    }

    public String getMotTrailerClassCode() {
        return motTrailerClassCode;
    }

    public void setMotTrailerClassCode(String motTrailerClassCode) {
        this.motTrailerClassCode = motTrailerClassCode;
    }

    public String getMotTrailerPlatetypeCode() {
        return motTrailerPlatetypeCode;
    }

    public void setMotTrailerPlatetypeCode(String motTrailerPlatetypeCode) {
        this.motTrailerPlatetypeCode = motTrailerPlatetypeCode;
    }

    public String getTrailerDriverLicence() {
        return trailerDriverLicence;
    }

    public void setTrailerDriverLicence(String trailerDriverLicence) {
        this.trailerDriverLicence = trailerDriverLicence;
    }

    public String getTrailerVin() {
        return trailerVin;
    }

    public void setTrailerVin(String trailerVin) {
        this.trailerVin = trailerVin;
    }

    public String getTrailerWeight() {
        return trailerWeight;
    }

    public void setTrailerWeight(String trailerWeight) {
        this.trailerWeight = trailerWeight;
    }

    public String getTrailerLoad() {
        return trailerLoad;
    }

    public void setTrailerLoad(String trailerLoad) {
        this.trailerLoad = trailerLoad;
    }

    public String getTrailerDrivingImage() {
        return trailerDrivingImage;
    }

    public void setTrailerDrivingImage(String trailerDrivingImage) {
        this.trailerDrivingImage = trailerDrivingImage;
    }

    public String getTrailerModel() {
        return trailerModel;
    }

    public void setTrailerModel(String trailerModel) {
        this.trailerModel = trailerModel;
    }

    public String getTrailerCode() {
        return trailerCode;
    }

    public void setTrailerCode(String trailerCode) {
        this.trailerCode = trailerCode;
    }
}
