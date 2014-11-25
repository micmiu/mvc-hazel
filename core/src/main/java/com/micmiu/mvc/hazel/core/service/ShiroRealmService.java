package com.micmiu.mvc.hazel.core.service;

import java.util.List;

import com.micmiu.mvc.hazel.core.authc.shiro.IPermission;

/**
 * 用户鉴权的service
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * 
 */
public interface ShiroRealmService {

	/**
	 * 找到登陆用户的信息[登陆名、姓名、密码]
	 * 
	 * @param username
	 * @return
	 */
	public String[] findLoginUser(String username);

	/**
	 * 查询用户的权限列表
	 * 
	 * @param username
	 * @return
	 */
	public List<IPermission> queryUserPermission(String username);
}
