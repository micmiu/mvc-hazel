package com.micmiu.mvc.hazel.web.module.system.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * 
 */
@Controller
public class IndexAction {

	@RequestMapping("/index.do")
	public String index(HttpServletRequest req) {
		return "system.index";
	}

}
