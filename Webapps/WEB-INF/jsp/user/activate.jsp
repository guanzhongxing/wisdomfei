<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>
<body>

<%@ include file="../public/header.jsp"%>

<div class="main  layui-clear">
  <div class="fly-panel fly-panel-user" pad20>
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title">
        <li class="layui-this">
          激活邮箱
        </li>
      </ul>
      <div class="layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
        <ul class="layui-form">
          <li class="layui-form-li">
            <label for="activate">您的邮箱：</label>
            <span class="layui-form-text"><%=request.getParameter("email") %>
              <!-- <em style="color:#999;">（已成功激活）</em> -->
              <%-- <em style="color:#c00;">（<%=request.getParameter("status").equals("0")?"激活链接已失效":"尚未激活" %>）</em> --%>
              <em style="color:#c00;">（已发送邮件，激活后可直接登录）</em>
            </span>
          </li>
          <li class="layui-form-li" style="margin-top: 20px; line-height: 26px;">
            <div>
              1. 如果您未收到邮件，或激活链接失效，您可以
              <a class="layui-form-a" style="color:#4f99cf;" id="LAY-activate" href="user/reactivate?email=<%=request.getParameter("email") %>">重新发送邮件</a>，或者
              <a class="layui-form-a" style="color:#4f99cf;" href="user_reg">更换邮箱</a>；
            </div>
            <div>
              2. 如果您始终没有收到 <em style="color:#c00;">代码库</em> 发送的邮件，请注意查看您邮箱中的垃圾邮件；
            </div>
            <div>
              3. 如果你实在无法激活邮件，您还可以加入QQ群寻找管理员解决：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=9d09b3b9cbcf584c9bab6b2576fb1c2aa6ca84bebb8f44e464382398e7d4acc5"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="代码库" title="代码库"></a>，或者邮件联系：admin@admintwo.com
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
  
</div>

<%@ include file="../public/footer.jsp"%>
<script>
layui.config({
  base: 'res/modules/user/'
}).use('user');
</script>
</body>
</html>