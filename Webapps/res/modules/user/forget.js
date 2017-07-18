/**
 * 
 * @Name: 注册功能
 * 
 */

layui.use([ 'element','layer', 'form', 'element' ], function() {
var $ = layui.jquery, form = layui.form(),element = layui.element, layer = layui.layer, element = layui.element()

// 自定义验证规则
form.verify({
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
				var flag;
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
					$("#updatepassword").empty();
					$("#updatepassword").append("邮件已经发送到您的邮箱，按照邮件提示重设密码<img alt=\"[微笑]\" title=\"[微笑]\" src=\"res/layui/images/face/00.gif\">");
					$.ajax({
						url : "user/emailUpdatePassword",
						type : "post",
						data : {
							email : email
						},
						dataType : 'json',
						cache : false,
						success : function(flag) {
						},
						error : function() {
							return 'ajax重置密码发送邮件失败';
						}
					});
				}else{
					return '邮箱尚未注册';
				}
			} else {
				return '验证码错误';
			}
		}
	}
});

});