<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>

<body>

<%@ include file="../public/header.jsp"%>

<div class="main layui-clear">
  <div class="fly-panel" pad20>
  <h2 class="page-title">分享代码</h2>
  
  <%if ((User) session.getAttribute("user") == null) {%>
  <div class="fly-none">登录后才能分享代码喔<img alt="[微笑]" title="[微笑]" src="res/layui/images/face/00.gif"></div>
  <%}else{ %>
  <div class="layui-form layui-form-pane">
    <form action="resource/add" method="post">
    	
      <input type="hidden" name="uuid" id="uuid" value="<%=java.util.UUID.randomUUID() %>" />
    
      <div class="layui-form-item">
        <label for="L_title" class="layui-form-label">标题</label>
        <div class="layui-input-block">
          <input type="text" id="name" name="name" lay-verify="name" autocomplete="off" class="layui-input">
        </div>
      </div>
      
      <div class="layui-form-item">
	    <label class="layui-form-label">类型</label>
	    <div class="layui-input-inline">
	      <select name="sort" id="sort" lay-verify="sort">
	        <option value="0" selected></option>
	        <option value="1">完整项目</option>
	        <option value="2">单一功能</option>
	        <option value="3">界面模板</option>
	        <option value="4">js特效</option>
	      </select>
	    </div>
	    
	    <label class="layui-form-label">积分</label>
	    <div class="layui-input-inline">
	      <select name="jifen" id="jifen">
	        <option value="0"  selected>0</option>
	        <option value="1">1</option>
	        <option value="2">2</option>
	        <option value="3">3</option>
	        <option value="4">4</option>
	        <option value="5">5</option>
	        <option value="6">6</option>
	        <option value="7">7</option>
	        <option value="8">8</option>
	        <option value="9">9</option>
	        <option value="10">10</option>
	      </select>
	    </div>
	    
	    <label class="layui-form-label">标签</label>
	    <div class="layui-input-inline">
	      <select name="label" id="label">
	        <option value="30"  selected>Java</option>
	        <option value="31">C</option>
	        <option value="32">C++</option>
	        <option value="33">C#</option>
	        <option value="34">PHP</option>
	        <option value="35">Python</option>
	        <option value="36">前端</option>
	        <option value="37">后端</option>
	        <option value="38">数据库</option>
	        <option value="39">服务器</option>
	        <option value="40">其他领域</option>
	      </select>
	    </div>
	  </div>	
	  <input type="hidden" name="urlpwd" id="urlpwd" value="0">
      <div class="layui-form-item layui-form-text">
        <div class="layui-input-block">
          <textarea id="description" name="description" lay-verify="description" placeholder="" class="layui-textarea fly-editor" style="height: 260px;">代码介绍：

运行环境：

gif效果图：
          </textarea>
        </div>
        <label for="L_content" class="layui-form-label" style="top: -2px;">详细描述</label>
      </div>
      
      <div class="layui-form-item">
		<fieldset class="layui-elem-field">
		  <legend>上传代码</legend>
		  <div class="layui-field-box">
			<span id="fileName"></span><br/>
			<input type="file" name="file" lay-ext="zip|rar" lay-type="file" lay-title="仅支持zip、rar格式，您可以上传一个小于20M的文件（注意：请勿上传任何与代码无关的文件，一旦发现，永久封号）" class="layui-upload-file">
			<input type="hidden" id="title" name="title" value="0"/>
			<input type="hidden" id="url" name="url" value="0"/>
		  </div>
		</fieldset>
	  </div>
      
      <!-- 用户id -->
      <input type="hidden" id="user_id" name="user_id" value="<%=((User) session.getAttribute("user")).getId()%>">
      
      <div class="layui-form-item">
		<label for="L_vercode" class="layui-form-label">验证码</label>
		<div class="layui-input-inline">
			<input type="text" id="L_vercode" name="formCheckCode"
				required lay-verify="formCheckCode" autocomplete="off"
				class="layui-input">
		</div>
		<div class="layui-form-mid">
			<!--若要点击图片刷新，重新得到一个验证码，要在后面加上个随机数，这样保证每次提交过去的都是不一样的path，防止因为缓存而使图片不刷新-->
			<img src="user/createImage"
				onclick="this.src='user/createImage?number='+ Math.random()"
				title="点击图片刷新验证码" /><br>
		</div>
	  </div>
      <div class="layui-form-item">
        <button class="layui-btn" lay-filter="resourceAdd" lay-submit>立即发布</button>
      </div>
    </form>
  </div>
  <%} %>
  
  </div>
</div>

<%@ include file="../public/footer.jsp"%>

<script>
layui.config({
  base: 'res/modules/resource/'
}).use('add');
</script>

</body>
</html>