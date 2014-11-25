package com.micmiu.mvc.hazel.web.utils;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.micmiu.mvc.hazel.web.entity.Menu;
import com.micmiu.mvc.hazel.web.module.system.vo.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

/**
 * 菜单权限 处理类
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class MenuPermUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(MenuPermUtil.class);

	/**
	 * 转化用户菜单
	 * 
	 * @param allMenus 所有菜单
	 * @param userMenus 前端展现的菜单结果
	 * @param ids 用户拥有的菜单
	 * @param contextPath
	 */
	public static void parseUserMenu(List<Menu> allMenus,
			List<TreeNode> userMenus, Set<Long> ids, String contextPath,
			MessageSource messageSource, Locale locale) {
		for (Menu menu : allMenus) {
			if (ids.contains(menu.getId())) {
				userMenus.add(recParseMenu(ids, menu, contextPath,
						messageSource, locale));
			}
		}
	}

	/**
	 * 递归用户菜单
	 * 
	 * @param ids
	 * @param menu
	 * @param contextPath
	 * @param messageSource
	 * @param locale
	 * @return
	 */
	private static TreeNode recParseMenu(Set<Long> ids, Menu menu,
			String contextPath, MessageSource messageSource, Locale locale) {
		TreeNode vo = new TreeNode();
		vo.setId(menu.getId() + "");
		String text = "";
		String menuName = messageSource.getMessage(menu.getMenuName(), null,
				menu.getMenuName(), locale);
		if (null != menu.getMenuURL() && !"".equals(menu.getMenuURL())) {

			if (menu.getMenuURL().startsWith("/")) {
				text = "<a href ='" + contextPath + menu.getMenuURL() + "'>"
						+ menuName + "</a>";
			} else {
				text = "<a href ='" + contextPath + "/" + menu.getMenuURL()
						+ "'>" + menuName + "</a>";
			}
		} else {
			text = "<a href='#'>" + menuName + "</a>";
		}
		vo.setText(text);
		if (!menu.getChildren().isEmpty()) {
			for (Menu childMenu : menu.getChildren()) {
				if (ids.contains(childMenu.getId())) {
					vo.getChildren().add(
							recParseMenu(ids, childMenu, contextPath,
									messageSource, locale));
				}
			}
		}
		return vo;
	}

	/**
	 * 转化菜单为HTML
	 * 
	 * @param treeNodeList
	 * @return
	 */
	public static String parseMenuHTML(List<TreeNode> treeNodeList) {
		StringBuffer menuHTML = new StringBuffer("<ul>");
		for (TreeNode node : treeNodeList) {
			MenuPermUtil.parseMenuHTML(menuHTML, node);
		}
		menuHTML.append("</ul>");
		logger.debug(menuHTML.toString());
		return menuHTML.toString();
	}

	/**
	 * 递归把菜单转为HTML
	 * 
	 * @param menuHTML
	 * @param node
	 */
	private static void parseMenuHTML(StringBuffer menuHTML, TreeNode node) {
		menuHTML.append("<li>").append(node.getText());
		if (!node.getChildren().isEmpty()) {
			menuHTML.append("<ul>");
			for (TreeNode children : node.getChildren()) {
				parseMenuHTML(menuHTML, children);
			}
			menuHTML.append("</ul>");
		}
		menuHTML.append("</li>");
	}

}
