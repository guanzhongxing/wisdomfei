<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>

<body>

<%@ include file="../public/header.jsp"%>

<div class="main layui-clear">
<div class="wrap">
  <div class="content" style="margin-right:0">
    <div class="fly-tab">
      <span>
          <a href="public_search?flag=true&sort=0" target="_blank">全部</a>
          <a href="public_search?flag=true&sort=1" target="_blank">完整项目</a>
          <a href="public_search?flag=true&sort=2" target="_blank">单一功能</a>
          <a href="public_search?flag=true&sort=3" target="_blank">界面模板</a>
          <a href="public_search?flag=true&sort=4" target="_blank">特效</a>
        </span>
      <form action="public_search?flag=true" method="post" target="_blank" class="fly-search">
          <i class="iconfont icon-sousuo"></i>
          <input class="layui-input" autocomplete="off" placeholder="搜索内容，回车跳转" type="text" name="keyword" required lay-verify="required">
      </form>
      <a href="resource_add" class="layui-btn jie-add">分享代码</a>
    </div>
  
    <ul id="ihrs" class="fly-list fly-list"></ul>
  
    <ul class="fly-list" id="fly_list_flow">
      <!-- <li class="fly-list-li">
        <a href="user/home.html" class="fly-list-avatar">
          <img src="http://tp4.sinaimg.cn/1345566427/180/5730976522/0" alt="">
        </a>
        <h2 class="fly-tip">
          <a href="resource">基于Layui的轻量级问答社区页面模版</a>
          <span class="fly-tip-stick">置顶</span>
          <span class="fly-tip-jing">精帖</span>
        </h2>
        <p>
          <span><a href="user/home.html">贤心</a></span>
          <span>刚刚</span>
          <span>layui框架综合</span>
          <span class="fly-list-hint"> 
            <i class="iconfont" title="回答">&#xe60c;</i> 317
            <i class="iconfont" title="人气">&#xe60b;</i> 6830
          </span>
        </p>
      </li> -->
    </ul>
  </div>
</div>
</div>

<%@ include file="../public/footer.jsp"%>

<script>
layui.config({
  base: 'res/modules/resource/'
}).use('index');
</script>

</body>
</html>