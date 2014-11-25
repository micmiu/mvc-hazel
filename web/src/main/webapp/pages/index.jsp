<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <div style="align:center;font-size:14px;margin:20px">
	您好,<shiro:principal property="name"/>!! <a href="${ctx}/logout.do" style="text-decoration:none">注销</a>
 </div>