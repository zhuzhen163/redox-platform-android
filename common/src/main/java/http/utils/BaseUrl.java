package http.utils;

/**
 * Created by Sxw
 * on 2019/3/25.
 */

public class BaseUrl {
//uat
//    public static String YT_Base = "http://apiuat.changchangbao.com/";//测试地址
//    public static String YT_BaseH5 = "http://h5uat.changchangbao.com/#/";//测试地址
//    public static String YT_CHART = "http://192.168.31.226:8082/pages/index.html";//测试地址
//dev
//    public static  String YT_Base = "http://apidev.changchangbao.com/";//dev测试地址
//    public static  String YT_BaseH5 = "http://h5dev.changchangbao.com/#/";//debug地址
//    public static  String YT_CHART = "http://192.168.31.226:8082/pages/index.html";//

    //正式
    public static String YT_Base = "http://api.changchangbao.com/";//正式
    public static final String YT_BaseH5 = "http://h5.changchangbao.com/#/";//正式
    public static final String YT_BasePC = "http://pc.changchangbao.com/#/";//正式
    public static final String YT_CHART = "http://www.56yuetai.com/appChart/pages/index.html";

//    public static final String YT_Base = "http://192.168.31.90:59200/";//志伟
//    public static final String YT_BaseH5 = "http://h5uat.changchangbao.com/#/";
//      public static final String YT_Base = "http://192.168.0.132:59200/";//志伟
//      public static final String YT_BaseH5 = "http://h5uat.changchangbao.com/#/";

//    public static  String YT_Base = "http://192.168.31.102:59200/";//鲁光光
//    public static final String YT_BaseH5 = "http://h5uat.changchangbao.com/#/";
//    public static final String YT_CHART = "http://192.168.31.226:8082/pages/index.html";//

//    public static final String YT_Base = "http://192.168.31.172:59200/";//韩宇轩
//    public static final String YT_BaseH5 = "http://h5uat.changchangbao.com/#/";
//        public static final String YT_Base = "http://192.168.0.133:59200/";//韩宇轩
//    public static final String YT_BaseH5 = "http://h5uat.changchangbao.com/#/";

//    public static final String YT_Base = "http://192.168.31.218:59200/";//张雷
//    public static final String YT_BaseH5 = "http://h5uat.changchangbao.com/#/";

//    public static final String Base = "http://192.168.31.52:59200/";
//    public static final String BaseH5 = "http://h5uat.changchangbao.com/#/";
//    public static final String YT_Base = "http://192.168.0.102:59200/";
//    public static final String YT_BaseH5 = "http://h5uat.changchangbao.com/#/";

    public static final String baseImg = "";//服务器返回了全地址
    public static final String version = "v1/";//版本
    public static final String mot = "mot/";//  部委接口里域名加/mot

    /**
     * 隐私协议地址
     */
    public static final String PRIVATE_URL = BaseUrl.YT_BaseH5 + "appPrivacyAgreement?p=厂厂宝月台预约";
    /**
     * 是否弹出过隐私协议
     */
    public static final String IS_SHOW_PRIVATE = "IS_SHOW_PRIVATE";

    public static final String updateApk = YT_Base+"collect/appPackage/";//升级
    public static final String appLocation = YT_Base+"collect/appLocation";//位置上报
    public static final String getContract = YT_Base+"contract/contractInfo/getContract";//获取合同
    public static final String getContractInfo = YT_Base+"contract/contractInfo/getContractInfo";

    public static final String user = "user/api/";//用户列表域名后加/user
    public static final String userRegister = user + version + "pltfRegister/userRegister";//司机
    public static final String fleetRegister = user + version + "pltfRegister/fleetRegister";//车队注册
    public static final String myInfo = user + version + "pltfUser/myInfo";//我的信息
    public static final String findUserAuth = user + version + "pltfUser/findUserAuth";//身份认证查询
    public static final String userAuth = user + version + "pltfUser/userAuth";//身份认证/添加车辆
    public static final String myCarList = user + version + "pltfUser/myCarList";//我的车辆列表
    public static final String selectByGroupId = user + version + "tmsGroup/selectByGroupId";//根据车队Id查询车队名称
    public static final String getUpdateMyInfoFleet = user + version + "pltfUser/getUpdateMyInfoFleet";//车队修改个人信息回显
    public static final String updateMyInfoFleet = user + version + "pltfRegister/updateMyInfoFleet";//车队修改个人信息
    public static final String loginSuccessDatas = user + version + "pltfUser/loginSuccessDatas";//用户登陆成功获取需要的数据

