package com.gt.websocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
/**
* ClassName:Global 用来存储访问的channel，channelGroup的原型是set集合，保证channel的唯一，如需根据参数标注存储，可以使用currentHashMap来存储。
* Function: TODO ADD FUNCTION.
*/
public class Global {
	//存储每一个客户端接入进来时的channel对象
	public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	//存放所有的ChannelHandlerContext
	public static Map<String, ChannelHandlerContext> pushCtxMap = new ConcurrentHashMap<String, ChannelHandlerContext>() ;
}
