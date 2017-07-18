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
	<h2  class="page-title">项目库：正在计划实施中。。。</h2>
	<div class="layui-form layui-form-pane">
		项目库介绍：项目库目前并没有决定以何种方式做出来，和小伙伴们讨论了一下，决定了二种方案<br/>
		方案一：整理可用的完整项目提供下载。<br/><br/>
		方案二：用户可在线制作简单java项目（包括前台+后台+数据库），并生成源码提供下载（实现很难）<br/><br/>
		如果你有更好的建议，欢迎加群：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=9d09b3b9cbcf584c9bab6b2576fb1c2aa6ca84bebb8f44e464382398e7d4acc5"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="代码库" title="代码库"></a><br/><br/>
	</div>
  </div>

</div>


<%@ include file="../public/footer.jsp"%>

<script>
layui.config({
  base: 'res/modules/video/'
}).use('index');
</script>

</body>
</html>