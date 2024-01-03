package com.reader.api;

public class CardInfo {
	// 卡号
	private String cardNumber;
	// 电子现金余额
	private int eBalance;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int geteBalance() {
		return eBalance;
	}

	public void seteBalance(int eBalance) {
		this.eBalance = eBalance;
	}
}
