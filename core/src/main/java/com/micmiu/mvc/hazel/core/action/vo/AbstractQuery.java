package com.micmiu.mvc.hazel.core.action.vo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.micmiu.mvc.hazel.core.action.vo.annotation.QueryPropery;
import org.apache.commons.lang3.StringUtils;

import com.micmiu.mvc.hazel.core.action.vo.annotation.SortProperty;

/**
 * 抽象查询类.
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public abstract class AbstractQuery {

	/**
	 * 获取子类所有排序字段及排序顺序Map. 需要生成的字段值可用@SortProperty标示.
	 * 
	 * @return Map<String, String> 排序字段Map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, SortType> getSortProperties() {
		// 排序字段有先后顺序, 必须采用LinkedHashMap
		Map<String, SortType> sortProperties = new LinkedHashMap<String, SortType>();
		List<Field> properties = RefAnnoTools.getAllPublicFields(getClass(),
				SortProperty.class);
		for (Field p : properties) {
			sortProperties.put(getQueryPropertyName(p.getName()), p
					.getAnnotation(SortProperty.class).type());
		}
		return sortProperties;
	}

	/**
	 * 获取子类所有查询字段值Map. 需要生成的字段值可用@QueryProperty标示.
	 * 
	 * @return Map<String, Object> 查询字段Map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getQueryProperties() {
		Map<String, Object> propertyValues = new HashMap<String, Object>();
		List<Field> properties = RefAnnoTools.getAllPublicFields(getClass(),
				QueryPropery.class);
		for (Field p : properties) {
			Object value = RefAnnoTools.getFieldValue(this, p.getName(), true);
			if (null != value) {
				propertyValues.put(getQueryPropertyName(p.getName()),
						RefAnnoTools.getFieldValue(this, p.getName(), true));
			}
		}
		return propertyValues;
	}

	/**
	 * 获取查询Field名称. 优先处理QueryPropery注解name,若未配置,用Field名称代替.
	 * 
	 * @param fieldName Field名称
	 * @return 查询属性名称
	 */
	protected String getQueryPropertyName(String fieldName) {
		String queryName = fieldName;
		QueryPropery qp = (QueryPropery) RefAnnoTools.getFieldAnnotation(
				getClass(), fieldName, QueryPropery.class);
		if (qp != null && StringUtils.isNotEmpty(qp.name())) {
			queryName = qp.name();
		}
		return queryName;
	}
}
