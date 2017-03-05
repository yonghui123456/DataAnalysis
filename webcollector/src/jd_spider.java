import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.zkpk.zhuaqu.WriteLocal;
import org.zkpk.zhuaqu.pingjia;
import org.zkpk.zhuaqu.price;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;

public class jd_spider extends RamCrawler {
	static List<String> urls = new ArrayList<String>();
	static double price ;
	static String zhuaqutime;
	
	public jd_spider(String URL){
		this.addSeed(new CrawlDatum(URL));
	}
	public jd_spider(){
		
	}
	public void add (String url){
		this.addSeed(new CrawlDatum(url));
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
		}
	}
	
	/**
	 * sign为产品url的skid
	 * args[1]为输出路径
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String sign = args[0];
		price pri = new price(sign);
		price = pri.price;
		pingjia p = new pingjia(sign);
		String f = "C:/jd_pingjia/jd_test_0419";
		File file = new File(f);
		if((!file.exists())||file.isFile()){
			file.mkdirs();
		}
		SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmmss");
		zhuaqutime = sm.format(new Date());
		WriteLocal.getbw(f+"/"+zhuaqutime+".txt");
//		for(int page=0;page<=Math.ceil(p.number/10.0)-1;page++){
//			String url = "http://club.jd.com/productpage/p-"+sign+"-s-0-t-3-p-"+page+".html";
//			jd_spider t = new jd_spider(url);
//			t.start();
//		}
//		if(urls.size()>0){
//			for(int i=0;i<urls.size();i++){
//				new jd_spider(urls.get(i)).start();
//			}
//		}
		for(int page=0;page<Math.ceil(p.number/10.0);page+=25){
			jd_spider test = new jd_spider();
			for(int j=page;j<page+25&&j<Math.ceil(p.number/10.0);j++){
				test.add("http://club.jd.com/productpage/p-"+sign+"-s-0-t-3-p-"+j+".html");
			}
			test.start();
		}
		if(urls.size()>0){
			for(int i=0;i<urls.size();i+=25){
				jd_spider test = new jd_spider();
				for(int j=i;j<i+25&&j<urls.size();j++){
					test.add(urls.get(j));
				}
				test.start(); 
			}
		}
		WriteLocal.close();
		String source=zhuaqutime+"\t"+sign+"\t"+Math.ceil(p.number/10.0)+"\t"+sm.format(new Date())+"\n";
		File biaozhi = new File(f+"/SUCCESS");
		BufferedWriter bw=null;
		bw=new BufferedWriter(new FileWriter(biaozhi,true));
		bw.write(source);
		bw.flush();
		
	}
 
}
