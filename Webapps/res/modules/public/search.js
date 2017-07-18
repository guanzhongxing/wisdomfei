/**
 * 
 * @Name: 首页
 * 
 */
layui.use(['element','layer', 'laytpl', 'form', 'upload', 'util'], function() {
	var $ = layui.jquery
	  ,element = layui.element
	  ,layer = layui.layer
	  ,laytpl = layui.laytpl
	  ,form = layui.form()
	  ,util = layui.util
	  ,device = layui.device()
	// 阻止IE7以下访问
	if (device.ie && device.ie < 8) {
		layer.alert('如果您非得使用ie浏览代码库，那么请使用ie8+');
	}
	
	$(document).ready(function(){
		keyword = $("#keyword").val();
		var jifen=999;
		var sort=0;
		var label=0;
		var name="";
		var url = window.location.href;//获取当前浏览器的url  
		index = url.indexOf("flag");//判断当前url是否有flag，如果有，说明是从其他页面跳转而来的，就执行下面的程序  
		if(index !=-1) {
			//如果url包含sort，获取sort值
			sortindex = url.indexOf("sort");
			labelindex = url.indexOf("label");
			if(sortindex!=-1){
				var start = url.indexOf("sort");  
				sort = url.substring(start + "sort".length+1);  
			}
			if(labelindex!=-1){
				var start = url.indexOf("label");  
				label = url.substring(start + "label".length+1);  
			}
			$.ajax({
				url : "resource/search",
				type : "post",
				dataType : 'json',
				data : {
					name : keyword,
					sort : sort,
					label : label,
					jifen : jifen
				},
				cache : false,
				async : false,
				success : function(data) {
					$("#searchResourcesList").empty();
					$("#searchNum").empty(); 
					var j=0;
					for(var i in data){
						var r_id = data[i].r_id;
						var r_sort = data[i].r_sort;
						var r_name = data[i].r_name;
						var r_label = data[i].r_label;
						var r_label_num = data[i].r_label_num;
						var r_nowtime = data[i].r_nowtime;
						var isfirst = data[i].isfirst;
						var isjing = data[i].isjing;
						var isyuan = data[i].isyuan;
						var u_id = data[i].u_id;
						var u_name = data[i].u_name;
						var u_label = data[i].u_label;
						var img = data[i].img;
						var res_comments_num = data[i].res_comments_num;
						var res_download_num = data[i].res_download_num;
						var res_keep_num = data[i].res_keep_num;
						var content="<li class=\"fly-list-li\">";
						content+="<a href=\"user_home?id="+u_id+"\" target=\"_blank\" class=\"fly-list-avatar\" >";
						content+="<img src=\""+img+"\" title=\""+u_name+"\"></a>";
						content+="<h2 class=\"fly-tip\">";
						content+="<a href=\"resource_detail?id="+r_id+"\" target=\"_blank\">"+r_name+"</a>";
						if(isfirst==1){
							content+="<span class=\"fly-tip-stick\">公告</span>";
						}
						if(isyuan==1){
							content+="<span class=\"fly-tip-jie\">原创</span>";
						}
						if(isjing==1){
							content+="<span class=\"fly-tip-jing\">精</span>";
						}
						content+="</h2>";
						content+="<p><span><a target=\"_blank\" href=\"user_home?id="+u_id+"\">"+u_name+"("+u_label+")</a></span>";
						if(r_id!=1){
							content+="<span>"+r_nowtime+"</span>";
						}else{
							content+="<span>2017-05-01</span>";
						}
						if(r_sort==1){
							content+="<a href=\"public_search?flag=true&sort=1\" target=\"_blank\"><span># 完整项目</span></a>";
						}else if(r_sort==2){
							content+="<a href=\"public_search?flag=true&sort=2\" target=\"_blank\"><span># 单一功能</span></a>";
						}else if(r_sort==3){
							content+="<a href=\"public_search?flag=true&sort=3\" target=\"_blank\"><span># 界面模板</span></a>";
						}else if(r_sort==4){
							content+="<a href=\"public_search?flag=true&sort=4\" target=\"_blank\"><span># js特效</span></a>";
						}
						if(r_label){
							content+="<a href=\"public_search?flag=true&label="+r_label_num+"\" target=\"_blank\"><span># "+r_label+"</span></a>";
						}
						content+="<span class=\"fly-list-hint\"> ";
						content+="<i class=\"iconfont\" title=\"评论\">&#xe60c;</i> <label id=\"res_comments_num\">"+res_comments_num+"</label>";
						content+="<i class=\"layui-icon\" title=\下载\" style=\"font-size: 14px;\">&#xe601;</i> <label id=\"res_download_num\">"+res_download_num+"</label>";
						content+="<i class=\"layui-icon\" title=\"收藏\" style=\"font-size: 14px;\">&#xe600;</i> <label id=\"res_keep_num\">"+res_keep_num+"</label>";
						content+="</span></p></li>";
			        	$("#searchResourcesList").append(content);
			        	j++;
					}
					if(j==0){
						$("#searchResourcesList").append("<li class=\"fly-none\">没有搜索到相关资源，重新定义条件进行筛选<img alt=\"[微笑]\" title=\"[微笑]\" src=\"res/layui/images/face/00.gif\"></li>");
						$("#searchNum").append("共"+j+"条结果");
					}else{
						$("#searchNum").append("共"+j+"条结果");
					}
				},
				error : function() {
					return 'ajax搜索资源失败';
				}
			});
			return false;
	    }  
	});
	
	form.on('submit(search)', function(data) {
		var name = data.field.name;
		var sort = data.field.sort;
		var label = data.field.label;
		var jifen = data.field.jifen;
		/*if(sort==0&&label==0&&jifen==999&&($.trim(name)==""||$.trim(name)==undefined||$.trim(name) == null)){
			layer.msg('至少选择一个条件',{icon:5,shift:6});
			return false;
		}*/
		
		$.ajax({
			url : "resource/search",
			type : "post",
			dataType : 'json',
			data : {
				name : data.field.name,
				sort : data.field.sort,
				label : data.field.label,
				jifen : data.field.jifen
			},
			cache : false,
			async : false,
			success : function(data) {
				$("#searchResourcesList").empty();
				$("#searchNum").empty(); 
				var j=0;
				for(var i in data){
					var r_id = data[i].r_id;
					var r_sort = data[i].r_sort;
					var r_name = data[i].r_name;
					var r_label = data[i].r_label;
					var r_label_num = data[i].r_label_num;
					var r_nowtime = data[i].r_nowtime;
					var isfirst = data[i].isfirst;
					var isjing = data[i].isjing;
					var isyuan = data[i].isyuan;
					var u_id = data[i].u_id;
					var u_name = data[i].u_name;
					var u_label = data[i].u_label;
					var img = data[i].img;
					var res_comments_num = data[i].res_comments_num;
					var res_download_num = data[i].res_download_num;
					var res_keep_num = data[i].res_keep_num;
					var content="<li class=\"fly-list-li\">";
					content+="<a href=\"user_home?id="+u_id+"\" target=\"_blank\" class=\"fly-list-avatar\" >";
					content+="<img src=\""+img+"\" title=\""+u_name+"\"></a>";
					content+="<h2 class=\"fly-tip\">";
					content+="<a href=\"resource_detail?id="+r_id+"\" target=\"_blank\">"+r_name+"</a>";
					if(isfirst==1){
						content+="<span class=\"fly-tip-stick\">公告</span>";
					}
					if(isyuan==1){
						content+="<span class=\"fly-tip-jie\">原创</span>";
					}
					if(isjing==1){
						content+="<span class=\"fly-tip-jing\">精</span>";
					}
					content+="</h2>";
					content+="<p><span><a target=\"_blank\" href=\"user_home?id="+u_id+"\">"+u_name+"("+u_label+")</a></span>";
					if(r_id!=1){
						content+="<span>"+r_nowtime+"</span>";
					}else{
						content+="<span>2017-05-01</span>";
					}
					if(r_sort==1){
						content+="<a href=\"public_search?flag=true&sort=1\" target=\"_blank\"><span># 完整项目</span></a>";
					}else if(r_sort==2){
						content+="<a href=\"public_search?flag=true&sort=2\" target=\"_blank\"><span># 单一功能</span></a>";
					}else if(r_sort==3){
						content+="<a href=\"public_search?flag=true&sort=3\" target=\"_blank\"><span># 界面模板</span></a>";
					}else if(r_sort==4){
						content+="<a href=\"public_search?flag=true&sort=4\" target=\"_blank\"><span># js特效</span></a>";
					}
					if(r_label){
						content+="<a href=\"public_search?flag=true&label="+r_label_num+"\" target=\"_blank\"><span># "+r_label+"</span></a>";
					}
					content+="<span class=\"fly-list-hint\"> ";
					content+="<i class=\"iconfont\" title=\"评论\">&#xe60c;</i> <label id=\"res_comments_num\">"+res_comments_num+"</label>";
					content+="<i class=\"layui-icon\" title=\下载\" style=\"font-size: 14px;\">&#xe601;</i> <label id=\"res_download_num\">"+res_download_num+"</label>";
					content+="<i class=\"layui-icon\" title=\"收藏\" style=\"font-size: 14px;\">&#xe600;</i> <label id=\"res_keep_num\">"+res_keep_num+"</label>";
					content+="</span></p></li>";
		        	$("#searchResourcesList").append(content);
		        	j++;
				}
				if(j==0){
					$("#searchResourcesList").append("<li class=\"fly-none\">没有搜索到相关资源，重新定义条件进行筛选<img alt=\"[微笑]\" title=\"[微笑]\" src=\"res/layui/images/face/00.gif\"></li>");
					$("#searchNum").append("共"+j+"条结果");
				}else{
					$("#searchNum").append("共"+j+"条结果");
				}
			},
			error : function() {
				return 'ajax搜索资源失败';
			}
		});
		return false;
	});
});
