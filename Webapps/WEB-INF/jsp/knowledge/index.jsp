<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<HTML>
<HEAD>
	<TITLE> 代码分享网-源码库、知识库、视频库、项目库，致力于为程序员提供最有帮助的代码</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="代码库，代码分享网，知识库">
	<meta name="description" content="代码库-代码分享网，致力于为程序员提供最有帮助的代码">
	<meta http-equiv="Content-Type" content="text/html"; charset="utf-8">  
	<link rel="stylesheet" href="res/modules/other/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="res/layui/css/layui.css">
	<link rel="stylesheet" href="res/css/global.css">
	<link rel="stylesheet" type="text/css" href="res/modules/other/fenye/fenye.css" media="screen"/>
	<Link rel="shortcut icon" href="res/images/logo.ico">
	<link rel="stylesheet" type="text/css" href="res/modules/other/search/style.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="res/modules/other/table/style.css" media="screen"/>
 </HEAD>

<BODY>

<%@ include file="../public/header.jsp"%>

<div class="main layui-clear">
<div class="fly-panel" pad20 style="height:1800px;">
	<h2 class="page-title">知识库：
	<button id="tijiao" class="layui-btn">分享知识</button>
	<button id="shenhe" class="layui-btn">待审核(<span id="waitshenhe">0</span>)</button>
	<!-- 搜索框 -->
	<span style="float:right;" class="zySearch" id="zySearch"></span>

	</h2>
	<div style="float:left;">
		<div class="zTreeDemoBackground left" style="float:left;width:190px;height:1000px;">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		<div class="zTreeDemoBackground left" style="float:left;margin-left:10px;width:840px;height:1000px;">
			<!-- <div style="height:50px;">
			<img src="res/images/laba.gif" alt="通知" />：
			<marquee id="affiche" align="left" behavior="scroll" direction="up"  hspace="0" height="40px" width="800px" vspace="0" loop="-1" scrollamount="1" scrolldelay="100" onMouseOut="this.start()" onMouseOver="this.stop()">
				今日新增知识：<span id="daynum">0</span>条，知识库总数量：<span id="allnum">0</span>条
				<div id="todaypeople"></div>
				<br/><a style="color:#4F99CF;" target="_blank" href="user_home?id=9">代码库</a>&nbsp;成功分享10条有用的知识，账户积分+1。时间：2016-06-07 23:08:08
				<br/><a style="color:#4F99CF;" target="_blank" href="user_home?id=9">代码库</a>&nbsp;成功分享10条有用的知识，账户积分+1。时间：2016-06-07 23:08:08
			</marquee>
			</div> -->
			<div class="demo">
			    <div id="fenye1"></div>
			</div>
			<input id="fenyesort" type="hidden" value="0"/>
			<input id="fenyekeyword" type="hidden" value="0"/>
			<div class="demo">
			<table id="rounded-corner" class="nicetable" summary="Employee Pay Sheet">
			    <thead>
			    	<tr>
			        	<th scope="col" style="width:390px;">知识标题</th>
			            <th scope="col">所属分类</th>
			            <th scope="col">收藏</th>
			            <th scope="col">分享时间</th>
			            <th scope="col">备注</th>
			        </tr>
			    </thead>
			    <tbody id="moretr">
			        <tr id="firsttr"></tr>
			    </tbody>
			</table>
			</div>
			
			<div class="demo" style="bottom:0px;">
			    <div id="fenye2"> </div>
			</div>
		</div>
	</div>
</div>
</div>

