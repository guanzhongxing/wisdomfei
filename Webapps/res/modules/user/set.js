/**
 * 
 * @Name: 主页展示
 * 
 */

layui.use([ 'element','layer', 'form', 'element', 'upload' ], function() {
var $ = layui.jquery, form = layui.form(), layer = layui.layer, element = layui.element

//自定义验证规则
form.verify({
	name : function(value) {
		if (!value) {
			return '昵称不能为空';
		}
		if (value.length > 15) {
			return '昵称长度太长了';
		}
	},
	qq : function(value) {
		if (!value) {
			return 'QQ号码不能为空';
		}
	},
	city : function(value) {
		if (!value) {
			return '城市不能为空';
		}
		if (value.length > 15) {
			return '城市名称太长了';
		}
	},
	motto : function(value) {
		if (!value) {
			return '座右铭不能为空';
		}else{
			// 页面验证完成后，可执行ajax更新用户基本信息
			form.on('submit(updateSet)', function(data) {
				$.ajax({
					url : "user/updateSet",
					type : "post",
					dataType : 'json',
					data : {
						email : data.field.email,
						name : data.field.name,
						sex : data.field.sex,
						city : data.field.city,
						motto : data.field.motto,
						qq: data.field.qq
					},
					cache : false,
					async : false,
					success : function(data) {
						if(data){
							layer.msg('信息更新成功', {
								icon: 1,
								time: 1000 //2秒关闭（如果不配置，默认是3秒）
								,shade: 0.1
							}, function(){
							}); 
						}
						else{
							return '信息更新失败';
						}
					},
					error : function() {
						return 'ajax更新用户基本信息失败';
					}
				});
				return false;
			});
		}
	}
});


layui.upload({
    url: 'user/uploadImg'
    ,elem:'#userImg'
    , method: 'post' //上传接口的http类型
    , success: function(res){
    	$("#userImg").attr('src',res);
    	var email = $('#email').val();
    	$.ajax({
			url : "user/updateImg",
			type : "post",
			dataType : 'json',
			data : {
				email : email,
				img : res
			},
			cache : false,
			async : false,
			success : function(data) {
				if(data){
					layer.msg('头像更新成功', {
						  icon: 1,
						  time: 1000 //2秒关闭（如果不配置，默认是3秒）
						  ,shade: 0.1
						}, function(){
							// 跳转
							location.href = "user_set";
						}); 
				}
				else{
					return '头像更新失败';
				}
			},
			error : function() {
				return 'ajax更新用户头像失败';
			}
    	})
    	
    }
  });

layui.upload({
    url: 'user/zhifubao'
    ,elem: '#zhifubao'
    , method: 'post' //上传接口的http类型
    , success: function(res){
    	$("#zhifubaoImg").attr('src',res);
    	var email = $('#email').val();
    	$.ajax({
			url : "user/zhifubao",
			type : "post",
			dataType : 'json',
			data : {
				email : email,
				img : res
			},
			cache : false,
			async : false,
			success : function(data) {
				if(data){
					layer.msg('支付宝二维码更新成功', {
						  icon: 1,
						  time: 1000 //2秒关闭（如果不配置，默认是3秒）
						  ,shade: 0.1
						}, function(){
							// 跳转
							location.href = "user_set";
						}); 
				}
				else{
					return '支付宝二维码更新失败';
				}
			},
			error : function() {
				return 'ajax更新支付宝二维码失败';
			}
    	})
    	
    }
  });

layui.upload({
    url: 'user/weixin'
    ,elem: '#weixin'
    , method: 'post' //上传接口的http类型
    , success: function(res){
    	$("#weixinImg").attr('src',res);
    	var email = $('#email').val();
    	$.ajax({
			url : "user/weixin",
			type : "post",
			dataType : 'json',
			data : {
				email : email,
				img : res
			},
			cache : false,
			async : false,
			success : function(data) {
				if(data){
					layer.msg('微信二维码更新成功', {
						  icon: 1,
						  time: 1000 //2秒关闭（如果不配置，默认是3秒）
						  ,shade: 0.1
						}, function(){
							// 跳转
							location.href = "user_set";
						}); 
				}
				else{
					return '微信二维码更新失败';
				}
			},
			error : function() {
				return 'ajax更新微信二维码失败';
			}
    	})
    	
    }
  });

//自定义验证规则
form.verify({
	nowpassword : function(value) {
		if (!value) {
			return '当前密码不能为空';
		}
	},
	password : function(value) {
		if (!value) {
			return '新密码不能为空';
		}
		if (value.length < 6)
			return '密码不能小于6位';
		if (value.length > 16)
			return '密码不能大于16位';
	},
	repassword : function(value) {
		if (!value) {
			return '确认密码不能为空';
		}
		// 新密码
		var pass = $('#L_pass').val();
		if (value != pass) {
			return '两次输入的密码不一致';
		}else{
			// 原密码
			var nowpassword = $('#L_nowpass').val();
			// 新密码
			var password = $('#L_pass').val();
			// 标志位
			var flag ;
			$.ajax({
				url : "user/checkPassword",
				type : "post",
				dataType : 'json',
				data : {
					password : nowpassword
				},
				cache : false,
				async : false,
				success : function(data) {
					flag = data;
				},
				error : function() {
					return 'ajax验证密码是否正确失败';
				}
			})
			if(flag){
				form.on('submit(updatePassword)', function(data) {
					$.post('user/updatePassword',data.field,function(res){
						if(res){
							layer.msg('密码修改成功', {
								  icon: 1,
								  time: 1000 //2秒关闭（如果不配置，默认是3秒）
								  ,shade: 0.1
								}, function(){
								}); 
						}else{
							layer.msg("密码修改失败");
						}
						});
					return false;
				});
			}
			else{
				return '当前密码不正确';
			}
			
			
		}
	}
});

$.ajax({
	url : "user/getUserPay",
	type : "post",
	dataType : 'json',
	cache : false,
	async : false,
	success : function(data) {
		var alipay = data.alipay;
		var weipay = data.weipay;
		$("#zhifubaoImg").attr('src',alipay);
		$("#weixinImg").attr('src',weipay);
	}
});

});