    public static final String findCarOwnerAuth = user + version + "register/findCarOwnerAuth";//查询认证信息
    public static final String carOwnerAuth = user + version + "register/carOwnerAuth";//完成信息认证
    public static final String carOwnerByPassword = "auth/oauth/token";// 刷新token
    public static final String updateUserPayPwd = user+version + "tmsUser/updateUserPayPwd";//支付密码
    public static final String updateUserMobile = user+version + "tmsUser/updateUserMobile";//更改手机号
    public static final String updateUserLoginPwd = user + version + "tmsUser/updateUserLoginPwd";//修改登录密码
    public static final String faceid = user + version + "tmsUser/get/faceid";//人脸识别
    public static final String bankCard2C = user + version + "tmsUserBindBanks/bankCard2C";//银行卡两要素验证
    public static final String bankCard4C = user + version + "tmsUserBindBanks/bankCard4C";//银行卡四要素验证
    public static final String register = user + version + "register/carOwner";//注册
    public static final String tmsUserBindBanks = user + version + "tmsUserBindBanks/findByUserId";//根据用户ID查询银行卡列表
    public static final String del =user+version + "tmsUserBindBanks/del";//解绑银行卡
    public static final String findMyInfo = user + version + "tmsUser/findMyInfo";//获取用户信息
    public static final String tmsUserBindBanksSave = user + version + "tmsUserBindBanks/save";//添加银行卡
    public static final String banks = user + version + "cascade/banks";//银行卡
    public static final String savePayee = user + version + "tmsUserBindBanks/savePayee";//新增收款人
    public static final String payeeList = user + version + "tmsUserBindBanks/payeeList";//查询收款人
    public static final String carEnergyType = user + version + "cascade/carEnergyType";//能源类型下拉
    public static final String carClassType = user + version + "cascade/carClassType";//车辆类型
    public static final String carPlatetype = user + version + "cascade/carPlatetype";//车牌类型
    public static final String createTeam = user + version + "tmsGroup/createTeam";//创建车队
    public static final String getMyTeam = user + version + "tmsGroup/getMyTeam";//获取我的车队
    public static final String createInviteCode = user + version + "tmsGroup/createInviteCode";//重新获取邀请码
    public static final String tmsUserCarSave = user + version + "tmsUserCar/save";//添加车辆
    public static final String getMyDrivers = user + version + "tmsUser/getMyDrivers";//我的司机
    public static final String delDriver = user + version + "tmsUser/delDriver";//解绑司机
    public static final String joinTeam = user + version + "tmsGroup/joinTeam";//加入车队
    public static final String getMyCars = user + version + "tmsUserCar/getMyCars";//我的车辆
    public static final String tmsUserCarDelete = user + version + "tmsUserCar/delete";//解绑车辆
    public static final String tmsUserCarChange = user + version + "tmsUserCar/change";//切换车辆
    public static final String tmsUserCarDetails = user + version + "tmsUserCar/getDetails";//车辆详情
    public static final String findAgreementNeeded = user + version + "tmsUser/findAgreementNeeded";//获取合同信息
    public static final String dismissTeam = user + version + "tmsGroup/dismissTeam";//
    public static final String isDismissTeam = user + version + "tmsGroup/isDismissTeam";//
    public static final String matchCar = user + version + "user/transportCapacity/matchCar";//运力选择
    public static final String updatePayWay = user + version + "tmsUserDriver/updatePayWay";//设置收款方式
    public static final String queryPayWay = user + version + "tmsUserDriver/queryPayWay";//查询收款方式
    public static final String setDefaultBank = user + version + "tmsUserBindBanks/setDefaultBank";//设置默认银行卡
    public static final String getLoginConfig = user + version + "systemConfig/getLoginConfig";// 获取一键登录配置
    public static final String isFrozen = user + version + "tmsGroup/isFrozen";// 客服是否可以生成二维码
    public static final String GETGROUPBYGROUPNUM = user + version + "pltfGroup/getGroupByGroupNum";//企业号查询企业信息

    public static final String auth = "auth/api/";//    认证列表里的接口域名后加/auth
    public static final String carOwnerByCode =auth+version + "mobile/token";//验证码登录
    public static final String getCode = auth + version + "sms/code";//获取验证码
    public static final String getSmsCode = auth + version + "sms/smsCode";//获取验证码
    public static final String imgCode = auth + version + "imgVerify/imgCode";//获取图形验证码
    public static final String passwordLogin = auth + version + "username/token";// 密码登录
    public static final String flashLogin = auth + version + "flashLogin/token";// 一键登录

