package com.wristband.gaoyusheng.base_lib_module;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyusheng on 16/12/7.
 */

public class ApiException extends Exception {

    private final int code;
    private String displayMessage;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;

    /**
     * 请求服务超时（网络通，服务处理超时）
     **/
    public static final int RC_REQUEST_TIMEOUT = -3;
    /**
     * 服务连接超时（网络不通，连接超时）
     **/
    public static final int RC_CONNECT_TIMEOUT = -4;
    /**
     * 服务无响应（网络通，服务无响应）
     **/
    public static final int RC_SERVER_NO_RESPONSE = -5;
    /**
     * 自定义超时
     **/
    public static final int RC_DEFINED_TIMEOUT = -6;
    /**
     * 服务连接失败（找不到服务，但IP通）
     **/
    public static final int RC_HTTP_HTTPHOSTCONNECTEXCEPTION = -7;
    /**
     * 未知主机错误
     **/
    public static final int RC_HTTP_UNKNOWNHOSTEXCEPTION = -8;

    public static final Map<Integer, String> DISPLAY_MAP = new HashMap<Integer, String>() {
        {
            this.put(UNKNOWN, "未知错误。");
            this.put(RC_CONNECT_TIMEOUT, "网络连接超时。");
            this.put(RC_REQUEST_TIMEOUT, "网络请求超时。");
            this.put(PARSE_ERROR, "解析错误。");
            this.put(RC_HTTP_UNKNOWNHOSTEXCEPTION, "服务器维护中。");
        }
    };

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        if (UNKNOWN == code) {
            this.displayMessage = DISPLAY_MAP.get(code) + throwable.getMessage();
        } else {
            this.displayMessage = DISPLAY_MAP.get(code);
        }
        if (StringUtils.isEmpty(this.displayMessage)) {
            this.displayMessage = throwable.getMessage();
        }
    }

    public int getCode() {
        return code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }
}