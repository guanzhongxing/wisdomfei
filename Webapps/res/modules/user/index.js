/**
 * 
 * @Name: 代码中心
 * 
 */

layui.use([ 'element','layer', 'form', 'element', 'upload' ], function() {
var $ = layui.jquery, form = layui.form(), layer = layui.layer, element = layui.element,device = layui.device()

// 阻止IE7以下访问
if (device.ie && device.ie < 8) {
	layer.alert('如果您非得使用ie浏览代码库，那么请使用ie8+');
}
	
$(document).ready(function(){
//获取页面隐藏的用户id
var id = $("#userId").val();
//查询用户分享的代码
$.ajax({
	url : "resource/getUser_indexResourcesInfoByUser_id",
	type : "post",
	data : {
		user_id : id
	},
	dataType : 'json',
	/*cache : false,*/
	async : false,
	success : function(data) {
		//计算用户分享代码的个数
		var j=0;
		for(var i in data){
			var id = data[i].id;
			var name = data[i].name;
			var nowtime = data[i].nowtime;
			var isjing = data[i].isjing;
			var isyuan = data[i].isyuan;
			var res_comments_num = data[i].res_comments_num;
			var res_download_num = data[i].res_download_num;
			var res_keep_num = data[i].res_keep_num;
			var content="<li>";
			if(isyuan==1){
				content+="<span class=\"fly-yuan\">原创</span>";
			}else if(isjing==1){
				content+="<span class=\"fly-jing\">精</span>";
			}
			content+="<a href=\"resource_detail?id="+id+"\" class=\"jie-title\" target=\"_blank\">"+name+"</a>";
			content+="<i>分享于"+nowtime+"</i>";
			/*content+="<a class=\"mine-edit\" href=\"resource/edit?id="+id+"\">编辑</a>";*/
	        content+="<em>"+res_comments_num+"评论/"+res_download_num+"下载/"+res_keep_num+"收藏</em>"; 
	        content+="</li>"; 
			
			$("#user_resources").append(content);
			j++;
		}
		if(j==0){
			$("#user_resources").append("<div class=\"fly-none\">您尚未分享代码</div>");
		}
		$("#myResourcesNum").text(j);
	},
	error : function() {
		layer.msg('ajax根据用户分享代码列表失败',{icon:5,shift:6});
	}
});
});

$(document).ready(function(){
//获取页面隐藏的用户id
var id = $("#userId").val();
$.ajax({
	url : "resource/getResourcesSortInfoByUser_id",
	type : "post",
	data : {
		user_id : id
	},
	dataType : 'json',
	/*cache : false,*/
	async : false,
	success : function(data) {
		//计算用户评论代码的个数
		var j=0;
		//计算用户下载代码的个数
		var k=0;
		//计算用户收藏代码的个数
		var l=0;
		for(var i in data){
			var sort = data[i].sort;
			var id = data[i].id;
			var name = data[i].name;
			var nowtime = data[i].nowtime;
			var isjing = data[i].isjing;
			var isyuan = data[i].isyuan;
			var res_comments_num = data[i].res_comments_num;
			var res_download_num = data[i].res_download_num;
			var res_keep_num = data[i].res_keep_num;
			var content="<li>";
			if(isyuan==1){
				content+="<span class=\"fly-yuan\">原创</span>";
			}else if(isjing==1){
				content+="<span class=\"fly-jing\">精</span>";
			}
			content+="<a href=\"resource_detail?id="+id+"\" class=\"jie-title\" target=\"_blank\">"+name+"</a>";
			if(sort==1){
				content+="<i>评论于"+nowtime+"</i>";
	        }else if(sort==2){
	        	content+="<i>下载于"+nowtime+"</i>";
	        }else if(sort==3){
	        	content+="<i>收藏于"+nowtime+"</i>";
	        }
	        content+="<em>"+res_comments_num+"评论/"+res_download_num+"下载/"+res_keep_num+"收藏</em>"; 
	        content+="</li>"; 
			
	        if(sort==1){
	        	$("#user_comments").append(content);
	        	j++;
	        }else if(sort==2){
	        	$("#user_download").append(content);
	        	k++;
	        }else if(sort==3){
	        	$("#user_keep").append(content);
	        	l++;
	        }
		}
		if(j==0){
			$("#user_comments").append("<div class=\"fly-none\">您尚未评论代码</div>");
		}
		if(k==0){
			$("#user_download").append("<div class=\"fly-none\">您尚未下载代码</div>");
		}
		if(l==0){
			$("#user_keep").append("<div class=\"fly-none\">您尚未收藏代码</div>");
		}
		$("#myCommentResourcesNum").text(j);
    	$("#myDownloadResourcesNum").text(k);
    	$("#myKeepResourcesNum").text(l);
	},
	error : function() {
		layer.msg('ajax根据用户分享代码列表失败',{icon:5,shift:6});
	}
});
});

});