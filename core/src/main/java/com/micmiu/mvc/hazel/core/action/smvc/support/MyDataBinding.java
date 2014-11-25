package com.micmiu.mvc.hazel.core.action.smvc.support;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.micmiu.mvc.hazel.core.action.vo.CustomTimestampEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 自定义类型绑定
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class MyDataBinding implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		SimpleDateFormat datetimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		datetimeFormat.setLenient(false);

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
		binder.registerCustomEditor(java.sql.Timestamp.class,
				new CustomTimestampEditor(datetimeFormat, true));
	}
}
