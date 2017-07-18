<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.admintwo.model.User"%>
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>
<%@ include file="../public/sessionout.jsp"%>
<body>

<%@ include file="../public/header.jsp"%>

<input id="userId" type="hidden" value="<%=((User) session.getAttribute("user")).getId()%>"/>

<div class="main fly-user-main layui-clear">
  <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
    <li class="layui-nav-item">
      <a href="user_home?id=<%=((User) session.getAttribute("user")).getId()%>">
        <i class="layui-icon">&#xe62e;</i>
        我的主页
      </a>
    </li>
    <li class="layui-nav-item">
      <a href="user_message">
        <i class="layui-icon">&#xe611;</i>
        我的消息
      </a>
    </li>
    <li class="layui-nav-item layui-this">
      <a href="user_index">
        <i class="layui-icon">&#xe635;</i>
        源码中心
      </a>
    </li>
    <li class="layui-nav-item">
      <a href="user_knowledge">
        <i class="layui-icon">&#xe60a;</i>
        知识中心
      </a>
    </li>
    <li class="layui-nav-item">
      <a href="user_duihuan">
        <i class="layui-icon">&#xe600;</i>
        赞助兑换
      </a>
    </li>
    <li class="layui-nav-item">
      <a href="user_set">
        <i class="layui-icon">&#xe620;</i>
        主页设置
      </a>
    </li>
  </ul>
  
  <div class="site-tree-mobile layui-hide">
    <i class="layui-icon">&#xe602;</i>
  </div>
  <div class="site-mobile-shade"></div>
  
  <div class="fly-panel fly-panel-user" pad20>
    
    <!-- <div class="fly-msg" style="margin-top: 15px;">
      您的邮箱尚未验证，这比较影响您的帐号安全，<a href="activate.html">立即去激活？</a>
    </div> -->
   
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title" id="LAY_mine">
        <li data-type="mine-jie" lay-id="index" class="layui-this">我分享的源码（<label id="myResourcesNum"></label>）</li>
        <li data-type="collection" lay-id="comment" data-url="/collection/find/" >我评论的源码（<label id="myCommentResourcesNum"></label>）</li>
        <li data-type="collection" lay-id="download" data-url="/collection/find/" >我下载的源码（<label id="myDownloadResourcesNum"></label>）</li>
        <li data-type="collection" lay-id="keep" data-url="/collection/find/" >我收藏的源码（<label id="myKeepResourcesNum"></label>）</li>
      </ul>
      <div class="layui-tab-content" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
          <ul id="user_resources" class="mine-view jie-row">
            <!-- <li>
              <a class="jie-title" href="/jie/8116.html" target="_blank">LayIM 3.5.0 发布，移动端版本大更新（带演示图）</a>
              <i>2017/3/14 上午8:30:00</i>
              <a class="mine-edit" href="/jie/edit/8116">编辑</a>
              <em>661阅/10答</em>
            </li> -->
          </ul>
          <div id="LAY_page"></div>
        </div>
        <div class="layui-tab-item">
          <ul id="user_comments" class="mine-view jie-row">
            <!-- <li>
              <a class="jie-title" href="http://fly.layui.com/jie/5366.html" target="_blank">评论</a>
              <i>收藏于23小时前</i>  </li> -->
          </ul>
          <div id="LAY_page1"></div>
        </div>
        <div class="layui-tab-item">
          <ul id="user_download" class="mine-view jie-row">
            <!-- <li>
              <a class="jie-title" href="http://fly.layui.com/jie/5366.html" target="_blank">下载</a>
              <i>收藏于23小时前</i>  </li> -->
          </ul>
          <div id="LAY_page1"></div>
        </div>
        <div class="layui-tab-item">
          <ul id="user_keep" class="mine-view jie-row">
            <!-- <li>
              <a class="jie-title" href="http://fly.layui.com/jie/5366.html" target="_blank">收藏</a>
              <i>收藏于23小时前</i>  </li> -->
          </ul>
          <div id="LAY_page1"></div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="../public/footer.jsp"%>
<script>
layui.config({
  base: 'res/modules/user/'
}).use('index');
</script>

</body>
</html>