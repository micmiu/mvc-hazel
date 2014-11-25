package com.micmiu.mvc.hazel.web.module.demo.action;

import java.util.List;

import com.micmiu.mvc.hazel.web.entity.Blog;
import com.micmiu.mvc.hazel.web.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * Description: data grid demo
 * 
 * @author <a href="http://www.micmiu.com">Michael</a>
 * @see <a href="http://www.micmiu.com">http://www.micmiu.com</a>
 * @time Create on 2013-6-3 下午12:55:39
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/demo/datagrid.do")
public class DataGridAction {

	private static final String PREFIX = "demo.dg.blog";

	@Autowired
	private BlogService blogService;

	@RequestMapping(params = { "method=dg4base" })
	public String list4base(Model model) {
		List<Blog> list = blogService.queryAll();
		model.addAttribute("blogs", list);
		return PREFIX + ".list.base";
	}

	@RequestMapping(params = { "method=dg4tb" })
	public String list4tb(Model model) {
		List<Blog> list = blogService.queryAll();
		model.addAttribute("blogs", list);
		return PREFIX + ".list.tb";
	}

	@RequestMapping(params = { "method=dg4export" })
	public String list4export(Model model) {
		List<Blog> list = blogService.queryAll();
		model.addAttribute("blogs", list);
		return PREFIX + ".list.export";
	}
}
