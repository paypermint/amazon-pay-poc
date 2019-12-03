package com.ppm.amazonepaypoc.models;

import java.time.Instant;

public class RefundRequest {
	public String getIsSandbox() {
		return isSandbox;
	}

	public void setIsSandbox(String isSandbox) {
		this.isSandbox = isSandbox;
	}

	private String amazonTransactionId;
	private String amazonTransactionIdType;
	private String refundAmount;
	private String refundCurrencyCode;
	private String isSandbox;
	private String refundReferneceId;

	public RefundRequest() {
		this.refundAmount = "5.50";
		this.isSandbox = "true";
		this.amazonTransactionIdType = "OrderReferenceId";
		this.refundCurrencyCode = "INR";
		this.refundReferneceId = "rfnd_" + Instant.now().toEpochMilli();
		;

	}

	public String getRefundReferneceId() {
		return refundReferneceId;
	}

	public void setRefundReferneceId(String refundReferneceId) {
		this.refundReferneceId = refundReferneceId;
	}

	public String getAmazonTransactionId() {
		return amazonTransactionId;
	}

	public void setAmazonTransactionId(String amazonTransactionId) {
		this.amazonTransactionId = amazonTransactionId;
	}

	public String getAmazonTransactionIdType() {
		return amazonTransactionIdType;
	}

	public void setAmazonTransactionIdType(String amazonTransactionIdType) {
		this.amazonTransactionIdType = amazonTransactionIdType;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundCurrencyCode() {
		return refundCurrencyCode;
	}

	public void setRefundCurrencyCode(String refundCurrencyCode) {
		this.refundCurrencyCode = refundCurrencyCode;
	}

}
