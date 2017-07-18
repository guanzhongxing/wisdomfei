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
    <li class="layui-nav-item">
      <a href="user_duihuan">
        <i class="layui-icon">&#xe600;</i>
        赞助兑换
      </a>
    </li>
    <li class="layui-nav-item layui-this">
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
        <li class="layui-this" lay-id="info" id="info">我的资料</li>
        <li lay-id="avatar" id="avatar">头像</li>
        <li lay-id="pass" id="pass">密码</li>
        <li lay-id="alipay" id="alipay">支付宝</li>
        <li lay-id="weipay" id="weipay">微信</li>
      </ul>
      <div class="layui-tab-content" style="padding: 20px 0;">
        <div class="layui-form layui-form-pane layui-tab-item layui-show">
          <form class="layui-form layui-form-pane" action="user/updateSet" method="post" enctype="multipart/form-data">
            <div class="layui-form-item">
              <label for="L_email" class="layui-form-label">邮箱/ID</label>
              <div class="layui-input-inline">
                <input type="text"  value="<%=((User) session.getAttribute("user")).getEmail()%>" class="layui-input" disabled>
                <input type="hidden" id="email" name="email" value="<%=((User) session.getAttribute("user")).getEmail()%>" class="layui-input">
              </div>
            </div>
            <div class="layui-form-item">
              <label for="L_name" class="layui-form-label">昵称</label>
              <div class="layui-input-inline">
                <input type="text" id="L_name" name="name" required lay-verify="name" autocomplete="off" value="<%=((User) session.getAttribute("user")).getName()%>" class="layui-input">
              </div>
              <div class="layui-inline">
                <div class="layui-input-inline">
                <%if(((User) session.getAttribute("user")).getSex() == 0){ %>
                  <input type="radio" name="sex" value="0" checked title="男">
                  <input type="radio" name="sex" value="1" title="女">
                <%}else{ %>
                  <input type="radio" name="sex" value="0" title="男">
                  <input type="radio" name="sex" value="1" checked title="女">
                <%} %>
                </div>
              </div>
            </div>
            <div class="layui-form-item">
              <label for="qq" class="layui-form-label">QQ</label>
              <div class="layui-input-inline">
                <input type="text" id="qq" name="qq" required lay-verify="qq" autocomplete="off" value="<%=((User) session.getAttribute("user")).getQq()%>" class="layui-input">
              </div>
            </div>
            <div class="layui-form-item">
              <label for="L_city" class="layui-form-label">城市</label>
              <div class="layui-input-inline">
                <input type="text" id="L_city" name="city" required lay-verify="city" autocomplete="off" value="<%=((User) session.getAttribute("user")).getCity()%>" class="layui-input">
              </div>
            </div>
            <div class="layui-form-item layui-form-text">
              <label for="L_motto" class="layui-form-label">座右铭</label>
              <div class="layui-input-block">
                <textarea id="L_motto" required lay-verify="motto"  id="L_motto"  name="motto" autocomplete="off" class="layui-textarea" style="height: 80px;"><%=((User) session.getAttribute("user")).getMotto()%></textarea>
              </div>
            </div>
            <div class="layui-form-item">
              <button class="layui-btn" key="set-mine" lay-filter="updateSet" lay-submit>确认修改</button>
            </div>
          	</form>
          </div>
          
          <div class="layui-form layui-form-pane layui-tab-item">
            <div class="layui-form-item">
              <div class="avatar-add">
                <p>建议尺寸168*168，支持jpg、png、gif，最大不能超过4M</p>
                <div class="upload-img">
                  <input type="file" name="file" id="userImg" class="layui-upload-file" lay-title="更换头像"> 
                </div>
                <img src="<%=((User) session.getAttribute("user")).getImg()%>" id="userImg">
                <span class="loading"></span>
              </div>
            </div>
          </div>
          
          <div class="layui-form layui-form-pane layui-tab-item">
            <form action="user/updatePassword" method="post">
              <div class="layui-form-item">
                <label for="L_nowpass" class="layui-form-label">当前密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="L_nowpass" name="nowpassword" required lay-verify="nowpassword" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label for="L_pass" class="layui-form-label">新密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="L_pass" name="password" required lay-verify="password" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">请填写6到16位密码</div>
              </div>
              <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">确认密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="L_repass" name="repassword" required lay-verify="repassword" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <button class="layui-btn" key="set-mine" lay-filter="updatePassword" lay-submit>确认修改</button>
              </div>
            </form>
          </div>
          
         <div class="layui-form layui-form-pane layui-tab-item">
            <div class="layui-form-item">
              <div class="avatar-add">
                <p>上传你的支付宝收账二维码，分享优质源码，挣点打赏钱</p>
                <div class="upload-img">
                  <input type="file" name="file" id="zhifubao" class="layui-upload-file" lay-title="支付宝二维码"> 
                </div>
                <img style="border-radius:0px;" src="res/images/dashang/img/userpayimg/zhifubao.jpg" id="zhifubaoImg">
                <span class="loading"></span>
              </div>
            </div>
          </div>
          
          <div class="layui-form layui-form-pane layui-tab-item">
            <div class="layui-form-item">
              <div class="avatar-add">
                <p>上传你的微信收账二维码，分享优质源码，挣点打赏钱</p>
                <div class="upload-img">
                  <input type="file" name="file" id="weixin" class="layui-upload-file" lay-title="微信二维码"> 
                </div>
                <img style="border-radius:0px;" src="res/images/dashang/img/userpayimg/weixin.jpg" id="weixinImg">
                <span class="loading"></span>
              </div>
            </div>
          </div>
          
        </div>
      </div>
    </div>
  </div>

<%@ include file="../public/footer.jsp"%>
<script>
layui.config({
  base: 'res/modules/user/'
}).use('set');
</script>

</body>
</html>