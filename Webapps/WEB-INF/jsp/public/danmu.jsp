<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.admintwo.model.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>弹幕</title>
<style type="text/css">

/* screen start*/
.screen {
	width: 300px;
	height: 100px;
	background: #669900;
}

.dm {
	width: 100%;
	height: 100%;
	position: absolute;
	top: 0;
	left: 0;
	display: none;
}

.dm .d_screen .d_del {
	width: 38px;
	height: 38px;
	background: #600;
	display: block;
	text-align: center;
	line-height: 38px;
	text-decoration: none;
	font-size: 20px;
	color: #fff;
	border-radius: 19px;
	/* border: 1px solid #fff; */
	z-index: 2;
	position: absolute;
	right: 20px;
	top: 15px;
	outline: none;
}

.dm .d_screen .d_del:hover {
	background: #F00;
}

.dm .d_screen .d_mask {
	width: 1%;
	height: 50%;
	/* background: #000; */
	position: absolute;
	top: 10px;
	left: 0;
	opacity: 0.6;
	filter: alpha(opacity = 60);
	z-index: 9;
}

.dm .d_screen .d_show {
	position: relative;
	z-index: 9;
}

.dm .d_screen .d_show div {
	font-size: 26px;
	line-height: 36px;
	font-weight: 500;
	position: absolute;
	top: 76px;
	left: 10;
	color: #fff;
}
/*end screen*/

/*send start*/
.send {
	width: 100%;
	height: 76px;
	position: absolute;
	bottom: 0;
	left: 0;
	/* border: 1px solid red; */
}

.send .s_filter {
	width: 100%;
	height: 76px;
	/* background: #000; */
	position: absolute;
	bottom: 0;
	left: 0;
	opacity: 0.6;
	filter: alpha(opacity = 60);
}

.send  .s_con {
	/* width: 100%; */
	height: 76px;
	position: absolute;
	top: 2px;
	left: 200px;
	z-index: 2;
	text-align: center;
	line-height: 76px;
}

.send .s_con .s_text {
	width: 200px;
	height: 36px;
	border: 0;
	border-radius: 6px 0 0 6px;
	outline: none;
	border: 1px solid #5bba32;
}

.send .s_con .s_submit {
	width: 100px;
	height: 36px;
	border-radius: 0 6px 6px 0;
	outline: none;
	font-size: 14px;
	color: #fff;
	background: #65c33d;
	font-family: "微软雅黑";
	cursor: pointer;
	border: 1px solid #5bba32;
}

.send .s_con .s_submit:hover {
	background: #3eaf0e;
}
/*end send*/
</style>
</head>
<body>
	<h2 class="fly-tip">
	<a href="#" id="startDm">点击此处开启弹幕功能 <br/>（感觉弹幕功能挺好玩的，感兴趣的小伙伴可以弹幕上交流哈<img alt="[嘻嘻]" title="[嘻嘻]" src="res/layui/images/face/01.gif">）</a>
	</h2>
	<!-- dm start -->
	<div class="dm">
		<!-- d_screen start -->
		<div class="d_screen">
			<a href="#" class="d_del">X</a>
			<div class="d_mask"></div>
			<div class="d_show"></div>
		</div>
		<!-- end d_screen -->

		<!-- send start -->
		<div class="send">
			<div class="s_filter"></div>
			<div class="s_con">
				<input type="text" class="s_text" /> <input type="button"
					value="发射弹幕" class="s_submit" id="btn" />
			</div>
		</div>
		<!-- end send -->
	</div>
	<!-- end dm-->
	<script type="text/javascript"
		src="res/modules/other/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="res/modules/other/danmu/websocket.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#startDm,.d_del").click(function() {
				$("#startDm,.dm").toggle(1000);
				//init_screen();
			});
			$("#btn").click(function() {
				<%if(((User) session.getAttribute("user"))==null){ %>
					alert("尚未登录");
				<%}else{ %>
					
					send();
				<%} %>
			});
			$(".s_text").keydown(function() {
				var code = window.event.keyCode;
				if (code == 13)//回车键按下时，输出到弹幕
				{
				<%if(((User) session.getAttribute("user"))==null){ %>
					alert("尚未登录");
				<%}else{ %>
					
					send();
				<%} %>
				}
			});

		});

		function launch() {
			var _height = $(window).height();
			var _left = $(window).width();
			var time = 10000;
			if (index % 2 == 0)
				time = 20000;
			_top += 80;
			if (_top > _height - 100)
				_top = 80;
			$("#" + index).css({
				left : _left,
				top : _top,
				color : getRandomColor()

			});
			$("#" + index).animate({
				left : "-" + _left + "px"
			}, time, function() {
			});
			index++;
		}

		 //初始化弹幕
		 function init_screen() {
		 var _top = 0;
		 var _height = $(window).height();
		 $(".d_show").find("div").show().each(function() {
		 var _left = $(window).width() - $(this).width();
		 var time=10000;
		 if($(this).index()%2==0)
		 time=20000;
		 _top+=80;
		 if(_top>_height-100)
		 _top=80;
		 $(this).css({
		 left:_left,
		 top:_top,
		 color:getRandomColor()

		 });
		 $(this).animate({
		 left:"-"+_left+"px"},
		 time,
		 function(){});
		 });
		 } 

		//随机获取颜色值
		function getRandomColor() {
			/* return '#' + (function(h) {
				return new Array(7 - h.length).join("0") + h
			})((Math.random() * 0x1000000 << 0).toString(16)) */
			var r = Math.floor(Math.random()*256);
            var g = Math.floor(Math.random()*256);
            var b = Math.floor(Math.random()*256);
            return "rgb("+r+","+g+","+b+")";
		}
	</script>
</body>
</html>