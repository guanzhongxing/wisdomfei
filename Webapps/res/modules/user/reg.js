/**
 * 
 * @Name: 注册功能
 * 
 */

layui.use(['element', 'layer', 'form', 'element' ], function() {
var $ = layui.jquery, form = layui.form(), layer = layui.layer, element = layui.element

// 自定义验证规则
form.verify({
	username : function(value) {
		if (!value) {
			return '昵称不能为空';
		}
		if (value.length > 15) {
			return '昵称长度太长了';
		}
	},
	pass : function(value) {
		if (!value) {
			return '密码不能为空';
		}
		if (value.length < 6)
			return '密码不能小于6位';
		if (value.length > 16)
			return '密码不能大于16位';
	},
	repass : function(value) {
		var pass = $('#L_pass').val();
		if (value != pass) {
			return '两次输入的密码不一致';
		}
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
				if (flag)
					return '邮箱已注册';
				else {
					form.on('submit(register)', function(data) {});
				}
			} else {
				return '验证码错误';
			}
		}
	}
});

});