<script type="text/javascript" src="res/modules/other/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="res/modules/other/ztree/js/jquery.ztree.all.min.js"></script>
<!-- 搜索框特效 -->
<script type="text/javascript" src="res/modules/other/search/zySearch.js"></script>
<script>
$(document).ready(function(){
	var treeNodes;  //定义Ztree的节点数组
	var setting = {
		async: {
			enable: false, //是否打开异步请求
			url:"ztree/getZtree"	//异步请求的地址				
		},data: {
			simpleData: {
				enable: true,
				idKey: "id",  //设置字段ID格式
				pIdKey: "pid"  //设置父ID的格式
			}
		},callback: {
			beforeExpand: beforeExpand,
			onExpand: onExpand,
			onClick: onClick
		}
	};
	$.ajax({	
		async : false, 
        cache:false,  
        type: "post",  
        dataType : "json",  //返回json格式	    
        url: "ztree/getZtree",  //请求的URL路径  
        success:function(data){ //请求成功后处理函数。  	
         //请求后返回的json是字符串，需要用eval()函数转换成Object类型
           treeNodes= eval("(" + data + ")");			        
        }
	 });
	var curExpandNode = null;
	function beforeExpand(treeId, treeNode) {
		var pNode = curExpandNode ? curExpandNode.getParentNode():null;
		var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
			if (treeNode !== treeNodeP.children[i]) {
				zTree.expandNode(treeNodeP.children[i], false);
			}
		}
		while (pNode) {
			if (pNode === treeNode) {
				break;
			}
			pNode = pNode.getParentNode();
		}
		if (!pNode) {
			singlePath(treeNode);
		}
	}
	function singlePath(newNode) {
		if (newNode === curExpandNode) return;
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                rootNodes, tmpRoot, tmpTId, i, j, n;
        if (!curExpandNode) {
            tmpRoot = newNode;
            while (tmpRoot) {
                tmpTId = tmpRoot.tId;
                tmpRoot = tmpRoot.getParentNode();
            }
            rootNodes = zTree.getNodes();
            for (i=0, j=rootNodes.length; i<j; i++) {
                n = rootNodes[i];
                if (n.tId != tmpTId) {
                    zTree.expandNode(n, false);
                }
            }
        } else if (curExpandNode && curExpandNode.open) {
			if (newNode.parentTId === curExpandNode.parentTId) {
				zTree.expandNode(curExpandNode, false);
			} else {
				var newParents = [];
				while (newNode) {
					newNode = newNode.getParentNode();
					if (newNode === curExpandNode) {
						newParents = null;
						break;
					} else if (newNode) {
						newParents.push(newNode);
					}
				}
				if (newParents!=null) {
					var oldNode = curExpandNode;
					var oldParents = [];
					while (oldNode) {
						oldNode = oldNode.getParentNode();
						if (oldNode) {
							oldParents.push(oldNode);
						}
					}
					if (newParents.length>0) {
						zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
					} else {
						zTree.expandNode(oldParents[oldParents.length-1], false);
					}
				}
			}
		}
		curExpandNode = newNode;
	}
	function onExpand(event, treeId, treeNode) {
		curExpandNode = treeNode;
	}
	function onClick(e,treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandNode(treeNode, null, null, null, true);
	}
	zTree = $.fn.zTree.init($("#treeDemo"), setting, treeNodes);
	
	/* 获取首页加载分页 */
	$.ajax({
		url : "ztree/getNumber",
		type : "post",
		data : {
			sort : 0,
			keyword : 0
		},
		dataType : 'json',
		async : false,
		success : function(count) {
			fenye(0,count,0);
		}
	});
	
	/* 获取待审核数量 */
	$.ajax({
		url : "ztree/getWaitNum",
		type : "post",
		data : {
			sort : 0,
			keyword : 0
		},
		dataType : 'json',
		async : false,
		success : function(count) {
			$("#waitshenhe").html(count);
		}
	});
	
	//获取公告
	$.ajax({	
		async : false, 
        cache:false,  
        type: "post",  
        dataType : "json",  //返回json格式	    
        url: "ztree/getToday",  //请求的URL路径  
        success:function(data){ //请求成功后处理函数。  	
        	for(var i in data){
				var message = data[i].message;
				var nowtime = data[i].nowtime;
				var daynum = data[i].daynum;
				var allnum = data[i].allnum;
				$("#daynum").html(daynum);
				$("#allnum").html(allnum);
				$("#todaypeople").append(message+"时间："+nowtime+"<br/>");
			}
        }
	 });
});
</script>
<script src="res/modules/other/fenye/jquery.paginate.js" type="text/javascript"></script>
<script type="text/javascript">
/* 点击树节点搜索 */
function clickZtree(id){
	$("#fenyesort").val(1);
	$("#fenyekeyword").val(id);
	$("#moretr").empty();
	$.ajax({
		url : "ztree/getNumber",
		type : "post",
		data : {
			sort : 1,
			keyword : id
		},
		dataType : 'json',
		async : false,
		success : function(count) {
			fenye(1,count,id);
		}
	});
}

