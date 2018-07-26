package com.drag.chirouosopark.pay.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.drag.chirouosopark.pay.PayResult;
import com.drag.chirouosopark.pay.Sign;
import com.drag.chirouosopark.pay.Xiadan;


@RestController
@RequestMapping(value = "/chirouosopark/pay")
public class PayController {
	
	private final static Logger log = LoggerFactory.getLogger(PayController.class);
	
	@RequestMapping(value = "/wxpay", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ResponseEntity<JSONObject> wxPay1(HttpServletRequest request,@RequestParam(required = true) String openid,int price) {
		JSONObject Json = Xiadan.wxPay(request,openid,price);
		return new ResponseEntity<JSONObject>(Json, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/signagain", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ResponseEntity<JSONObject> signAgain(@RequestParam(required = true) String repay_id) {
		JSONObject Json = Sign.signAgain(repay_id);
		return new ResponseEntity<JSONObject>(Json, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/payresult", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ResponseEntity<String> payResult(HttpServletRequest request) {
		String Json = PayResult.payResult(request);
		return new ResponseEntity<String>(Json, HttpStatus.OK);
	}
	
}
