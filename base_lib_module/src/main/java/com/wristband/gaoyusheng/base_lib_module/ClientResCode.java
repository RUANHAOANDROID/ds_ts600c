package com.wristband.gaoyusheng.base_lib_module;

import java.util.HashMap;
import java.util.Map;

public class ClientResCode {
    ////////////////////////////////////////////////////////////
    /////////// 共同异常 //////////////////////////////////////////
    /**
     * 业务处理成功
     **/
    public static final String RC_SUCCESS = "0200";
    /**
     * 已经入住
     **/
    public static final String RC_IS_CHECK_IN = "1022004";
    /**
     * 手机验证码失效code
     **/
    public static final String RC_INVALID_CODE = "1020007";
    /**
     * 手机号已绑定
     **/
    public static final String RC_RPC_EXCEPTION_HZG_MOBILE_BIND = "1020010";
    /**
     * 系统处理异常，内部错误
     **/
    public static final String RC_SERVER_INTERNAL_ERROR = "-1";
    /**
     * 请求参数错误
     **/
    public static final String RC_PARAMS_ERROR = "-2";
    /**
     * 请求服务超时（网络通，服务处理超时）
     **/
    public static final String RC_REQUEST_TIMEOUT = "-3";
    /**
     * 服务连接超时（网络不通，连接超时）
     **/
    public static final String RC_CONNECT_TIMEOUT = "-4";
    /**
     * 服务无响应（网络通，服务无响应）
     **/
    public static final String RC_SERVER_NO_RESPONSE = "-5";
    /**
     * 自定义超时
     **/
    public static final String RC_DEFINED_TIMEOUT = "-6";
    /**
     * 服务连接失败（找不到服务，但IP通）
     **/
    public static final String RC_HTTP_HTTPHOSTCONNECTEXCEPTION = "-7";
    /**
     * 未知主机错误
     **/
    public static final String RC_HTTP_UNKNOWNHOSTEXCEPTION = "-8";
    /**
     * 请求签名错误
     **/
    public static final String RC_PARAMS_SIGN_ERROR = "-9";
    /**
     * 其他网络异常
     **/
    public static final String RC_OTHER_NETWORK_ERROR = "-99";
    ////////////////////////////////////////////////////////////

    /**
     * 设备未绑定
     **/
    public static final String RC_CLIENT_DEVICE_UNBIND = "0433";

    /**
     * 请刷员工卡，谢谢
     **/
    public static final String RC_RPC_EXCEPTION_LOGINBYCARD = "1022025";

    // 卡机：1024
    /**
     * 吐卡失败，请联系前台处理
     **/
    public static final String RC_RPC_EXCEPTION_AUTOCARD_SPITCARD = "1024006";
    /**
     * 收卡失败，请联系前台处理
     **/
    public static final String RC_RPC_EXCEPTION_AUTOCARD_TAKECARD = "1024007";
    /**
     * 卡机复位返回false
     **/
    public static final String RC_RPC_EXCEPTION_AUTOCARD_RESET_FALSE = "1024009";
    /**
     * 卡机金硅离店 空 或 其他异常
     **/
    public static final String RC_RPC_EXCEPTION_AUTOCARD_CHECKOUT = "1024010";
    /**
     * 制卡成功后吐卡失败
     **/
    public static final String RC_RPC_EXCEPTION_AUTOCARD_MOVE_CARD_TO_HOLDER_FALSE = "1024011";
    /**
     * 卡机连接失败
     */
    public static final String CARD_MACHINE_CONNECT_FAILED = "1024012";

    // 1026开头 3.0 PAD错误
    /**
     * 智能门锁打开失败
     */
    public static final String RC_RPC_EXCEPTION_SMART_LOCK_OPEN_FAILED = "102601";
    /**
     * 智能客控打开失败
     */
    public static final String RC_RPC_EXCEPTION_CUSTOMER_CONTROL_OPEN_FAILED = "102602";
    /**
     * POS通信失败
     */
    public static final String RC_RPC_EXCEPTION_POS_TRANSFER_FAILED = "102603";

