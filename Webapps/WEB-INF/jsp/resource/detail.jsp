<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.admintwo.model.User"%>
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>

<body>

<%@ include file="../public/header.jsp"%>
<input type="hidden" value="<%=request.getParameter("id")%>" id="resourcesId"/>
<%if ((User) session.getAttribute("user") == null) {%>
<input type="hidden" value="0" id="user_id"/>
<%}else{ %>
<input type="hidden" value="<%=((User) session.getAttribute("user")).getId()%>" id="user_id"/>
<input type="hidden" value="<%=((User) session.getAttribute("user")).getJifen()%>" id="user_jifen"/>
<input type="hidden" value="<%=((User) session.getAttribute("user")).getQq()%>" id="user_qq"/>
<%} %>

<div class="main layui-clear">
  <div class="wrap">
    <div class="content detail">
      <div id="resource_detail" class="fly-panel detail-box">
        <!-- 打赏部分 -->
        <div id="dashang">
	      	<%@ include file="../public/dashang.jsp"%>
        </div>
        <!-- 资源详细描述 -->
      </div> 

	  <!-- 评论部分 -->
      <%@ include file="../public/comments.jsp"%>
	  
    </div>
  </div>
  
  <div class="edge">
    <dl class="fly-panel fly-list-one" id="commentstop"> 
      <dt class="fly-panel-title">本月评论榜</dt>
    </dl>
    
    <dl class="fly-panel fly-list-one" id="downloadtop"> 
      <dt class="fly-panel-title">本月下载榜</dt>
    </dl>
    
    <dl class="fly-panel fly-list-one" id="keeptop"> 
      <dt class="fly-panel-title">本月收藏榜</dt>
    </dl>
  </div>
</div>

<%@ include file="../public/footer.jsp"%>
<script>
layui.config({
  base: 'res/modules/resource/'
}).use('detail');
</script>
</body>
</html>