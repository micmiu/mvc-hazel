package com.micmiu.mvc.hazel.core.action.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.micmiu.mvc.hazel.core.action.vo.easyui.PropertyGridData;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * DataGrid 数据转换工具类
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@SuppressWarnings("rawtypes")
public class GridBeanTools {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static List<PropertyGridData> convertPropertyGridData(Object bean) {
		return convertPropertyGridData(bean, null);
	}

	public static List<PropertyGridData> convertPropertyGridData(Object bean,
			Map<String, String> showMap) {
		List<PropertyGridData> volist = new ArrayList<PropertyGridData>();
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor());

			// JSONObject obj = JSONObject.fromObject(bean, jsonConfig);

			JSONObject obj = JSONObject.fromObject(mapper
					.writeValueAsString(bean));
			Iterator it = obj.keys();

			while (it.hasNext()) {
				String key = (String) it.next();
				Object valObj = obj.get(key);
				String value = null;
				if (null == valObj
						|| (valObj instanceof JSONObject && ((JSONObject) valObj)
								.isNullObject())) {
					value = "";
				} else {
					value = valObj.toString();
				}

				if (null == showMap) {
					volist.add(new PropertyGridData(key, value));
				} else {
					if (null != showMap.get(key)) {
						volist.add(new PropertyGridData(showMap.get(key), value));
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return volist;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Blog blog = new Blog();
		blog.setAuthor("Michael");
		blog.setCategory("分类");
		blog.setUrl("www.micmiu.com");

		ObjectMapper mapper = new ObjectMapper();

		System.out.println(JSONObject.fromObject(blog));
		System.out.println(mapper.writeValueAsString(blog));

		List<PropertyGridData> list = convertPropertyGridData(blog);

		for (PropertyGridData data : list) {
			System.out.println(data);
		}
	}

	static class Blog {
		private String title;

		private String author;

		private String category;

		private String url;

		private String other;

		private String creater;

		private Date publishDate;

		public String getTitle() {
			return title;
		}

		public String getAuthor() {
			return author;
		}

		public String getCategory() {
			return category;
		}

		public String getUrl() {
			return url;
		}

		public String getOther() {
			return other;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setOther(String other) {
			this.other = other;
		}

		@JsonSerialize(using = CustomDateSerializer.class)
		public Date getPublishDate() {
			return publishDate;
		}

		public void setPublishDate(Date publishDate) {
			this.publishDate = publishDate;
		}

		public String getCreater() {
			return creater;
		}

		public void setCreater(String creater) {
			this.creater = creater;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}
