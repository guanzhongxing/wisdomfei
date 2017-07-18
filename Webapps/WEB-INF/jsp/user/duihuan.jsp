<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>
<%@ include file="../public/sessionout.jsp"%>
<body>

<%@ include file="../public/header.jsp"%>

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
    <li class="layui-nav-item">
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
    <li class="layui-nav-item layui-this">
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
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title" id="LAY_mine">
        <li class="layui-this" lay-id="info" id="info">兑换积分</li>
        <li lay-id="avatar" id="avatar">升级称号</li>
      </ul>
      <div class="layui-tab-content" style="padding: 20px 0;">
         <input type="hidden" id="email" name="email" value="<%=((User) session.getAttribute("user")).getEmail()%>" class="layui-input">
         <div class="layui-form layui-form-pane layui-tab-item layui-show">
         	<ul class="app-bind">
              <li class="fly-msg app-havebind">
                <span>非常感谢您的赞助<i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>  </span>
                <span>，您可以用赞助兑换积分喔。</span><br/>
                                                我的积分：<span style="color:red" id="myjifen"><%=((User) session.getAttribute("user")).getJifen()%></span> 积分<br/>
                                                我的赞助：<span style="color:red" id="myzanzhu"><%=((User) session.getAttribute("user")).getMoney()%></span> 元（可兑换：<span id="isduihuan"><%=((User) session.getAttribute("user")).getMoney()*10%> 积分</span>）
	            <form class="layui-form layui-form-pane" action="user/moneyToJifen" method="post" >
	            <div class="layui-form-item">
	              <div class="layui-input-inline" style="width: 100px;">
			        <input type="text" name="moneyToJifen" required lay-verify="moneyToJifen" placeholder="￥" autocomplete="off" class="layui-input">
			        <input type="hidden" id="myMoney" name="myMoney" value="<%=((User) session.getAttribute("user")).getMoney()%>">
			      </div>
	              <button class="layui-btn" key="set-mine" lay-filter="moneyToJifen" lay-submit>兑换</button>
	            </div>
	            </form>
              </li>
            </ul>
         </div>
         
         <div class="layui-form layui-form-pane layui-tab-item">
           <ul class="app-bind">
              <li class="fly-msg app-havebind">
                                 我的称号：<span style="color:red" id="mychenghao"></span> <br/>
                                 我的赞助：<span style="color:red" id="mymoney"></span> 元
                <span id="nextchenghao"></span>
	            <div class="layui-form-item">
	              <div class="layui-input-inline" style="width: 100px;">
			           所需赞助：<input type="text" id="moneyTochenghao" name="moneyTochenghao"  placeholder="￥ " autocomplete="off" class="layui-input" disabled>
			      </div>
			      <br/>
	              <button id="chenghaobutton" class="layui-btn" key="set-mine" lay-filter="moneyTochenghao" lay-submit>升级</button>
	            </div>
              </li>
            </ul>
         </div>
         
        </div>
      </div>
    </div>
  </div>

<%@ include file="../public/footer.jsp"%>
<script>
layui.config({
  base: 'res/modules/user/'
}).use('duihuan');
</script>

</body>
</html>