package com.wristband.gaoyusheng.base_lib_module.pos;

import java.math.BigDecimal;

/**
 * Created by gaoyusheng on 17/3/9.
 */

public class PosResponse {
    // 小费
    private String Tips;
    // 总计
    private String Total;
    // 金额 中银：0.1 == 10 通联:0.1 = 0.1?
    private String Amount;
    // 交易金额，BigDecimal格式
    private BigDecimal RealAmount;
    // 余额
    private String BalanceAmount;
    // 凭证号
    private String PosTraceNumber;
    // 原始流水号
    private String OldTraceNumber;
    // 有效期
    private String ExpireDate;
    // 批次号
    private String BatchNumber;
    // 商户号
    private String MerchantNumber;
    // 商户名
    private String MerchantName;
    // 终端号
    private String TerminalNumber;
    // 交易参考号
    private String HostSerialNumber;
    // 授权号
    private String AuthNumber;
    // 返回码
    private String RejCode;
    // 发卡行号
    private String IssNumber;
    // 发卡行名称
    private String IssName;
    // 收单机构
    private String AcqNumber;
    // 卡号
    private String CardNumber;
    // 交易日期
    private String TransDate;
    // 交易时间
    private String TransTime;
    // 返回码解释
    private String RejCodeExplain;
    // 卡片回收标志
    private String CardBack;
    // 备注
    private String Memo;
    // 键盘输入值（MD5）
    private String KeyValue;
    // Pos当前交易笔数
    private String PosTransCounts;
    // 微信、支付宝账号
    private String AccountNumber;
    // 扫码支付的折扣金额
    private String DiscountAmount;
    // 交易唯一标识
    private String TransCheck;
    // 脱机标识：0-联机、1-脱机
    private String IsOffline;

    // 万商卡附加KEY
    // 产品号码
    private String ProductNumber;
    // 产品名称
    private String ProductName;

    public String getTips() {
        return Tips;
    }

    public void setTips(String tips) {
        Tips = tips;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getBalanceAmount() {
        return BalanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        BalanceAmount = balanceAmount;
    }

    public String getPosTraceNumber() {
        return PosTraceNumber;
    }

    public void setPosTraceNumber(String posTraceNumber) {
        PosTraceNumber = posTraceNumber;
    }

    public String getOldTraceNumber() {
        return OldTraceNumber;
    }

    public void setOldTraceNumber(String oldTraceNumber) {
        OldTraceNumber = oldTraceNumber;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public String getBatchNumber() {
        return BatchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
    }

    public String getMerchantNumber() {
        return MerchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        MerchantNumber = merchantNumber;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }

    public String getTerminalNumber() {
        return TerminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        TerminalNumber = terminalNumber;
    }

    public String getHostSerialNumber() {
        return HostSerialNumber;
    }

    public void setHostSerialNumber(String hostSerialNumber) {
        HostSerialNumber = hostSerialNumber;
    }

    public String getAuthNumber() {
        return AuthNumber;
    }

    public void setAuthNumber(String authNumber) {
        AuthNumber = authNumber;
    }

    public String getRejCode() {
        return RejCode;
    }

    public void setRejCode(String rejCode) {
        RejCode = rejCode;
    }

    public String getIssNumber() {
        return IssNumber;
    }

    public void setIssNumber(String issNumber) {
        IssNumber = issNumber;
    }

    public String getIssName() {
        return IssName;
    }

    public void setIssName(String issName) {
        IssName = issName;
    }

    public String getAcqNumber() {
        return AcqNumber;
    }

    public void setAcqNumber(String acqNumber) {
        AcqNumber = acqNumber;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public String getTransTime() {
        return TransTime;
    }

    public void setTransTime(String transTime) {
        TransTime = transTime;
    }

    public String getRejCodeExplain() {
        return RejCodeExplain;
    }

    public void setRejCodeExplain(String rejCodeExplain) {
        RejCodeExplain = rejCodeExplain;
    }

    public String getCardBack() {
        return CardBack;
    }

    public void setCardBack(String cardBack) {
        CardBack = cardBack;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getKeyValue() {
        return KeyValue;
    }

    public void setKeyValue(String keyValue) {
        KeyValue = keyValue;
    }

    public String getPosTransCounts() {
        return PosTransCounts;
    }

    public void setPosTransCounts(String posTransCounts) {
        PosTransCounts = posTransCounts;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        DiscountAmount = discountAmount;
    }

    public String getTransCheck() {
        return TransCheck;
    }

    public void setTransCheck(String transCheck) {
        TransCheck = transCheck;
    }

    public String getIsOffline() {
        return IsOffline;
    }

    public void setIsOffline(String isOffline) {
        IsOffline = isOffline;
    }

    public String getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(String productNumber) {
        ProductNumber = productNumber;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public boolean isSuccess() {
        return "00".equals(RejCode);
    }

    public BigDecimal getRealAmount() {
        return RealAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        RealAmount = realAmount;
    }
}
