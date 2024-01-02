package com.wristband.gaoyusheng.base_lib_module.card_machine;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wristband.gaoyusheng.base_lib_module.ClientResCode.RC_SUCCESS;

/**
 * Created by gaoyusheng on 17/2/10.
 */

public class CardDriverResponse {
    public static String SUCCESS = "0200";

    // 卡机卡嘴有卡（传感器1有卡）
    public static String MOUTH_HAS_CARD = "8000";
    // 读卡位置有卡（传感器1和2有卡）
    public static String READ_CARD_POSITION_HAS_CARD = "8001";
    // 收卡箱满
    public static String SEND_CARD_BOX_FULL = "8002";
    // 发卡槽已空
    public static String SEND_CARD_BOX_EMPTY = "8003";
    // 堵塞卡
    public static String JAM_CARD = "8004";
    // 重叠卡
    public static String OVERLAP_CARD = "8005";
    // 回收箱满
    public static String RECEIVE_CARD_BOX_FULL = "8006";
    // 卡机连接失败
    public static String CONNECT_FAILED = "8007";
    // 发卡失败
    public static String SEND_CARD_FAILED = "8008";
    // 发卡失败 寻卡失败
    public static String SEND_CARD_FAILED_FIND_CARD = "8018";
    // 发卡失败 发卡超时
    public static String SEND_CARD_FAILED_SEND_CARD_OUTTIME = "8028";
    // 发卡失败 读取序列号失败
    public static String SEND_CARD_FAILED_READ_SERIAL_NUMBER = "8038";
    // 发卡失败 ACK验证错误
    public static String SEND_CARD_FAILED_ACK_CONFIRM = "8048";
    // 发卡失败 命令数据错误
    public static String SEND_CARD_FAILED_DATA_ERROR = "8058";



    // COM40520打开失败
    public static String COM_40520_OPEN_FAILED = "8009";
    // 卡机通信失败
    public static String SERIAL_PORT_CONNECT_FAILED = "8010";
    // 卡槽预空
    public static String SEND_CARD_BOX_PRE_EMPTY = "8011";
    // 密码验证失败
    public static String WRONG_PASSWORD = "67";

    public static final Map<String, String> CARD_MACHINE_CODE_MSG = new HashMap<String, String>() {
        {
            // 设备错误码及对应的错误描述
            this.put(SUCCESS, RC_SUCCESS);
            this.put(MOUTH_HAS_CARD, "卡机卡嘴有卡");
            this.put(READ_CARD_POSITION_HAS_CARD, "读卡位置有卡");
            this.put(SEND_CARD_BOX_FULL, "发卡槽已满");
            this.put(JAM_CARD, "塞卡");
            this.put(OVERLAP_CARD, "重叠卡");
            this.put(RECEIVE_CARD_BOX_FULL, "收卡槽已满");
            this.put(SEND_CARD_BOX_EMPTY, "发卡槽空卡");
            this.put(CONNECT_FAILED, "卡机连接失败");
            this.put(SEND_CARD_FAILED, "发卡失败");
            this.put(WRONG_PASSWORD, "验证密码错误");
            this.put(COM_40520_OPEN_FAILED, "COM40520打开失败");
            this.put(SEND_CARD_BOX_PRE_EMPTY, "发卡槽卡量少");
        }
    };

    /* 开始时间 */
    private long startTimeInMills;
    /* 完成时间 */
    private long endTimeInMills;
    /* 代码200为Success */
    private String code;
    /* 代码对应的Message */
    private String msg;

    private List<String> status;

    private byte[] readData;

    private String cardSn;

    /**
     * for adel lock, transfer parsed card data
     */
    private String referenceData;

    public CardDriverResponse() {
        code = SUCCESS;
        msg = "成功";
    }

    public List<String> getStatus() {
        if (status == null) {
            return Lists.newArrayList();
        }
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public CardDriverResponse(List<String> status, long startTimeInMills, long endTimeInMills) {
        this.code = SUCCESS;
        this.status = status;
        this.startTimeInMills = startTimeInMills;
        this.endTimeInMills = endTimeInMills;
    }

    public CardDriverResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CardDriverResponse(String code, String msg, long startTimeInMills, long endTimeInMills) {
        this.code = code;
        this.msg = msg;
        this.startTimeInMills = startTimeInMills;
        this.endTimeInMills = endTimeInMills;
    }

    public boolean isSuccess() {
        return SUCCESS.equals(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public byte[] getReadData() {
        return readData;
    }

    public void setReadData(byte[] readData) {
        this.readData = readData;
    }

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }

    public long getStartTimeInMills() {
        return startTimeInMills;
    }

    public void setStartTimeInMills(long startTimeInMills) {
        this.startTimeInMills = startTimeInMills;
    }

    public long getEndTimeInMills() {
        return endTimeInMills;
    }

    public void setEndTimeInMills(long endTimeInMills) {
        this.endTimeInMills = endTimeInMills;
    }

    public String getReferenceData() {
        return referenceData;
    }

    public void setReferenceData(String referenceData) {
        this.referenceData = referenceData;
    }


    @Override
    public String toString() {
        return "CardDriverResponse{" +
                "startTimeInMills=" + startTimeInMills +
                ", endTimeInMills=" + endTimeInMills +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                ", readData=" + Arrays.toString(readData) +
                ", cardSn='" + cardSn + '\'' +
                ", referenceData='" + referenceData + '\'' +
                '}';
    }
}
