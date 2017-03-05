package org.zkpk.zhuaqu;

import org.json.JSONObject;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;

public class price extends RamCrawler {
	public static double price ;
	public price(String sign){
		String url_price="http://p.3.cn/prices/mgets?skuIds=J_"+sign;
		CrawlDatum datum_price = new CrawlDatum(url_price);
		addSeed(datum_price);
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
		JSONObject json = new JSONObject(doc.substring(1, doc.length()-2));
		price = Double.valueOf(json.optString("p"));
	}
//	public static void main(String[] args) throws Exception {
//		price crawler = new price("10014481423");
//		System.out.println(price);
//	}
// 
}
