package com.drag.chirouosopark.task;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.drag.chirouosopark.common.exception.AMPException;
import com.drag.chirouosopark.zl.dao.ZlGoodsDao;
import com.drag.chirouosopark.zl.dao.ZlUserDao;
import com.drag.chirouosopark.zl.entity.ZlGoods;
import com.drag.chirouosopark.zl.entity.ZlUser;

import lombok.extern.slf4j.Slf4j;
/**
 * 定时任务查询助力有效期
 * @author longyunbo
 *
 */
@Slf4j
@Component
public class ZlValidhoursTask {
	
	@Autowired
	ZlGoodsDao zlGoodsDao;
	@Autowired
	ZlUserDao zlUserDao;
	
	@Scheduled(cron = "${jobs.hoursCheckTask.schedule}")
	@Transactional
	public void find() {
		
		try {
			Date nowTime = new Timestamp(System.currentTimeMillis());
			List<ZlGoods> zlGoodsList = zlGoodsDao.findByIsEnd(0);
			for (ZlGoods zlGoods : zlGoodsList) {
				//拼团有效时间，默认为24小时
				int zlValidhours = zlGoods.getZlValidhours();
				int goodsId = zlGoods.getZlgoodsId();
				//根据商品编号查询出砍价中的用户
				List<ZlUser> userList = zlUserDao.findByZlGoodsIdAndZlstatus(goodsId, ZlUser.PTSTATUS_MIDDLE);
				if(userList != null && userList.size() > 0) {
					for(ZlUser user : userList) {
						Date createTime =  user.getCreateTime();
						long compareDate = (nowTime.getTime() - createTime.getTime()) / (60*60*1000);
						if(compareDate >= zlValidhours) {
							//修改为砍价失败
							user.setZlstatus(ZlUser.PTSTATUS_FAIL);
							zlUserDao.saveAndFlush(user);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("定时异常{}", e);
			throw AMPException.getException("定时任务异常!");
		}

	}
}
