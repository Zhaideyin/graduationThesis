package spider;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * Created by tongbu on 2017/3/10 0010.
 */
public class Job51 implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    public static final String job51ListUrl = "https://www.zhihu.com/r/search\\?\\w+=\\w+&\\w+=\\w+&offset=0";//问题url列表
    public static final String job51Url = "http://search.51job.com/list/010000,000000,0000,00,9,99,Java%25E5%25BC%2580%25E5%258F%2591%25E5%25B7%25A5%25E7%25A8%258B%25E5%25B8%2588,2,3.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=21&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";//问题的最终url包含答案详情的url
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());

        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());

        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        Spider.create(new Job51()).addUrl("http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=010000%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword=Java%E5%BC%80%E5%8F%91%E5%B7%A5%E7%A8%8B%E5%B8%88&keywordtype=2&curr_page=1&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9").thread(5).run();
    }
}
