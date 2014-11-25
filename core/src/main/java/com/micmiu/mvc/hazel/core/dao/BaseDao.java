package com.micmiu.mvc.hazel.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.micmiu.mvc.hazel.core.action.vo.SortType;

/**
 * 
 * 基础泛型DAO
 * 
 * @param <E> 实体类
 * @param <ID> 实体类ID类型
 * 
 * @author <a href="http://www.micmiu.com">Michael</a>
 * @time Create on 2013-7-3 下午1:20:23
 * @version 1.0
 */
public interface BaseDao<E, ID extends Serializable> {

	/**
	 * 创建实体对象.
	 * 
	 * @param entity
	 */
	public ID create(E entity);

	/**
	 * 更新实体对象.
	 * 
	 * @param entity
	 */
	public void update(E entity);

	/**
	 * 按ID删除实体对象.
	 * 
	 * @param id 实体id
	 */
	public void delete(ID id);

	/**
	 * 按ID批量删除实体对象.
	 * 
	 * @param ids 实体id数组
	 */
	public void delete(ID[] ids);

	/**
	 * 按ID返回实体对象.
	 * 
	 * @param id
	 * @return T 按返回实体。
	 */
	public E find(ID id);

	/**
	 * 按属性查找实体对象.
	 * 
	 * @param propertyName 字段名
	 * @param value 字段值
	 * @return E 实体对象
	 */
	public E find(String propertyName, Object value);

	/**
	 * 查询所有对象实体.
	 * 
	 * @return List<E> 所有对象实体
	 */
	public List<E> queryAll();

	/**
	 * 按字段查找实体列表.
	 * 
	 * @param propertyName 字段名
	 * @param value 字段值
	 * @return 实体列表
	 */
	public List<E> query(String propertyName, Object value);

	/**
	 * 按字段查找实体列表.
	 * 
	 * @param properties 查询字段 <字段名,字段值>
	 * @return 实体列表
	 */
	public List<E> query(Map<String, Object> properties);

	/**
	 * 按字段查找实体列表.
	 * 
	 * @param properties 查询字段 <字段名,字段值>
	 * @param sortProperties 排序字段 <字段名,DESC/ASC>
	 * @return 实体列表
	 */
	public List<E> query(Map<String, Object> properties,
			Map<String, SortType> sortProperties);

	/**
	 * 按字段查找实体列表.
	 * 
	 * @param properties 查询字段 <字段名,字段值>
	 * @param sortProperties 排序字段 <字段名,DESC/ASC>
	 * @param start 记录开始数
	 * @param rows 查询记录数
	 * @return 实体列表
	 */
	public List<E> query(Map<String, Object> properties,
			Map<String, SortType> sortProperties, int start, int rows);

	/**
	 * 获取所有记录数.
	 * 
	 * @return 所有记录数.
	 */
	public Long countAll();

	/**
	 * 按条件获取记录数.
	 * 
	 * @param properties 查询字段 <字段名,字段值>
	 * @return 记录数.
	 */
	public Long count(Map<String, Object> properties);

}
