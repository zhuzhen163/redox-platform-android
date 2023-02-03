package com.redoxyt.platform.bean;

/**
 * 预约列表
 */
public class ReservationBean {

    private String id;
    private String reserveId;//预约ID
    private String orderType;//预约单类型,1:普通订单;2:紧急订单
    private String orderTypeName;//预约单类型名称
    private String reserveType;//预约类型,1:送货预约;2:提货预约
    private String reserveTypeName;//预约类型
    private String reserveCode;//预约码
    private String warehouseGroupId;//仓库机构ID
    private String warehouseId;//仓库ID
    private String groupId;//仓库机构ID
    private String warehouseName;//仓库名称，货主名称
    private String warehouseCode;//仓库代码
    private String warehouseAddress;//仓库详细地址
    private String fleetId;//车队ID
    private String fleetName;//车队名称
    private String transportCode;//运单号
    private String weight;//运量KG
    private String driverId;//司机ID
    private String driverName;//司机名称
    private String driverMobile;//司机手机号
    private String carCode;//车牌号
    private String platformId;//月台ID, 月台号
    private String platformTimezoneId;//预约时段ID
    private String platformName;//月台名称
    private String classHour;//班次小时数
    private String maxClassCars;//班次最大车辆数
    private String reserveStartDate;//预约开始时间
    private String reserveEndDate;//预约结束时间
    private int reserveStatus;//预约状态,0:未开始;1:已支付;2:已报到;3:作业中;4:已完成;5:取消预约
    private String reserveStatusName;//预约状态名称
    private double payAmount;//支付金额
    private String payTime;//支付时间
    private String arriveDatetime;//司机报道时间
    private String workStartDatetime;//开始作业时间
    private String createTime;
    private String reserveDate;
    private String reserveTimeZone;
    private WechatPayOrder wechatPayRecord;
    private String parkingMinutes;//停车时长-分钟
    private int parkPayStatus;//停车费支付状态
    private double parkPayAmount;//停车收费金额
    private String parkPayTime;//停车费支付时间
    private double platformLat;//纬度
    private double platformLon;//经度
    private int aiStatus;//预约方式,0:非AI,1:Ai

    public int getAiStatus() {
        return aiStatus;
    }

    public void setAiStatus(int aiStatus) {
        this.aiStatus = aiStatus;
    }

    public double getPlatformLat() {
        return platformLat;
    }

    public void setPlatformLat(double platformLat) {
        this.platformLat = platformLat;
    }

    public double getPlatformLon() {
        return platformLon;
    }

    public void setPlatformLon(double platformLon) {
        this.platformLon = platformLon;
    }

    public String getParkPayTime() {
        return parkPayTime;
    }

    public void setParkPayTime(String parkPayTime) {
        this.parkPayTime = parkPayTime;
    }

    public String getReserveTypeName() {
        return reserveTypeName;
    }

    public void setReserveTypeName(String reserveTypeName) {
        this.reserveTypeName = reserveTypeName;
    }

    public int getParkPayStatus() {
        return parkPayStatus;
    }

    public void setParkPayStatus(int parkPayStatus) {
        this.parkPayStatus = parkPayStatus;
    }

    public double getParkPayAmount() {
        return parkPayAmount;
    }

    public void setParkPayAmount(double parkPayAmount) {
        this.parkPayAmount = parkPayAmount;
    }

    public String getParkingMinutes() {
        return parkingMinutes;
    }

    public void setParkingMinutes(String parkingMinutes) {
        this.parkingMinutes = parkingMinutes;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public WechatPayOrder getWechatPayRecord() {
        return wechatPayRecord;
    }

    public void setWechatPayRecord(WechatPayOrder wechatPayRecord) {
        this.wechatPayRecord = wechatPayRecord;
    }

    public class WechatPayOrder{
        private String orderAmount;
        private int payStatus;
        private String payStatusName;
        private String createTime;

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getPayStatusName() {
            return payStatusName;
        }

        public void setPayStatusName(String payStatusName) {
            this.payStatusName = payStatusName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getReserveTimeZone() {
        return reserveTimeZone;
    }

    public void setReserveTimeZone(String reserveTimeZone) {
        this.reserveTimeZone = reserveTimeZone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getWorkStartDatetime() {
        return workStartDatetime;
    }

    public void setWorkStartDatetime(String workStartDatetime) {
        this.workStartDatetime = workStartDatetime;
    }

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getReserveType() {
        return reserveType;
    }

    public void setReserveType(String reserveType) {
        this.reserveType = reserveType;
    }

    public String getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(String reserveCode) {
        this.reserveCode = reserveCode;
    }

    public String getWarehouseGroupId() {
        return warehouseGroupId;
    }

    public void setWarehouseGroupId(String warehouseGroupId) {
        this.warehouseGroupId = warehouseGroupId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public String getFleetId() {
        return fleetId;
    }

    public void setFleetId(String fleetId) {
        this.fleetId = fleetId;
    }

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    public String getTransportCode() {
        return transportCode;
    }

    public void setTransportCode(String transportCode) {
        this.transportCode = transportCode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformTimezoneId() {
        return platformTimezoneId;
    }

    public void setPlatformTimezoneId(String platformTimezoneId) {
        this.platformTimezoneId = platformTimezoneId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getClassHour() {
        return classHour;
    }

    public void setClassHour(String classHour) {
        this.classHour = classHour;
    }

    public String getMaxClassCars() {
        return maxClassCars;
    }

    public void setMaxClassCars(String maxClassCars) {
        this.maxClassCars = maxClassCars;
    }

    public String getReserveStartDate() {
        return reserveStartDate;
    }

    public void setReserveStartDate(String reserveStartDate) {
        this.reserveStartDate = reserveStartDate;
    }

    public String getReserveEndDate() {
        return reserveEndDate;
    }

    public void setReserveEndDate(String reserveEndDate) {
        this.reserveEndDate = reserveEndDate;
    }

    public int getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(int reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public String getReserveStatusName() {
        return reserveStatusName;
    }

    public void setReserveStatusName(String reserveStatusName) {
        this.reserveStatusName = reserveStatusName;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getArriveDatetime() {
        return arriveDatetime;
    }

    public void setArriveDatetime(String arriveDatetime) {
        this.arriveDatetime = arriveDatetime;
    }
}
