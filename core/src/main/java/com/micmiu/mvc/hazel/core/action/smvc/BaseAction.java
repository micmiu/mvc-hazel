package com.micmiu.mvc.hazel.core.action.smvc;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.micmiu.mvc.hazel.core.action.ActionConstant;
import com.micmiu.mvc.hazel.core.action.vo.AbstractQuery;
import com.micmiu.mvc.hazel.core.action.vo.OperationType;
import com.micmiu.mvc.hazel.core.action.vo.Page;
import com.micmiu.mvc.hazel.core.action.vo.RefAnnoTools;
import com.micmiu.mvc.hazel.core.action.vo.easyui.DatagridQuery;
import com.micmiu.mvc.hazel.core.action.vo.easyui.PropertyGridData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.micmiu.mvc.hazel.core.action.smvc.views.CsvView;
import com.micmiu.mvc.hazel.core.action.smvc.views.JxlExcelView;
import com.micmiu.mvc.hazel.core.action.smvc.views.PdfiText5View;
import com.micmiu.mvc.hazel.core.action.smvc.views.PoiExcelView;
import com.micmiu.mvc.hazel.core.action.smvc.views.ViewConstant;
import com.micmiu.mvc.hazel.core.action.vo.GridBeanTools;
import com.micmiu.mvc.hazel.core.service.BaseService;

/**
 * 抽象Action 实现基本操作：CRUD+Export
 * <table><tr><td>method=showList</td><td>一般列表</td></tr>
 * <tr><td>method=pageQuery</td><td>分页列表</td></tr>
 * <tr><td>method=create</td><td>创建</td></tr>
 * <tr><td>method=read</td><td>读取</td></tr>
 * <tr><td>method=update</td><td>更新</td></tr>
 * <tr><td>method=delete</td><td>删除</td></tr>
 * <tr><td>method=export</td><td>导出</td></tr>
 * <tr><td>method=deleteBatch</td><td>批量删除</td></tr>
 * <tr><td>method=getViewData</td><td>读取数据返回propertygrid格式</td></tr>
 * </table>
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * 
 * @param <E> 实体类
 * @param <V> 展现的VO
 * @param <ID> 主键
 * @param <Q> 查询vo
 */
public abstract class BaseAction<E, V, ID extends Serializable, Q extends DatagridQuery> {

	protected static Logger logger = LoggerFactory.getLogger(BaseAction.class);

	@Autowired
	protected MessageSource messageSource;

	/**
	 * 实体Class.
	 */
	protected final Class<E> clazz;

	@SuppressWarnings("unchecked")
	public BaseAction() {

		this.clazz = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

	}

	/**
	 * 获取当前的service
	 * 
	 * @return
	 */
	public abstract BaseService<E, ID> getBaseService();

	/**
	 * 权限检查
	 * 
	 * @param operation
	 */
	protected abstract void checkAuth(String operation);

	/**
	 * 获取模块的权限名称
	 * 
	 * @return
	 */
	protected abstract String getPermssionName();

	/**
	 * 视图前缀
	 * 
	 * @return
	 */
	protected abstract String getViewPrefix();

	/**
	 * 返回重定向view
	 * 
	 * @return
	 */
	protected abstract String getRedirectView();

	/**
	 * 页面form attr 属性名称
	 * 
	 * @return
	 */
	protected abstract String getModelAttr();

	/**
	 * 判断数据是否可以删除
	 * 
	 * @param id
	 * @param msgkey
	 * @return true -> 可以删除 false->不能删除
	 */
	protected abstract boolean allowDeleteData(ID id, StringBuffer msgkey);

	/**
	 * 判断数据是否可以删除
	 * 
	 * @param ids
	 * @param msgkey
	 * @return true -> 可以删除 false->不能删除
	 */
	protected abstract boolean allowDeleteData(ID[] ids, StringBuffer msgkey);

	/**
	 * 转化批量删除的主键
	 * 
	 * @param request
	 * @param entity
	 */
	protected abstract ID[] parseDeleteIDS(HttpServletRequest request);

