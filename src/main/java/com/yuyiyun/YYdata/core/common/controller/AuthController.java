package com.yuyiyun.YYdata.core.common.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuyiyun.YYdata.core.common.constant.JwtConstants;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.shiro.service.UserAuthService;
import com.yuyiyun.YYdata.core.util.JwtTokenUtil;
import com.yuyiyun.YYdata.modular.system.entity.User;
import com.yuyiyun.YYdata.modular.system.service.UserService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

@RestController
@RequestMapping("/yydataApi")
public class AuthController {
	@Autowired
	private UserService userService;

	@RequestMapping("/auth")
	public ResponseData token(String type, String username, String password, String refreshToken) {
//		System.out.println(type+"-"+username);
		// 判断type是否为空
		if (type == null || type.trim() == "") {
			return ResponseData.error(701, "参数异常！");
		} else if (type.equals(JwtConstants.GRANT_TYPE_TOKEN)) {
			if (type == username || username.trim() == "") {
				return ResponseData.error(702, "username参数缺失！");
			} else if (password == null || password.trim() == "") {
				return ResponseData.error(703, "password参数缺失！");
			} else {
				User user = userService.getByAccount(username);
				if (user == null) {
					return ResponseData.error(705, "username无效！");
				} else if (!user.getPassword().equals(ShiroKit.md5(password, user.getSalt()))) {
					return ResponseData.error(706, "password无效！");
				} else {
					String token = JwtTokenUtil.generateToken(user.getUserId().toString());
					HashMap<String, Object> data = new HashMap<String, Object>();
					data.put("access_token", token);
					data.put("token_type", "Bearer ");
					data.put("usernameFromToken", JwtTokenUtil.getUsernameFromToken(token));
					data.put("suedAtDateFromToken", JwtTokenUtil.getIssuedAtDateFromToken(token));
					data.put("sxpirationDateFromToken", JwtTokenUtil.getExpirationDateFromToken(token));
					return ResponseData.success(data);
				}
			}

		} else if (type.equals(JwtConstants.GRANT_TYPE_REFRESHTTOKEN)) {
			if (password == null || password.trim() == "") {
				return ResponseData.error(704, "refreshToken参数缺失！");
			}
		}
		return ResponseData.error("You Silly!☺");
	}
}
