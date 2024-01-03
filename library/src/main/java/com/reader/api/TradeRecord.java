package com.reader.api;

import java.io.Serializable;

public class TradeRecord implements Serializable{
	// 终端号
	private String termID;
	// 商户号
	private String merchID;
	// 卡号
	private String cardNumber;
	// 交易日期
	private String date;
	// 交易流水号
	private String sn;
	// 交易应答码
	private String rcCode;
	// 批次号
	private String batchNO;
	// 交易金额
	private int amount;
	// 交易类型，00-脱机电子现金，01-小额免密联机消费
	private int tradeType;
	// 折扣后金额
	private int discountAmount;
	
	//检索参考号
	 
	private String refNo;
	
	public String getRefNo() {
		return refNo;
	}
	
	public void setRefNo(String srefno) {
		this.refNo = srefno;
	}


	public String getTermID() {
		return termID;
	}

	public void setTermID(String termID) {
		this.termID = termID;
	}

	public String getMerchID() {
		return merchID;
	}

	public void setMerchID(String merchID) {
		this.merchID = merchID;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getRcCode() {
		return rcCode;
	}

	public void setRcCode(String rcCode) {
		this.rcCode = rcCode;
	}

	public String getBatchNO() {
		return batchNO;
	}

	public void setBatchNO(String batchNO) {
		this.batchNO = batchNO;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}
}
