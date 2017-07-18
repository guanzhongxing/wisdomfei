/**
 * 
 * @Name: 代码库尾部--换肤处理
 * 
 */

layui.define(['element','layer', 'util'], function(exports){
  
  var $ = layui.jquery
  ,element = layui.element
  ,layer = layui.layer
  ,util = layui.util
  ,device = layui.device()
  
  // 阻止IE7以下访问
  if(device.ie && device.ie < 8){
    layer.alert('如果您非得使用ie浏览代码库，那么请使用ie8+');
  }

  util.fixbar({
	  bar1: '&#xe61b'
	  ,click: function(type){
    	// 界面换风格，此处用ajax替换
    	// 判断用户是否登录。未登录：提示登录后风格才能保存，已登录：修改数据库，session重新设置
	  if(type === 'bar1'){
    	var flag = "";
    	$.ajax({
			url : "user/isUserSession",
			type : "post",
			dataType : 'json',
			cache : false,
			async : false,
			success : function(data) {
				flag = data;
			},
			error : function() {
				layer.msg('ajax判断用户session是否存在失败',{icon:5,shift:6});
			}
		});
    	if(flag){
    		// 界面风格数据库修改，成功后刷新页面
    		var style = "";
    		$.ajax({
				url : "user/updateStyle",
				type : "post",
				dataType : 'json',
				cache : false,
				async : false,
				success : function(data) {
					style = data;
				},
				error : function() {
					layer.msg('ajax修改用户风格失败',{icon:5,shift:6});
				}
			});
    		if(style){
    			// 刷新当前页面
    			window.location.reload()
    		}
    	}else{
    		layer.msg('温馨提示：登录后，界面风格才可以保存喔', {
				  icon: 1,
				  time: 1500 //2秒关闭（如果不配置，默认是3秒）
				  ,shade: 0.1
				}, function(){
					// do something
				});
    		layui.link("res/css/full.css");
    	}
	   }
	}
	})
	
	$(document).ready(function(){
		var flag = "";
    	$.ajax({
			url : "user/isUserSession",
			type : "post",
			dataType : 'json',
			cache : false,
			async : false,
			success : function(data) {
				flag = data;
			}
		});
    	//用户已登录，可查看用户是否有最新消息
    	if(flag){
    		$.ajax({
    			url : "user/getNewMessage",
    			type : "post",
    			dataType : 'json',
    			cache : false,
    			async : false,
    			success : function(data) {
    				if(data!=0){
    					$("#newmessage").html("("+data+")");
    				}
    			}
    		});
    	}
	});

});

