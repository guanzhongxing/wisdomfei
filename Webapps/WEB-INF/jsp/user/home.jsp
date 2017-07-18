<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.admintwo.model.User,com.admintwo.serviceImpl.UserServiceImpl" %>
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>

<body>

<%@ include file="../public/header.jsp"%>

<input type="hidden" value="<%=request.getParameter("id")%>" id="userId"/>

<div id="user_home_append" class="fly-home" style="background-image: url();">
  <!-- <img src="res/images/avatar/default.png" alt="代码库" id="homeImg">
  <h1>
    <span id="homeName">代码库</span>
    <i class="iconfont icon-nan" id="homeSex"></i> 
    <i class="iconfont icon-nv"></i>
    
    <span style="color:#5FB878;" id="homeLabel"></span>
    <span style="color:#c00;">（超级码农）</span>
    <span style="color:#5FB878;">（活雷锋）</span>
    
    <span id="homeIsactive"></span>
  </h1>
  <p class="fly-home-info">
    <i class="iconfont icon-zuichun" title="积分"></i>
    <span style="color: #FF7200;" id="homejifen">0</span>
    <span>积分,</span>
    <span style="color: #FF7200;" id="homeMoney">0</span>
    <span>赞助</span>
    <i class="iconfont icon-shijian"></i>
    <span id="homeTime">2017-03-26 </span>
    <i class="iconfont icon-chengshi"></i>
    <span id="homeCity">西安</span>
  </p>
  <p class="fly-home-sign" id="homeMotto">（人生仿若一场修行）</p> -->
</div>

<div class="main fly-home-main">
  <div class="layui-inline fly-home-jie">
    <div class="fly-panel">
      <h3 class="fly-panel-title"><span id="homeName1"></span> 最近的分享</h3>
      
      <ul id="user_resources" class="jie-row">
        <!-- <li>
          <span class="fly-jing">精</span>
          <a href="/jie/{{item.id}}.html" class="jie-title">使用 layui 秒搭后台大布局（基本结构）</a>
          <i>1天前</i>
          <em>1136阅/27答</em>
        </li>
        <li>
          <a href="/jie/{{item.id}}.html" class="jie-title">使用 layui 秒搭后台大布局（基本结构）</a>
          <i>1天前</i>
          <em>1136阅/27答</em>
        </li> -->
      </ul>
      <!-- <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><i style="font-size:14px;">没有发表任何求解</i></div> -->
    </div>
  </div>
  
  <div class="layui-inline fly-home-da">
    <div class="fly-panel">
      <h3 class="fly-panel-title"><span id="homeName2"></span> 最近的评论/回复</h3>
      <ul id="user_comments" class="home-jieda">
      </ul>
      <!-- <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><span>没有回答任何问题</span></div> -->
    </div>
  </div>

</div>

<%@ include file="../public/footer.jsp"%>
<script>
layui.config({
  base: 'res/modules/user/'
}).use('home');
</script>

</body>
</html>