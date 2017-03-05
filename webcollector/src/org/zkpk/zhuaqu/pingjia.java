package org.zkpk.zhuaqu;

import org.json.JSONObject;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;

public class pingjia extends RamCrawler {
	public static int number ;
	public pingjia(String sign){
		String url_pingjia = "http://club.jd.com/productpage/p-"+sign+"-s-0-t-3-p-0.html";
		CrawlDatum datum_pingjia = new CrawlDatum(url_pingjia);
		addSeed(datum_pingjia);
		try {
			this.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		String doc = page.getHtml();
//		System.out.println(doc);
		JSONObject json = new JSONObject(doc);
		JSONObject pingjiatiaoshu_json = json.optJSONObject("productCommentSummary");
		number = Integer.valueOf(pingjiatiaoshu_json.optString("commentCount"));
//		System.out.println(number);
	}
//	public static void main(String[] args) throws Exception {
//		pingjia crawler = new pingjia("10014481423");
//		crawler.start();
//	}
 
}
