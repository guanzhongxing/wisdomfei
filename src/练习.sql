#资源评论数
select r.id,count(c.id) as res_comments_num
from resources r,comments c
where r.id=20 and r.id=c.resources_id

#资源下载数
select r.id,count(d.id) as res_download_num from resources r,download d where r.id=20 and r.id=d.resources_id

#资源收藏数
select k.user_id,count(k.id) as res_keep_num
from resources r,keep k
where r.id=20 and r.id=k.resources_id

#资源收藏表
CREATE TABLE `keep` (
   `id` int(6) NOT NULL AUTO_INCREMENT,
   `user_id` int(6) NOT NULL COMMENT '收藏的用户id',
   `resources_id` int(6) NOT NULL COMMENT '收藏的资源',
   `nowtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
   PRIMARY KEY (`id`),
   KEY `FK_keep_user_id` (`user_id`),
   KEY `FK_keep_resources_id` (`resources_id`),
   CONSTRAINT `FK_keep_resources_id` FOREIGN KEY (`resources_id`) REFERENCES `resources` (`id`),
   CONSTRAINT `FK_keep_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8




select r.id as r_id,r.name as r_name,r.jifen as r_jifen,r.description,r.url,r.nowtime as r_nowtime,r.isfirst,r.isjing,r.isyuan,
u.id as u_id,u.name as u_name,u.jifen as u_jifen,u.img,l.name as label,
if(isnull(k.id),0,1) as isshoucang,
ifnull(kp.res_keep_num,0) as res_keep_num,
ifnull(c.res_comments_num,0) as res_comments_num,
ifnull(d.res_download_num,0) as res_download_num
from resources r
left join user u on u.id=r.user_id
left join labels l on l.id=u.label
#k.user_id：通过session获取当前用户id,判断用户是否收藏。0未收藏，1收藏
left join keep k on k.resources_id=r.id and k.user_id=6
#r.id=20：通过指定资源查询收藏数，可优化查询速度
left join (select resources_id,count(kp.id) as res_keep_num from resources r,keep kp where r.id=20 and r.id=kp.resources_id)kp on r.id=kp.resources_id
#r.id=20：通过指定资源查询评论数，可优化查询速度
left join (select resources_id,count(c.id) as res_comments_num from resources r,comments c where r.id=20 and r.id=c.resources_id)c on r.id=c.resources_id
#r.id=20：通过指定资源查询下载数，可优化查询速度
left join (select resources_id,count(d.id) as res_download_num from resources r,download d where r.id=20 and r.id=d.resources_id)d on r.id=d.resources_id
#r.id=20：指定资源，进行过滤
where r.id=20 and r.isfriend=0 

select c.id,c.content,c.nowtime,c.lou,
u1.id,u1.name,
from comments c
left join user u1 on u1.id=c.user_id1
where resources_id=21 and isfriend=0


#获取某一资源的所有评论
select c.id as c_id,c.content as c_content,c.nowtime as c_nowtime,c.user1_id as c_user1_id,c.user2_id as c_user2_id,c.lou as c_lou,
u1.user1_id,u1.name as user1_name,u1.img as user1_img,u1.label as user1_label,
ifnull(u2.user2_id,0) as user2_id,u2.name as user2_name,u2.img as user2_img,u2.label as user2_label
from comments c
#指定用户1的id，u1.id=6
left join (select u1.id as user1_id,u1.name,u1.img, l.name as label from user u1,labels l where u1.label=l.id) u1 on c.user1_id=u1.user1_id
left join (select u2.id as user2_id,u2.name,u2.img, l.name as label from user u2,labels l where u2.label=l.id) u2 on c.user2_id=u2.user2_id
where resources_id=86 and isfriend=0
order by user2_id,c_lou

#首页信息 TODO
select r.id as r_id,r.sort as r_sort,r.name as r_name,l2.name as r_label,r.label as r_label_num,r.nowtime as r_nowtime,r.isfirst,r.isjing,r.isyuan,
u.id as u_id,u.name as u_name,l1.name as u_label,u.img,
ifnull(c.res_comments_num,0) as res_comments_num,
ifnull(d.res_download_num,0) as res_download_num,
ifnull(kp.res_keep_num,0) as res_keep_num
from resources r
left join user u on u.id=r.user_id
left join labels l1 on l1.id=u.label
left join labels l2 on l2.id=r.label
#查询收藏数
left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on r.id=kp.resources_id
#查询评论数
left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on r.id=c.resources_id
#查询下载数
left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on r.id=d.resources_id
where r.isfriend=0 or r.id=1
order by r.id=1 desc,r.nowtime desc limit 30;

select resources_id as r_id,count(resources_id) as res_comments_num from comments group by resources_id

CREATE TABLE `comments` (
   `id` int(6) NOT NULL AUTO_INCREMENT,
   `content` longtext NOT NULL COMMENT '评论内容',
   `isfriend` int(1) NOT NULL DEFAULT '0' COMMENT '0友好、1不友好',
   `nowtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
   `resources_id` int(6) NOT NULL COMMENT '该评论所属资源',
   `user1_id` int(6) NOT NULL COMMENT '该评论所属用户',
   `user2_id` int(6) NOT NULL DEFAULT '0' COMMENT '0为根评论,其他为回复评论',
   `lou` int(4) NOT NULL COMMENT '1楼、2楼、3楼...',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 
 CREATE TABLE `download` (
   `id` int(6) NOT NULL AUTO_INCREMENT,
   `user_id` int(6) NOT NULL COMMENT '下载的用户id',
   `resources_id` int(6) NOT NULL COMMENT '该下载所属的资源',
   `nowtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
   PRIMARY KEY (`id`),
   KEY `FK_download_user_id` (`user_id`),
   KEY `FK_download_resources_id` (`resources_id`),
   CONSTRAINT `FK_download_resources_id` FOREIGN KEY (`resources_id`) REFERENCES `resources` (`id`),
   CONSTRAINT `FK_download_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 
 CREATE TABLE `keep` (
   `id` int(6) NOT NULL AUTO_INCREMENT,
   `user_id` int(6) NOT NULL COMMENT '收藏的用户id',
   `resources_id` int(6) NOT NULL COMMENT '收藏的资源',
   `nowtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
   PRIMARY KEY (`id`),
   KEY `FK_keep_user_id` (`user_id`),
   KEY `FK_keep_resources_id` (`resources_id`),
   CONSTRAINT `FK_keep_resources_id` FOREIGN KEY (`resources_id`) REFERENCES `resources` (`id`),
   CONSTRAINT `FK_keep_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 
 CREATE TABLE `labels` (
   `id` int(6) NOT NULL AUTO_INCREMENT,
   `name` varchar(10) NOT NULL COMMENT '标签名称',
   `isfriend` int(1) NOT NULL DEFAULT '0' COMMENT '0友好、1不友好',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8
 
 CREATE TABLE `messages` (
   `id` int(6) NOT NULL AUTO_INCREMENT,
   `message` varchar(255) NOT NULL COMMENT '消息信息',
   `sort` int(1) NOT NULL DEFAULT '0' COMMENT '0系统消息、1评论消息、2兑换消息、3上传图片消息、4上传文件消息、5下载消息(用户扣除积分)、6下载消息(资源用户积分增加)',
   `email` varchar(20) NOT NULL COMMENT '所属用户',
   `isRead` int(1) NOT NULL DEFAULT '0' COMMENT '0未读、1读',
   `nowtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 
 

CREATE TABLE `resources` (
   `id` int(6) NOT NULL AUTO_INCREMENT,
   `name` varchar(50) NOT NULL COMMENT '分享的资源标题',
   `sort` int(1) NOT NULL DEFAULT '0' COMMENT '0无分类、1完整项目、2单一功能、3界面模板、4js特效',
   `jifen` int(6) NOT NULL DEFAULT '0' COMMENT '下载所需积分',
   `label` int(4) NOT NULL DEFAULT '0' COMMENT '10-20，参考标签表',
   `description` longtext NOT NULL COMMENT '资源详细描述',
   `title` varchar(255) NOT NULL DEFAULT '0' COMMENT '资源原始名称',
   `url` varchar(255) NOT NULL DEFAULT '0' COMMENT '百度云资源url',
   `urlpwd` varchar(50) NOT NULL DEFAULT '0' COMMENT '百度云资源的提取码',
   `nowtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
   `isfirst` int(1) NOT NULL DEFAULT '0' COMMENT '0不公告、1公告',
   `isjing` int(1) NOT NULL DEFAULT '0' COMMENT '0不精、1精',
   `isyuan` int(1) NOT NULL DEFAULT '0' COMMENT '0不原创、1原创',
   `isfriend` int(1) NOT NULL DEFAULT '0' COMMENT '0友好、1不友好',
   `user_id` int(6) NOT NULL COMMENT '所属用户的id',
   PRIMARY KEY (`id`),
   KEY `FK_resources_user_id` (`user_id`),
   CONSTRAINT `FK_resources_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8
 
 alter table resources AUTO_INCREMENT=1
 
 CREATE TABLE `user` (
   `id` int(6) NOT NULL AUTO_INCREMENT,
   `name` varchar(20) NOT NULL COMMENT '昵称，如：西安-飞哥',
   `password` varchar(32) NOT NULL,
   `email` varchar(30) NOT NULL COMMENT '邮箱用于账号登录、激活、修改密码',
   `jifen` int(6) NOT NULL DEFAULT '5' COMMENT '账户积分，用于下载',
   `money` int(6) NOT NULL DEFAULT '0' COMMENT '赞助网站',
   `isactive` int(1) NOT NULL DEFAULT '0' COMMENT '0未激活、1激活、2账户封',
   `style` int(1) NOT NULL DEFAULT '0' COMMENT '界面风格，默认上下风格',
   `nowtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
   `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别，默认0男生，1女生',
   `label` varchar(20) NOT NULL DEFAULT '3' COMMENT '个人账户的一些标签',
   `img` varchar(100) NOT NULL DEFAULT 'res/images/avatar/default.png' COMMENT '个人头像的url',
   `city` varchar(20) NOT NULL DEFAULT '某城市' COMMENT '个人所在城市',
   `motto` varchar(100) NOT NULL DEFAULT '在此立下自己的座右铭' COMMENT '个人座右铭',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8
 
#查询用户最近的10条评论
select r.id as r_id,r.name as r_name,c.content as c_content,c.nowtime as c_nowtime
from comments c
left join resources r on c.resources_id=r.id
where user1_id = 1 
order by nowtime desc limit 10 
 
#获取某个用户分享代码列表
select r.id,r.name,r.nowtime,r.isjing,r.isyuan,
ifnull(c.res_comments_num,0) as res_comments_num,
ifnull(d.res_download_num,0) as res_download_num,
ifnull(kp.res_keep_num,0) as res_keep_num
from resources r
#查询收藏数
left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on r.id=kp.resources_id
#查询评论数
left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on r.id=c.resources_id
#查询下载数
left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on r.id=d.resources_id
where user_id = 1

#查询用户评论、下载、收藏的资源列表
select r.id,r.name,cs.nowtime,r.isjing,r.isyuan,
ifnull(c.res_comments_num,0) as res_comments_num,
ifnull(d.res_download_num,0) as res_download_num,
ifnull(kp.res_keep_num,0) as res_keep_num
from comments cs 
left join resources r on cs.resources_id = r.id
#查询收藏数
left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on cs.resources_id=kp.resources_id
#查询评论数
left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on cs.resources_id=c.resources_id
#查询下载数
left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on cs.resources_id=d.resources_id
#需要指定用户
where cs.user1_id = 1
group by cs.resources_id

#联合查询用户评论、下载、收藏的资源列表
#sort为1代表用户评论的代码列表
(select 1 as sort,cs.resources_id as id,r.name,cs.nowtime,r.isjing,r.isyuan,
ifnull(cs.res_comments_num,0) as res_comments_num,
ifnull(d.res_download_num,0) as res_download_num,
ifnull(kp.res_keep_num,0) as res_keep_num
from(select c.resources_id,c.nowtime,count(c.resources_id) as res_comments_num from comments c 
#需要指定用户
where c.user1_id = 1
group by c.resources_id) cs
left join resources r on cs.resources_id = r.id
#查询收藏数
left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on cs.resources_id=kp.resources_id
#查询下载数
left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on cs.resources_id=d.resources_id
)
union all
#sort为2代表用户下载的代码列表
(select 2 as sort,d.resources_id as id,r.name,d.nowtime,r.isjing,r.isyuan,
ifnull(c.res_comments_num,0) as res_comments_num,
ifnull(d.res_download_num,0) as res_download_num,
ifnull(kp.res_keep_num,0) as res_keep_num
from (select d.resources_id,d.nowtime,count(d.resources_id) as res_download_num from download d
#需要指定用户
where d.user_id = 1
group by d.resources_id) d
left join resources r on d.resources_id = r.id
#查询收藏数
left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on d.resources_id=kp.resources_id
#查询评论数
left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on d.resources_id=c.resources_id
)
union all
#sort为3代表用户收藏的代码列表
(select 3 as sort,kp.resources_id as id,r.name,kp.nowtime,r.isjing,r.isyuan,
ifnull(c.res_comments_num,0) as res_comments_num,
ifnull(d.res_download_num,0) as res_download_num,
ifnull(kp.res_keep_num,0) as res_keep_num
from (select k.resources_id,k.nowtime,count(k.resources_id) as res_keep_num from keep k
#需要指定用户
where k.user_id = 1 
group by k.resources_id)kp
left join resources r on kp.resources_id = r.id
#查询评论数
left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on kp.resources_id=c.resources_id
#查询下载数
left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on kp.resources_id=d.resources_id
)
order by sort,nowtime desc


#优化联合查询用户评论、下载、收藏的资源列表
select a.sort,a.id,r.name,a.nowtime,r.isjing,r.isyuan,
ifnull(c.res_comments_num,0) as res_comments_num,
ifnull(d.res_download_num,0) as res_download_num,
ifnull(kp.res_keep_num,0) as res_keep_num
from 
#sort为1代表用户评论的代码列表
(select 1 as sort,c.resources_id as id,c.nowtime as nowtime from comments c 
#需要指定用户
where c.user1_id = 1
group by c.resources_id
union all
#sort为2代表用户下载的代码列表
select 2 as sort,d.resources_id as id,d.nowtime as nowtime from download d
#需要指定用户
where d.user_id = 1
group by d.resources_id
union all
#sort为3代表用户收藏的代码列表
select 3 as sort,k.resources_id as id,k.nowtime as nowtime from keep k
#需要指定用户
where k.user_id = 1 
group by k.resources_id)as a
left join resources r on a.id = r.id
#查询收藏数
left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on a.id=kp.resources_id
#查询评论数
left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on a.id=c.resources_id
#查询下载数
left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on a.id=d.resources_id
order by sort,nowtime desc

#查询某个用户消息
select m.id,m.message,m.sort,m.isRead,m.nowtime
from messages m
where m.email='1181014088@qq.com'
order by m.nowtime desc


insert into resources(name,description,user_id) values('1','1','1');

#最近一个月分享次数最多的人,1为本月分享榜，2为最新新人榜，3为称号排行榜，4为赞助排行榜，5为积分排行榜
(select 1 as sort,user.id,user.name,user.img,r.num from (
select user_id,count(user_id) as num
from resources
where date_sub(curdate(),interval 60 day)<=nowtime
group by user_id )r
left join user on r.user_id=user.id
order by r.num desc,id limit 12)
union all (select 2 as sort,user.id,user.name,user.img,user.id as num from user order by id desc limit 4)
union all (select 3 as sort,user.id,user.name,user.img,labels.name as num from user left join labels on user.label=labels.id where user.label>2 order by user.label desc,id limit 12)
union all (select 4 as sort,user.id,user.name,user.img,user.money as num from user where user.money>0 order by money desc,id limit 12)
union all (select 5 as sort,user.id,user.name,user.img,user.jifen as num from user where user.jifen>0 order by jifen desc,id limit 12)


#称号排行榜、赞助排行榜、积分排行榜


#最近一个月评论、下载、收藏次数最多的代码
select r.sort,r.id,resources.name as name,r.num
from (
(select 1 as sort,resources_id as id,count(resources_id) as num from comments where date_sub(curdate(),interval 60 day)<=nowtime group by resources_id order by num desc limit 12)
union all
(select 2 as sort,resources_id as id,count(resources_id) as num from download where date_sub(curdate(),interval 60 day)<=nowtime group by resources_id order by num desc limit 12)
union all
(select 3 as sort,resources_id as id,count(resources_id) as num from keep where date_sub(curdate(),interval 60 day)<=nowtime group by resources_id order by num desc limit 12)
) r
left join resources on r.id = resources.id
order by r.sort,num desc


#查询公告、原创、精
select id,name,isfirst,isyuan,isjing from resources
where isfirst=1 or isyuan=1 or isjing=1


#升级称号
select  id,mymoney,GROUP_CONCAT(mylabel SEPARATOR ".") as mylabel,money from
(select u.label as id,u.money as mymoney,l.name as mylabel,l.money from user u left join labels l on u.label <= l.id where u.id = 1 limit 0,2) l

update user set label=label+1,money=money-1 where id=1

#查询左侧根节点
select id,pid,name,url,parent,open from ztree where istree >0 and isfriend=0 order by istree,isorder

select * from ztree where istree=2

#修改第二层的超链接
update ztree set click=CONCAT("clickZtree(",id,");") where istree=2

update ztree set url=0 where istree=2
insert into ztree(istree,id,pid,name,url,remark) values(3,?,?,?,?,?)

select count(*) from ztree where istree=3 and isfriend=0

#查询待审核知识
select tid as id,a.name as title,url,b.name as sort from ztree a left join (select id,name from ztree where istree in(1,2)) b on a.pid=b.id where isfriend=0 and istree=3

update ztree set isfriend=0 where istree=3

select * from ztree where istree=3

#查询页面展示知识
select tid as id,a.name as title,url,b.name as sort,number,remark,nowtime from ztree a 
left join 
(select id,name from ztree where istree in(1,2)) b 
on a.pid=b.id 
where isfriend=1 and istree=3 
order by a.tid desc
limit ?,?

select a.tid as id,a.name as title,a.url,b.name as sort,a.number,a.remark,a.nowtime from ztree a 
left join ztree b
on a.pid=b.id 
where a.isfriend=1 and a.istree=3 
order by a.tid desc

select tid as id,a.name as title,url,b.name as sort,number,remark,nowtime from ztree a left join (select id,name from ztree where istree in(1,2)) b on a.pid=b.id where a.name like %0% and isfriend=1 and istree=3 order by a.tid desc limit ?,? 

select * from ztree where istree in(1,2)

update ztree set click = concat('clickZtree(',tid,')') where istree =1

update ztree set number=number+1 where tid=102 and istree=3 and isfriend=1

select count(*) from ztree where pid=11 and istree=3 and isfriend=1

#查询当天分享知识数量和总知识数量
select count(*) from ztree where to_days(nowtime) = to_days(now()) and istree=3 and isfriend=1;
select count(*) from ztree where istree=3 and isfriend=1


