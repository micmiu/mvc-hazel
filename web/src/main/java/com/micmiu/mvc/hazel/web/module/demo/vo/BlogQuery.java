package com.micmiu.mvc.hazel.web.module.demo.vo;

import com.micmiu.mvc.hazel.core.action.vo.annotation.QueryPropery;
import com.micmiu.mvc.hazel.core.action.vo.easyui.DatagridQuery;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class BlogQuery extends DatagridQuery {

	private String title;

	@QueryPropery
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
