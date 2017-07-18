/**
 * 
 * @Name: 知识中心
 * 
 */

layui.use([ 'element','layer', 'form', 'element', 'upload' ], function() {
var $ = layui.jquery, form = layui.form(), layer = layui.layer, element = layui.element,device = layui.device()

// 阻止IE7以下访问
if (device.ie && device.ie < 8) {
	layer.alert('如果您非得使用ie浏览代码库，那么请使用ie8+');
}
	
$(document).ready(function(){
$.ajax({
	url : "ztree/getKeepTreeByUser_id",
	type : "post",
	dataType : 'json',
	/*cache : false,*/
	async : false,
	success : function(data) {
		//计算用户收藏代码的个数
		var l=0;
		for(var i in data){
			var id = data[i].id;
			var name = data[i].name;
			var url = data[i].url;
			var nowtime = data[i].nowtime;
			var content="<li>";
			content+="<a href=\""+url+"\" class=\"jie-title\" target=\"_blank\">"+name+"</a>";
        	content+="<i>收藏于"+nowtime+"</i>";
	        content+="</li>"; 
			
        	$("#user_keep").append(content);
        	l++;
		}
		if(l==0){
			$("#myKeepTreeNum").html(l);
			$("#user_keep").append("<div class=\"fly-none\">您尚未收藏代码</div>");
		}else{
			$("#myKeepTreeNum").html(l);
		}
	},
	error : function() {
		layer.msg('ajax根据用户获取收藏代码列表失败',{icon:5,shift:6});
	}
});
});

});