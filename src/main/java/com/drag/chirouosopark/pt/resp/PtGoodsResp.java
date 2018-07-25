package com.drag.chirouosopark.pt.resp;

import com.drag.chirouosopark.common.BaseResponse;

import lombok.Data;

@Data
public class PtGoodsResp extends BaseResponse{
	
	private static final long serialVersionUID = -4195525113654121659L;
	
	private int ptgoodsId;
	
	private String ptcode;
}
