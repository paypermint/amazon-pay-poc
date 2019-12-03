package com.ppm.amazonepaypoc.models;

import java.util.Map;

public class VerifySignature {

	private String amazonOrderId;
	private String description;
	private String orderTotalAmount;
	private String orderTotalCurrencyCode;
	private String reasonCode;
	private String sellerOrderId;
	private String signature;
	private String status;
	private String transactionDate;

	public VerifySignature() {
		this.amazonOrderId = "S04-7103131-1351536";
		this.description = "Txn Success";
		this.orderTotalAmount = "10.01";
		this.orderTotalCurrencyCode = "INR";
		this.reasonCode = "001";
		this.sellerOrderId = "ord123451113";
		this.signature = "jv9ibX7XUwuLfBfz04Y7hKDJmhXmHZBwe64p3tN8znQ=";
		this.status = "SUCCESS";
		this.transactionDate = "1574845179270";
	}

	public VerifySignature(Map<String, String> queryMap) {
		this.amazonOrderId = queryMap.get("amazonOrderId");
		this.description = queryMap.get("description");
		this.orderTotalAmount = queryMap.get("orderTotalAmount");
		this.orderTotalCurrencyCode = queryMap.get("orderTotalCurrencyCode");
		this.reasonCode = queryMap.get("reasonCode");
		this.sellerOrderId = queryMap.get("sellerOrderId");
		this.signature = queryMap.get("signature") + "=";
		this.status = queryMap.get("status");
		this.transactionDate = queryMap.get("transactionDate");
	}

	public String getAmazonOrderId() {
		return amazonOrderId;
	}

	public void setAmazonOrderId(String amazonOrderId) {
		this.amazonOrderId = amazonOrderId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(String orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public String getOrderTotalCurrencyCode() {
		return orderTotalCurrencyCode;
	}

	public void setOrderTotalCurrencyCode(String orderTotalCurrencyCode) {
		this.orderTotalCurrencyCode = orderTotalCurrencyCode;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getSellerOrderId() {
		return sellerOrderId;
	}

	public void setSellerOrderId(String sellerOrderId) {
		this.sellerOrderId = sellerOrderId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

}
