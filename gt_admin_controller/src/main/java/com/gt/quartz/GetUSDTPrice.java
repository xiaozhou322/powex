package com.gt.quartz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.service.front.FrontConstantMapService;
import com.gt.util.HttpClientUtils;

import net.sf.json.JSONObject;


/**
 * 定时获取USDT的时价
 * @author lyw
 *
 */
public class GetUSDTPrice {
	private static final Logger log = LoggerFactory.getLogger(GetUSDTPrice.class);
	
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	static Pattern priceInfo = Pattern.compile("<div class=price1>(.*?)</div>", Pattern.DOTALL);
	/**
	 * 数据统计定时任务入口
	 */
	public void work() {
		synchronized (this) {
			String usdtPrice ="";
			try{
				usdtPrice= this.getFromHuobi();
			}catch (Exception e) {
				e.printStackTrace() ;
			}
			if(null==usdtPrice || usdtPrice.equals("")) {
				try{
					usdtPrice= this.getFromFeixiaohao();
				}catch (Exception e) {
					e.printStackTrace() ;
				}
			}
			if(null==usdtPrice || usdtPrice.equals("")) {
				usdtPrice = "6.5";
			}
			this.frontConstantMapService.put("USDT_PRICE", usdtPrice);
		}
	}
	
	private String getFromHuobi() throws Exception {

		String url = "https://otc-api.eiijo.cn/v1/data/trade-market?country=37&currency=1&payMethod=0&currPage=1&coinId=2&tradeType=buy&blockType=general&online=1";
		if (this.frontConstantMapService.get("usdturl_hb")!=null) {
			url = this.frontConstantMapService.get("usdturl_hb").toString();
		}
		String result = "";
		String str = HttpClientUtils.sendGet(url);
		JSONObject json = JSONObject.fromObject(str);
		if(json!=null) {
			if(json.containsKey("data") && json.get("data")!=null){
				JSONObject lastOrder  = json.getJSONArray("data").getJSONObject(0);
				result = lastOrder.getString("price");
	        }
		}
		System.out.println("火币价格："+result);
		return result;
	}
	
	private String getFromFeixiaohao() throws Exception {

		String url = "https://m.feixiaohao.com/currencies/tether/";
		if (this.frontConstantMapService.get("usdturl_fxh")!=null) {
			url = this.frontConstantMapService.get("usdturl_fxh").toString();
		}
		String result = "";
		String str = HttpClientUtils.sendGet(url);
		if(str!=null && !str.equals("")) {
			Matcher m = priceInfo.matcher(str);
			if(m.find()) {
				result = m.group(1).trim().replace("¥", "");
			}
		}
		System.out.println("非小号价格："+result);
		return result;
	}
	
}
