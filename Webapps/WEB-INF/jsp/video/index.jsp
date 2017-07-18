<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder,java.net.URLEncoder"%>  
<!DOCTYPE html>
<html>
<%@ include file="../public/link.jsp"%>

<body>

<%@ include file="../public/header.jsp"%>

<input type="hidden" id="keyword" value="<%=request.getParameter("keyword")==null?"":request.getParameter("keyword")%>">

<div class="main layui-clear">
  <div class="fly-panel" pad20>
	<h2  class="page-title">视频库：正在计划实施中。。。</h2>
	<div class="layui-form layui-form-pane">
		视频库介绍：录制一套3年以上java深入学习系列视频。<br/><br/>
		目的：其实站长刚毕业1年，技术也一般，做这个视频库的目的是逼自己学习这些知识。不逼自己一把，时间都浪费在看视频和玩lol、王者荣耀上了<br/><br/>
		如果你想和站长一起学习的话，欢迎加群：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=9d09b3b9cbcf584c9bab6b2576fb1c2aa6ca84bebb8f44e464382398e7d4acc5"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="代码库技术交流群" title="代码库技术交流群"></a><br/><br/>
		如果你有更好的学习建议，非常欢迎联系我，QQ：1181014088<br/><br/>
		目录：这是个大概目录，录制的时候有可能会有变动。<br/><br/>
		1、JVM相关知识<br/>
<pre>
对于刚刚接触Java的人来说，JVM相关的知识不一定需要理解很深，对此里面的概念有一些简单的了解即可。不过对于一个有着3年以上Java经验的资深开发者来说，不会JVM几乎是不可接受的。
JVM作为java运行的基础，很难相信对于JVM一点都不了解的人可以把java语言吃得很透。面试超过3年Java经验的开发者的时候， JVM几乎就是一个必问的问题了。当然JVM不是唯一决定技术能力好坏的面试问题，但是可以佐证java开发能力的高低。
在JVM这个大类中，需要掌握的知识有：
JVM内存模型和结构
GC原理，性能调优
调优：Thread Dump，分析内存结构
class 二进制字节码结构，class loader 体系 ， class加载过程，实例创建过程
方法执行过程：Java各个大版本更新提供的新特性(需要简单了解)
</pre>
		2、Java的运行(基础必备)<br/>
<pre>
这条可能出看很简单，java程序的运行谁不会呢?不过很多时候， 我们只是单纯通过IDE去执行java程序，底层IDE又是如何执行java程序呢?很多人并不了解。
这 个知识点是最最基本的java开发者需要掌握的，初学java，第一个肯定是教你如何在命令行中执行java程序，但是很多人一旦把java学完 了，IDE用上了，就把这个都忘了。为什么强调要知道这个呢，知道了java最纯粹的启动方式之后，你才能在启动出问题的时候，去分析当时启动的目录多 少，执行命名如何，参数如何，是否有缺失等。 这样有利于你真正开发中去解决那些奇奇怪怪的可能和环境相关的问题。
在这里需要掌握的知识有：
javac 编译java文件为 class 文件
java 命令的使用， 带package的java类如何在命令行中启动
java程序涉及到的各个路径(classpath， java。library。path， java运行的主目录等)
</pre>
		3、数据类型<br/>
<pre>
这条没有什么好多说的，无非就是Java中的基本类型和对象类型的掌握。可以再了解一些JDK如何自动转换方面的知识，包括装箱拆箱等，还要注意避免装箱之后的类型相等的判断
主要知识点：
基本类型： int， long， float， double， boolean ， 。。。
对应的对象类型： Integer 等类型到基本类型的转换， 装箱和拆箱
Object类型： equals， hashcode
String 类型的特点
</pre>
		4、对象和实例，对象的创建<br/>
