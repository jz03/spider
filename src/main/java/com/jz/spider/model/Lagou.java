package com.jz.spider.model;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.FilePipeline;

/**
 * 拉勾网
 *
 * @author: 冀陆涛
 * @create: 2018-07-29 17:14
 **/
@TargetUrl("https://www.lagou.com/jobs/.*")
public class Lagou {
    //职位诱惑
    @ExtractBy(value = "div.BlogContent", type = ExtractBy.Type.Css)
    private String seduction;

    //岗位描述
    @ExtractBy("//dd[@class='job_bt']/text()")
    private String desc;

    //工作地址
    @ExtractBy("//dd[@class='job-address clearfix']/text()")
    private String address;
    //招聘单位
    @ExtractBy("//div[@class='company']/text()")
    private String unit;
    //公司规模
    @ExtractBy(value = "\\d*\\-\\d*人", type = ExtractBy.Type.Regex)
    private String companySize;

    public static void main(String[] args) {
        OOSpider.create(Site.me().setRetryTimes(3).setSleepTime(100),
                new ConsolePageModelPipeline(), Lagou.class)
                .addPipeline(new FilePipeline("F:\\git\\spider\\result"))
                .setDownloader(new SeleniumDownloader("F:\\chromedriver.exe"))
                .addUrl("https://www.lagou.com/")
                .runAsync();
    }

}
