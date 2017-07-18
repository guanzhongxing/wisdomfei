<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.admintwo.model.User"%>
<head>
<meta charset="utf-8">
<title>代码分享网-源码库、知识库、视频库、项目库，致力于为程序员提供最有帮助的代码</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="keywords" content="代码库，代码分享网">
<meta http-equiv="Content-Type" content="text/html" ; charset="utf-8">
<meta name="description" content="代码库-代码分享网，致力于为程序员提供最有帮助的代码">
<Link rel="shortcut icon" href="res/images/logo.ico">
<link rel="stylesheet" href="res/layui/css/layui.css">
<link rel="stylesheet" href="res/css/global.css">

<%
	if ((User) session.getAttribute("user") != null) {
		if (((User) session.getAttribute("user")).getStyle() == 1) {
%>
<link rel="stylesheet" href="res/css/full.css">
<%
	}
	}
%>
<script>
	var _hmt = _hmt || [];
	(function() {
		var hm = document.createElement("script");
		hm.src = "https://hm.baidu.com/hm.js?49889d9149ea3475919030325b18afef";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s);
	})();
</script>

</head>
