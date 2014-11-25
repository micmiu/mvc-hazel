package com.micmiu.mvc.hazel.web.module.demo.action;

import com.micmiu.mvc.hazel.web.module.demo.vo.FormBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Controller
@RequestMapping(value = "/demo/index.do")
public class DemoAction {

	private static final String PREFIX = "demo";

	@RequestMapping()
	public String index() {
		return PREFIX + ".index";

	}

	@RequestMapping(params = { "method=showForm" })
	public String showForm(FormBean bean) {

		return PREFIX + ".form.easyui";

	}

	@RequestMapping(params = { "method=save" })
	@ResponseBody
	public String save(FormBean bean, RedirectAttributes redirectAttributes) {
		System.out.println("--->:" + bean);
		String message = "bean info:" + bean;
		redirectAttributes.addFlashAttribute("message", message);
		return message;
	}

}