    public static final String order = "order/api/";//订单列表  运单
    public static final String pub = order + version + "pub/upload/upLoadImg";//图片上传识别
    public static final String upLoadImgNoToken = order + version + "pub/upload/upLoadImgNoToken";//图片上传识别
    public static final String myFinishedShipping = order + version + "tmsShipping/get/myFinishedShipping";//已完成运单
    public static final String sendCar = order + version + "tmsShipping/sendCar";//确认接单
    public static final String truckingInfo = order + version + "trucking/info";//获取与派车单信息
    public static final String shippingMobileAdd = order + version + "shipping/mobileAdd";//输入手机号派发运单
    public static final String scanAdd = order + version + "shipping/scanAdd";//扫码确认接单
    public static final String shippingConfirm = order + version + "shipping/confirm";//派单列表确认接单
    public static final String shippingInfo = order + version + "shipping/info";//获取运单详细信息/提货回显
    public static final String shippingConfirmInfo = order + version + "shipping/shippingConfirmInfo";//确认接单详情
    public static final String shippingCancel = order + version + "shipping/cancel";//放弃接单
    public static final String ownerCodes = order + version + "trucking/ownerCodes";//订单运单
    public static final String runList = order + version + "shipping/runList";//在途运单列表
    public static final String runShippingList = order + version + "shipping/runShippingList";//我的运单在途运单列表
    public static final String finishedList = order + version + "shipping/finishedList";//已完成运单列表
    public static final String receivedList = order + version + "shipping/receivedList";//司机接到的运单
    public static final String shippingReceipt = order + version + "shippingReceipt/sign";//提货/卸货签到
    public static final String shippingReceiptBill = order + version + "shippingReceipt/bill";//上传提货单完成提货
    public static final String shippingList = order + version + "shipping/list";//运单列表
    public static final String isReceivedShippings = order + version + "shipping/isReceivedShippings";//客服派单记录
    public static final String upLoadImg = order + version + "pub/upload/img";//APP上传提货签收榜单图及现场图
    public static final String onshipping = order + version + "tmsShipping/get/onshipping";//APP上传提货签收榜单图及现场图
    public static final String complaintSave = order + version + "complaint/save";//
    public static final String myComplaint = order + version + "complaint/myComplaint";//
    public static final String getStatusByCarCode = order + version + "shipping/getStatusByCarCode";//判断切换车辆时该车辆运单状态
    public static final String findIsFinishedByUserId = order + version + "shipping/findIsFinishedByUserId";//判断解绑司机时司机运单状态
    public static final String getComplainClass = order + version + "complaint/getComplainClass";//投诉类型级联下拉框
    public static final String appraise = order + version + "complaint/appraise";//处理评价（满意不满意）
    public static final String toBeConfirmed = order + version + "shipping/toBeConfirmed";//待核销
    public static final String driverConfirm = order + version + "shipping/driverConfirm";//司机确认金额成功/异议

    public static final String pay = "pay/api/";//提现，转账
    public static final String wechatPayRecord = pay + version + "wechatPayRecord/paying";//调起微信支付后，修改支付状态为支付中
    public static final String wechatPayRecordPolling = pay + version + "wechatPayRecord/polling";//轮询支付结果


    public static final String driverBalance = pay + version + "virtualAccount/driverBalance";//查询司机账户余额
    public static final String execute = pay + version + "withdraw/execute";//提现
    public static final String financeListApp = pay + version + "finance/listApp";//我的账单
    public static final String queryFlowList = pay + version + "pay/queryFlowList";//查询银行支付资金流水单


    public static final String card = "card/api/";//油气卡
    public static final String selectByUserId = card + version + "tmsUserCard/selectByUserId";//卡包列表
    public static final String getMyCardRecord = card + version + "tmsCardRecord/getMyCardRecord";//卡详情
    public static final String recordConsumption = card + version + "card/recordConsumptionNoLogin";//点击消费
    public static final String selectStatus = card + version + "tmsGroupCard/selectStatus";//查询油气卡开关和下拉


