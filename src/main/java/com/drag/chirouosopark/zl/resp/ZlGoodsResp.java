package com.drag.chirouosopark.zl.resp;

import com.drag.chirouosopark.common.BaseResponse;

import lombok.Data;

@Data
public class ZlGoodsResp extends BaseResponse{
	

	private static final long serialVersionUID = -3252823562378248431L;

	private int zlgoodsId;
	
	private String zlcode;
}
