<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder,java.net.URLEncoder"%>  
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>

<body>

<%@ include file="../public/header.jsp"%>

<input type="hidden" id="keyword" value="<%=request.getParameter("keyword")==null?"":request.getParameter("keyword")%>">

<div class="main layui-clear">
  <div class="fly-panel" pad20>
	<h2 class="page-title">代码库网站协议</h2>
	1、资源的所有权益归上传用户所有；<br/>
	2、代码库仅提供分享平台，并不能对任何下载资源负责；<br/>
	3、下载资源中如有侵权或不适当内容，请与我们联系（admin@admintwo.com）；<br/>
	4、本站不保证本站提供的资源的准确性、安全性和完整性, 同时也不承担用户因使用这些下载资源对自己和他人造成任何形式的伤害或损失；<br/>
	5、代码库为个人站点，为非营利性站点，所有资源均是网上搜集或私下交流学习之用。<br/>
  </div>
</div>


<%@ include file="../public/footer.jsp"%>

<script>
layui.config({
  base: 'res/modules/public/'
}).use('search');
</script>

</body>
</html>