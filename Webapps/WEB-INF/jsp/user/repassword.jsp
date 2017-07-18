<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>
<body>

<%@ include file="../public/header.jsp"%>

<div class="main layui-clear">
  <div class="fly-panel fly-panel-user" pad20>
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title">
        <li class="layui-this">重设密码</li>
      </ul>
      <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
          <div class="layui-form layui-form-pane">
            <form method="post" action="user/rePassword">
              <input type="hidden" name="email" value="<%=request.getParameter("email")%>">
              <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">密码</label>
				<div class="layui-input-inline">
					<input type="password" id="L_pass" name="password"
						lay-verify="pass" autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">请填写6到16位密码</div>
			  </div>
			  <div class="layui-form-item">
				<label for="L_repass" class="layui-form-label">确认密码</label>
				<div class="layui-input-inline">
					<input type="password" id="L_repass" name="repassword"
						required lay-verify="repass" autocomplete="off"
						class="layui-input">
				</div>
			  </div>
              <div class="layui-form-item">
                <button class="layui-btn" alert="1" lay-filter="repassword" lay-submit>提交</button>
              </div>
            </form>
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
}).use('repassword');
</script>

</body>
</html>