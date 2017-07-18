<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<HTML>
<HEAD>
<title> 代码分享网-源码库、知识库、视频库、项目库，致力于为程序员提供最有帮助的代码</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="代码库，代码分享网，知识库">
<meta name="description" content="代码库-代码分享网，致力于为程序员提供最有帮助的代码">
<Link rel="shortcut icon" href="res/images/logo.ico">
<style type="text/css">
	.hide_box{z-index:999;filter:alpha(opacity=50);background:#666;opacity: 0.5;-moz-opacity: 0.5;left:0;top:0;height:99%;width:100%;position:fixed;display:none;}
	.shang_box{width:540px;/* height:540px; */padding:10px;background-color:#fff;border-radius:10px;position:fixed;z-index:1000;left:50%;top:50%;margin-left:-280px;margin-top:-200px;border:1px dotted #dedede;display:none;}
	.shang_box img{border:none;border-width:0;}
	.dashang{display:block;width:50px;margin:5px auto;height:25px;line-height:25px;padding:10px;background-color:#E74851;color:#fff;text-align:center;text-decoration:none;border-radius:50px;font-weight:bold;font-size:16px;transition: all 0.3s;}
	.dashang:hover{opacity:0.8;padding:15px;font-size:18px;}
	.shang_close{float:right;display:inline-block;}
	.shang_logo{display:block;text-align:center;margin:20px auto;}
	.shang_tit{width: 100%;height: 75px;text-align: center;line-height: 66px;color: #a3a3a3;font-size: 16px;background: url('res/images/dashang/img/cy-reward-title-bg.jpg');font-family: 'Microsoft YaHei';margin-top: 7px;margin-right:2px;}
	.shang_tit p{color:#a3a3a3;text-align:center;font-size:16px;}
	.shang_payimg{width:140px;padding:10px;border:6px solid #EA5F00;margin:0 auto;border-radius:3px;height:140px;}
	.shang_payimg img{display:block;text-align:center;width:140px;height:140px; }
	.pay_explain{text-align:center;margin:10px auto;font-size:12px;color:#545454;}
	.radiobox{width: 16px;height: 16px;background: url('res/images/dashang/img/radio2.jpg');display: block;float: left;margin-top: 5px;margin-right: 14px;}
	.checked .radiobox{background:url('res/images/dashang/img/radio1.jpg');}
	.shang_payselect{text-align:center;margin:0 auto;margin-top:40px;cursor:pointer;height:60px;width:280px;}
	.shang_payselect .pay_item{display:inline-block;margin-right:10px;float:left;}
	.shang_info{clear:both;}
	.shang_info p,.shang_info a{color:#C3C3C3;text-align:center;font-size:12px;text-decoration:none;line-height:2em;}
</style>
<script type="text/javascript" src="res/modules/other/ztree/js/jquery-1.4.4.min.js"></script>
</head>

<body>
<p><a href="javascript:void(0)" onclick="dashangToggle()" class="dashang" title="打赏，支持一下">打赏</a></p>
<div class="hide_box"></div>
<div class="shang_box">
	<a class="shang_close" href="javascript:void(0)" onclick="dashangToggle()" title="关闭"><img src="res/images/dashang/img/close.jpg" alt="取消" /></a>
	<div class="shang_tit">
		<p>作者很不错，打赏1块钱</p>
	</div>
	<div class="shang_payimg">
		<img id="dashangimg" src="res/images/dashang/img/userpayimg/weixin.jpg" alt="扫码支持" title="扫一扫" />
	</div>
		<div class="pay_explain">你的打赏，就是作者分享代码最大的动力<img alt="[嘻嘻]" title="[嘻嘻]" src="res/layui/images/face/01.gif"></div>
	<div class="shang_payselect">
		<div id="weixinimg" class="pay_item checked" data-id="weixin">
			<span class="radiobox"></span>
			<span class="pay_logo"><img src="res/images/dashang/img/wechat.jpg" alt="微信" /></span>
		</div>
		<div id="zhifubaoimg" class="pay_item" data-id="zhifubao">
			<span class="radiobox"></span>
			<span class="pay_logo"><img src="res/images/dashang/img/alipay.jpg" alt="支付宝" /></span>
		</div>
	</div>
	<div class="shang_info">
		<p>打开<span id="shang_pay_txt">微信</span>扫一扫，即可进行扫码打赏支持</p>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$(".pay_item").click(function(){
		$(this).addClass('checked').siblings('.pay_item').removeClass('checked');
		var dataid=$(this).attr('data-id');
		$(".shang_payimg img").attr("src","res/images/dashang/img/userpayimg/"+dataid);
		$("#shang_pay_txt").text(dataid=="alipay"?"支付宝":"微信");
	});
});
function dashangToggle(){
	$(".hide_box").fadeToggle();
	$(".shang_box").fadeToggle();
}
</script>
<!-- 代码 结束 -->
</body>
</html>