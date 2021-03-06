package com.yuyiyun.YYdata.core.interceptor;

import com.yuyiyun.YYdata.core.common.constant.JwtConstants;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.util.JwtTokenUtil;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.util.RenderUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Rest Api接口鉴权
 *
 * @author duhao
 * @Date 2018/7/20 23:11
 */
public class RestApiInteceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		System.out.println("----"+request.getServletPath());
		if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
			return true;
		}
		return check(request, response);
	}

	private boolean check(HttpServletRequest request, HttpServletResponse response) {
		if (request.getServletPath().equals(JwtConstants.AUTH_PATH)) {
			return true;
		}
		final String requestHeader = request.getHeader(JwtConstants.AUTH_HEADER);
		String authToken;
		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			authToken = requestHeader.substring(7);

			// 验证token是否过期,包含了验证jwt是否正确
			try {
				boolean flag = JwtTokenUtil.isTokenExpired(authToken);
				if (flag) {
					RenderUtil.renderJson(response, new ErrorResponseData(BizExceptionEnum.TOKEN_EXPIRED.getCode(),
							BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
					return false;
				}
			} catch (JwtException e) {
				// 有异常就是token解析失败
				RenderUtil.renderJson(response, new ErrorResponseData(BizExceptionEnum.TOKEN_ERROR.getCode(),
						BizExceptionEnum.TOKEN_ERROR.getMessage()));
				return false;
			}
		} else {
			// header没有带Bearer字段
			RenderUtil.renderJson(response, new ErrorResponseData(BizExceptionEnum.TOKEN_ERROR.getCode(),
					BizExceptionEnum.TOKEN_ERROR.getMessage()));
			return false;
		}
		return true;
	}

}
