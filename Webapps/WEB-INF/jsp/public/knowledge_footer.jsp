<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="zanzhu.jsp"%>
<div class="footer">
  <p>Copyright © 2017 代码分享网<a href="http://www.miitbeian.gov.cn/" target="_blank">陕ICP备17005556号-1</a></p>
</div>
<script>
(function(){
    var bp = document.createElement('script');
    var curProtocol = window.location.protocol.split(':')[0];
    if (curProtocol === 'https'){
   bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
  }
  else{
  bp.src = 'http://push.zhanzhang.baidu.com/push.js';
  }
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(bp, s);
})();
</script>
<script src="res/layui/layui.js"></script>
<script>
layui.config({
  base: 'res/modules/public/'
}).use('knowledge_footer');
</script>
