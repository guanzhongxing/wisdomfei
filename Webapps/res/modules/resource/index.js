/**
 * 
 * @Name: 代码资源流动加载
 * 
 */
layui.define(['element','layer', 'util', 'flow'], function(exports){
  
  var $ = layui.jquery
  ,element = layui.element
  ,layer = layui.layer
  ,flow = layui.flow
  ,util = layui.util
  ,device = layui.device()
  
  var flow = layui.flow;
  //当你执行这样一个方法时，即对页面中的全部带有lay-src的img元素开启了懒加载（当然你也可以指定相关img）
  flow.lazyimg(); 
  
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
				if(i>=59){
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
						if(i<=3||i>=29){
							content+="<img id=\""+r_id+"\" src=\"res/images/1.png\" onMouseOver=\"this.src='res/images/2.png'\" onMouseOut=\"this.src='res/images/1.png'\" onclick=\"layer.open({type: 1,title:'"+r_name+"',closeBtn: 0,offset:'100px',area: '600px',skin: 'layui-layer-nobg',shadeClose: true,content: '<img style=width:600px; src="+r_urlpwd+">'});\" data-gif=\""+r_urlpwd+"\">";
						}else{
							content+="<img id=\""+r_id+"\" lay-src=\"res/images/1.png\" onMouseOver=\"this.src='res/images/2.png'\" onMouseOut=\"this.src='res/images/1.png'\" onclick=\"layer.open({type: 1,title: '"+r_name+"',closeBtn: 0,offset:'100px',area: '600px',skin: 'layui-layer-nobg',shadeClose: true,content: '<img style=width:600px; src="+r_urlpwd+">'});\" data-gif=\""+r_urlpwd+"\">";
						}
						content+="<br><br></p>";
					}
					
					content+="<p><span><a href=\"user_home?id="+u_id+"\">"+u_name+"("+u_label+")</a></span>";
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
					content+="<span class=\"fly-list-hint\">";
					content+="<i class=\"iconfont\" title=\"评论\">&#xe60c;</i> <label id=\"res_comments_num\">"+res_comments_num+"</label>";
					content+="<i class=\"layui-icon\" title=\下载\" style=\"font-size: 14px;\">&#xe601;</i> <label id=\"res_download_num\">"+res_download_num+"</label>";
					content+="<i class=\"layui-icon\" title=\"收藏\" style=\"font-size: 14px;\">&#xe600;</i> <label id=\"res_keep_num\">"+res_keep_num+"</label>";
					content+="</span></p></li>";
					$("#fly_list_flow").append(content);
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
  });
  
  //总页数
  var pages=1;
  //每页数量
  var rows=60;
  //获取数据总量，计算总页数
  $(document).ready(function(){
	  $.ajax({
			url : "resource/getAllResourcesNum",
			type : "post",
			dataType : 'json',
			cache : false,
			async : false,
			success : function(num) {
				pages=Math.ceil(num/rows);
			}
	  })
  });
  
  flow.load({
    elem: '#fly_list_flow' //流加载容器
    ,done: function(page, next){ //执行下一页的回调
      var lis = [];
      //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
	  $.ajax({
			url : 'resource/list?page='+(page+1)+'&&rows='+rows,
			type : "post",
			dataType : 'json',
			cache : false,
			async : false,
			success : function(data) {
				//假设你的列表返回在data集合中
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
						if(i<=1){
							content+="<img id=\""+r_id+"\" src=\"res/images/1.png\" onMouseOver=\"this.src='res/images/2.png'\" onMouseOut=\"this.src='res/images/1.png'\" onclick=\"layer.open({type: 1,title:'"+r_name+"',closeBtn: 0,offset:'100px',area: '600px',skin: 'layui-layer-nobg',shadeClose: true,content: '<img style=width:600px; src="+r_urlpwd+">'});\" data-gif=\""+r_urlpwd+"\">";
						}else{
							content+="<img id=\""+r_id+"\" lay-src=\"res/images/1.png\" onMouseOver=\"this.src='res/images/2.png'\" onMouseOut=\"this.src='res/images/1.png'\" onclick=\"layer.open({type: 1,title: '"+r_name+"',closeBtn: 0,offset:'100px',area: '600px',skin: 'layui-layer-nobg',shadeClose: true,content: '<img style=width:600px; src="+r_urlpwd+">'});\" data-gif=\""+r_urlpwd+"\">";
						}
						content+="<br><br></p>";
					}
					
					content+="<p><span><a href=\"user_home?id="+u_id+"\">"+u_name+"("+u_label+")</a></span>";
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
					content+="<span class=\"fly-list-hint\">";
					content+="<i class=\"iconfont\" title=\"评论\">&#xe60c;</i> <label id=\"res_comments_num\">"+res_comments_num+"</label>";
					content+="<i class=\"layui-icon\" title=\下载\" style=\"font-size: 14px;\">&#xe601;</i> <label id=\"res_download_num\">"+res_download_num+"</label>";
					content+="<i class=\"layui-icon\" title=\"收藏\" style=\"font-size: 14px;\">&#xe600;</i> <label id=\"res_keep_num\">"+res_keep_num+"</label>";
					content+="</span></p></li>";
			        $("#fly_list_flow").append(content);
				}
				
				//执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
				//pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
				next(lis.join(''), page <= pages);    
			}
      });
    }
  });
  
  // 阻止IE7以下访问
  if(device.ie && device.ie < 8){
    layer.alert('如果您非得使用ie浏览代码库，那么请使用ie8+');
  }

});