<pre>
在这方面，开发者需要了解class和instance的概念以及之间的差别， 这是java面向对象特性的一个基础。主要知识点有：
Class和 Instance 的概念；
Instance 创建的过程：1. 无继承：分配内存空间， 初始化变量， 调用构造函数；2.有继承：处理静态动作， 分配内存空间， 变量定义为初始值 ， 从基类->子类， 处理定义处的初始化， 执行构造方法；
需要注意的点：静态属性等从基类->子类进行初始化；默认无参构造方法相关的特性。
</pre>
		5、访问控制<br/>
<pre>
这也是java封装特性的一个基础，需要掌握的有：
public protected default private 对于class， method， field 的修饰作用
</pre>
		6、流程控制<br/>
<pre>
Java 流程控制的基础， 虽然有些语法不一定很常用，但是都需要了解，并且在合适的地方使用它们。
需要掌握的有：if， switch， loop， for， while 等流程控制的语法
</pre>
		7、面向对象编程的概念<br/>
<pre>
这 是一个java的核心概念，对于任何java开发者都需要熟练掌握。Java中很多特性或者说知识点都是和java面向对象编程概念相关的。 一个好的开发者不仅仅需要了解这些特性(知识点)本身，也更需要知道这些对象在java的面向对象编程概念中是如何体现出来的，这样更有利于开发者掌握 java这门开发语言，以及其他面向对象编程的语言。在这里只是简单罗列了一下，主要的知识点包括有：
面向对象三大特性：封装，继承，多态; 各自的定义概念，有哪些特性体现出来，各自的使用场景
静态多分派，动态单分派的概念
重载的概念和使用
继承：接口多实现，基类单继承
抽象，抽象类，接口
多态：方法覆盖的概念和使用
接口回调
</pre>
		8、Static<br/>
<pre>
静态属性在java日常开发中也是经常使用，需要了解和 static 关键字相关的用法，还有和其他关键字的配合使用， 如是否可以和 abstract， final 等关键字联合使用。
主要需要掌握的有：
静态属性的定义，使用，以及类加载时如何初始化
静态方法的定义和使用
静态类的定义和使用
静态代码块的定义和初始化时机
</pre>
		9、基础知识点<br/>
<pre>
这里主要罗列一些散落的，没有系统归类的一些java知识点。在日常的开发中用到也不少。 这块内容其实还有很多，目前只是暂时归纳了这几个在这里：
包括有：equals ， hashcode ， string/stringbuffer ，final ， finally ， finalize
</pre>
		10、集合框架<br/>
<pre>
这 个是一个需要多加掌握的部分，做java开发，可以说没有不用到集合框架的，这很重要。但是这里的知识点并不难，但是对于集合最好要了解内部的实现方式， 因为这样有助于你在各个不同的场景选择适合的框架来解决问题，比如有1W个元素的集合，经常要进行contains判断操作，知道了集合的特性或者内部实 现，就很容易做出正确的选择。
这里包括了如下内容(并发相关不包含在内)：
集合框架的体系： 基础Collection ，Map
具体集合实现的内容， List ，Set ，Map 具体的实现，内部结构， 特殊的方法， 适用场景等
集合相关的工具类 Collections 等的用法
</pre>
		11、异常框架<br/>
<pre>
异 常在java的开发中可能没有那么被重视。一般遇到异常，直接上抛，或者随便catch一下处理之后对于程序整体运行也没有什么大的影响。不过在企业级设 计开发中， 异常的设计与处理的好坏，往往就关系着这个系统整体的健壮性。一个好的系统的异常对于开发者来说，处理应该统一，避免各处散落很多异常处理逻辑;对于系统 来说，异常应该是可控的，并且是易于运维的，某些异常出现后，应该有应对的方法，知道如何运维处理，所以虽然异常框架很简单，但是对于整个企业级应用开发 来说，异常处理是很重要的，处理好异常就需要了解Java中的异常体系。
这部分需要掌握的知识点不多，主要就是：
异常的体系：
Throwable
Exception
RuntimeException
Error
RuntimeException 和 一般 Exception 的区别， 具体处理方法等
</pre>
		12、Java IO<br/>
