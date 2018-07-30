package com.jz.spider.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.management.JMException;

/**
 * @author: 冀陆涛
 * @create: 2018-07-29 10:00
 **/
public class GithubRepoPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1/strong/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page

            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws JMException {
        Spider.create(new GithubRepoPageProcessor())
                .addUrl("https://github.com/jz03")
                .addPipeline(new FilePipeline("F:\\git\\spider\\result"))
                .thread(5)
                .run();

//        JMX监控
//        Spider spider =Spider.create(new GithubRepoPageProcessor())
//                .addUrl("https://github.com/jz03")
//                .addPipeline(new FilePipeline("F:\\git\\spider\\result"));
//
//        SpiderMonitor.instance().register(spider);
//        spider.start();
    }
}
