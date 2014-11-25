package com.micmiu.mvc.hazel.core.action.smvc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.micmiu.mvc.hazel.core.action.vo.RefAnnoTools;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.micmiu.mvc.hazel.core.action.smvc.views.ViewConstant;
import com.micmiu.mvc.hazel.core.action.vo.Page;
import com.micmiu.mvc.hazel.core.action.vo.easyui.DatagridPage;
import com.micmiu.mvc.hazel.core.action.vo.easyui.DatagridQuery;

/**
 * {@link BaseAction} 进一步简单实现了基本CRUD+Export功能
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * 
 * @param <E> 实体类
 * @param <ID> 注解
 * @param <Q> 查询vo
 */

public abstract class BaseHandlerAction<E, V, ID extends Serializable, Q extends DatagridQuery>
		extends BaseAction<E, V, ID, Q> {

	public BaseHandlerAction() {
		super();
	}

	@Override
	protected void checkAuth(String operation) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.checkPermission(this.getPermssionName() + ":" + operation);

	}

	/**
	 * 以小写类名作为权限别名
	 */
	@Override
	protected String getPermssionName() {
		return this.clazz.getSimpleName().toLowerCase();
	}

	/**
	 * 以小写类名作为form属性
	 */
	@Override
	protected String getModelAttr() {
		return this.clazz.getSimpleName().toLowerCase();
	}

	@Override
	protected boolean handler4Create(HttpServletRequest request, E entity) {
		return true;
	}

	@Override
	protected void handler4CreateShow(HttpServletRequest request, E entity,
			Model model) {

	}

	@Override
	protected void handler4UpdateShow(HttpServletRequest request, E entity,
			Model model) {

	}

	@Override
	protected boolean handler4Save(HttpServletRequest request, E entity) {
		return true;
	}

	@Override
	protected boolean handler4Update(HttpServletRequest request, E entity) {
		return true;
	}

	@Override
	protected boolean allowDeleteData(ID id, StringBuffer msgkey) {
		return true;
	}

	@Override
	protected boolean allowDeleteData(ID[] ids, StringBuffer msgkey) {
		return true;
	}

	@Override
	protected void handler4Export(HttpServletRequest request,
			Map<String, Object> model) {
		model.put(ViewConstant.EXPORT_FILENAME, messageSource.getMessage(
				this.clazz.getSimpleName().toLowerCase() + "."
						+ ViewConstant.EXPORT_FILENAME, null,
				RequestContextUtils.getLocale(request)));
		model.put(ViewConstant.EXPORT_SHEETNAME, messageSource.getMessage(
				this.clazz.getSimpleName().toLowerCase() + "."
						+ ViewConstant.EXPORT_SHEETNAME, null,
				RequestContextUtils.getLocale(request)));
		model.put(ViewConstant.EXPORT_TITLE, messageSource.getMessage(
				this.clazz.getSimpleName().toLowerCase() + "."
						+ ViewConstant.EXPORT_TITLE, null,
				RequestContextUtils.getLocale(request)));

		Map<String, String> showMap = new LinkedHashMap<String, String>();
		RefAnnoTools.getBeanAnnoMap(this.clazz, showMap, messageSource,
				RequestContextUtils.getLocale(request));

		model.put(ViewConstant.EXPORT_COLUMN_MAP, showMap);

	}

	protected abstract V convertE2V(E e,HttpServletRequest request);

	@Override
	protected Page<V> convertPageE2V(Page<E> pageE,HttpServletRequest request) {
		Page<V> pageV = new DatagridPage<V>();
		try {
			BeanUtils.copyProperties(pageV, pageE);
			List<V> vlist = new ArrayList<V>();
			for (E e : pageE.getPageList()) {
				vlist.add(convertE2V(e,request));
			}
			pageV.setPageList(vlist);
		} catch (Exception e) {
			logger.warn("convert Page<E> to Page<V> error:", e);
		}
		return pageV;
	}
}