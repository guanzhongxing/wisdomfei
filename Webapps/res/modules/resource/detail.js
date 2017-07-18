/**
 * 
 * @Name: 资源展示页
 * 
 */

layui.use(['element','layer', 'laytpl', 'form', 'upload', 'util', 'layedit'], function() {
	var $ = layui.jquery
	  ,element = layui.element
	  ,layer = layui.layer
	  ,laytpl = layui.laytpl
	  ,form = layui.form()
	  ,util = layui.util
	  ,layedit = layui.layedit
	  ,device = layui.device()
	  
	  
//获取资源所有相关信息，进行页面填充
$(document).ready(function(){
//获取页面隐藏的用户id
var id = $("#resourcesId").val();
var qq = $("#user_qq").val();
if(qq==0){
	layer.open({
		  type: 1
		  ,title: false //不显示标题栏
		  ,closeBtn: false
		  ,area: '700px;'
		  ,shade: 0.8
		  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
		  ,resize: false
		  ,btn: ['现在就去绑定QQ号', '稍后']
		  ,btnAlign: 'c'
		  ,moveType: 1 //拖拽模式，0或者1
		  ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">有的时候，下载的代码出现bug而且资料又不全，这是让人抓狂的，你觉得呢？<br/><br/>源码库现在推出&nbsp;<strong style=\"color:#4898d5;\">QQ联系作者</strong>&nbsp;&nbsp;功能，方便大家尽快的定位问题、解决问题，节省你的时间。<br/><br/>温馨提示：解决问题后，记得打赏一波，鼓励一下作者（哪怕是一块钱也是好的）</div>'
		  ,success: function(layero){
		    var btn = layero.find('.layui-layer-btn');
		    btn.find('.layui-layer-btn0').attr({
		      href: 'user_set'
		      ,target: '_blank'
		    });
		  }
		});
}
// ajax获取资源信息
$.ajax({
	url : "resource/detail",
	type : "post",
	data : {
		id : id
	},
	dataType : 'json',
	cache : false,
	async : false,
	success : function(resourceUserLabels) {
		$("title").text(resourceUserLabels.resources.name);
		$("meta[name=keywords]").attr("content",resourceUserLabels.words); 
		$("meta[name=description]").attr("content","代码库-代码分享网，致力于为程序员提供最有帮助的代码，该代码主要功能为:"+resourceUserLabels.resources.name);
		
		var content="<h1>"+resourceUserLabels.resources.name+"</h1>";
		content+="<div class=\"fly-tip fly-detail-hint\">";
		if(resourceUserLabels.resources.isfirst==1){
			content+="<span class=\"fly-tip-stick\" id=\"isfirst\">公告</span> ";
		}
		if(resourceUserLabels.resources.isyuan==1){
			content+="<span class=\"fly-tip-jie\" id=\"isyuan\">原创</span> ";
		}
		if(resourceUserLabels.resources.isjing==1){
			content+="<span class=\"fly-tip-jing\" id=\"isjing\">精</span> ";
		}
		content+="<span>已入库</span>";
		
		content+="<div class=\"fly-list-hint\"> ";
		content+="<i class=\"iconfont\" title=\"评论\">&#xe60c;</i> <label id=\"res_comments_num\">"+resourceUserLabels.res_comments_num+"</label>";
		content+="<i class=\"layui-icon\" title=\"下载\" style=\"font-size: 14px;\">&#xe601;</i> <label id=\"res_download_num\">"+resourceUserLabels.res_download_num+"</label>";
		content+="<i class=\"layui-icon\" title=\"收藏\" style=\"font-size: 14px;\">&#xe600;</i> <label id=\"res_keep_num\">"+resourceUserLabels.res_keep_num+"</label>";
		content+="</div>";
		content+="</div>";
		content+="<div class=\"detail-about\">";
		content+="<a class=\"jie-user\" href=\"user_home?id="+resourceUserLabels.userLabels.user.id+"\" id=\"userId\" target=\"_blank\">";
		content+="<img src=\""+resourceUserLabels.userLabels.user.img+"\" title=\""+resourceUserLabels.userLabels.user.name+"\" id=\"userImg\">";
		content+="<cite>";
		content+="<span><label id=\"userName\">"+resourceUserLabels.userLabels.user.name+"</label>&nbsp;<label id=\"userLabel\">("+resourceUserLabels.userLabels.labels.name+")</label>";
		content+="<em>发布于<label id=\"r_nowtime\">"+resourceUserLabels.r_nowtime+"</label></em></span>";
		content+="</cite>";
		content+="</a>";
		content+="<div class=\"detail-hits\">";
		content+="<span style=\"color:#FF7200\">积分：<label id=\"r_jifen\">"+resourceUserLabels.resources.jifen+"</label></span>";
		if(resourceUserLabels.resources.url!=0){
			content+="<span id=\"download\" class=\"layui-btn layui-btn-mini jie-admin\">下载源码</span>";
		}
		if(resourceUserLabels.isshoucang==0){
			content+="<span id=\"shoucang0\" class=\"layui-btn layui-btn-mini jie-admin layui-btn layui-btn-normal\" data-type=\"add\">收藏</span>";
			content+="<span id=\"shoucang1\" class=\"layui-btn layui-btn-mini jie-admin\" data-type=\"add\" style=\"display:none;\">取消收藏</span>";
		}else{
			content+="<span id=\"shoucang1\" class=\"layui-btn layui-btn-mini jie-admin\" data-type=\"add\">取消收藏</span>";
			content+="<span id=\"shoucang0\" class=\"layui-btn layui-btn-mini jie-admin layui-btn layui-btn-normal\" data-type=\"add\" style=\"display:none;\">收藏</span>";
		}
		content+="</div>";
		content+="</div>";
		if(resourceUserLabels.userLabels.user.qq!=0){
			content+="<div><a href=\"tencent://message/?uin="+resourceUserLabels.userLabels.user.qq+"\"><img border=\"0\" src=\"res/images/qq.gif\" alt=\"联系作者\" title=\"联系作者\"  style=\"width:23px;\" /></a>";
			content+="<span style=\"color: #999;font-size: 12px;\">：点击QQ图标联系作者寻求帮助，得到帮助解决问题后，记得打赏支持一下作者的辛苦劳动</span></div>";
		}
		content+="<div class=\"detail-body photos\" style=\"margin-bottom: 20px;\">";
		content+="<div id=\"description\">"+resourceUserLabels.resources.description+"</div>";
		content+="<p style=\"text-align:right;margin-right:15px;\">";
		content+="</p>";
		content+="</div>";
		$("#resource_detail").append(content);
		
	},
	error : function() {
		layer.msg('ajax根据资源id获取资源详细页面失败',{icon:5,shift:6});
	}
});

if(id==1){
	$.ajax({
		url : "resource/getFirstResourcesList",
		type : "post",
		dataType : 'json',
		cache : false,
		async : false,
		success : function(data) {
			//计算公告、原创、精个数
			for(var i in data){
				var id = data[i].id;
				var name = data[i].name;
				var isfirst = data[i].isfirst;
				var isyuan = data[i].isyuan;
				var isjing = data[i].isjing;
				//将id为1资源去掉
				if(id!=1){
					if(isfirst==1){
						$("#isfirstpre").append("<a target=\"_blank\" href=\"resource_detail?id="+id+"\">"+name+"</a><br/>");
					}else if(isyuan==1){
						$("#isyuanpre").append("<a target=\"_blank\" href=\"resource_detail?id="+id+"\">"+name+"</a><br/>");
					}else if(isjing==1){
						$("#isjingpre").append("<a target=\"_blank\" href=\"resource_detail?id="+id+"\">"+name+"</a><br/>");
					}
				}
			}
		},
		error : function() {
			layer.msg('ajax获取资源集合失败',{icon:5,shift:6});
		}
	});
}
});
	  
$("#shoucang0").on("click",function(){
	//获取页面隐藏的资源id和用户id
	var resources_id = $("#resourcesId").val();
	var user_id = $("#user_id").val();
	if(user_id==0){
		layer.msg('尚未登录',{icon:5,shift:6});
		return false;
	}
	// ajax收藏
	$.ajax({
		url : "resource/shoucang",
		type : "post",
		data : {
			resources_id : resources_id,
		},
		dataType : 'json',
		cache : false,
		async : false,
		beforeSend: function () {
	        layer.load(2, {time: 10*1000});
	    },
		success : function(flag) {
			layer.closeAll('loading');
			if(flag){
				$("#shoucang0").hide();
				$("#shoucang1").show();
				var res_keep_num = $("#res_keep_num").html();
				$("#res_keep_num").text(parseInt(res_keep_num)+1);
			}else{
				layer.msg('ajax收藏资源失败',{icon:5,shift:6});
			}
		},
		error : function() {
			layer.msg('ajax收藏资源失败',{icon:5,shift:6});
		}
	});
});

$("#shoucang1").on("click",function(){
	//获取页面隐藏的资源id和用户id
	var resources_id = $("#resourcesId").val();
	var user_id = $("#user_id").val();
	if(user_id==0){
		layer.msg('尚未登录',{icon:5,shift:6});
		return false;
	}
	// ajax收藏
	$.ajax({
		url : "resource/deleteshoucang",
		type : "post",
		data : {
			resources_id : resources_id,
		},
		dataType : 'json',
		cache : false,
		async : false,
		beforeSend: function () {
	        layer.load(2, {time: 10*1000});
	    },
		success : function(flag) {
			layer.closeAll('loading');
			if(flag){
				$("#shoucang0").show();
				$("#shoucang1").hide();
				var res_keep_num = $("#res_keep_num").html();
				$("#res_keep_num").text(parseInt(res_keep_num)-1);
			}else{
				layer.msg('ajax取消收藏资源失败',{icon:5,shift:6});
			}
		},
		error : function() {
			layer.msg('ajax取消收藏资源失败',{icon:5,shift:6});
		}
	});
});

$("#download").on("click",function(){
	//获取页面隐藏的资源id、该资源所属用户id
	var resources_id = $("#resourcesId").val();
	var user_id = $("#user_id").val();
	if(user_id==0){
		layer.msg('尚未登录',{icon:5,shift:6});
		return false;
	}
	
	// 获取返回的数据
	var data;
	// ajax判断积分是否足够然后下载
	$.ajax({
		url : "resource/jifen",
		type : "post",
		data : {
			resources_id : resources_id,
		},
		dataType : 'json',
		cache : false,
		async : false,
		beforeSend: function () {
	        layer.load(2, {time: 10*1000});
	    },
		success : function(result) {
			layer.closeAll('loading');
			data = result;
		},
		error : function() {
			layer.msg('ajax判断用户积分是否足够失败',{icon:5,shift:6});
		}
	});
	if(data.code==1){
		//用户积分不够或者未登录
		layer.msg(data.msg,{icon:5,shift:6});
	}else if(data.code==2||data.code==0){
		//用户已下载，或者积分足够下载
		layer.confirm(data.msg, {
			  btn: ['下载','取消']
			}, function(index){
			  var url = data.url;
			  window.location.href="resource/download?resources_id="+resources_id;
			  layer.close(index);
			  //如果为积分下载，下载数加1
			  if(data.code==0){
				  var res_download_num = $("#res_download_num").html();
				  $("#res_download_num").text(parseInt(res_download_num)+1);
			  }
			}, function(){});
	}
});


layui.focusInsert = function(obj, str){
    var result, val = obj.value;
    obj.focus();
    if(document.selection){ //ie
      result = document.selection.createRange(); 
      document.selection.empty(); 
      result.text = str; 
    } else {
      result = [val.substring(0, obj.selectionStart), str, val.substr(obj.selectionEnd)];
      obj.focus();
      obj.value = result.join('');
    }
  };
  
  var gather = {
    
    //Ajax
    json: function(url, data, success, options){
      var that = this;
      options = options || {};
      data = data || {};
      return $.ajax({
        type: options.type || 'post',
        dataType: options.dataType || 'json',
        data: data,
        url: url,
        success: function(res){
          if(res.status === 0) {
            success && success(res);
          } else {
            layer.msg(res.msg||res.code, {shift: 6});
          }
        }, error: function(e){
          options.error || layer.msg('请求异常，请重试', {shift: 6});
        }
      });
    }

    //计算字符长度
    ,charLen: function(val){
      var arr = val.split(''), len = 0;
      for(var i = 0; i <  val.length ; i++){
        arr[i].charCodeAt(0) < 299 ? len++ : len += 2;
      }
      return len;
    }
    
    ,form: {}

    //简易编辑器
    ,layEditor: function(options){
      var html = '<div class="fly-edit">'
    	  +'<span type="face" title="插入表情"><i class="iconfont icon-biaoqing"></i>表情</span>'
    	  +'<span type="code" title="插入代码"><i class="iconfont icon-daima"></i>代码</span>'
    	  +'<span type="href" title="超链接格式：a(href)[text]"><i class="iconfont icon-lianjie"></i>链接</span>'
        /*+'<span type="picture" title="插入图片：img[src]"><i class="iconfont icon-tupian"></i>图片</span>'*/
        +'<span type="yulan" title="预览"><i class="iconfont icon-yulan"></i>预览</span>'
      +'</div>';
      var log = {}, mod = {
        picture: function(editor){ //插入图片
          layer.open({
            type: 1
            ,id: 'fly-jie-upload'
            ,title: '插入图片'
            ,area: 'auto'
            ,shade: false
            ,area: '465px'
            ,skin: 'layui-layer-border'
            ,content: ['<ul class="layui-form layui-form-pane" style="margin: 20px;">'
              ,'<li class="layui-form-item">'
                ,'<label class="layui-form-label">URL</label>'
                ,'<div class="layui-input-inline">'
                    ,'<input required name="image" placeholder="支持直接粘贴远程图片地址" value="" class="layui-input">'
                  ,'</div>'
                  ,'<input required type="file" name="file" class="layui-upload-file" value="">'
              ,'</li>'
              ,'<li class="layui-form-item" style="text-align: center;">'
                ,'<button type="button" lay-submit lay-filter="uploadImages" class="layui-btn">确认</button>'
              ,'</li>'
            ,'</ul>'].join('')
            ,success: function(layero, index){
              var image =  layero.find('input[name="image"]');
              layui.upload({
            	    before: function () {
                      layer.load(2, {time: 10*1000});
                    },
	                url: 'resource/uploadImg'
	                ,elem: '#fly-jie-upload .layui-upload-file'
	                ,success: function(res){
	                  layer.closeAll('loading');
	                  if(res.code == 0){
	                    image.val(res.src);
	                  } else {
	                    layer.msg(res.msg, {icon: 5});
	                  }
	                }
	              });
	          
              form.on('submit(uploadImages)', function(data){
                var field = data.field;
                if(!field.image) return image.focus();
                layui.focusInsert(editor[0], 'img['+ field.image + '] ');
                layer.close(index);
              });
            }
          });
        }
        ,face: function(editor, self){ //插入表情
          var str = '', ul, face = gather.faces;
          for(var key in face){
            str += '<li title="'+ key +'"><img src="'+ face[key] +'"></li>';
          }
          str = '<ul id="LAY-editface" class="layui-clear">'+ str +'</ul>';
          layer.tips(str, self, {
            tips: 3
            ,time: 0
            ,skin: 'layui-edit-face'
          });
          $(document).on('click', function(){
            layer.closeAll('tips');
          });
          $('#LAY-editface li').on('click', function(){
            var title = $(this).attr('title') + ' ';
            layui.focusInsert(editor[0], 'face' + title);
          });
        }
        ,href: function(editor){ //超链接
          layer.prompt({
            title: '请输入合法链接'
            ,shade: false
          }, function(val, index, elem){
            if(!/^http(s*):\/\/[\S]/.test(val)){
              layer.tips('这根本不是个链接，不要骗我。', elem, {tips:1});
              return false;
            }
            layui.focusInsert(editor[0], ' a('+ val +')['+ val + '] ');
            layer.close(index);
          });
        }
        ,code: function(editor){ //插入代码
          layer.prompt({
            title: '请贴入代码'
            ,formType: 2
            ,maxlength: 10000
            ,shade: false
          }, function(val, index, elem){
            layui.focusInsert(editor[0], '[pre]\n'+ val + '\n[/pre]');
            layer.close(index);
          });
        }
        ,yulan: function(editor){ //预览
          var content = editor.val();
          
          content = /^\{html\}/.test(content) 
            ? content.replace(/^\{html\}/, '')
          : gather.content(content);

          layer.open({
            type: 1
            ,title: '预览'
            ,area: ['100%', '100%']
            ,scrollbar: false
            ,content: '<div class="detail-body" style="margin:20px;">'+ content +'</div>'
          });
        }
      };
      
      layui.use('face', function(face){
        options = options || {};
        gather.faces = face;
        $(options.elem).each(function(index){
          var that = this, othis = $(that), parent = othis.parent();
          parent.prepend(html);
          parent.find('.fly-edit span').on('click', function(event){
            var type = $(this).attr('type');
            mod[type].call(that, othis, this);
            if(type === 'face'){
              event.stopPropagation()
            }
          });
        });
      });
      
    }

    ,escape: function(html){
      return String(html||'').replace(/&(?!#?[a-zA-Z0-9]+;)/g, '&amp;')
      .replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/'/g, '&#39;').replace(/"/g, '&quot;');
    }

    //内容转义
    ,content: function(content){
      //支持的html标签
      var html = function(end){
        return new RegExp('\\['+ (end||'') +'(pre|div|table|thead|th|tbody|tr|td|ul|li|ol|li|dl|dt|dd|h2|h3|h4|h5)\\]\\n*', 'g');
      };
      content = gather.escape(content||'') //XSS
      .replace(/img\[([^\s]+?)\]/g, function(img){  //转义图片
        return '<img src="' + img.replace(/(^img\[)|(\]$)/g, '') + '">';
      }).replace(/@(\S+)(\s+?|$)/g, '@<a href="javascript:;" class="fly-aite">$1</a>$2') //转义@
      .replace(/face\[([^\s\[\]]+?)\]/g, function(face){  //转义表情
        var alt = face.replace(/^face/g, '');
        return '<img alt="'+ alt +'" title="'+ alt +'" src="' + gather.faces[alt] + '">';
      }).replace(/a\([\s\S]+?\)\[[\s\S]*?\]/g, function(str){ //转义链接
        var href = (str.match(/a\(([\s\S]+?)\)\[/)||[])[1];
        var text = (str.match(/\)\[([\s\S]*?)\]/)||[])[1];
        if(!href) return str;
        var rel =  /^(http(s)*:\/\/)\b(?!(\w+\.)*(sentsin.com|layui.com))\b/.test(href.replace(/\s/g, ''));
        return '<a href="'+ href +'" target="_blank"'+ (rel ? ' rel="nofollow"' : '') +'>'+ (text||href) +'</a>';
      }).replace(html(), '\<$1\>').replace(html('/'), '\</$1\>') //转移代码
      .replace(/\n/g, '<br>') //转义换行   
      return content;
    }
  };

//加载编辑器
gather.layEditor({
elem: '.fly-editor'
});

//刷新user2_id为0
$("#user2_id").val(0);
//监听所有回复按钮
$(document).on('click','div[id*="comment_"]',function(){
//$('div[id^="comment_"]').click(function(){
	var id = $(this).attr("id");
	//点击回复按钮，获取该评论的一些信息
	//var c_resources_id = $(this).find(':input:eq(0)').val();
	var c_user1_id = $(this).find(':input:eq(1)').val();
	var c_user2_id = $(this).find(':input:eq(2)').val();
	var c_low = $(this).find(':input:eq(3)').val();
	var num = id.split('_');
	//获取指定id
	num = num[1];
	//$("#resources_id").val(c_resources_id);
	$("#user1_id").val(c_user1_id);
	$("#user2_id").val(c_user2_id);
	$("#lou").val(c_low);
	$("#huifu"+num).after($("#addcomments"));
	
});

//获取资源所有评论，进行页面填充
$(document).ready(function(){
	//获取页面隐藏的用户id
	var resources_id = $("#resourcesId").val();
	//将当前资源id赋值到评论表单中
	$("#resources_id").val(resources_id);
	// ajax获取资源信息
	$.ajax({
		url : "comments/getCommentsByResources_id",
		type : "post",
		data : {
			resources_id : resources_id,
		},
		dataType : 'json',
		cache : false,
		async : false,
		success : function(result) {
			//定义一个最大楼数
			var maxlou=0;
			
			//判断是否有评论
			if(result.length==0){
				$("#allcomments").append("<li class=\"fly-none\">没有任何评论</li>");
			}else{
				//result==返回的所有评论集合commentsUserLabelsList
				for(var i in result){//遍历json数组时，这么写p为索引，0,1
					//alert(result[i].comments.content);
					//遍历出来最大楼，用于新增评论时使用
					var lou = result[i].comments.lou;
					if(lou>maxlou){
						maxlou = lou;
					}
					
					//获取该条评论的所有数据
					var c_id = result[i].comments.id;//该评论id
					var c_content = result[i].comments.content;//该评论内容
					var c_nowtime = result[i].comments.nowtime;//该评论时间
					
					var u1_id = result[i].u1.user.id;//用户1的id
					var u1_name = result[i].u1.user.name;//用户1的名称
					var u1_img = result[i].u1.user.img;//用户1的头像
					var u1_label = result[i].u1.labels.name;//用户1的标签
					
					var u2_id = result[i].u2.user.id;//用户2的id
					var u2_name = result[i].u2.user.name;//用户2的名称
					var u2_img = result[i].u2.user.img;//用户2的头像
					var u2_label = result[i].u2.labels.name;//用户1的标签
					
					//判断用户2的id是否为0
					if(u2_id==0){
						//如果用户2为0，需要在id="allcomments"的ul中进行拼接
						//拼接内容
						var content="<li class=\"jieda-daan\">";
						content+="<div class=\"detail-about detail-about-reply\">";
						content+="<div id=\"lou"+lou+"_"+u1_id+"\">";
						content+="<div style=\"width: 56px; float: left;\">\<a target=\"_blank\" class=\"jie-user\" href=\"user_home?id="+u1_id+"\"><img src=\""+u1_img+"\" title=\""+u1_name+"\"></a></div>";
						content+="<div style=\"margin-left: 56px;\">";
						content+="<div><a class=\"jie-user\" target=\"_blank\" href=\"user_home?id="+u1_id+"\" style=\"color: #4f99cf; font-size: 12px; margin-right: 0px;\"><span id=\"lou"+lou+"\">"+lou+"#</span>"+u1_name+"("+u1_label+")</a>: "+c_content+"</div>";
						
						content+="<div id=\"comment_"+c_id+"\" onMouseOver=\"document.getElementById('c_id_"+c_id+"').style.visibility=''\" onMouseOut=\"document.getElementById('c_id_"+c_id+"').style.visibility='hidden'\">";
						content+="<input type=\"hidden\" id=\"c_resources_id\" value=\"0\"/>";
						content+="<input type=\"hidden\" id=\"c_user1_id\" value=\"0\"/>";
						content+="<input type=\"hidden\" id=\"c_user2_id\" value=\""+u1_id+"\"/>";
						content+="<input type=\"hidden\" id=\"c_lou\" value=\""+lou+"\"/>";
						content+="<span style=\"color: #999;\">"+c_nowtime+"</span>";
						content+="<span id=\"c_id_"+c_id+"\" style=\"color: #999; padding-right: 5px; visibility: hidden;\" onmouseover=\"this.style.cssText='color:#666;'\" onmouseout=\"this.style.cssText='color:#999'\">";
						content+="<i class=\"iconfont icon-svgmoban53\"></i>&nbsp;&nbsp;回复</span>";
						content+="</div>";
						content+="<div id=\"huifu"+c_id+"\"></div>";
						
						content+="</div>";
						content+="<ul id=\"lou"+lou+"_child\"></ul>";
						content+="</div>";
						content+="</li>";
						
						$("#allcomments").append(content);
						
						//将最大楼层+1，然后赋值到评论表单中
						$("#lou").val(maxlou+1);
						
					}else{
						//如果用户2不为0，需要在id="lou楼层数_用户1_0"的ul中进行拼接
						//拼接内容
						var content="<li>";
						content+="<div style=\"margin-left: 56px;\">";
						content+="<div>";
						content+="<div style=\"width: 56px; float: left;\">\<a class=\"jie-user\" target=\"_blank\" href=\"user_home?id="+u1_id+"\"><img src=\""+u1_img+"\" title=\""+u1_name+"\"></a></div>";
						content+="<div style=\"margin-left: 56px;\">";
						content+="<div><a class=\"jie-user\" target=\"_blank\" href=\"user_home?id="+u1_id+"\" style=\"color: #4f99cf; font-size: 12px;\">"+u1_name+"("+u1_label+")</a><label style=\"font-size: 12px;color:#999;\">回复</label><a class=\"jie-user\" target=\"_blank\" href=\"user_home?id="+u2_id+"\" style=\"color: #4f99cf; font-size: 12px;\">"+u2_name+"("+u2_label+")</a>: "+c_content+"</div>";
						
						content+="<div id=\"comment_"+c_id+"\" onMouseOver=\"document.getElementById('c_id_"+c_id+"').style.visibility=''\" onMouseOut=\"document.getElementById('c_id_"+c_id+"').style.visibility='hidden'\">";
						content+="<input type=\"hidden\" id=\"c_resources_id\" value=\"0\"/>";
						content+="<input type=\"hidden\" id=\"c_user1_id\" value=\"0\"/>";
						content+="<input type=\"hidden\" id=\"c_user2_id\" value=\""+u1_id+"\"/>";
						content+="<input type=\"hidden\" id=\"c_lou\" value=\""+lou+"\"/>";
						content+="<span style=\"color: #999;\">"+c_nowtime+"</span>";
						content+="<span id=\"c_id_"+c_id+"\" style=\"color: #999; padding-right: 5px; visibility: hidden;\" onmouseover=\"this.style.cssText='color:#666;'\" onmouseout=\"this.style.cssText='color:#999'\">";
						content+="<i class=\"iconfont icon-svgmoban53\"></i>&nbsp;&nbsp;回复</span>";
						content+="</div>";
						content+="<div id=\"huifu"+c_id+"\"></div>";
						
						content+="</div>";
						content+="</div>";
						content+="</div>";
						content+="</li>";
						
						$("#lou"+lou+"_child").append(content);
						
						//将最大楼层+1，然后赋值到评论表单中
						$("#lou").val(maxlou+1);
					}
				}
			}
			
		},
		error : function() {
			layer.msg('ajax根据资源id获取所有评论失败',{icon:5,shift:6});
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
//获取页面隐藏的资源id和用户id
var resources_id = $("#resourcesId").val();
$("#resources_id").val(resources_id);
//填充表单
//自定义验证规则
form.verify({
	c_content : function(value) {
		var user_id = $("#user_id").val();
		if(user_id==0){
			return '尚未登录';
		}
		if (!value) {
			return '评论不能为空';
		}
		if (value.length > 30000) {
			return '评论太长了';
		}else{
			//获取页面隐藏的资源id和用户id
			var c_content = $("#c_content").val();
			
			//转html
			c_content =/^\{html\}/.test(c_content)? c_content.replace(/^\{html\}/, ''): gather.content(c_content);
			
			var c_user2_id = $("#user2_id").val();
			var c_lou = $("#lou").val();
			var user_id = $("#user_id").val();
			// ajax评论
			$.ajax({
				url : "user/addcomments",
				type : "post",
				data : {
					c_content : c_content,
					c_resources_id : resources_id,
					c_user2_id : c_user2_id,
					c_lou : c_lou
				},
				dataType : 'json',
				cache : false,
				async : false,
				beforeSend: function () {
			        layer.load(2, {time: 10*1000});
			    },
				success : function(flag) {
					layer.closeAll('loading');
				}
			});
			$("#c_content").val("");
			// 刷新当前页面
			window.location.reload();
			
		}
	}
});

//根据资源id获取资源拥有者id，根据拥有者id获取二维码图片地址
//获取页面隐藏的资源id
var resources_id = $("#resourcesId").val();
$.ajax({
	url : "user/getUserPayByResources_id",
	type : "post",
	data :{
		resources_id:resources_id
	},
	dataType : 'json',
	cache : false,
	async : false,
	success : function(data) {
		var alipay = data.alipay;
		var weipay = data.weipay;
		$("#dashangimg").attr('src',weipay); 
		var alipayuser = alipay.substring(alipay.lastIndexOf('/') + 1, alipay.length);
		var weipayuser = weipay.substring(weipay.lastIndexOf('/') + 1, weipay.length);
		$("#weixinimg").attr('data-id',weipayuser); 
		$("#zhifubaoimg").attr('data-id',alipayuser); 
	}
});


/*exports('detail', gather);*/

});