    // 内部传输
    public static final Map<String, String> RC_CODE_MAP = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            // 共同
            this.put(RC_SUCCESS, "业务处理成功");
            this.put(RC_SERVER_INTERNAL_ERROR, "系统繁忙，请稍后再试（系统处理异常，内部错误）");
            this.put(RC_PARAMS_ERROR, "请求参数错误");
            this.put(RC_PARAMS_SIGN_ERROR, "请求签名错误");
            this.put(RC_REQUEST_TIMEOUT, "请求服务超时（网络通，服务处理超时）");
            this.put(RC_CONNECT_TIMEOUT, "服务连接超时（网络不通，连接超时）");
            this.put(RC_SERVER_NO_RESPONSE, "服务无响应（网络通，服务无响应）");
            this.put(RC_DEFINED_TIMEOUT, "自定义超时（例：定义5秒超时）");
            this.put(RC_OTHER_NETWORK_ERROR, "其他网络异常");
            this.put(RC_HTTP_HTTPHOSTCONNECTEXCEPTION, "服务连接失败（找不到服务，但IP通）");
            this.put(RC_HTTP_UNKNOWNHOSTEXCEPTION, "未知服务主机错误（如IP错误）");

            // 卡机
            this.put(RC_RPC_EXCEPTION_AUTOCARD_SPITCARD, "吐卡异常，请联系前台处理");
            this.put(RC_RPC_EXCEPTION_AUTOCARD_TAKECARD, "收卡异常，请联系前台处理");
            this.put(RC_RPC_EXCEPTION_AUTOCARD_RESET_FALSE, "卡机复位返回false");
            this.put(RC_RPC_EXCEPTION_AUTOCARD_CHECKOUT, "金硅公安离店异常，请联系前台处理");
            this.put(RC_RPC_EXCEPTION_AUTOCARD_MOVE_CARD_TO_HOLDER_FALSE, "制卡成功后吐卡失败");
            this.put(CARD_MACHINE_CONNECT_FAILED, "卡机连接失败");

            // 驱动
            this.put(AUTOCARD_ERRORID_PARAMS_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_OPENSERI_ERROR, "设备未连接，请检查通讯线是否正常");
            this.put(AUTOCARD_ERRORID_SENDNOCARD_ERROR, "卡槽无卡，请联系前台处理");
            this.put(AUTOCARD_ERRORID_CARDFULL_ERROR, "收卡槽满卡，请联系前台处理");
            this.put(AUTOCARD_ERRORID_OPENWIRELESS_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_FINDCARD_NOCARD_ERROR, "未知卡片，请联系前台处理");
            this.put(AUTOCARD_ERRORID_CONFILICT_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_FINDCARD_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_LOADPASS_ERROR, "加载密码失败，请联系IT配置酒店门锁类型");
            this.put(AUTOCARD_ERRORID_CHECKPASS_ERROR, "验证密码失败，请联系IT配置酒店门锁类型");
            this.put(AUTOCARD_ERRORID_READCARD_ERROR, "读卡失败，请联系前台处理");
            this.put(AUTOCARD_ERRORID_WRITECARD_ERROR, "写卡失败，请联系前台处理");
            this.put(AUTOCARD_ERRORID_MOVECARD_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_OPERTIMEOUT_ERROR, "请检查发卡槽房卡放置是否正确");
            this.put(AUTOCARD_ERRORID_NOTCONNECTED_ERROR, "设备未连接，请检查通讯线是否正常");
            this.put(AUTOCARD_ERRORID_SENDCARDHADCARD_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_SPITCARDNOCARD_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_TAKECARDNOCARD_ERROR, "读取房卡信息失败，请联系前台处理");
            this.put(AUTOCARD_ERRORID_TAKECARD_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_SENDCARD_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_CHECKERROR_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_GET_LOCK_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_RESET_ERROR, "卡机复位失败");
            this.put(AUTOCARD_ERRORID_PARSE_CARD_ERROR, "房卡信息异常，请联系前台处理");
            this.put(AUTOCARD_ERRORID_LOCKTYPEFORMAT_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_ROOMNOFORMAT_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_BATCHNOFORMAT_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_WATERNOFORMAT_ERROR, "卡机错误，请联系前台处理");
            this.put(AUTOCARD_ERRORID_INNATEFORMAT_ERROR, "卡机错误，请联系前台处理");

            // 公安直连
            this.put(UNKNOW_ERROR, "公安直连错误，未知错误");
            this.put(UPLOAD_FAILED_WITH_REASON, "公安直连错误，上传失败");
            this.put(NO_UPLOAD_ADDRESS, "公安直连错误，缺少上传地址");
            this.put(NO_SIGN_KEY, "公安直连错误，缺少sign key");
            this.put(CHECK_FAILED, "公安直连错误，前台人证合一验证失败");

