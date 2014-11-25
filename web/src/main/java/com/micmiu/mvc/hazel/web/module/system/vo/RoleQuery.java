package com.micmiu.mvc.hazel.web.module.system.vo;

import com.micmiu.mvc.hazel.core.action.vo.annotation.QueryPropery;
import com.micmiu.mvc.hazel.core.action.vo.easyui.DatagridQuery;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class RoleQuery extends DatagridQuery {

	private String roleName;

	@QueryPropery
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
