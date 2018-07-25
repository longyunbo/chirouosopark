package com.drag.chirouosopark.pay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drag.chirouosopark.pay.common.Configure;
import com.drag.chirouosopark.pay.common.RandomStringGenerator;
import com.drag.chirouosopark.pay.common.Signature;
import com.drag.chirouosopark.pay.model.SignInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 再签名
 */
@Slf4j
@Service
public class Sign {
	private static final Logger L = Logger.getLogger(Sign.class);

	public static JSONObject signAgain(String repay_id) {
		JSONObject json = new JSONObject();
		try {
			// String repay_id = request.getParameter("repay_id");
			SignInfo signInfo = new SignInfo();
			signInfo.setAppId(Configure.appid);
			long time = System.currentTimeMillis() / 1000;
			signInfo.setTimeStamp(String.valueOf(time));
			signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
			signInfo.setRepay_id("prepay_id=" + repay_id);
			signInfo.setSignType("MD5");
			// 生成签名
			String sign = Signature.getSign(signInfo);
			json.put("timeStamp", signInfo.getTimeStamp());
			json.put("nonceStr", signInfo.getNonceStr());
			json.put("package", signInfo.getRepay_id());
			json.put("signType", signInfo.getSignType());
			json.put("paySign", sign);
			L.info("-------再签名:" + json.toJSONString());
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------", e);
		}
		return json;
	}

}
