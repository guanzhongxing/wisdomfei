﻿/**
 * 
 * @Name: 分享代码
 * 
 */
layui.define(['element','layer', 'laytpl', 'form', 'upload', 'util', 'layedit'], function(exports){
  
  var $ = layui.jquery
  ,element = layui.element
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,form = layui.form()
  ,util = layui.util
  ,layedit = layui.layedit
  ,device = layui.device()
  
  // 阻止IE7以下访问
  if(device.ie && device.ie < 8){
    layer.alert('如果您非得使用ie浏览代码库，那么请使用ie8+');
  }
  
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
	    	  +'<span type="code" title="插入代码"><i class="iconfont icon-daima"></i>代码</span>'
	    	  +'<span type="href" title="超链接格式：a(href)[text]"><i class="iconfont icon-lianjie"></i>链接</span>'
	        +'<span type="face" title="插入表情"><i class="iconfont icon-biaoqing"></i>表情</span>'
	        +'<span type="picture" title="插入图片：img[src]"><i class="iconfont icon-tupian"></i>图片</span>'
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
	                      layer.load(2, {time: 30*1000});
	                    },
		                url: 'resource/uploadImg'
		                ,elem: '#fly-jie-upload .layui-upload-file'
		                ,success: function(res){
		                  layer.closeAll('loading');
		                  if(res.code == 0){
		                    image.val(res.src);
		                    document.getElementById("urlpwd").value=res.src;
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
  
  //获取分享代码uuid
  var uuid = $('#uuid').val();
  layui.upload({
	  before: function () {
          layer.load(2, {time: 30*1000});
      },
      url: 'https://www.admintwo.com/resource/uploadFile?uuid='+uuid
      ,success: function(res){
    	layer.closeAll('loading');
        if(res.code == 0){
          $("#fileName").html(res.title);
          $("#title").val(res.title);
          $("#url").val(res.src);
        } else {
          layer.msg(res.msg, {icon: 5});
        }
      }
    });
  
  
  //自定义验证规则
  form.verify({
  	name : function(value) {
  		if (!value) {
  			return '标题不能为空';
  		}
  		if (value.length > 50) {
			return '标题太长了';
		}
  	},
  	sort : function(value) {
  		if (!value) {
  			return '请选择代码类型';
  		}
  	},
  	description: function(value) {
  		if (!value) {
  			return '详细描述不能为空';
  		}
  	},
  	formCheckCode : function(value) {
		if (!value)
			return '验证码不能为空';
		else {
			// 获取页面输入的验证码
			var formcheckCode = $('#L_vercode').val();
			// ajax获取后台验证码
			var checkCode = "";
			$.ajax({
				url : "user/getCheckCode",
				type : "post",
				dataType : 'json',
				cache : false,
				async : false,
				success : function(data) {
					checkCode = data;
				},
				error : function() {
					return 'ajax获取验证码失败';
				}
			});
			// 前台和后台验证码进行比较
			if (formcheckCode.toLowerCase() == checkCode.toLowerCase()) {
				// 页面验证完成后，可执行ajax用户分享代码
				form.on('submit(resourceAdd)', function(data) {
					var content = data.field.description;
					content =/^\{html\}/.test(content)? content.replace(/^\{html\}/, ''): gather.content(content);
					var description = '<div class="detail-body">'+ content +'</div>';
					$.ajax({
						url : "resource/add",
						type : "post",
						dataType : 'json',
						data : {
							name : data.field.name,
							sort : data.field.sort,
							jifen : data.field.jifen,
							label : data.field.label,
							//description : data.field.description,
							description : description,
							title: data.field.title,
							url : data.field.url,
							urlpwd : data.field.urlpwd,
							user_id : data.field.user_id
						},
						cache : false,
						async : false,
						success : function(data) {
							if(data){
								layer.msg('分享成功', {
									  icon: 1,
									  time: 1000 //2秒关闭（如果不配置，默认是3秒）
									  ,shade: 0.1
									}, function(){
										// 跳转
										location.href = "user_index";
									}); 
							}else{
								return '分享失败';
							}
						},
						error : function() {
							return 'ajax处理用户分享代码';
						}
					});
					return false;
				});
			} else {
				return '验证码错误';
			}
		}
	}
  });

  exports('add', gather);


});

