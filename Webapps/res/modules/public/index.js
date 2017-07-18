/**
 * 
 * @Name: 首页
 * 
 */
layui.use(['element','layer', 'laytpl', 'form', 'upload', 'flow', 'util', 'layedit'], function() {
	var $ = layui.jquery
	  ,element = layui.element
	  ,layer = layui.layer
	  ,laytpl = layui.laytpl
	  ,form = layui.form()
	  ,util = layui.util
	  ,layedit = layui.layedit
	  ,device = layui.device()
	var flow = layui.flow;
	//当你执行这样一个方法时，即对页面中的全部带有lay-src的img元素开启了懒加载（当然你也可以指定相关img）
	flow.lazyimg(); 
	// 阻止IE7以下访问
	if (device.ie && device.ie < 8) {
		layer.alert('如果您非得使用ie浏览代码库，那么请使用ie8+');
	}

$(document).ready(function(){
	// ajax获取首页资源信息
	$.ajax({
		url : "resource/index",
		type : "post",
		dataType : 'json',
		cache : false,
		async : false,
		success : function(data) {
			for(var i in data){
				var r_id = data[i].r_id;
				var r_sort = data[i].r_sort;
				var r_name = data[i].r_name;
				var r_urlpwd = data[i].r_urlpwd;
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
				
				if(r_urlpwd!=0){
					content+="<p><br>";
					if(i<=5){
						content+="<img id=\""+r_id+"\" src=\"res/images/1.png\" onMouseOver=\"this.src='res/images/2.png'\" onMouseOut=\"this.src='res/images/1.png'\" onclick=\"layer.open({type: 1,title:'"+r_name+"',closeBtn: 0,offset:'100px',area: '600px',skin: 'layui-layer-nobg',shadeClose: true,content: '<img style=width:600px; src="+r_urlpwd+">'});\" data-gif=\""+r_urlpwd+"\">";
					}else{
						content+="<img id=\""+r_id+"\" lay-src=\"res/images/1.png\" onMouseOver=\"this.src='res/images/2.png'\" onMouseOut=\"this.src='res/images/1.png'\" onclick=\"layer.open({type: 1,title: '"+r_name+"',closeBtn: 0,offset:'100px',area: '600px',skin: 'layui-layer-nobg',shadeClose: true,content: '<img style=width:600px; src="+r_urlpwd+">'});\" data-gif=\""+r_urlpwd+"\">";
					}
					content+="<br><br></p>";
				}
				
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
		        if(r_id!=1){
		        	$("#ihrs").append(content);
		        }else{
		        	$("#ihrs1").append(content);
		        }
			}
		},
		error : function() {
			layer.msg('ajax加载首页失败', {
				icon : 5,
				shift : 6
			});
		}
	});
	
	// ajax获取分享榜
	$.ajax({
		url : "user/top",
		type : "post",
		dataType : 'json',
		cache : false,
		async : false,
		success : function(data) {
			var num1=1,num2=1,num3=1,num4=1,num5=1;
			for(var i in data){
				var sort = data[i].sort;
				var id = data[i].id;
				var name = data[i].name;
				var img = data[i].img;
				var num = data[i].num;
				if(sort==1){
					var content="<dd>";
					content+="<a target=\"_blank\" href=\"user_home?id="+id+"\">";
					content+="<img src=\""+img+"\">";
					content+="<cite>"+num+"次分享</cite>";
					if(num1==1){
						content+="<i style=\"color:#F00000;\">No."+num1+"</i>";
					}else if(num1==2){
						content+="<i style=\"color:#DAA520;\">No."+num1+"</i>";
					}else if(num1==3){
						content+="<i style=\"color:#B87333;\">No."+num1+"</i>";
					}else{
						content+="<i>No."+num1+"</i>";
					}
					content+="</a>";
					content+="</dd>";
					$("#usertop").append(content);
					if(num1==1){
						$("#bangshou").append(name);
					}
					if(num1>=2&&num1<=6){
						$("#houxuan").append(name);
						if(num1!=6){
							$("#houxuan").append("、");
						}
					}
					num1++;
				}
				if(sort==2){
					var content="<dd>";
					content+="<a target=\"_blank\" href=\"user_home?id="+id+"\">";
					content+="<img src=\""+img+"\">";
					content+="<cite>"+name+"</cite>";
					content+="<i>最近加入</i>";
					content+="</a>";
					content+="</dd>";
					$("#xinren").append(content);
				}
				if(sort==3){
					var content="<dd>";
					content+="<a target=\"_blank\" href=\"user_home?id="+id+"\">";
					content+="<img src=\""+img+"\">";
					content+="<cite>"+num+"</cite>";
					if(num3==1){
						content+="<i style=\"color:#F00000;\">No."+num3+"</i>";
					}else if(num3==2){
						content+="<i style=\"color:#DAA520;\">No."+num3+"</i>";
					}else if(num3==3){
						content+="<i style=\"color:#B87333;\">No."+num3+"</i>";
					}else{
						content+="<i>No."+num3+"</i>";
					}
					content+="</a>";
					content+="</dd>";
					$("#chenghao").append(content);
					num3++;
				}
				if(sort==4){
					var content="<dd>";
					content+="<a target=\"_blank\" href=\"user_home?id="+id+"\">";
					content+="<img src=\""+img+"\">";
					content+="<cite>"+num+"元赞助</cite>";
					if(num4==1){
						content+="<i style=\"color:#F00000;\">No."+num4+"</i>";
					}else if(num4==2){
						content+="<i style=\"color:#DAA520;\">No."+num4+"</i>";
					}else if(num4==3){
						content+="<i style=\"color:#B87333;\">No."+num4+"</i>";
					}else{
						content+="<i>No."+num4+"</i>";
					}
					content+="</a>";
					content+="</dd>";
					$("#zanzhu").append(content);
					num4++;
				}
				if(sort==5){
					var content="<dd>";
					content+="<a target=\"_blank\" href=\"user_home?id="+id+"\">";
					content+="<img src=\""+img+"\">";
					content+="<cite>"+num+"个积分</cite>";
					if(num5==1){
						content+="<i style=\"color:#F00000;\">No."+num5+"</i>";
					}else if(num5==2){
						content+="<i style=\"color:#DAA520;\">No."+num5+"</i>";
					}else if(num5==3){
						content+="<i style=\"color:#B87333;\">No."+num5+"</i>";
					}else{
						content+="<i>No."+num5+"</i>";
					}
					content+="</a>";
					content+="</dd>";
					$("#jifen").append(content);
					num5++;
				}
			}
		}
	});
	
	// ajax最热榜
	$.ajax({
		url : "user/resourcestop",
		type : "post",
		dataType : 'json',
		cache : false,
		async : false,
		success : function(data) {
			for(var i in data){
				var sort = data[i].sort;
				var id = data[i].id;
				var name = data[i].name;
				var num = data[i].num;
				var content="<dd>";
				content+="<a target=\"_blank\" href=\"resource_detail?id="+id+"\">"+name+"</a>";
				if(sort==1){
					content+="<span><i class=\"iconfont\" title=\"评论\">&#xe60c;</i>"+num+"</span>";
				}else if(sort==2){
					content+="<span><i class=\"layui-icon\" title=\"下载\" style=\"font-size: 14px;\">&#xe601;</i> "+num+"</span>";
				}else if(sort==3){
					content+="<span><i class=\"layui-icon\" title=\"收藏\" style=\"font-size: 14px;\">&#xe600;</i> "+num+"</span>";
				}
				content+="</dd>";
				if(sort==1){
					$("#commentstop").append(content);
				}else if(sort==2){
					$("#downloadtop").append(content);
				}else if(sort==3){
					$("#keeptop").append(content);
				}
			}
		}
	});
	
});

});