/* 关键字搜索 */
$("#zySearch").zySearch({
	"width":"355",
	"height":"33",
	"parentClass":"pageTitle",
	"callback":function(keyword){
		if(keyword.trim().length==0){
			alert("还没有输入关键词呢");
			return false;
		}
		$("#fenyesort").val(2);
		$("#fenyekeyword").val(keyword);
		$("#moretr").empty();
		$.ajax({
			url : "ztree/getNumber",
			type : "post",
			data : {
				sort : 2,
				keyword : keyword
			},
			dataType : 'json',
			async : false,
			success : function(count) {
				fenye(2,count,keyword);
			}
		});
	}
});

/* sort：0表示首页加载，1表示树节点加载，2表示关键词加载 */
function fenye(sort,allcount,value){
	if(allcount==0){
		$("#firsttr").append("<td>暂无数据</td><td>暂无数据</td><td>暂无数据</td><td>暂无数据</td><td>暂无数据</td>");
		return false;
	}
	/* allcount表示总记录数，规定每页maxResult显示30条记录，计算总页数count */
	maxResult=30;
	count =  Math.ceil(allcount/maxResult);
	
	/* 初始加载一次 */
	$.ajax({
		url : "ztree/getTreesByFenye",
		type : "post",
		data : {
			sort : sort,
			maxResult : maxResult,
			page:1,
			keyword:value
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			for(var i in data){
				var id = data[i].id;
				var title = data[i].title;
				var url = data[i].url;
				var sort = data[i].sort;
				var number = data[i].number;
				var remark = data[i].remark;
				var nowtime = data[i].nowtime;
				content='<tr>';
				content+='<td><a href="'+url+'" style="color:black;" target="_blank" onmouseover="this.style.color=\'#FFF\'" onmouseout="this.style.color=\'black\'">'+title+'</a></td>';
				content+='<td>'+sort+'</td>';
				content+='<td><span class="jieda-zan" type="zan"><i id="zantubiao'+id+'" class="layui-icon" onclick="javascript:zan('+id+')" onMouseOver="this.style.color=\'red\'" onMouseOut="this.style.color=\'#669\'">&#xe600;</i>&nbsp;<label id="zannum'+id+'">'+number+'</label></span></td>';
				content+='<td>'+nowtime+'</td>';
				content+='<td>'+remark+'</td>';
				content+='</tr>';
				$("#moretr").append(content);
			}
		}
	});
	
	$("#fenye1,#fenye2").paginate({
		count 		: count,
		start 		: 1,
		display     : 25,
		border					: false,
		text_color  			: '#79B5E3',
		background_color    	: 'none',	
		text_hover_color  		: '#2573AF',
		background_hover_color	: 'none', 
		images		: false,
		mouse		: 'press',
		onChange     			: function(page){
			sort = $("#fenyesort").val();
			value = $("#fenyekeyword").val();
			//page为用户选中页
			$.ajax({
				url : "ztree/getTreesByFenye",
				type : "post",
				data : {
					sort : sort,
					maxResult : maxResult,
					page:page,
					keyword:value
				},
				dataType : 'json',
				async : false,
				success : function(data) {
					$("#moretr").empty();
					for(var i in data){
						var id = data[i].id;
						var title = data[i].title;
						var url = data[i].url;
						var sort = data[i].sort;
						var number = data[i].number;
						var remark = data[i].remark;
						var nowtime = data[i].nowtime;
						content='<tr>';
						content+='<td><a href="'+url+'" style="color:black;" target="_blank" onmouseover="this.style.color=\'#FFF\'" onmouseout="this.style.color=\'black\'">'+title+'</a></td>';
						content+='<td>'+sort+'</td>';
						content+='<td><span class="jieda-zan" type="zan"><i id="zantubiao'+id+'" class="layui-icon" onclick="javascript:zan('+id+')" onMouseOver="this.style.color=\'red\'" onMouseOut="this.style.color=\'#669\'">&#xe600;</i>&nbsp;<label id="zannum'+id+'">'+number+'</label></span></td>';
						content+='<td>'+nowtime+'</td>';
						content+='<td>'+remark+'</td>';
						content+='</tr>';
						$("#moretr").append(content);
					}
				}
			});
		}
	});
}

