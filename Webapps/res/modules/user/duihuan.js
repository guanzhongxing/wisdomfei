/**
 * 
 * @Name: 主页展示
 * 
 */

layui.use(['element', 'layer', 'form', 'element', 'upload' ], function() {
var $ = layui.jquery, form = layui.form(), element = layui.element, layer = layui.layer, element = layui.element()

//自定义验证规则
form.verify({
	moneyToJifen : function(value) {
		// 我的赞助
		var myMoney = $('#myMoney').val();
		if (!value) {
			return '没有输入兑换的金额';
		}
		if((/^(\+|-)?\d+$/.test(value))&&value>0){  
			// 标志位
			var flag ;
			$.ajax({
				url : "user/checkMoney",
				type : "post",
				dataType : 'json',
				data : {
					money : value
				},
				cache : false,
				async : false,
				success : function(data) {
					flag = data;
				},
				error : function() {
					return 'ajax验证余额是否充足失败';
				}
			})
			if(flag){
				form.on('submit(moneyToJifen)', function(data) {
					$.post('user/moneyToJifen',data.field,function(res){
						if(res){
							layer.msg('赞助兑换成功', {
								  icon: 1,
								  time: 1000 //2秒关闭（如果不配置，默认是3秒）
								  ,shade: 0.1
								}, function(){
									// do something
									var email = $('#email').val();
									$.ajax({
										url : "user/getUserByEmail",
										type : "post",
										dataType : 'json',
										data : {
											email : email
										},
										cache : false,
										async : false,
										success : function(data) {
											$("#myjifen").html(data.jifen);
											$("#myzanzhu").html(data.money);
											$("#isduihuan").html(data.money*10);
										},
										error : function() {
											return 'ajax验证余额是否充足失败';
										}
									})
									
								}); 
						}else{
							layer.msg("赞助兑换失败");
						}
						});
					return false;
				});
			}else{
				return '赞助不足喔';
			}
	    }else{
			return '只能是正整数喔';
		}
	}
});

var price;
var nextlabel;

$(document).ready(function(){
	$.ajax({
		url : "user/getlabel",
		type : "post",
		dataType : 'json',
		cache : false,
		async : false,
		success : function(data) {
			var id = data.id;
			var mymoney = data.mymoney;
			var label = data.mylabel.split('.');
			var money = data.money;
			var mychenghao = label[0];
			var nextchenghao = label[1];
			$("#mychenghao").html(mychenghao);
			if(mymoney<money){
				$("#nextchenghao").html("（赞助不足，无法升级到："+nextchenghao+"）");
				$("#chenghaobutton").hide();
			}else{
				$("#nextchenghao").html("（可升级到："+nextchenghao+"）");
			}
			$("#mymoney").html(mymoney);
			$("#moneyTochenghao").attr('placeholder',"￥ "+money+" 元");
			price = money;
			nextlabel = nextchenghao;
		},
		error : function() {
			layer.msg('ajax获取用户称号失败',{icon:5,shift:6});
		}
	});
});

$("#chenghaobutton").click(function(){
	layer.confirm('升级到<span style="color:red"> '+nextlabel+'</span> 需要<span style="color:red"> '+price+'</span> 赞助',{icon: 3, title:'提示'}, function(index){
	    var flag;
		$.ajax({
			url : "user/updateLabel",
			type : "post",
			dataType : 'json',
			data : {
				money:price
			},
			cache : false,
			async : false,
			success : function(data) {
				flag = data;
			},
			error : function() {
				layer.msg('ajax升级称号失败',{icon:5,shift:6});
			}
	   });
	   layer.close(index);
	   if(flag){
		   layer.msg('升级称号成功', {
				icon: 1,
				time: 1000 //2秒关闭（如果不配置，默认是3秒）
				,shade: 0.1
			}, function(){
			}); 
		   $.ajax({
				url : "user/getlabel",
				type : "post",
				dataType : 'json',
				cache : false,
				async : false,
				success : function(data) {
					var id = data.id;
					var mymoney = data.mymoney;
					var label = data.mylabel.split('.');
					var money = data.money;
					var mychenghao = label[0];
					var nextchenghao = label[1];
					$("#mychenghao").html(mychenghao);
					if(mymoney<money){
						$("#nextchenghao").html("（赞助不足，无法升级到："+nextchenghao+"）");
						$("#chenghaobutton").hide();
					}else{
						$("#nextchenghao").html("（可升级到："+nextchenghao+"）");
					}
					$("#mymoney").html(mymoney);
					$("#moneyTochenghao").attr('placeholder',"￥ "+money+" 元");
					price = money;
					nextlabel = nextchenghao;
				},
				error : function() {
					layer.msg('ajax获取用户称号失败',{icon:5,shift:6});
				}
			});
	   }else{
		   layer.msg('升级称号失败',{icon:5,shift:6});
	   }
	}); 
});

});