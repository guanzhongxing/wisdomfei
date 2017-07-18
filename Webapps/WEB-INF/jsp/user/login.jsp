<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>

<body>

	<%@ include file="../public/header.jsp"%>

	<div class="main layui-clear">

		<div class="fly-panel fly-panel-user" pad20>
			<div class="layui-tab layui-tab-brief">
				<ul class="layui-tab-title">
					<li class="layui-this"><a href="user_login">登入</a></li>
					<li><a href="user_reg">注册</a></li>
				</ul>
				<div class="layui-form layui-tab-content" id="LAY_ucm"
					style="padding: 20px 0;">
					<div class="layui-tab-item layui-show">
						<div class="layui-form layui-form-pane">
							<form method="post" action="user/login">
								<div class="layui-form-item">
									<label for="L_email" class="layui-form-label">邮箱</label>
									<div class="layui-input-inline">
										<input type="text" id="L_email" name="email" required
											lay-verify="email" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label for="L_pass" class="layui-form-label">密码</label>
									<div class="layui-input-inline">
										<input type="password" id="L_pass" name="pass" required
											lay-verify="pass" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label for="L_vercode" class="layui-form-label">验证码</label>
									<div class="layui-input-inline">
										<input type="text" id="L_vercode" name="formCheckCode"
											required lay-verify="formCheckCode" autocomplete="off"
											class="layui-input">
									</div>
									<div class="layui-form-mid">
										<!--若要点击图片刷新，重新得到一个验证码，要在后面加上个随机数，这样保证每次提交过去的都是不一样的path，防止因为缓存而使图片不刷新-->
										<img src="user/createImage"
											onclick="this.src='user/createImage?number='+ Math.random()"
											title="点击图片刷新验证码" /><br>
									</div>
								</div>
								<div class="layui-form-item">
									<button class="layui-btn" lay-filter="login" lay-submit>立即登录</button>
									<span style="padding-left: 20px;"> <a href="user_forget">忘记密码？</a>
									</span>
								</div>
								<!-- <div class="layui-form-item fly-form-app">
									<span>或者使用社交账号登入</span> <a
										href="http://fly.layui.com:8098/app/qq"
										onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})"
										class="iconfont icon-qq" title="QQ登入"></a> <a
										href="http://fly.layui.com:8098/app/weibo/"
										onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})"
										class="iconfont icon-weibo" title="微博登入"></a>
								</div> -->
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
			base : 'res/modules/user/'
		}).use('login');
	</script>
	<script type="text/javascript" src="res/modules/other/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="res/modules/other/mail/autoMail.1.0.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$('#L_email').autoMail({
			emails:['qq.com','163.com','126.com','sina.com','sohu.com','yahoo.cn','gmail.com','hotmail.com','live.cn']
		});
	});
	</script>
</body>
</html>