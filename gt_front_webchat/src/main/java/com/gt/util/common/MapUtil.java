package com.gt.util.common;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapUtil {
	
	/**
	 * 根据key模糊查询
	 * */
	public static List<ChannelHandlerContext> findValueByLikeKey(String str, Map<String, ChannelHandlerContext> map){
		List<ChannelHandlerContext> list = new ArrayList<ChannelHandlerContext>();
	    Iterator it = map.entrySet().iterator();
	    while(it.hasNext()) {
	        Map.Entry<String, ChannelHandlerContext> entry = (Map.Entry<String, ChannelHandlerContext>)it.next();
	        if (entry.getKey().contains(str)) {
	            list.add(entry.getValue());
	        }
	    }
	    return list;
	}
	
	public static List<String> findKeysByLikeString(String str, Map<String, ChannelHandlerContext> map){
		List<String> list = new ArrayList<String>();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, ChannelHandlerContext> entry = (Map.Entry<String, ChannelHandlerContext>)it.next();
			if(entry.getKey().contains(str)){
				list.add(entry.getKey());
			}
		}
		return list;
	}
	
}
