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
	<h2  class="page-title">代码搜索</h2>
	<div class="layui-form layui-form-pane">
		<form method="post" action="user/login">
			<div class="layui-form-item">
				<label class="layui-form-label">代码类型</label>
				<div class="layui-input-block">
				<%! int num = 0; %>
	      		<% 	
	      			String sort = request.getParameter("sort");
					if(sort!= null){
						num = Integer.parseInt(sort);
					}else{
						num = 0;
					}
				%>
	      		<input type="radio" name="sort" value="0" title="全部" checked>
	      		<input type="radio" name="sort" value="1" <%=num==1?"checked":""%> title="完整项目">
	      		<input type="radio" name="sort" value="2" <%=num==2?"checked":""%> title="单一功能">
	      		<input type="radio" name="sort" value="3" <%=num==3?"checked":""%> title="界面模板">
	      		<input type="radio" name="sort" value="4" <%=num==4?"checked":""%> title="js特效">
			    </div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">所属分类</label>
				<div class="layui-input-block">
				<%! int l_num = 0; %>
	      		<% 	
	      			String label = request.getParameter("label");
					if(label!= null){
						l_num = Integer.parseInt(label);
					}else{
						l_num = 0;
					}
				%>
			      <input type="radio" name="label" value="0" title="全部" checked>
			      <input type="radio" name="label" value="30" title="java" <%=l_num==30?"checked":""%>>
				  <input type="radio" name="label" value="31" title="c" <%=l_num==31?"checked":""%>>
			      <input type="radio" name="label" value="32" title="c++" <%=l_num==32?"checked":""%>>
			      <input type="radio" name="label" value="33" title="c#" <%=l_num==33?"checked":""%>>
			      <input type="radio" name="label" value="34" title="PHP" <%=l_num==34?"checked":""%>>
			      <input type="radio" name="label" value="35" title="Python" <%=l_num==35?"checked":""%>>
			      <input type="radio" name="label" value="36" title="前端" <%=l_num==36?"checked":""%>>
			      <input type="radio" name="label" value="37" title="后端" <%=l_num==37?"checked":""%>>
			      <input type="radio" name="label" value="38" title="数据库" <%=l_num==38?"checked":""%>>
			      <input type="radio" name="label" value="39" title="服务器" <%=l_num==39?"checked":""%>>
			      <input type="radio" name="label" value="40" title="其他" <%=l_num==40?"checked":""%>>
			    </div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">所需积分</label>
				<div class="layui-input-block">
				  <input type="radio" name="jifen" value="999" title="全部" checked>
				  <input type="radio" name="jifen" value="0" title="0">
			      <input type="radio" name="jifen" value="1" title="1-5">
				  <input type="radio" name="jifen" value="2" title="6-10">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">关键词</label>
				<div class="layui-input-inline">
					<input type="text" id="name" name="name" required value="<%=request.getParameter("keyword")==null?"":request.getParameter("keyword")%>"
						lay-verify="name" autocomplete="off" class="layui-input">
				</div>
				<button class="layui-btn" lay-filter="search" lay-submit>立即搜索</button>
			</div>
		</form>
	</div>
  </div>
  
  <br/>
  
  <div class="fly-panel" pad20 id="searchList">
  	<h2 class="page-title">搜索结果：<label id="searchNum"></label></h2>
	<ul class="fly-list" id="searchResourcesList">
        <!-- <li class="fly-list-li">
          <a href="user_home?id=1" class="fly-list-avatar" >
            <img src="res/images/userImg/user1.jpg" title="飞哥">
          </a>
          <h2 class="fly-tip">
            <a href="resource_detail?id=1">代码库公告、原创代码、高质量代码总结</a>
            <span class="fly-tip-stick">公告</span>
            <span class="fly-tip-jie">原创</span>
            <span class="fly-tip-jing">精</span>
          </h2>
          <p>
            <span><a href="user_home?id=6">飞哥</a></span>
            <span>2017-04-23</span>
            <span class="fly-list-hint"> 
              <i class="iconfont" title="评论">&#xe60c;</i> <label id="res_comments_num">0</label>
              <i class="layui-icon" title="下载" style="font-size: 14px;">&#xe601;</i> <label id="res_download_num">0</label>
              <i class="layui-icon" title="收藏" style="font-size: 14px;">&#xe600;</i> <label id="res_keep_num">0</label>
            </span>
          </p>
        </li> -->
    </ul>
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