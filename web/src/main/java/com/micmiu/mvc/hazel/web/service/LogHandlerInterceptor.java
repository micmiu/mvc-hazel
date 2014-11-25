package com.micmiu.mvc.hazel.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.micmiu.mvc.hazel.web.utils.MenuPermUtil;

/**
 * 增加日志拦截器
 * 
 * @author <a href="http://www.micmiu.com">Michael</a>
 * @time Create on 2013-9-4 上午9:59:09
 * @version 1.0
 */
public class LogHandlerInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(MenuPermUtil.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			String clazzName = handlerMethod.getBean().getClass()
					.getSimpleName();
			String methodName = handlerMethod.getMethod().getName();
			Object obj = SecurityUtils.getSubject().getPrincipal();
			if (null != obj) {
				logger.info(String.format(
						">>>> Log : %s execute class %s's method %s",
						obj.toString(), clazzName, methodName));
			}
		}

	}
}
