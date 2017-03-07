package spider;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
public class ZhiHuSpider implements PageProcessor{
    public static final String ZhiHuListUrl="/question/[0-9]+";//问题url列表
    public static final String ZhiHuQuesUrl="https://www.zhihu.com/question/[0-9]+";//问题的最终url包含答案详情的url
    //模拟登陆知乎
    private Site site = new Site().setRetryTimes(3).setSleepTime(100)
            //添加cookie之前一定要先设置主机地址，否则cookie信息不生效
            .setDomain("www.zhihu.com")
            //添加抓包获取的cookie信息
            .addCookie("_za", "55df5c9b-fb89-4c34-8926-2960f1562b4b")//o
            .addCookie("_ga", "GA1.2.1533197037.1477273708")//o
            //.addCookie("udid", "AJCA0aAcmgmPTj0wuYklftKs4ZlexHybzwI=|1457741092")
            .addCookie("d_c0", "AADAMMsFXQqPTj36AahnXhlkgY7zK1Y6Kik=|1470821334")//o
            .addCookie("_zap", "0e1c53d4-1f03-4ff7-aa9a-e3a509bf2cf8")//o
            .addCookie("q_c1", "99cd03fe625441d4aa555a54e4a444d7|1487055114000|1470821334000")//o
            .addCookie("aliyungf_tc", "AQAAAJ4TCjjTqgYALaLT3nY1BMJgKLX/")//o
            .addCookie("l_cap_id","MWIxMDU2NTc2ZTQ5NDk1Njg2Njg1MjE5YjQ0OTMzZWQ=|1487135806|5b4e26bbd18e3fe9125186ae8458eeff19ce83c5")//n
            .addCookie("_xsrf", "cdc43c3bac644f50e26a18bdae0ca4d5")//o
            //.addCookie("r_cap_id", "ZmUxYzUyNjYzNjJkNDlmYmJlODRmZTMxYzJjZjcyNmI=|1488807971|07d6e10ef56e76e4a18438aacbb83cb3e9325883")
            .addCookie("cap_id", "NTFiNzRkYTBhZTk2NDI2OTkxODA1N2IwYTkyODczMGE=|1487135806|5d6e3ac088f341b31b8562baf97be2d5a84ef377")//o
            .addCookie("login","NzNiYjIyYzc3NjI3NGEzNDhiYTJjYjU0ZDZkZWEzYTE=|1487135823|e50ae14861dffa4ac17d887540a60638d3616e43")//n
            .addCookie("s-q","java")//o
            .addCookie("s-i","2")//o
            .addCookie("sid","nh9ohg0o")//o
            .addCookie("s-t","autocomplete")//n
            .addCookie("z_c0","Mi4wQUFDQTY4NGpBQUFBQU1Bd3l3VmRDaGNBQUFCaEFsVk5aSEhMV0FBSU5XcnlwejQzd1praEdMMEYxN2FOM3NoV053|1488868552|d8808f83f18c30cf38656408ed8a606d9e9ed763")//o
            .addCookie("nweb_qa","heifetz")//o
            .addCookie("__utma","51854390.1533197037.1477273708.1488855860.1488867952.2")//o
            .addCookie("__utmb","51854390.0.10.1488867952")//o
            .addCookie("__utmc","51854390")//o
            .addCookie(" __utmz","51854390.1488867952.2.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic")//o
            .addCookie("__utmv","51854390.100-1|2=registration_date=20140110=1^3=entry_date=20140110=1")
               //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
            .addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch, br")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
            .addHeader("Connection", "keep-alive")
            .addHeader("Referer", "https://www.zhihu.com/search?type=content&q=java+");
    //抓取数据
          public void process(Page page) {
              //列表页
              if (page.getHtml().links().regex(ZhiHuListUrl).match()) {
                  page.addTargetRequests(page.getHtml().xpath("//div[@class=contents").links().regex(ZhiHuQuesUrl).all());
                  page.addTargetRequests(page.getHtml().links().regex(ZhiHuQuesUrl).all());

                  //文章页
              } else {
                  page.putField("questionTitle:", page.getHtml().xpath("/h1[class='QuestionHeader-title']/text()"));
                 /* page.putField("content", page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
                  page.putField("date", page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
                  List<String> titleList= page.getHtml().xpath("div[@class=title]/a/allText()").all();
                  // System.out.println(page.getHtml());
                  System.out.println(titleList.size());
                  for (String title:titleList
                          ) {
                      System.out.println(title);
                  }*/
              }
    }

    public Site getSite() {
        return site;
    }
//测试
    public static void main(String[] args) {
        Request request = new Request("https://www.zhihu.com/search?type=content&q=java");
        request.setMethod(HttpConstant.Method.GET);
        //只有post请求才支持参入参数
        NameValuePair[] nameValuePair = {new BasicNameValuePair("type","content"),
                new BasicNameValuePair("q","java")};
        Spider.create(new ZhiHuSpider())
                .addRequest(request)
                .addPipeline(new ConsolePipeline())
                // 开启5个线程抓取
                .thread(1)
                // 启动爬虫
                .run();

    }
}
