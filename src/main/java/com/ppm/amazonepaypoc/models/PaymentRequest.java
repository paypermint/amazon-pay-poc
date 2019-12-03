package com.ppm.amazonepaypoc.models;

import java.time.Instant;

public class PaymentRequest {

	public static final String merchantID = "AZ4WQCLDT2DF0";
	public static final String accessKey = "AKIAJAKG6LME27HVCD3A";
	public static final String secretKey = "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";

	public String getOrderTotalAmount() {
		return OrderTotalAmount;
	}

	public void setOrderTotalAmount(String orderTotalAmount) {
		OrderTotalAmount = orderTotalAmount;
	}

	public String getOrderTotalCurrencyCode() {
		return OrderTotalCurrencyCode;
	}

	public void setOrderTotalCurrencyCode(String orderTotalCurrencyCode) {
		OrderTotalCurrencyCode = orderTotalCurrencyCode;
	}

	public String getSellerOrderID() {
		return SellerOrderID;
	}

	public void setSellerOrderID(String sellerOrderID) {
		SellerOrderID = sellerOrderID;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getIsSandbox() {
		return IsSandbox;
	}

	public void setIsSandbox(String isSandbox) {
		IsSandbox = isSandbox;
	}

	private String OrderTotalAmount;
	private String OrderTotalCurrencyCode;
	private String sellerID;
	private String SellerOrderID;
	private String StartTime;
	private String IsSandbox;

	public PaymentRequest() {

		this.OrderTotalAmount = "10.01";
		this.IsSandbox = "true";
		this.sellerID = merchantID;
		this.SellerOrderID = "ord_" + Instant.now().toEpochMilli();
		this.OrderTotalCurrencyCode = "INR";
	}

	public String createConcatenatedString() {
		String s = new String();
		s = "POST\\n\n" + "amazonpay.amazon.in\\n\n" + "/\\n\n";
		s += "AWSAccessKeyId=" + accessKey + "\n" + "&SignatureMethod=" + "HmacSHA256" + "\n" + "&SignatureVersion="
				+ "2" + "\n" + "&isSandbox=" + this.IsSandbox + "\n" + "&orderTotalAmount=" + this.OrderTotalAmount
				+ "\n" + "&orderTotalCurrencyCode=" + this.OrderTotalCurrencyCode + "\n" + "&sellerId=" + this.sellerID
				+ "\n" + "&sellerOrderId=" + this.SellerOrderID + "\n" + "&startTime=" + this.StartTime;
		return s;
	}

	public String createPayload(String sig) {
		String s = new String();
		s = "AWSAccessKeyId=" + accessKey + "\n" + "&SignatureMethod=" + "HmacSHA256" + "\n" + "&SignatureVersion="
				+ "2" + "\n" + "&isSandbox=" + this.IsSandbox + "\n" + "&orderTotalAmount=" + this.OrderTotalAmount
				+ "\n" + "&orderTotalCurrencyCode=" + this.OrderTotalCurrencyCode + "\n" + "&sellerId=" + this.sellerID
				+ "\n" + "&sellerOrderId=" + this.SellerOrderID + "\n" + "&startTime=" + this.StartTime + "\n"
				+ "&Signature=" + sig;
		return s;
	}
}
