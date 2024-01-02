package com.wristband.gaoyusheng.base_lib_module;

/**
 * Created by gaoyusheng on 16/11/29.
 */

public class BusinessException extends RuntimeException {
    private String retCd;  //异常对应的返回码
    private String msgDes;  //异常对应的描述信息
    private String requestId; //请求ID

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        msgDes = message;
    }

    public BusinessException(String retCd, String msgDes) {
        super();
        this.retCd = retCd;
        this.msgDes = msgDes;
    }

    public BusinessException(String retCd, String msgDes, String requestId) {
        super();
        this.retCd = retCd;
        this.msgDes = msgDes;
        this.requestId = requestId;
    }

    public String getRetCd() {
        return retCd;
    }

    public String getMsgDes() {
        return msgDes;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "retCd='" + retCd + '\'' +
                ", msgDes='" + msgDes + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
