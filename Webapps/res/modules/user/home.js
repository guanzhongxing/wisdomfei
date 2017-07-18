/**
 * 
 * @Name: 主页展示
 * 
 */

layui.use([ 'element','layer', 'form' ], function() {
var $ = layui.jquery, element = layui.element,form = layui.form(), layer = layui.layer;
$(document).ready(function(){
//获取页面隐藏的用户id
var id = $("#userId").val();
// ajax获取用户信息
var promise1 = $.ajax({
	url : "user/home",
	type : "post",
	data : {
		id : id
	},
	dataType : 'json',
	cache : false,
	async : false,
	success : function(userLabels) {
		var content="<img src=\""+userLabels.user.img+"\" title=\""+userLabels.user.name+"\" id=\"homeImg\">";
		content+="<h1>";
		content+="<span id=\"homeName\">"+userLabels.user.name+"</span> ";
		if(userLabels.user.sex == 0){
			content+="<i class=\"iconfont icon-nan\" id=\"homeSex\"></i> ";
		}else{
			content+="<i class=\"iconfont icon-nv\" id=\"homeSex\"></i> ";
		}
		if(userLabels.user.email=='1181014088@qq.com'){
			content+="<span style=\"color:#c00;\" id=\"homeLabel\">("+userLabels.labels.name+")</span>";
		}else{
			content+="<span style=\"color:#5FB878;\" id=\"homeLabel\">("+userLabels.labels.name+")</span>";
		}
		if(userLabels.user.isactive == 2){
			content+="<span id=\"homeIsactive\">（该号已被封）</span>";
			
		}
		content+="</h1>";
		content+="<p class=\"fly-home-info\">";
		content+="<i class=\"iconfont icon-zuichun\" title=\"积分\"></i>";
		content+="<span style=\"color: #FF7200;\" id=\"homejifen\">"+userLabels.user.jifen+"</span>";
		content+="<span> 积分,</span>";
		content+="<span style=\"color: #FF7200;\" id=\"homeMoney\">"+userLabels.user.money+"</span>";
		content+="<span> 赞助</span>";
		content+="<i class=\"iconfont icon-shijian\"></i>";
		content+="<span id=\"homeTime\">"+userLabels.user.nowtime+"</span>";
		content+="<i class=\"iconfont icon-chengshi\"></i>";
		content+="<span id=\"homeCity\">"+userLabels.user.city+"</span>";
		content+="</p>";
		content+="<p class=\"fly-home-sign\" id=\"homeMotto\">（"+userLabels.user.motto+"）</p>";
		content+="";

		$("#user_home_append").append(content);
	},
	error : function() {
		layer.msg('ajax根据用户id获取主页信息失败',{icon:5,shift:6});
	}
});

//查询用户分享的代码
var promise2 = promise1.then(function(data){ 
	return $.ajax({
	url : "resource/getResourcesInfoByUser_id",
	type : "post",
	data : {
		user_id : id
	},
	dataType : 'json',
	/*cache : false,*/
	async : false,
	success : function(data) {
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
			content+="<i>"+nowtime+"</i>";
	        content+="<em>"+res_comments_num+"评论/"+res_download_num+"下载/"+res_keep_num+"收藏</em>"; 
	        content+="</li>"; 
			
			$("#user_resources").append(content);
		}
	},
	error : function() {
		layer.msg('ajax根据用户分享代码列表失败',{icon:5,shift:6});
	}
});
});

//查询前10条评论信息
var promise3 = promise2.then(function(data){ 
return $.ajax({
	url : "comments/getTenCommentsByUser_id",
	type : "post",
	data : {
		user1_id : id
	},
	dataType : 'json',
	/*cache : false,*/
	async : false,
	success : function(data) {
		for(var i in data){
			var r_id = data[i].r_id;
			var r_name = data[i].r_name;
			var c_content = data[i].c_content;
			var c_nowtime = data[i].c_nowtime;
			var content="<li>";
			content+="<p>";
			content+="<span>"+c_nowtime+"</span>";
			content+="在<a href=\"resource_detail?id="+r_id+"\" target=\"_blank\">"+r_name+"</a>中评论/回复：";
			content+="</p>";
			content+="<div class=\"home-dacontent\">";
			content+=c_content;
			content+="</div>";
	        content+="</li>"; 
			$("#user_comments").append(content);
		}
	},
	error : function() {
		layer.msg('ajax获取用户最近10条评论失败',{icon:5,shift:6});
	}
});
});
});

});