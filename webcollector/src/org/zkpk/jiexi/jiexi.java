package org.zkpk.jiexi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zkpk.ruku.EnterData;


public class jiexi {
	boolean sign = true;
	
	public void start(String doc,String platfrom,String xinhao,String zhuaqutime) {
		/**
		 * json格式解析
		 */
		JSONObject json = new JSONObject(doc);
		//价格
		String price = json.optString("price");
		price = price.substring(1, price.length()-1);
		if(price.equals("-1")){
			price = "0";
		}
		/**
		 * label为京东已经为产品打好的标签，每个URl只写一次
		 */
		
		if((!json.optString("comments").isEmpty())&&!json.optJSONArray("comments").isNull(0)){
			JSONArray value = json.optJSONArray("comments");
			//标签
			String label_result="";
			for(int i = 0;i<value.length();i++){
				if(sign){
					JSONArray labels = json.optJSONArray("hotCommentTagStatistics");
					String label="";
					if(labels!=null){
						sign=false;
						for(int j=0;j<labels.length();j++){
							if(j==labels.length()-1){
								label+=labels.getJSONObject(j).optString("name");
							}else{
								label+=labels.getJSONObject(j).optString("name")+",";
							}
						}
						label_result=label;
					}else{
						label_result="null";
					}
						
				}else{
					label_result="null";
				}
				/*
				 * 每个result为一条销售记录产生的数据
				 */
				JSONObject result = value.getJSONObject(i);
				String yinxiang = "null";
				/*
				 * 判断用户是否填写了用户印象
				 */
				if(result.has("commentTags")){
					JSONArray commentTags = result.optJSONArray("commentTags");
					String biaoqian = "";
					for(int j = 0;j<commentTags.length();j++){
						JSONObject yinxiang_json = commentTags.optJSONObject(j);
						biaoqian += (yinxiang_json.optString("name").replaceAll("\t", " ")+" ");
					}
					yinxiang = biaoqian;
				}
				//评价内容
				String content = result.optString("content").replaceAll("[\\t\\n\\r]", " ");
				//判断评价是否为空，如果为空则不输出
				if(!content.isEmpty()){
				//title
				String referenceName = result.optString("referenceName").replaceAll("\t", "");
				//会员等级
				String userLevelName = result.optString("userLevelName").replaceAll("\t", " ");
				//购买平台
				String userClientShow = result.optString("userClientShow").replaceAll("\t", " ");
				//if(!userClientShow.equals("")){
					//userClientShow= userClientShow.substring(userClientShow.indexOf('>')+3,userClientShow.lastIndexOf('<'));
				//}else{
					userClientShow="京东PC客户端";
				//}
				//购买地区
				String userProvince = result.optString("userProvince").replaceAll("\t", " ");
					if(userProvince.isEmpty()){
						userProvince = "null";
					}
				//颜色
				String color = result.optString("productColor");
				if(color.isEmpty()){
					color="null";
				}
				//网络类型
				String productSize = result.optString("productSize");
				if(productSize.isEmpty()){
					productSize="null";
				}
				//评价时间
				String creationTime = result.optString("creationTime");
				//平台 	型号	title	comment	会员等级	购买平台	地区	用户印象	颜色	 价格	网络类型	评价时间	抓取时间	标签
					try {
						EnterData.insertData(platfrom.trim(), xinhao.trim(), referenceName.trim(), 
								content.trim(), userLevelName.trim(), userClientShow.trim(),
								userProvince.trim(),yinxiang.trim(), color.trim(), price.trim(),
								productSize.trim(), creationTime.trim(), zhuaqutime.trim(),
								label_result.trim());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
