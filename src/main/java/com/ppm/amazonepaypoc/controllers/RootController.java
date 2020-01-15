package com.ppm.amazonepaypoc.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazon.pwain.PWAINBackendSDK;
import com.amazon.pwain.PWAINException;
import com.amazon.pwain.mws.model.GetRefundDetailsResponseData;
import com.amazon.pwain.mws.model.RefundDetails;
import com.amazon.pwain.mws.model.RefundResponse;
import com.amazon.pwain.types.MerchantConfiguration;
import com.amazon.pwain.types.PWAINConstants;
import com.ppm.amazonepaypoc.models.PaymentRequest;
import com.ppm.amazonepaypoc.models.RefundRequest;
import com.ppm.amazonepaypoc.models.VerifySignature;

@Controller
public class RootController {
	public static final String merchantID = "AZ4WQCLDT2DF0";
	public static final String accessKey = "AKIAJAKG6LME27HVCD3A";
	public static final String secretKey = "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";
	public static final String returnUrl = "https://amazonpay.payscape.in/api/v1/payments/status";
	
	private static final Logger logger = LoggerFactory.getLogger(RootController.class);

	public static Map<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
		}
		return map;
	}

	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}

	@GetMapping("/pay")
	public String pay(Model model) {
		model.addAttribute("requests", new PaymentRequest());
		return "payment";
	}

	@PostMapping("/pay")
	public void pay(@ModelAttribute PaymentRequest req, Model m, HttpServletResponse httpServletResponse)
			throws InvalidKeyException, NoSuchAlgorithmException, IOException {
		logger.info("Inside pay");
		try {
			// Add the payment request values to HashMap
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put(PWAINConstants.ORDER_TOTAL_AMOUNT, req.getOrderTotalAmount());
			parameters.put(PWAINConstants.ORDER_TOTAL_CURRENCY_CODE, req.getOrderTotalCurrencyCode());
			parameters.put(PWAINConstants.SELLER_ORDER_ID, req.getSellerOrderID());
			parameters.put(PWAINConstants.REDIRECT_URL, returnUrl);
			/* Set optional parameters */
			// Default environment is Production.
			// For testing in Sandbox mode add IS_SANDBOX parameter,
			// Remove IS_SANDBOX parameter when going live
			parameters.put(PWAINConstants.IS_SANDBOX, req.getIsSandbox());
			// Transaction timeout in seconds
			parameters.put(PWAINConstants.TRANSACTION_TIMEOUT, "10000");

			PWAINBackendSDK backendSDK = new PWAINBackendSDK(new MerchantConfiguration.Builder()
					.withAwsAccessKeyId(accessKey).withAwsSecretKeyId(secretKey).withSellerId(merchantID).build());
			String amazonPayRedirectUrl = backendSDK.getPaymentUrl(parameters);

			// Redirect the customer to amazonPayRedirectUrl generated above
			// Sample redirection code for a Servlet
			httpServletResponse.sendRedirect(amazonPayRedirectUrl);
		} catch (PWAINException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/verify")
	public String verify(Model model) {
		return "verify_sig";
	}

	@GetMapping("/verify/sig")
	public String verifySignature(@RequestParam(name = "response_string", required = true) String responseString,
			Model m) {
		logger.info("Inside verifySignature");
		logger.info("verifySignature : responseString : " + responseString);
		try {
			String result = java.net.URLDecoder.decode(responseString, StandardCharsets.UTF_8.name());
			logger.info("verifySignature : result = " + result);
			VerifySignature verifySignatureObj = new VerifySignature(getQueryMap(result));

			try {
				HashMap<String, String> paymentResponseParameters = new HashMap<>();
				paymentResponseParameters.put(PWAINConstants.AMAZON_ORDER_ID, verifySignatureObj.getAmazonOrderId());
				paymentResponseParameters.put(PWAINConstants.DESCRIPTION, verifySignatureObj.getDescription());
				paymentResponseParameters.put(PWAINConstants.ORDER_TOTAL_AMOUNT,
						verifySignatureObj.getOrderTotalAmount());
				paymentResponseParameters.put(PWAINConstants.ORDER_TOTAL_CURRENCY_CODE,
						verifySignatureObj.getOrderTotalCurrencyCode());
				paymentResponseParameters.put(PWAINConstants.REASON_CODE, verifySignatureObj.getReasonCode());
				paymentResponseParameters.put(PWAINConstants.SELLER_ORDER_ID, verifySignatureObj.getSellerOrderId());
				paymentResponseParameters.put(PWAINConstants.SIGNATURE, verifySignatureObj.getSignature());
				paymentResponseParameters.put(PWAINConstants.STATUS, verifySignatureObj.getStatus());
				paymentResponseParameters.put(PWAINConstants.TRANSACTION_DATE, verifySignatureObj.getTransactionDate());

				PWAINBackendSDK backendSDK = new PWAINBackendSDK(new MerchantConfiguration.Builder()
						.withAwsAccessKeyId(accessKey).withAwsSecretKeyId(secretKey).withSellerId(merchantID).build());

				backendSDK.verifySignature(paymentResponseParameters);
				m.addAttribute("response",
						"POC : Amazon Pay Integration: Transaction status: " + verifySignatureObj.getStatus());
				// Signature verification successful please process the order

			} catch (PWAINException e) {
				// Signature verification failed
				e.printStackTrace();
				m.addAttribute("response", "POC : Amazon Pay Integration: Signature verification failed");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			m.addAttribute("response", "error while parsing response string");
		}
		return "verify_sig_resp";
	}

	@GetMapping("/refund")
	public String refund(Model m) {
		m.addAttribute("createRefundRequestObj", new RefundRequest());
		return "create_refund";

	}

	@PostMapping("/refund")
	public String refund(@ModelAttribute RefundRequest refundRequest, Model m) {
		logger.info("Inside refund");
		try {

			Map<String, String> refundParams = new HashMap<>();
			refundParams.put(PWAINConstants.AMAZON_TRANSACTION_ID, refundRequest.getAmazonTransactionId());
			refundParams.put(PWAINConstants.AMAZON_TRANSACTION_ID_TYPE, refundRequest.getAmazonTransactionIdType());
			refundParams.put(PWAINConstants.REFUND_AMOUNT, refundRequest.getRefundAmount());
			refundParams.put(PWAINConstants.REFUND_CURRENCY_CODE, refundRequest.getRefundCurrencyCode());
			// Default environment is Production.
			// For testing in Sandbox mode add IS_SANDBOX field,
			// Remove IS_SANDBOX when going live
			refundParams.put(PWAINConstants.IS_SANDBOX, refundRequest.getIsSandbox());
			refundParams.put(PWAINConstants.REFUND_REFERENCE_ID, refundRequest.getRefundReferneceId());
			PWAINBackendSDK backendSDK = new PWAINBackendSDK(new MerchantConfiguration.Builder()
					.withAwsAccessKeyId(accessKey).withAwsSecretKeyId(secretKey).withSellerId(merchantID).build());
			RefundResponse response = backendSDK.refund(refundParams);
			RefundDetails refundResponse = (RefundDetails) response.getDetails().get(0);

			logger.info("refundResponse : " + refundResponse);
			m.addAttribute("refund : refundResponse", refundResponse);
		} catch (PWAINException e) {
			e.printStackTrace();
			m.addAttribute("refundResponse", e.getMessage());
		}
		return "refund_resp";

	}

	@GetMapping("/refundDetails")
	public String refundDetails(Model m) {
		m.addAttribute("is_sandbox", "true");
		m.addAttribute("amazon_refund_id", "S04-5703544-2234308-R039376");
		return "get_refund_details";
	}

	@GetMapping("/getRefundDetails")
	public String getrefundDetails(Model m,
			@RequestParam(name = "is_sandbox", required = false, defaultValue = "true") String isSandBox,
			@RequestParam(name = "amazon_refund_id", required = true) String amazonRefundId) {
		logger.info("Inside getRefundDetails");

		try {

			Map<String, String> refundDetailsParams = new HashMap<>();
			// Remove IS_SANDBOX when going live
			refundDetailsParams.put(PWAINConstants.IS_SANDBOX, isSandBox);
			refundDetailsParams.put(PWAINConstants.AMAZON_REFUND_ID, amazonRefundId);

			PWAINBackendSDK backendSDK = new PWAINBackendSDK(new MerchantConfiguration.Builder()
					.withAwsAccessKeyId(accessKey).withAwsSecretKeyId(secretKey).withSellerId(merchantID).build());
			GetRefundDetailsResponseData response = backendSDK.getRefundDetails(refundDetailsParams);

			logger.info("refundResponse : " + response);
			m.addAttribute("refundResponse", response);
		} catch (PWAINException e) {
			m.addAttribute("refundResponse", e.getMessage());
			e.printStackTrace();
		}
		return "refund_resp";
	}
}