/* 收藏功能 */
function zan(id){
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
	if(flag){
		$.ajax({
			url : "ztree/addNumById",
			type : "post",
			data : {
				numid:id
			},
			dataType : 'json',
			async : false,
			success : function(flag) {
				if(flag){
					var zannum = $("#zannum"+id).text();
					zannum = parseInt(zannum)+1;
					$("#zannum"+id).text("已收藏");
					$("#zantubiao"+id).remove();
				}else{
					alert('已经收藏过该知识了');
				}
			}
		});
	}else{
		alert('尚未登录');
	}
}

$("#tijiao").click(function(){
	content='<form class="layui-form layui-form-pane">';
	content+='<span style="color:##e2e2e2;font-weight: 300;">有的时候，找不到合适的资料或者找到的资料压根没有用，这是让人最烦的，你觉得呢？<br/><br/>知识库模块是整理有用、高质量的资料，以便大家快速找到所需知识，节省你的时间。<br/><br/>我为人人，人人为我，非常希望你能够分享更有用的资料，和大家一起学习，共同进步。<br/><br/>温馨提示：请勿提交重复或无用的知识，一旦发现，永久封号。</span>';
	content+='<input type="text" style="border-radius:10px;padding:5px;margin:15px 5px 10px 0px;" placeholder="标题（必填*）" id="title" name="title" required lay-verify="title" autocomplete="off" class="layui-input">';
	content+='<input type="text" style="border-radius:10px;padding:5px;margin:10px 5px 10px 0px;" placeholder="超链接（必填*）" id="url" name="url" required lay-verify="url" autocomplete="off" class="layui-input">';
	content+='<input type="text" onclick="showMenu();return false;" style="border-radius:10px;padding:5px;margin:10px 5px 10px 0px;" placeholder="分类（必选*）" id="sort" name="sort" required lay-verify="sort" autocomplete="off" class="layui-input">';
	content+='<div id="showsorttree" class="zTreeDemoBackground left" style="float:left;width:180px;margin:0px 0px 10px 10px;"><ul id="sorttree" class="ztree" style="background-color: #fff;"></ul></div>';
	content+='<input type="hidden" id="pid">';
	content+='<input type="text" style="border-radius:10px;padding:5px;margin:10px 5px 10px 0px;" placeholder="备注（7字以内，可不填）" id="note" name="note" required lay-verify="note" autocomplete="off" class="layui-input">';
	content+='</form>';
	//提交知识
	layer.open({
	  type: 1
	  ,title: "知识库-分享知识" //不显示标题栏
	  ,closeBtn: 2
	  ,scrollbar:true
	  ,fix:true
	  ,area: ['700px','auto']
	  ,shade: 0
	  ,id: 'LAY_tijiao' //设定一个id，防止重复弹出
	  ,resize: true
	  ,btn: ['分享知识']
	  ,btnAlign: 'c'
	  ,moveType: 0 //拖拽模式，0或者1
	  ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">'+content+'</div>'
	  ,success: function(layero){
		  
	  },yes: function(index, layero){
		    title = $("#title").val();
		    url = $("#url").val();
		    sort = $("#sort").val();
		    note = $("#note").val();
		    pid = $("#pid").val();
		    if(title.trim().length==0){
		    	alert("还没有标题呢");
		    	return false;
		    }else if(title.length>255){
		    	alert("标题太长了，限制255个字符");
		    	return false;
		    }
		    if(url.trim().length==0){
		    	alert("还没有超链接呢");
		    	return false;
		    }else if(url.length>255){
		    	alert("超链接太长了，限制255个字符");
		    	return false;
		    }
		    if(sort.trim().length==0){
		    	alert("还没有分类呢");
		    	return false;
		    }
		    if(note.length>7){
		    	alert("备注太长了，7字以内就行");
		    	return false;
		    }
		    $.ajax({
		    	url : "ztree/insertTree",
		    	type : "post",
		    	data : {
		    		name : title,
		    		url :url,
		    		remark :note,
		    		pid :pid
		    	},
		    	dataType : 'json',
		    	async : false,
		    	success : function(flag) {
		    		if(flag){
			    		layer.msg('知识分享成功', {
							icon: 1,
							time: 1000 //2秒关闭（如果不配置，默认是3秒）
							,shade: 0.1
						}, function(){
				    		window.location.reload();
						}); 
		    		}
		    	}
			 });
		    
		    layer.close(index); //如果设定了yes回调，需进行手工关闭
		 }
	});
	
	$("#showsorttree").hide();

});

