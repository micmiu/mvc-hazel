package com.micmiu.mvc.hazel.core.action.vo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.micmiu.mvc.hazel.core.action.vo.SortType;

/**
 * 排序属性.
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SortProperty {

	SortType type() default SortType.ASC;
}