            // 智能门锁
            this.put(RC_RPC_EXCEPTION_SMART_LOCK_OPEN_FAILED, "智能门锁打开失败");

            // pos
            this.put(RC_RPC_EXCEPTION_POS_TRANSFER_FAILED, "POS连接失败");
        }
    };

    /**
     * @Title: getErrorLogResMsg @Description: 获取错误码对应的错误信息 @param code @return
     * String @throws
     */
    public static String getErrorLogResMsg(String code) {
        return RC_CODE_MAP.get(code);
    }

    /**
     * 卡机错误，参数无效
     **/
    public static final String AUTOCARD_ERRORID_PARAMS_ERROR = "1030001";
    /**
     * 卡机错误，打卡串口失败
     **/
    public static final String AUTOCARD_ERRORID_OPENSERI_ERROR = "1030002";
    /**
     * 卡机错误，发卡槽无卡
     **/
    public static final String AUTOCARD_ERRORID_SENDNOCARD_ERROR = "1030003";
    /**
     * 卡机错误，收卡槽已满
     **/
    public static final String AUTOCARD_ERRORID_CARDFULL_ERROR = "1030004";
    /**
     * 卡机错误，打开天线失败
     **/
    public static final String AUTOCARD_ERRORID_OPENWIRELESS_ERROR = "1030005";
    /**
     * 卡机错误，寻卡失败，读头位置无卡
     **/
    public static final String AUTOCARD_ERRORID_FINDCARD_NOCARD_ERROR = "1030006";
    /**
     * 卡机错误，防冲突失败
     **/
    public static final String AUTOCARD_ERRORID_CONFILICT_ERROR = "1030007";
    /**
     * 卡机错误，寻卡失败
     **/
    public static final String AUTOCARD_ERRORID_FINDCARD_ERROR = "1030008";
    /**
     * 卡机错误，加载密码失败
     **/
    public static final String AUTOCARD_ERRORID_LOADPASS_ERROR = "1030009";
    /**
     * 卡机错误，验证密码失败
     **/
    public static final String AUTOCARD_ERRORID_CHECKPASS_ERROR = "1030010";
    /**
     * 卡机错误，读卡失败
     **/
    public static final String AUTOCARD_ERRORID_READCARD_ERROR = "1030011";
    /**
     * 卡机错误，写卡失败
     **/
    public static final String AUTOCARD_ERRORID_WRITECARD_ERROR = "1030012";
    /**
     * 卡机错误，移动卡失败
     **/
    public static final String AUTOCARD_ERRORID_MOVECARD_ERROR = "1030013";
    /**
     * 卡机错误，操作超时
     **/
    public static final String AUTOCARD_ERRORID_OPERTIMEOUT_ERROR = "1030014";
    /**
     * 卡机错误，设备未连接
     **/
    public static final String AUTOCARD_ERRORID_NOTCONNECTED_ERROR = "1030015";
    /**
     * 卡机错误，发卡时卡嘴位置有卡
     **/
    public static final String AUTOCARD_ERRORID_SENDCARDHADCARD_ERROR = "1030016";
    /**
     * 卡机错误，吐卡时读头位置无卡
     **/
    public static final String AUTOCARD_ERRORID_SPITCARDNOCARD_ERROR = "1030017";
    /**
     * 卡机错误，收卡时卡嘴或读头位置无卡
     **/
    public static final String AUTOCARD_ERRORID_TAKECARDNOCARD_ERROR = "1030018";
    /**
     * 卡机错误，收卡时出错
     **/
    public static final String AUTOCARD_ERRORID_TAKECARD_ERROR = "1030019";
    /**
     * 卡机错误，发卡时出错
     **/
    public static final String AUTOCARD_ERRORID_SENDCARD_ERROR = "1030020";
    /**
     * 卡机错误，身份验证未通过
     **/
    public static final String AUTOCARD_ERRORID_CHECKERROR_ERROR = "1030021";
    /**
     * 卡机错误，门锁类型转换失败
     **/
    public static final String AUTOCARD_ERRORID_LOCKTYPEFORMAT_ERROR = "1030022";
    /**
     * 卡机错误，房间号类型转换失败
     **/
    public static final String AUTOCARD_ERRORID_ROOMNOFORMAT_ERROR = "1030023";
    /**
     * 卡机错误，批次号类型转换失败
     **/
    public static final String AUTOCARD_ERRORID_BATCHNOFORMAT_ERROR = "1030024";
    /**
     * 卡机错误，流水号类型转换失败
     **/
    public static final String AUTOCARD_ERRORID_WATERNOFORMAT_ERROR = "1030025";
    /**
     * 卡机错误，同住类型转换失败
     **/
    public static final String AUTOCARD_ERRORID_INNATEFORMAT_ERROR = "1030026";
    /**
     * 卡机错误，获取锁失败
     **/
    public static final String AUTOCARD_ERRORID_GET_LOCK_ERROR = "1030027";
    /**
     * 卡机错误，卡机复位失败
     **/
    public static final String AUTOCARD_ERRORID_RESET_ERROR = "1030028";
    /**
     * 卡机错误，卡机解析卡信息失败
     **/
    public static final String AUTOCARD_ERRORID_PARSE_CARD_ERROR = "1030029";

    // 上传公安接口异常码
    // 未知错误
    public static final String UNKNOW_ERROR = "3998";
    // 上传失败
    public static final String UPLOAD_FAILED_WITH_REASON = "4007";
    // 缺少上传地址
    public static final String NO_UPLOAD_ADDRESS = "5000";
    // 缺少key
    public static final String NO_SIGN_KEY = "5001";
    // 前台人证合一验证失败
    public static final String CHECK_FAILED = "5002";

    public static final String ALREADY_CHECK_OUT_CODE = "0411";

    public static final Map<String, String> RC_AUTOCARD_CODE_MAP = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            // 设备错误码及对应的错误描述
            this.put("0", RC_SUCCESS);
            this.put("1", AUTOCARD_ERRORID_PARAMS_ERROR);
            this.put("2", AUTOCARD_ERRORID_OPENSERI_ERROR);
            this.put("3", AUTOCARD_ERRORID_SENDNOCARD_ERROR);
            this.put("4", AUTOCARD_ERRORID_CARDFULL_ERROR);
            this.put("5", AUTOCARD_ERRORID_OPENWIRELESS_ERROR);
            this.put("6", AUTOCARD_ERRORID_FINDCARD_NOCARD_ERROR);
            this.put("7", AUTOCARD_ERRORID_CONFILICT_ERROR);
            this.put("8", AUTOCARD_ERRORID_FINDCARD_ERROR);
            this.put("9", AUTOCARD_ERRORID_LOADPASS_ERROR);
            this.put("10", AUTOCARD_ERRORID_CHECKPASS_ERROR);
            this.put("11", AUTOCARD_ERRORID_READCARD_ERROR);
            this.put("12", AUTOCARD_ERRORID_WRITECARD_ERROR);
            this.put("13", AUTOCARD_ERRORID_MOVECARD_ERROR);
            this.put("14", AUTOCARD_ERRORID_OPERTIMEOUT_ERROR);
            this.put("15", AUTOCARD_ERRORID_NOTCONNECTED_ERROR);
            this.put("16", AUTOCARD_ERRORID_SENDCARDHADCARD_ERROR);
            this.put("17", AUTOCARD_ERRORID_SPITCARDNOCARD_ERROR);
            this.put("18", AUTOCARD_ERRORID_TAKECARDNOCARD_ERROR);
            this.put("19", AUTOCARD_ERRORID_TAKECARD_ERROR);
            this.put("20", AUTOCARD_ERRORID_SENDCARD_ERROR);
            this.put("21", AUTOCARD_ERRORID_RESET_ERROR);
            this.put("22", AUTOCARD_ERRORID_PARSE_CARD_ERROR);
            this.put("-100", AUTOCARD_ERRORID_CHECKERROR_ERROR);
            this.put("-101", AUTOCARD_ERRORID_GET_LOCK_ERROR);
            this.put("-98", AUTOCARD_ERRORID_LOCKTYPEFORMAT_ERROR);
            this.put("-97", AUTOCARD_ERRORID_ROOMNOFORMAT_ERROR);
            this.put("-96", AUTOCARD_ERRORID_BATCHNOFORMAT_ERROR);
            this.put("-95", AUTOCARD_ERRORID_WATERNOFORMAT_ERROR);
            this.put("-94", AUTOCARD_ERRORID_INNATEFORMAT_ERROR);
        }
    };
}