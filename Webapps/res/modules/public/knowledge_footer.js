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
	  bar1: ''
	  ,click: function(type){
    	
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

