package org.zkpk.zhuaqu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.zkpk.jiexi.jiexi;
import org.zkpk.ruku.EnterData;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;

public class test extends RamCrawler {
	static List<String> urls = new ArrayList<String>();
	static double price ;
	static String platfrom;
	static String xinhao;
	static String zhuaqutime;
	static jiexi jx ;
	
	public test(String url){
		CrawlDatum datum = new CrawlDatum(url);
		addSeed(datum);
	}


	@Override
	public void visit(Page page, CrawlDatums next) {
		String doc = page.getHtml();
		if (doc.isEmpty()){
			urls.add(page.getUrl());
		}else{
			JSONObject json = new JSONObject(doc);
			json.append("price", price);
			WriteLocal.getWrite(json.toString());
			jx.start(json.toString(), platfrom, xinhao, zhuaqutime);
		}
	}
	
	/**
	 * sign为产品url的skid，比如你要抓取的json包所在的url为
	 * http://club.jd.com/productpage/p-2174898-s-0-t-3-p-0.html,那么这里的
	 * skid=2174898
	 * args[1]为输出路径，这里咱们定义了输出路径为"/jd_pingjia",你也可以通过传参的方式
	 * 指定抓取的json文件要写入的路径。
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String sign = args[0];
		price pri = new price(sign);
		price = pri.price;
		pingjia p = new pingjia(sign);
		String f = "/jd_pingjia";
		File file = new File(f);
		if((!file.exists())||file.isFile()){
			file.mkdirs();
		}
		platfrom="京东";
		xinhao=args[1];
		SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmmss");
		zhuaqutime = sm.format(new Date());
		jx = new jiexi();
		WriteLocal.getbw(f+"/"+zhuaqutime+".txt");
		EnterData.getconnection();
		for(int page=0;page<=Math.ceil(p.number/10.0)-1;page++){
			String url = "http://club.jd.com/productpage/p-"+sign+"-s-0-t-3-p-"+page+".html";
			test t = new test(url);
			t.start();
		}
		if(urls.size()>0){
			for(int i=0;i<urls.size();i++){
				new test(urls.get(i)).start();
			}
		}
		WriteLocal.close();
		EnterData.close();
		String source=zhuaqutime+"\t"+sign+"\t"+Math.ceil(p.number/10.0)+"\n";
		File biaozhi = new File(f+"/SUCCESS");
		BufferedWriter bw=null;
		bw=new BufferedWriter(new FileWriter(biaozhi,true));
		bw.write(source);
		bw.flush();
		
	}
 
}