<pre>
IO 在java中不仅仅是文件读写那么简单，也包括了 socket 网络的读写等等一切的输入输出操作。比如说 标准HTTP请求中Post的内容的读取也是一个输出的过程，等等…
对于IO，Java不仅提供了基本Input、Output相关的api，也提供了一些简化操作的Reader、Writer等api，在某些开发(涉及大量IO操作的项目)中也很重要，一般日常的开发中也会涉及(日志，临时文件的读写等)。
在这中的知识点主要有：
基本IO的体系： 包括有InputStream ， OutputStream， Reader/Writer， 文件读取，各种流读取等
NIO 的概念， 具体使用方式和使用场景
</pre>
		13、多线程并发<br/>
<pre>
多线程是Java中普遍认为比较难的一块。多线程用好了可以有效提高cpu使用率， 提升整体系统效率， 特别是在有大量IO操作阻塞的情况下;但是它也是一柄双刃剑， 如果用不好，系统非但提升不大，或者没有提升，而且还会带来多线程之间的调试时等问题。
在多线程中内容有很多，只是简单说明一下Java中初步使用多线程需要掌握的知识点，以后有机会单独再详细介绍一些高级特性的使用场景。
多线程的实现和启动
callable 与 runable 区别
syncrhoized ，reentrantLock 各自特点和比对
线程池
future 异步方式获取执行结果
concurrent 包
lock
..
</pre>
		14、网络<br/>
<pre>
Java 中也是提供了可以直接操作 TCP协议、UDP协议的API。在需要强调网络性能的情况下，可以直接使用TCP/UDP 进行通讯。在查看Tomcat等的源码中，就可以看到这些相关API的使用情况。不过一般也比较少会直接使用TCP，会使用诸如MINA、Netty这样 的框架来进行处理，因为这个方面的开发涉及不多，所以就不再详细罗列了。
</pre>
		15、时间日期处理<br/>
<pre>
几乎对于每个应用来说，时间日期的处理也是绕不过去的，但是JDK8 之前的时间相关API用法并不友好。在那个时代，可以选择Joda等时间框架。到了JDK8 发布之后，全新的时间API基本融合了其他框架的优点，已经可以很好的直接使用了。
对于Java开发者来说，需要熟练地使用API来对时间和日期做相关的处理。
具体知识点不再罗列，会在以后再写个专门的文章来总结一下JDK8中时间日期API的用法。
</pre>
		16、XML解析/ JSON解析<br/>
<pre>
其实这两块内容都不是J2SE里面的内容，但是在日常开发中，和其他程序交互，和配置文件交互，越来越离不开这两种格式的解析。
不过对于一个开发者来说，能够了解一些XML/JSON具体解析的原理和方法，有助于你在各个具体的场景中更好的选择合适你的方式来使得你的程序更有效率和更加健壮。
XML： 需要了解 DOM解析和 SAX解析的基本原理和各自的适用场景
JSON： 需要了解一些常用JSON框架的用法， 如 Jackson， FastJson， Gson 等。
</pre>
		17、Maven的使用<br/>
<pre>
Maven 也不是Java里面的内容，但是maven是革命性的，给java开发带来了巨大的便利。从依赖的引入和管理，开发流程的更新和发布产出，乃至版本的更 新，使用maven可以大大简化开发过程中的复杂度，从而节省大量时间。可以说，maven已经成为java开发者的标配了。所以把maven也作为一 个java开发者对于基础必备的知识点。以后会再放上一些对于maven使用的经验和技巧等，这里就不再细说了。
</pre>
		18、泛型的使用<br/>
<pre>
这是JDK5开始引入的新概念，其实是个语法糖，在编写java代码时会有些许便利， 一般的应用或者是业务的开发，只需要简单使用，不一定会用到定义泛型这样的操作， 但是开发一些基础公共组件会使用到，可以在需要的时候再细看这个部分，一般情况下只要会简单使用即可。
</pre>
		待补充。。。
	</div>
  </div>

</div>


<%@ include file="../public/footer.jsp"%>

<script>
layui.config({
  base: 'res/modules/video/'
}).use('index');
</script>

</body>
</html>