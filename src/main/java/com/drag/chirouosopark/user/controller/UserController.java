package com.drag.chirouosopark.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.drag.chirouosopark.pt.entity.PtGoods;
import com.drag.chirouosopark.user.dao.UserDao;
import com.drag.chirouosopark.user.entity.User;
import com.drag.chirouosopark.user.form.UserForm;
import com.drag.chirouosopark.user.resp.UserResp;
import com.drag.chirouosopark.user.service.UserService;
import com.drag.chirouosopark.user.vo.ActivityVo;
import com.drag.chirouosopark.user.vo.UserVo;
import com.drag.chirouosopark.utils.WxUtil;


@RestController
@RequestMapping(value = "/chirouosopark/user")
public class UserController {
	
	private final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	
	/**
	 * 查询用户权限
	 * @return
	 */
	@RequestMapping(value = "/checkauth", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ResponseEntity<Boolean> checkAuth(@RequestParam(required = true) String openid,@RequestParam(required = true) String authIds) {
		Boolean auth = false;
		User user = userDao.findByOpenid(openid);
		auth =  userService.checkAuth(user, authIds);
		return new ResponseEntity<Boolean>(auth, HttpStatus.OK);
	}
	
	/**
	 * 根据code获取openid
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/openid", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ResponseEntity<String> getOpenid(@RequestParam(required = true) String code) {
		String openid = WxUtil.returnOpenid(code);
		return new ResponseEntity<String>(openid, HttpStatus.OK);
	}
	
	/**
	 * 根据openid获取用户信息
	 * @param openid
	 * @return
	 */
	@RequestMapping(value = "/queryuser", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ResponseEntity<UserVo> queryUserByOpenid(@RequestParam(required = true) String openid) {
		UserVo userVo = userService.queryUserByOpenid(openid);
		return new ResponseEntity<UserVo>(userVo, HttpStatus.OK);
	}
	
	/**
	 * 用户新增
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/useradd", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ResponseEntity<UserResp> userAdd(@RequestBody UserForm form) {
		UserResp br = userService.userAdd(form);
		return new ResponseEntity<UserResp>(br, HttpStatus.OK);
	}
	
	/**
	 * 根据openid获取活动
	 * @param openid
	 * @return
	 */
	@RequestMapping(value = "/queryactivity", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ResponseEntity<List<ActivityVo>> queryActivityByOpenid(@RequestParam(required = true) String openid) {
		List<ActivityVo> userVo = userService.queryActivityByOpenid(openid);
		return new ResponseEntity<List<ActivityVo>>(userVo, HttpStatus.OK);
	}
	
}
