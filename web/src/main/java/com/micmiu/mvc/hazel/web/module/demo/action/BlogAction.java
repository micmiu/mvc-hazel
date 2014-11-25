package com.micmiu.mvc.hazel.web.module.demo.action;

import javax.servlet.http.HttpServletRequest;

import com.micmiu.mvc.hazel.core.action.smvc.SimpleBaseAction;
import com.micmiu.mvc.hazel.core.common.StringUtil;
import com.micmiu.mvc.hazel.core.service.BaseService;
import com.micmiu.mvc.hazel.web.module.demo.vo.BlogQuery;
import com.micmiu.mvc.hazel.web.service.BlogService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.micmiu.mvc.hazel.web.entity.Blog;

/**
 * 博客演示基本的CRUD
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * 
 */
@Controller
@RequestMapping(value = "/demo/blog.do")
public class BlogAction extends SimpleBaseAction<Blog, Long, BlogQuery> {

	private static final String PREFIX = "demo.dg.blog";

	private static final String REDIRECT = "redirect:/demo/blog.do?method=showList";

	@Autowired
	private BlogService blogService;

	@Override
	public BaseService<Blog, Long> getBaseService() {
		return blogService;
	}

	@Override
	public String getViewPrefix() {
		return PREFIX;
	}

	@Override
	public String getRedirectView() {
		return REDIRECT;
	}

	@Override
	protected void checkAuth(String operation) {
		// no check
	}

	@Override
	protected boolean handler4Create(HttpServletRequest request, Blog entity) {
		super.handler4Create(request, entity);
		if (null == entity.getCreater() || "".equals(entity.getCreater())) {
			entity.setCreater(SecurityUtils.getSubject().getPrincipal()
					.toString());
		}
		return true;
	}

	@Override
	protected Long[] parseDeleteIDS(HttpServletRequest request) {
		return StringUtil.parseIdstr(request.getParameter("ids"));
	}

}
