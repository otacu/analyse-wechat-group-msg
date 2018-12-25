# analyse-wechat-group-msg
分析处理微信群消息

是对my-itchat微信网页版封装的一个应用。

页面可以登陆微信，可以用excel导入要统计的人名，当微信群中出现包含人名的约定格式的字符串时，把这个人的状态改为已完成。

CREATE TABLE `tb_myitchat_student_finish_status` (
  `idx` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `status` smallint(1) unsigned DEFAULT NULL COMMENT '状态：0未完成，1已完成',
  PRIMARY KEY (`idx`)
);
