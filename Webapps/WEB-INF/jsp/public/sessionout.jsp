<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.admintwo.model.User"%>
<%
	if (((User) session.getAttribute("user")) == null) {
		response.sendRedirect("user_login");
		return;
	}
%>