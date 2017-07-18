/**
 * 
 * @Name: 消息中心
 * 
 */

layui.use([ 'element','layer', 'form', 'element', 'upload' ], function() {
var $ = layui.jquery, form = layui.form(), layer = layui.layer, element = layui.element,device = layui.device()

// 阻止IE7以下访问
if (device.ie && device.ie < 8) {
	layer.alert('如果您非得使用ie浏览代码库，那么请使用ie8+');
}
	
$(document).ready(function(){
//查询用户消息
$.ajax({
	url : "messages/getMessageByEmail",
	type : "post",
	dataType : 'json',
	cache : false,
	async : false,
	success : function(data) {
		//计算最新、评论、兑换、下载、系统消息的个数
		var a=0;
		/*var b=0;*/
		var c=0;
		var d=0;
		var e=0;
		for(var i in data){
			var id = data[i].id;
			var message = data[i].message;
			var sort = data[i].sort;
			var isRead = data[i].isRead;
			var nowtime = data[i].nowtime;
			if(isRead==0 && sort!=3 && sort!=4){
				var content="";
				content+="<li id=message_"+id+">";
				content+="<blockquote class=\"layui-elem-quote\">";
				if(sort==0){
					content+="系统消息："+message;
				}else{
					content+=message;
				}
				content+="</blockquote>";
				content+="<p><span>"+nowtime+"</span><a name=\"read_"+id+"\" class=\"layui-btn layui-btn-small layui-btn-danger fly-delete\">删除</a></p>";
				content+="</li>";
				$("#newMessagesList").append(content);
				a++;
			}
			if(sort==0){
				var content="";
				content+="<li>";
				content+="<blockquote class=\"layui-elem-quote\">";
				content+="系统消息："+message;
				content+="<span style=\"color:#999;float:right;\">"+nowtime+"</span>";
				content+="</blockquote>";
				content+="</li>";
				$("#systemMessagesList").append(content);
				e++;
			}
			/*评论消息不展示*/
			/*else if(sort==1){
				var content="";
				content+="<li>";
				content+="<blockquote class=\"layui-elem-quote\">";
				content+=message;
				content+="<span style=\"color:#999;float:right;\">"+nowtime+"</span>";
				content+="</blockquote>";
				content+="</li>";
				$("#commentsMessagesList").append(content);
				b++;
			}*/
			else if(sort==2){
				var content="";
				content+="<li>";
				content+="<blockquote class=\"layui-elem-quote\">";
				content+=message;
				content+="<span style=\"color:#999;float:right;\">"+nowtime+"</span>";
				content+="</blockquote>";
				content+="</li>";
				$("#duihuanMessagesList").append(content);
				c++;
			}else if(sort==5){
				var content="";
				content+="<li>";
				content+="<blockquote class=\"layui-elem-quote\">";
				content+=message;
				content+="<span style=\"color:#999;float:right;\">"+nowtime+"</span>";
				content+="</blockquote>";
				content+="</li>";
				$("#downloadMessagesList").append(content);
				d++;
			}else if(sort==6){
				var content="";
				content+="<li>";
				content+="<blockquote class=\"layui-elem-quote\">";
				content+=message;
				content+="<span style=\"color:#999;float:right;\">"+nowtime+"</span>";
				content+="</blockquote>";
				content+="</li>";
				$("#downloadMessagesList").append(content);
				d++;
			}
		}
		if(a==0){
			$("#newMessagesList").append("<div class=\"fly-none\">您暂时没有最新消息</div>");
		}else{
			$("#readAllButton").append("<button class=\"layui-btn layui-btn-danger\" id=\"readAll\">清空全部消息</button>");
		}
		/*if(b==0){
			$("#commentsMessagesList").append("<div class=\"fly-none\">您暂时没有评论消息</div>");
		}*/
		if(c==0){
			$("#duihuanMessagesList").append("<div class=\"fly-none\">您暂时没有兑换消息</div>");
		}
		if(d==0){
			$("#downloadMessagesList").append("<div class=\"fly-none\">您暂时没有下载消息</div>");
		}
		if(e==0){
			$("#systemMessagesList").append("<div class=\"fly-none\">您暂时没有系统消息</div>");
		}
		$("#newNum").text(a);
		/*$("#commentsNum").text(b);*/
		$("#duihuanNum").text(c);
		$("#downloadNum").text(d);
		$("#systemNum").text(e);
	},
	error : function() {
		layer.msg('ajax获取用户消息失败',{icon:5,shift:6});
	}
});
});

//监听所有删除按钮
$(document).on('click','li[id*="message_"]',function(){
	var id = $(this).attr("id");
	var num = id.split('_');
	//获取指定id
	id = num[1];
	$.ajax({
		url : "user/readMessage",
		type : "post",
		dataType : 'json',
		data : {
			id : id
		},
		cache : false,
		async : false,
		success : function(flag) {
			if(flag){
				$("#message_"+id).remove();
				var newNum = $("#newNum").html();
				$("#newNum").text(parseInt(newNum)-1);
				if(parseInt($("#newNum").html())==0){
					$("#newMessages").empty();
		            $("#newMessages").append("<div class=\"fly-none\">您暂时没有最新消息</div>");
				}
			}else{
				layer.msg('删除最新消息失败',{icon:5,shift:6});
			}
		},
		error : function() {
			layer.msg('ajax删除最新消息失败',{icon:5,shift:6});
		}
	})
})

//删除所有最新消息
$("#readAll").on("click",function(){
	$.ajax({
		url : "user/readAllMessage",
		type : "post",
		dataType : 'json',
		cache : false,
		async : false,
		success : function(flag) {
			if(flag){
				$("#newMessages").empty();
	            $("#newMessages").append("<div class=\"fly-none\">您暂时没有最新消息</div>");
	            var newNum = $("#newNum").html();
				$("#newNum").text(0);
			}else{
				layer.msg('清空消息失败',{icon:5,shift:6});
			}
		},
		error : function() {
			layer.msg('ajax清空消息失败',{icon:5,shift:6});
		}
	});
});

});