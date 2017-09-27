# MB
> MB是基于Jfinal开发的多人博客或者社区网站,二次开发之后也可以作为资讯网站等。具有简单、大气、美观、响应式设计，易二次开发的优点。前台部分界面参考了[mblog](https://gitee.com/mtons/mblog)，后台使用AdminLTE。


### 技术选型：

* JDK8
* Jfinal 3.1
* 缓存 Ehcache
* 视图模板 Jfinal enjoy模板
* 其它 Jsoup、fastjson、ajax
* jQuery
* Bootstrap 前端框架
* UEditor编辑器
* font-wesome 字体/图标
* webuploader
* layer
* jcrop

### 图片演示
![MB文章](https://git.oschina.net/uploads/images/2017/0927/142025_37dfcf48_907426.png "1.png")
![MB视频](https://git.oschina.net/uploads/images/2017/0927/142102_e1c35dfe_907426.png "2.png")
![发现](https://git.oschina.net/uploads/images/2017/0927/143438_b8111e2a_907426.png "QQ截图20170927142644(1).png")
![走廊](https://git.oschina.net/uploads/images/2017/0927/143501_9c694b25_907426.png "QQ截图20170927143300(1).png")
![后台](https://git.oschina.net/uploads/images/2017/0927/142429_3b75fe0e_907426.png "5.png")


### 如何运行部署

1.新建mysql数据库fcms,导入sql文件夹下的sql文件。
2.运行JcmsConfig.java。
3.在浏览器中输入http://localhost:8080 进行访问。
4.部署于Tomcat时，使用mvn install进行打包获得war包后进行部署。



### 开源协议

如果您的网站使用了 MB, 请在网站页面页脚处保留MB相关版权信息链接。
