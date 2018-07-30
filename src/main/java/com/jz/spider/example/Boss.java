package com.jz.spider.example;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 抓取boss直聘网上的Java招聘信息
 * 请求过于频繁会报403错误
 *
 * @author: 冀陆涛
 * @create: 2018-07-30 15:07
 **/
public class Boss implements PageProcessor {
    private Site site;
    private Logger logger = Logger.getLogger(getClass());

    public void process(Page page) {

        //设定URL请求规则
        page.addTargetRequests(page.getHtml().links().regex("https://www.zhipin.com/job_detail/.*").all());

        if (!page.getUrl().toString().contains("query")) {
            if(page.getHtml().toString().contains("Java")){
                //设定抓取规则
                page.putField("desc", page.getHtml().regex("职位描述.*(?=竞争力分析)"));
            }
        } else {
            logger.warn("*********************跳过改请求！****************************");
            page.getResultItems().setSkip(true);
        }

    }

    public Site getSite() {
        if (null == site) {
            //设定请求头信息
            site = Site.me().setDomain("zhipin.com").setSleepTime(10000)
                    .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .setCharset("UTF-8");
        }
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new Boss()).thread(1)
                //设定抓取结果存放的位置
                .addPipeline(new FilePipeline("F:\\git\\spider\\result"))
                //自定义下载网页引擎，采用selenium来进行页面渲染，软后在进行抓取网页有用信息
                .setDownloader(new SeleniumDownloader("F:\\chromedriver.exe"))
                //设定开始URL
                .addUrl("https://www.zhipin.com")
                .runAsync();
    }
}
