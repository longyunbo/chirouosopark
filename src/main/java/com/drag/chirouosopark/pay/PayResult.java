package com.drag.chirouosopark.pay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.drag.chirouosopark.pay.common.StreamUtil;

/**
 * 接收支付结果
 */
public class PayResult{
	private static final Logger L = Logger.getLogger(PayResult.class);
       
	public static String payResult(HttpServletRequest request) throws ServletException, IOException {
		String reqParams = StreamUtil.read(request.getInputStream());
		L.info("-------支付结果:"+reqParams);
		StringBuffer sb = new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
		return sb.toString();
	}

}
