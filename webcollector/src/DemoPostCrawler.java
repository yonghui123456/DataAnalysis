/*
 * Copyright (C) 2014 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

import org.json.JSONObject;
import org.zkpk.zhuaqu.WriteLocal;
import org.zkpk.zhuaqu.pingjia;
import org.zkpk.zhuaqu.price;


public class DemoPostCrawler extends BreadthCrawler {
	static List<String> urls = new ArrayList<String>();
	static double price ;
	static String zhuaqutime;

    public DemoPostCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }
    
    public void add(String url){
    	this.addSeed(new CrawlDatum(url).meta("method", "GET"));
    }

    @Override
    public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum.getUrl());
        
        request.setMethod(crawlDatum.meta("method"));
        String outputData=crawlDatum.meta("outputData");
        if(outputData!=null){
            request.setOutputData(outputData.getBytes("utf-8"));
        }
        return request.getResponse();
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
    	String doc = page.getHtml();
		if (doc.isEmpty()){
			urls.add(page.getUrl());
		}else{
			JSONObject json = new JSONObject(doc);
			json.append("price", price);
			System.out.println(json.toString());
			WriteLocal.getWrite(json.toString());
		}
    }
    public static void main(String[] args) throws Exception {
    	String sign = args[0];
		price pri = new price(sign);
		price = pri.price;
		pingjia p = new pingjia(sign);
		String f = "/jd_pingjia/jd_test";
		File file = new File(f);
		if((!file.exists())||file.isFile()){
			file.mkdirs();
		}
		SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmmss");
		zhuaqutime = sm.format(new Date());
		WriteLocal.getbw(f+"/"+zhuaqutime+".txt");
        for(int i=0;i<Math.ceil(p.number/10.0);i+=50){
        	DemoPostCrawler test = new DemoPostCrawler("/jd_pingjia/jd_test/craw", false);
        	for(int j=i;j<i+50;j++){
        		test.add("http://club.jd.com/productpage/p-"+sign+"-s-0-t-3-p-"+j+".html");
        	}
        	test.start(1);
        }
        if(urls.size()>0){
        	
			for(int i=0;i<urls.size();i+=50){
				DemoPostCrawler test = new DemoPostCrawler("/jd_pingjia/jd_test/craw", false);
	        	for(int j=i;j<i+50 && j<urls.size();j++){
	        		test.add(urls.get(j));
	        	}
	        	test.start(1);
			}
        }
        WriteLocal.close();
		String source=zhuaqutime+"\t"+sign+"\t"+Math.ceil(p.number/10.0)+"\n";
		File biaozhi = new File(f+"/SUCCESS");
		BufferedWriter bw=null;
		bw=new BufferedWriter(new FileWriter(biaozhi,true));
		bw.write(source);
		bw.flush();
    }

}
