package com.micmiu.mvc.hazel.core.authc.shiro;

/**
 * 权限接口
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * 
 */
public interface IPermission {

	/**
	 * 获取权限字符串
	 * 
	 * @return
	 */
	public String getPermOperation();
}
