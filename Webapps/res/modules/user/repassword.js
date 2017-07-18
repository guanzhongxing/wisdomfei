/**
 * 
 * @Name: 重设密码功能
 * 
 */

layui.use([ 'element','layer', 'form', 'element' ], function() {
var $ = layui.jquery, form = layui.form(), layer = layui.layer, element = layui.element

// 自定义验证规则
form.verify({
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
		}else{
			form.on('submit(repassword)', function(data) {
				$.ajax({
					url : "user/rePassword",
					type : "post",
					dataType : 'json',
					data : {
						email : data.field.email,
						password : data.field.password,
					},
					cache : false,
					async : false,
					success : function(data) {
						if(data){
							layer.msg('密码更新成功', {
								icon: 1,
								time: 1000 //2秒关闭（如果不配置，默认是3秒）
								,shade: 0.1
							}, function(){
								window.location = 'user_login';
							}); 
						}
						else{
							return '密码更新失败';
						}
					},
					error : function() {
						return 'ajax更新用户密码失败';
					}
				});
				return false;
			});
		}
	}
});

});