# spider
采用WebMagic实现的爬虫

## 遇到的问题

1.直接用中央仓库中的代码会出现Downloader错误

 解决方法：直接将webmagic源码下载下来进行本地maven打包处理,将会把源码自动打包到本地仓库，然后把主工程中的依赖进行更新即可。

2.好多抓取的页面需要渲染之后才能用，否则抓取的页面没有有效信息

 解决方法：
 首先安装phantomjs,下载地址如下，解压安装，进行环境配置即可

      http://phantomjs.org/download.html

     然后在pom文件中引用下边的依赖；

            <dependency>
                <groupId>us.codecraft</groupId>
                <artifactId>webmagic-selenium</artifactId>
                <version>0.7.3</version>
            </dependency>

     最后进行修改源代码中的配置文件路径,配置文件内容可以参考resources/config.ini
       源代码中的位置： us.codecraft.webmagic.downloader.selenium.WebDriverPool.DEFAULT_CONFIG_FILE


#### 参考链接：
* 源码 https://github.com/code4craft/webmagic
* 文档 http://webmagic.io/docs/zh/
