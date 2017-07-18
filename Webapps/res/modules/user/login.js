/**
 * 
 * @Name: 登录功能
 * 
 */

layui.use([ 'element','layer', 'form' ], function() {
var $ = layui.jquery, element = layui.element,form = layui.form(), layer = layui.layer

// 自定义验证规则
form.verify({
	pass : function(value) {
		if (!value)
			return '密码不能为空';
	},
	formCheckCode : function(value) {
		if (!value)
			return '验证码不能为空';
		else {
			// 获取页面输入的验证码
			var formcheckCode = $('#L_vercode').val();
			// ajax获取后台验证码
			var checkCode = "";
			$.ajax({
				url : "user/getCheckCode",
				type : "post",
				dataType : 'json',
				cache : false,
				async : false,
				success : function(data) {
					checkCode = data;
				},
				error : function() {
					return 'ajax获取验证码失败';
				}
			});
			// 前台和后台验证码进行比较
			if (formcheckCode.toLowerCase() == checkCode.toLowerCase()) {
				// 获取页面输入的邮箱
				var email = $('#L_email').val();
				// 判断邮箱是否存在
				var flag = "";
				$.ajax({
					url : "user/existEmail",
					type : "post",
					data : {
						email : email
					},
					dataType : 'json',
					cache : false,
					async : false,
					success : function(data) {
						flag = data;
					},
					error : function() {
						return 'ajax验证邮箱是否存在失败';
					}
				});
				if (flag) {
					// 判断邮箱是否激活、是否封号、密码是否正确
					var password = $('#L_pass').val();
					var isactive = "";
					$.ajax({
						url : "user/isactive",
						type : "post",
						data : {
							email : email,
							password : password
						},
						dataType : 'json',
						cache : false,
						async : false,
						success : function(data) {
							isactive = data;
						},
						error : function() {
							return 'ajax验证邮箱是否激活失败';
						}
					});
					if (isactive == 0){
						window.location.href="user_activate?email="+email+"&status=1";
						return "邮箱未激活";
					}else if(isactive == 2){
						return "邮箱被封号";
					}else if(isactive == 3){
						return "密码不正确";
					}else if(isactive == 1) {
						form.on('submit(login)', function(data) {});
					}
				} else {
					return '邮箱未注册';
				}
			} else {
				return '验证码错误';
			}
		}
	}
});

});