	/**
	 * 添加显示前自定义处理过程
	 * 
	 * @param request
	 * @param entity
	 */
	protected abstract void handler4CreateShow(HttpServletRequest request,
			E entity, Model model);

	/**
	 * 更新显示前自定义处理过程
	 * 
	 * @param request
	 * @param entity
	 */
	protected abstract void handler4UpdateShow(HttpServletRequest request,
			E entity, Model model);

	/**
	 * 
	 * 
	 * @param request
	 * @param entity
	 */
	/**
	 * 保存（添加或者更新）自定义处理过程
	 * 
	 * @param request
	 * @param entity
	 * @return 如果是true 父类中执行更新实体类 false 父类中不执行更新实体类
	 */
	protected abstract boolean handler4Save(HttpServletRequest request, E entity);

	/**
	 * 添加操作的自定义处理过程
	 * 
	 * @param request
	 * @param entity
	 * @return 如果是true 父类中执行添加实体类 false 父类中不执行添加实体类
	 */
	protected abstract boolean handler4Create(HttpServletRequest request,
			E entity);

	/**
	 * 更新操作的自定义处理过程
	 * 
	 * @param request
	 * @param entity
	 * @return 如果是true 父类锺更新实体类 false 父类中不在更新实体类
	 * 
	 */
	protected abstract boolean handler4Update(HttpServletRequest request,
			E entity);

	/**
	 * 导出前自定义处理过程
	 * 
	 * @param request
	 * @param model
	 */
	protected abstract void handler4Export(HttpServletRequest request,
			Map<String, Object> model);

	/**
	 * 分页查询的结果转为自定义的vo
	 * 
	 * @param pageV
	 * @param pageE
	 */
	protected abstract Page<V> convertPageE2V(Page<E> pageE,
			HttpServletRequest request);

