<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <li class="layui-nav-item layui-this">
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
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title" id="LAY_mine">
        <li data-type="mine-jie" lay-id="noRead" class="layui-this">最新消息（<label id="newNum"></label>）</li>
        <!-- <li data-type="collection" lay-id="yesRead" data-url="/collection/find/" >评论信息（<label id="commentsNum"></label>）</li> -->
        <li data-type="collection" lay-id="yesRead" data-url="/collection/find/" >兑换明细（<label id="duihuanNum"></label>）</li>
        <li data-type="collection" lay-id="yesRead" data-url="/collection/find/" >下载明细（<label id="downloadNum"></label>）</li>
        <li data-type="collection" lay-id="yesRead" data-url="/collection/find/" >系统消息（<label id="systemNum"></label>）</li>
      </ul>
      <div class="layui-tab-content" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
           	<div class="fly-panel fly-panel-user" pad20>
			  
			  <div id="newMessages" class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
		        <!-- <div class="fly-none">您暂时没有最新消息</div> -->
			    <!-- <button class="layui-btn layui-btn-danger" id="readAll">清空全部消息</button> -->
			    <div id="readAllButton"></div>
			    <div id="LAY_minemsg" style="margin-top: 10px;">
		        <ul id="newMessagesList" class="mine-msg">
		          <!-- <li>
		            <blockquote class="layui-elem-quote">
		              <a href="/jump?username=Absolutely" target="_blank"><cite>Absolutely</cite></a>回答了您的求解<a target="_blank" href="/jie/8153.html/page/0/#item-1489505778669"><cite>layui后台框架</cite></a>
		            </blockquote>
		            <p><span>1小时前</span><a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a></p>
		          </li>
		          <li data-id="123">
		            <blockquote class="layui-elem-quote">
		              系统消息：欢迎使用 
		            </blockquote>
		            <p><span>1小时前</span><a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a></p>
		          </li> -->
		        </ul>
		      </div>
			 </div>
			  
		  </div>
          <div id="LAY_page"></div>
        </div>
        
        <!-- <div class="layui-tab-item">
          <div class="fly-panel fly-panel-user" pad20>
			  <div id="commentsMessages" class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
			    <div id="LAY_minemsg" style="margin-top: 10px;">
		        <div class="fly-none">您暂时没有评论消息</div>
		        <ul id="commentsMessagesList">
		          <li>
		            <blockquote class="layui-elem-quote">
		              <a href="/jump?username=Absolutely" target="_blank"><cite>Absolutely</cite></a>回答了您的求解<a target="_blank" href="/jie/8153.html/page/0/#item-1489505778669"><cite>layui后台框架</cite></a>
		              <span style="color:#999;float:right;">1小时前</span>
		            </blockquote>
		          </li>
		        </ul>
		      </div>
			 </div>
		  </div>
          <div id="LAY_page1"></div>
        </div> -->
        
        <div class="layui-tab-item">
          <div class="fly-panel fly-panel-user" pad20>
			  <div id="duihuanMessages" class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
			    <div id="LAY_minemsg" style="margin-top: 10px;">
		        <!-- <div class="fly-none">您暂时没有兑换消息</div> -->
		        <ul id="duihuanMessagesList">
		          <!-- <li>
		            <blockquote class="layui-elem-quote">
		              <a href="/jump?username=Absolutely" target="_blank"><cite>Absolutely</cite></a>回答了您的求解<a target="_blank" href="/jie/8153.html/page/0/#item-1489505778669"><cite>layui后台框架</cite></a>
		              <span style="color:#999;float:right;">1小时前</span>
		            </blockquote>
		          </li> -->
		        </ul>
		      </div>
			 </div>
		  </div>
          <div id="LAY_page1"></div>
        </div>
        
        <div class="layui-tab-item">
          <div class="fly-panel fly-panel-user" pad20>
			  <div id="downloadMessages" class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
			    <div id="LAY_minemsg" style="margin-top: 10px;">
		        <!-- <div class="fly-none">您暂时没有下载消息</div> -->
		        <ul id="downloadMessagesList">
		          <!-- <li>
		            <blockquote class="layui-elem-quote">
		              <a href="/jump?username=Absolutely" target="_blank"><cite>Absolutely</cite></a>回答了您的求解<a target="_blank" href="/jie/8153.html/page/0/#item-1489505778669"><cite>layui后台框架</cite></a>
		              <span style="color:#999;float:right;">1小时前</span>
		            </blockquote>
		          </li> -->
		        </ul>
		      </div>
			 </div>
		  </div>
          <div id="LAY_page1"></div>
        </div>
        
        <div class="layui-tab-item">
          <div class="fly-panel fly-panel-user" pad20>
			  <div id="systemMessages" class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
			    <div id="LAY_minemsg" style="margin-top: 10px;">
		        <!-- <div class="fly-none">您暂时没有系统消息</div> -->
		        <ul id="systemMessagesList">
		          <!-- <li>
		            <blockquote class="layui-elem-quote">
		              	系统消息：欢迎使用 layui
		              <span style="color:#999;float:right;">1小时前</span>
		            </blockquote>
		          </li> -->
		        </ul>
		      </div>
			 </div>
		  </div>
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
}).use('message');
</script>
</body>
</html>