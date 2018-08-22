package com.drag.chirouosopark.pay;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drag.chirouosopark.pay.common.Configure;
import com.drag.chirouosopark.pay.common.HttpRequest;
import com.drag.chirouosopark.pay.common.RandomStringGenerator;
import com.drag.chirouosopark.pay.common.Signature;
import com.drag.chirouosopark.pay.model.OrderInfo;
import com.drag.chirouosopark.pay.model.OrderReturnInfo;
import com.drag.chirouosopark.utils.IpUtils;
import com.thoughtworks.xstream.XStream;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一下单接口
 */
@Slf4j
@Service
public class Xiadan {
	private static final Logger L = Logger.getLogger(Xiadan.class);

	public static JSONObject wxPay(HttpServletRequest request,String openid,int price) {
		JSONObject json = new JSONObject();
		try {
			log.info("-------下单参数openid:{},price:{}" + openid,price);
			String out_trade_no = RandomStringGenerator.getRandomStringByLength(32);
			OrderInfo order = new OrderInfo();
			order.setAppid(Configure.appid);
			order.setMch_id(Configure.mch_id);
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			order.setBody("诸锣记-付款");
			order.setOut_trade_no(out_trade_no);
			order.setTotal_fee(price);
			order.setSpbill_create_ip(IpUtils.getIpAddr(request));
			order.setNotify_url("https://zlj.wisdomdr.com/chirouosopark/pay/payresult");
			order.setTrade_type("JSAPI");
			order.setOpenid(openid);
			order.setSign_type("MD5");
			// 生成签名
			String sign = Signature.getSign(order);
			order.setSign(sign);

			String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			System.out.println(result);
			log.info("---------下单返回:" + result);
			XStream xStream = new XStream();
			xStream.alias("xml", OrderReturnInfo.class);

			OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
			returnInfo.setOut_trade_no(out_trade_no);
			JSONObject itemJSONObj = JSONObject.parseObject(JSON.toJSONString(returnInfo));
			return itemJSONObj;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("-------", e);
		}
		return json;

	}

}