function showMenu(){
	$("#showsorttree").show();
	var treeNodes;  //定义Ztree的节点数组
	var setting = {
		async: {
			enable: false, //是否打开异步请求
			url:"ztree/getSimpleZtree"	//异步请求的地址				
		},data: {
			simpleData: {
				enable: true,
				idKey: "id",  //设置字段ID格式
				pIdKey: "pid"  //设置父ID的格式
			}
		},callback: {
			beforeExpand: beforeExpand,
			onExpand: onExpand,
			onClick: onClick
		}
	};
	$.ajax({	
		async : false, 
        cache:false,  
        type: "post",  
        dataType : "json",  //返回json格式	    
        url: "ztree/getSimpleZtree",  //请求的URL路径  
        success:function(data){ //请求成功后处理函数。  	
         //请求后返回的json是字符串，需要用eval()函数转换成Object类型
           treeNodes= eval("(" + data + ")");			        
        }
	 });
	var curExpandNode = null;
	function beforeExpand(treeId, treeNode) {
		var pNode = curExpandNode ? curExpandNode.getParentNode():null;
		var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
		var zTree = $.fn.zTree.getZTreeObj("sorttree");
		for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
			if (treeNode !== treeNodeP.children[i]) {
				zTree.expandNode(treeNodeP.children[i], false);
			}
		}
		while (pNode) {
			if (pNode === treeNode) {
				break;
			}
			pNode = pNode.getParentNode();
		}
		if (!pNode) {
			singlePath(treeNode);
		}
	}
	function singlePath(newNode) {
		if (newNode === curExpandNode) return;
        var zTree = $.fn.zTree.getZTreeObj("sorttree"),
                rootNodes, tmpRoot, tmpTId, i, j, n;
        if (!curExpandNode) {
            tmpRoot = newNode;
            while (tmpRoot) {
                tmpTId = tmpRoot.tId;
                tmpRoot = tmpRoot.getParentNode();
            }
            rootNodes = zTree.getNodes();
            for (i=0, j=rootNodes.length; i<j; i++) {
                n = rootNodes[i];
                if (n.tId != tmpTId) {
                    zTree.expandNode(n, false);
                }
            }
        } else if (curExpandNode && curExpandNode.open) {
			if (newNode.parentTId === curExpandNode.parentTId) {
				zTree.expandNode(curExpandNode, false);
			} else {
				var newParents = [];
				while (newNode) {
					newNode = newNode.getParentNode();
					if (newNode === curExpandNode) {
						newParents = null;
						break;
					} else if (newNode) {
						newParents.push(newNode);
					}
				}
				if (newParents!=null) {
					var oldNode = curExpandNode;
					var oldParents = [];
					while (oldNode) {
						oldNode = oldNode.getParentNode();
						if (oldNode) {
							oldParents.push(oldNode);
						}
					}
					if (newParents.length>0) {
						zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
					} else {
						zTree.expandNode(oldParents[oldParents.length-1], false);
					}
				}
			}
		}
		curExpandNode = newNode;
	}
	function onExpand(event, treeId, treeNode) {
		curExpandNode = treeNode;
	}
	function onClick(e,treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("sorttree");
		zTree.expandNode(treeNode, null, null, null, true);
	}
	zTree = $.fn.zTree.init($("#sorttree"), setting, treeNodes);
	
	$("showsorttree").css('display','block'); 
}