    public static final String platform = "platform/api/";//月台
    public static final String bannerList = platform + version + "item/bannerList";//banner图
    public static final String litsByWarehouseCode = platform + version + "platform/litsByWarehouseCode";//根据仓库代码查询有效月台集合
    public static final String getByReserveList = platform + version + "platform/reservation/list";//根据预约码查询预约信息
    public static final String getByReserveCode = platform + version + "platform/reservation/getByReserveCode";//库管员扫码查询预约信息
    public static final String executeJob = platform + version + "platform/reservation/executeJob";//库管员执行作业
    public static final String getReserveDateTimezone = platform + version + "pltfPlatformRule/getReserveDateTimezone";//根据月台Id获取可预约日期
    public static final String getTimezones = platform + version + "pltfPlatformRule/getTimezones";//根据月台Id、车队Id、指定日期查询预约时间段
    public static final String createReservation = platform + version + "platform/reservation/createReservation";//新建预约
    public static final String listByReserveCode = platform + version + "warehouse/rule/listByReserveCode/";//根据预约码查询入场检查列表
    public static final String driverReport = platform + version + "platform/reservation/driverReport";//司机是否满足报道(预约时间、距离)
    public static final String cancelReservation = platform + version + "platform/reservation/cancelReservation";//根据预约Id修改报道状态、
    public static final String ruleCommit = platform + version + "warehouse/rule/commit";//提交入场检查
    public static final String countDownQueueNum = platform + version + "platform/reservation/countDownQueueNum";//倒计时、排队车数
    public static final String getBydriverPhone = platform + version + "black/getBydriverPhone";//根据手机号查询是否在黑名单
    public static final String platformPay = platform + version + "platform/reservation/platformPay";//支付统一下单接口
    public static final String parkingFeePay = platform + version + "platform/reservation/parkingFeePay";//停车费下单
    public static final String getGroupPlatformOwn = platform + version + "platform/reservation/getGroupPlatformOwn";//查询司机预约的仓库是否允许司机自己填入场检查
    public static final String listCheckRecord = platform + version + "warehouse/rule/listCheckRecord";//查询入场检查记录列表
    public static final String getListByFleetId = platform + version + "PltfFleetWarehouse/getListByFleetId";//车队查询常用仓库
    public static final String savePltfFleetWarehouse = platform + version + "PltfFleetWarehouse/savePltfFleetWarehouse";//车队新增常用仓库
    public static final String deletePltfFleetWarehouseById = platform + version + "PltfFleetWarehouse/deletePltfFleetWarehouseById";//车队删除常用仓库
    public static final String selectByWarehouseCode = platform + version + "warehouse/selectByWarehouseCode";//仓库代码查询仓库信息
    public static final String listWarehouse = platform + version + "warehouse/listWarehouse";//根据用户查询仓库列表
    public static final String getPlatformLists = platform + version + "platform/getDownListPlatforms";//月台列表信息下拉列表
    public static final String managementEfficiencyboard = platform + version + "platform/reservation/managementEfficiencyboard";//效率看板
    public static final String reservationHistory = platform + version + "platform/reservation/reservationHistory";//统计历史预约数据
    public static final String trackingKanBan = platform + version + "platform/reservation/trackingKanBan";//看板
    public static final String countByReserveType = platform + version + "platform/reservation/countByReserveType";//仓库出入库预约统计
    public static final String reservationIndex = platform + version + "platform/reservation/reservationIndex";//管理员预约首页查询
    public static final String managementBillboard = platform + version + "platform/reservation/managementBillboard";//管理看板查询
    public static final String warehouseBillboard = platform + version + "platform/reservation/warehouseBillboard";//管理看板仓库查询
    public static final String managementTrackingKanBan = platform + version + "platform/reservation/managementTrackingKanBan";//管理看板仓库查询
    public static final String managementTrackingKanBanInfo = platform + version + "platform/reservation/managementTrackingKanBanInfo";//管理看板仓库查询
    public static final String ADDMUCHWAREHOUSERESERVE = platform + version + "muchWarehouse/addMuchWarehouseReserve";//新建预约列表
    public static final String noOnTimeArrive = platform + version + "platform/reservation/noOnTimeArrive";//无法按时到场
    public static final String GETMUCHWAREHOUSERESERVELIST = platform + version + "muchWarehouse/getMuchWarehouseReserveList";//查询多仓预约列表
    public static final String MUCHWAREHOUSEONNEXTSTEP = platform + version + "muchWarehouse/muchWarehouseOnNextStep";//点击下一步,到Ai预约
    public static final String remainMuchWarehouse = platform + version + "muchWarehouse/remainMuchWarehouse";//多仓完成订单,操作剩余仓库
    public static final String DELETEMUCHWAREHOUSERESERVE = platform + version + "muchWarehouse/deleteMuchWarehouseReserve";//确定取消，删除计划

    public static String LoadImgUrl;

    public static int TO_MAIN_MYRESERVATIONFRAGMENT = 0;

}