	/**
	 * 获取空实体对象
	 * 
	 * @return
	 */
	protected E getNewInstance() {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 显示列表页面
	 * 
	 * @return
	 */
	@RequestMapping(params = { "method=showList" })
	public String showList() {
		checkAuth(OperationType.READ.getValue());
		return getViewPrefix() + ".list";
	}

	@RequestMapping(params = { "method=pageQuery" })
	@ResponseBody
	public Page<V> pageQuery(Model model, Q query, HttpServletRequest request) {
		checkAuth(OperationType.READ.getValue());
		Page<E> pageE = doPagedQuery(query, getBaseService(), query.getPage(),
				query.getRows());
		return convertPageE2V(pageE, request);

	}

	@RequestMapping(params = { "method=read" })
	public String read(ID id, Model model) {
		checkAuth(OperationType.READ.getValue());
		model.addAttribute(getModelAttr(), getBaseService().find(id));
		return getViewPrefix() + ".view";
	}

	@RequestMapping(params = { "method=getViewData" })
	@ResponseBody
	public Map<String, Object> getViewData(ID id, HttpServletRequest request) {
		checkAuth(OperationType.READ.getValue());
		Map<String, String> showMap = new HashMap<String, String>();
		RefAnnoTools.getBeanAnnoMap(clazz, showMap, messageSource,
				RequestContextUtils.getLocale(request));

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<PropertyGridData> list = new ArrayList<PropertyGridData>();
		list = GridBeanTools.convertPropertyGridData(getBaseService().find(id),
				showMap);
		retMap.put("total", list.size());
		retMap.put("rows", list);
		return retMap;
	}

	@RequestMapping(params = { "method=showForm" })
	public String showForm(HttpServletRequest request, ID id, Model model) {
		E entity;
		if (null != id) {
			checkAuth(OperationType.UPDATE.getValue());
			entity = getBaseService().find(id);
			this.handler4UpdateShow(request, entity, model);
			model.addAttribute("showFormType", "update");
		} else {
			checkAuth(OperationType.CREATE.getValue());
			entity = getNewInstance();
			this.handler4CreateShow(request, entity, model);
			model.addAttribute("showFormType", "create");
		}
		model.addAttribute(this.getModelAttr(), entity);
		return getViewPrefix() + ".form";
	}

	@RequestMapping(params = { "method=create" })
	@ResponseBody
	public String create(E entity, HttpServletRequest request) {
		checkAuth(OperationType.CREATE.getValue());
		if (this.handler4Create(request, entity)) {
			getBaseService().create(entity);
		}
		String message = messageSource.getMessage(ActionConstant.MSG_SUCC, null,
				RequestContextUtils.getLocale(request));
		return message;
	}

	@RequestMapping(params = { "method=update" })
	@ResponseBody
	public String update(E entity, HttpServletRequest request) {
		checkAuth(OperationType.UPDATE.getValue());
		if (this.handler4Update(request, entity)) {
			getBaseService().update(entity);
		}
		String message = messageSource.getMessage(ActionConstant.MSG_SUCC, null,
				RequestContextUtils.getLocale(request));
		return message;
	}

	@RequestMapping(params = "method=deleteBatch")
	@ResponseBody
	public String deleteBatch(HttpServletRequest request) {
		checkAuth(OperationType.DELETE.getValue());
		String message = null;
		try {
			ID[] ids = this.parseDeleteIDS(request);
			StringBuffer msgkey = new StringBuffer();
			if (this.allowDeleteData(ids, msgkey)) {
				getBaseService().delete(ids);
				message = messageSource.getMessage(ActionConstant.MSG_SUCC,
						null, RequestContextUtils.getLocale(request));
			} else {
				message = messageSource.getMessage(msgkey.toString(), null,
						RequestContextUtils.getLocale(request));
			}
		} catch (Exception e) {
			message = messageSource.getMessage(ActionConstant.MSG_FAILED, null,
					RequestContextUtils.getLocale(request));
		}
		return message;
	}

	@RequestMapping(params = { "method=delete" })
	public String delete(ID id, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		checkAuth(OperationType.DELETE.getValue());
		StringBuffer msgkey = new StringBuffer();
		String message = null;
		if (allowDeleteData(id, msgkey)) {
			getBaseService().delete(id);
			message = messageSource.getMessage(ActionConstant.MSG_SUCC, null,
					RequestContextUtils.getLocale(request));
		} else {
			message = messageSource.getMessage(msgkey.toString(), null,
					RequestContextUtils.getLocale(request));
		}
		redirectAttributes.addFlashAttribute("message", message);
		return this.getRedirectView();
	}

	@RequestMapping(params = { "method=export" }, method = RequestMethod.POST)
	public ModelAndView export(HttpServletRequest request, Q pageQuery,
			String exportType) {
		checkAuth(OperationType.EXPORT.getValue());

		List<E> list = getBaseService().query(pageQuery.getQueryProperties(),
				pageQuery.getSortProperties());
		Map<String, Object> model = new HashMap<String, Object>();
		this.handler4Export(request, model);
		model.put(ViewConstant.EXPORT_ROW_DATA, list);
		if ("POI".equals(exportType)) {
			return new ModelAndView(new PoiExcelView(), model);
		} else if ("JXL".equals(exportType)) {
			return new ModelAndView(new JxlExcelView(), model);
		} else if ("CSV".equals(exportType)) {
			return new ModelAndView(new CsvView(), model);
		}
		return new ModelAndView(new PdfiText5View(), model);
	}

	/**
	 * 封装分页查询逻辑. 考虑到各子类实现对分页查询的权限配置需求的差异,作为protected方法,以避免该方法直接对外暴露.
	 * 
	 * @param q 查询对象
	 * @param s 查询Service
	 * @param pg 当前页
	 * @param rows 每页记录数
	 * @return Page<E> 页对象
	 */
	protected Page<E> doPagedQuery(AbstractQuery q, BaseService<E, ID> s,
			int pg, int rows) {
		Page<E> page = new Page<E>();
		page.setCurrentPage(pg);
		page.setRowsPerPage(rows);
		page.setPageList(s.query(q.getQueryProperties(), q.getSortProperties(),
				(pg - 1) * rows, rows));
		page.setTotalCount(s.count(q.getQueryProperties()));
		return page;
	}

}