/* 点击树节点，文本框赋值 */
function clickSimpleZtree(id,name){
	$("#pid").val(id);
	$("#sort").val(name);
	$("#showsorttree").hide();
}

/* 审核知识 */
$("#shenhe").click(function(){
	content='<span style="color:##e2e2e2;font-weight: 300;">代码库域名是admintwo，你知道是什么意思吗？<br/><br/>中文简单翻译：两个管理员。除了站长管理员之外，所有代码库注册用户也都是管理员。<br/><br/>提交的知识，注册用户均可审核，只要你们认为是有用的知识，站长相信那绝对是好的。</span>';
	content+='<div id="waitZtrees">';
	content+='<table style="width:650px;margin-top:20px;" id="rounded-corner" class="nicetable" summary="Employee Pay Sheet">';
	content+='<thead><tr>';
	content+='<th scope="col" style="width:450px;">知识标题</th>';
	content+='<th scope="col">所属分类</th>';
	content+='<th scope="col">待审核</th>';
	content+='</tr></thead><tbody>';
	//ajax获取待审核知识填充
	$.ajax({
    	url : "ztree/getWaitTrees",
    	type : "post",
    	dataType : 'json',
    	async : false,
    	success : function(data) {
    		if(data){
    			count=0;
    			for(var i in data){
    				var id = data[i].id;
    				var title = data[i].title;
    				var url = data[i].url;
    				var sort = data[i].sort;
    				content+='<tr>';
    				content+='<td><a href="'+url+'" style="color:black;" target="_blank" onmouseover="this.style.color=\'#FFF\'" onmouseout="this.style.color=\'black\'">'+title+'</a></td>';
    				content+='<td>'+sort+'</td>';
    				content+='<td id="button'+id+'"><a  href="javascript:void(0);" onclick="shenhebyid('+id+',0)" style="color:blue" onmouseover="this.style.color=\'red\'" onmouseout="this.style.color=\'blue\'">通过</a>';
    				content+='<a  href="javascript:void(0);" onclick="shenhebyid('+id+',1)" style="color:blue;margin-left:10px;" onmouseover="this.style.color=\'red\'" onmouseout="this.style.color=\'blue\'">不通过</a>';
    				content+='</td>';
    				content+='</tr>';
    				count++;
    			}
    			if(count===0){
    				content+='<tr>';
    				content+='<td>暂无数据</td>';
    				content+='<td>暂无数据</td>';
    				content+='<td>暂无数据</td>';
    				content+='</tr>';
    			}
    		}
    	}
	 });
	content+='</tbody></table>';
	content+='</div>';
	
	//审核知识
	layer.open({
		type: 1
		,title: "知识库-审核知识" //不显示标题栏
		,closeBtn: 2
		,area: '700px;'
		,shade: 0
		,id: 'LAY_tijiao' //设定一个id，防止重复弹出
		,resize: false
		,btn: false
		,btnAlign: 'c'
		,moveType: 1 //拖拽模式，0或者1
		,content: '<div style="padding: 25px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">'+content+'</div>'
	});
	
});

/* 通过id审核,0代表通过，1不通过 */
function shenhebyid(id,flag){
	$.ajax({
		url : "user/isUserSession",
		type : "post",
		dataType : 'json',
		cache : false,
		async : false,
		success : function(data) {
			confirm="";
			if(flag==0){
				confirm="1、确定链接可以打开<br/>2、确定此知识有用";
			}else if(flag==1){
				confirm="1、确定链接打不开或者打开太慢<br/>2、或者确定此知识无用";
			}
			if(data){
				layer.confirm(confirm, {icon: 3, title:'提示'}, function(index){
					$.ajax({
						url : "ztree/shenheTree",
						type : "post",
						data : {
				    		treeid:id,
				    		flag:flag
				    	},
						dataType : 'json',
						cache : false,
						async : false,
						success : function(result) {
							if(result){
								$("#button"+id).html("已审核");
							}
						}
					});
				    layer.close(index);
				});
			}else{
				alert('审核前需要登录，尚未登录');
			}
		}
	});
}


</script>
<%@ include file="../public/knowledge_footer.jsp"%>
</BODY>
</HTML>