package com.drag.chirouosopark.pay;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drag.chirouosopark.pay.common.CertHttpUtil;
import com.drag.chirouosopark.pay.common.Configure;
import com.drag.chirouosopark.pay.common.RandomStringGenerator;
import com.drag.chirouosopark.pay.common.Signature;
import com.drag.chirouosopark.pay.model.PayReturnInfo;
import com.drag.chirouosopark.pay.model.PayReturnResultInfo;
import com.thoughtworks.xstream.XStream;

import lombok.extern.slf4j.Slf4j;

/**
 * 退款接口
 */
@Slf4j
@Service
public class PayReturn {
	private static final Logger L = Logger.getLogger(PayReturn.class);

	public static JSONObject wxReturn(String out_trade_no,int price) {
		JSONObject json = new JSONObject();
		try {
			File cfgFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "apiclient_cert.p12");
			String certPath = cfgFile.getPath();
			
			//随机生成的退款编号
			String out_refund_no = RandomStringGenerator.getRandomStringByLength(32);
			
			PayReturnInfo returnInfo = new PayReturnInfo();
			returnInfo.setAppid(Configure.appid);
			returnInfo.setMch_id(Configure.mch_id);
			returnInfo.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			returnInfo.setOut_trade_no(out_trade_no);
			returnInfo.setOut_refund_no(out_refund_no);
			returnInfo.setTotal_fee(price);
			returnInfo.setRefund_fee(price);
			// 生成签名
			String sign = Signature.getSign(returnInfo);
			returnInfo.setSign(sign);

//			String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/secapi/pay/refund", returnInfo);
			String result = CertHttpUtil.postData("https://api.mch.weixin.qq.com/secapi/pay/refund", returnInfo, Configure.mch_id, certPath);
			System.out.println(result);
			L.info("---------退款返回:" + result);
			XStream xStream = new XStream();
			xStream.alias("xml", PayReturnResultInfo.class);

			PayReturnResultInfo payReturnResultInfo = (PayReturnResultInfo) xStream.fromXML(result);
			JSONObject itemJSONObj = JSONObject.parseObject(JSON.toJSONString(payReturnResultInfo));
			return itemJSONObj;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------", e);
		}
		return json;

	}

}
