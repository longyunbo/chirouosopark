package com.drag.chirouosopark.task;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.drag.chirouosopark.common.exception.AMPException;
import com.drag.chirouosopark.kj.dao.KjGoodsDao;
import com.drag.chirouosopark.kj.dao.KjUserDao;
import com.drag.chirouosopark.kj.entity.KjGoods;
import com.drag.chirouosopark.kj.entity.KjUser;

import lombok.extern.slf4j.Slf4j;
/**
 * 定时任务查询砍价有效期
 * @author longyunbo
 *
 */
@Slf4j
@Component
public class KjValidhoursTask {
	
	@Autowired
	KjGoodsDao kjGoodsDao;
	@Autowired
	KjUserDao kjUserDao;
	
	@Scheduled(cron = "${jobs.hoursCheckTask.schedule}")
	@Transactional
	public void find() {
		
		try {
			Date nowTime = new Timestamp(System.currentTimeMillis());
			List<KjGoods> kjGoodsList = kjGoodsDao.findByIsEnd(0);
			for (KjGoods kjGoods : kjGoodsList) {
				//拼团有效时间，默认为24小时
				int kjValidhours = kjGoods.getKjValidhours();
				int goodsId = kjGoods.getKjgoodsId();
				//根据商品编号查询出砍价中的用户
				List<KjUser> userList = kjUserDao.findByKjGoodsIdAndKjstatus(goodsId, KjUser.PTSTATUS_MIDDLE);
				if(userList != null && userList.size() > 0) {
					for(KjUser user : userList) {
						Date createTime =  user.getCreateTime();
						long compareDate = (nowTime.getTime() - createTime.getTime()) / (60*60*1000);
						if(compareDate >= kjValidhours) {
							//修改为砍价失败
							user.setKjstatus(KjUser.PTSTATUS_FAIL);
							kjUserDao.saveAndFlush(user);
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
