package spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2017/3/6.
 */
public class ZhiHuSpider implements PageProcessor{
    //模拟登陆知乎
    private Site site = new Site().setRetryTimes(3).setSleepTime(100)
            //添加cookie之前一定要先设置主机地址，否则cookie信息不生效
            .setDomain("www.zhihu.com")
            //添加抓包获取的cookie信息
            .addCookie("_za", "b73d54b3-0d34-45fb-8ed8-b36ca98aad26")
            .addCookie("_ga", "GA1.2.819284506.1436374467")
            .addCookie("udid", "AJCA0aAcmgmPTj0wuYklftKs4ZlexHybzwI=|1457741092")
            .addCookie("d_c0", "ADBA6jGK3gmPTiw5_ht0PGMTyb74lGOm8yQ=|1462333217")
            .addCookie("_zap", "0b7fe792-abd3-42c0-a54d-db2ac3a62d97")
            .addCookie("q_c1", "64fc53b70f2b418d80502dbb687e7d20|1486878584000|1486878584000")
            .addCookie("aliyungf_tc", "AQAAAPF7qSLjDwsAEySWtmpf+AGRpdOo")
            .addCookie("_xsrf", "03808d3da7a81d360bb9e0404a8d4a8f")
            .addCookie("r_cap_id", "ZmUxYzUyNjYzNjJkNDlmYmJlODRmZTMxYzJjZjcyNmI=|1488807971|07d6e10ef56e76e4a18438aacbb83cb3e9325883")
            .addCookie("cap_id", "Y2JlNzdkYjVmM2RjNDNiY2I0M2RjOWQ3ODg1YzZhYjc=|1488807971|7e9a9dce30aee32a4104d68047c03aadfa065345")
            .addCookie("s-q","Java%20%E6%A1%86%E6%9E%B6")
            .addCookie("s-i","11")
            .addCookie("sid","u298t0ol")
            .addCookie("z_c0","Mi4wQUFDQTY4NGpBQUFBTUVEcU1ZcmVDUmNBQUFCaEFsVk5XX2JrV0FCd2NNLUlmeFNBZnhLUVdVcFNzdWZsUjZXcW53|1488808288|5aa0e402679ed31e30e8046a5587c190b69b4aae")
            .addCookie("nweb_qa","heifetz")
            .addCookie("__utma","51854390.819284506.1436374467.1486878716.1488806452.2")
            .addCookie("__utmb","51854390.0.10.1488806452")
            .addCookie("__utmc","51854390; __utmz=51854390.1488806452.2.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic")
            .addCookie("__utmv","51854390.100-1|2=registration_date=20140110=1^3=entry_date=20140110=1")
               //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
            .addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch, br")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
            .addHeader("Connection", "keep-alive")
            .addHeader("Referer", "https://www.zhihu.com/");
    //抓取数据
    public void process(Page page) {
        page.putField("aboutme", page.getHtml().xpath("//*[@id=\"feed-p2426219_10201_0\"]/div[2]/div[2]/h2/a/text()").toString());
    }

    public Site getSite() {
        return site;
    }
//测试
    public static void main(String[] args) {
        Spider.create(new ZhiHuSpider())
                .addUrl("https://www.zhihu.com")
                .addPipeline(new ConsolePipeline())
                // 开启5个线程抓取
                .thread(1)
                // 启动爬虫
                .run();
    }
}
