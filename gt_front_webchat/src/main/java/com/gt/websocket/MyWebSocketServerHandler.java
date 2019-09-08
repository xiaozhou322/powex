package com.gt.websocket;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gt.entity.FotcOrder;
import com.gt.entity.FotcWebChat;
import com.gt.service.front.FotcOrderService;
import com.gt.service.front.FotcWebChatService;
import com.gt.util.Utils;
import com.gt.util.common.MapUtil;
import com.gt.util.springbean.SpringBeanUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

/**
 * ClassName:MyWebSocketServerHandler Function: TODO ADD FUNCTION.
 */
public class MyWebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
	
	private static final Logger logger = Logger.getLogger(WebSocketServerHandshaker.class.getName());
	
	private WebSocketServerHandshaker handshaker;
	
	private FotcOrderService fotcOrderService = (FotcOrderService) SpringBeanUtil.getBeanByName("fotcOrderService");
	private FotcWebChatService fotcWebChatService = (FotcWebChatService) SpringBeanUtil.getBeanByName("fotcWebChatService");
	
	/**
	 * channel 通道 action 活跃的
	 * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 添加
		Global.group.add(ctx.channel());
		System.out.println("客户端与服务端连接开启：" + ctx.channel().remoteAddress().toString());
	}

	/**
	 * channel 通道 Inactive 不活跃的
	 * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端关闭了通信通道并且不可以传输数据
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// 移除
		Global.group.remove(ctx.channel());
		System.out.println("客户端与服务端连接关闭：" + ctx.channel().remoteAddress().toString());
		//剔除pushCtxMap中的channel
		for(String key : Global.pushCtxMap.keySet()){
			if(ctx.equals(Global.pushCtxMap.get(key))){
				//从连接池内剔除
				System.out.println(Global.pushCtxMap.size());
				System.out.println("剔除"+key);
				Global.pushCtxMap.remove(key);
				System.out.println(Global.pushCtxMap.size());
			}
		}
	}

	/**
	 * 接收客户端发送的消息 channel 通道 Read 读
	 * 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据。但是这个数据在不进行解码时它是ByteBuf类型的
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 传统的HTTP接入
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, ((FullHttpRequest) msg));
			// WebSocket接入
		} else if (msg instanceof WebSocketFrame) {
			
			System.out.println(handshaker.uri());
			
			if ("anzhuo".equals(ctx.attr(AttributeKey.valueOf("type")).get())) {
				handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
			}else {
				handlerWebSocketFrame2(ctx, (WebSocketFrame) msg);
			}
		}
	}

	/**
	 * channel 通道 Read 读取 Complete 完成 在通道读取完成后会在这个方法里通知，对应可以做刷新操作 ctx.flush()
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// 判断是否关闭链路的指令
		if (frame instanceof CloseWebSocketFrame) {
			System.out.println(1);
			handshaker.close(ctx.channel(),
					(CloseWebSocketFrame) frame.retain());
			return;
		}
		// 判断是否ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(
					new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			System.out.println("本例程仅支持文本消息，不支持二进制消息");
			throw new UnsupportedOperationException(String.format( "%s frame types not supported", frame.getClass().getName()));
		}
		// 返回应答消息
		String request = ((TextWebSocketFrame) frame).text();
		System.out.println("服务端收到：" + request);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine(String.format("%s received %s", ctx.channel(), request));
		}
		TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString() + ctx.channel().id() + "：" + request);
		// 群发
		Global.group.writeAndFlush(tws);
		// 返回【谁发的发给谁】
		// ctx.channel().writeAndFlush(tws);
		
		Global.pushCtxMap.put(request, ctx);
	}

	private void handlerWebSocketFrame2(final ChannelHandlerContext ctx, WebSocketFrame frame) {
		// 判断是否关闭链路的指令
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		// 判断是否ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			System.out.println("本例程仅支持文本消息，不支持二进制消息");
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
		}
		// 返回应答消息
		String request = ((TextWebSocketFrame) frame).text();
		System.out.println("服务端2收到：" + request);
		if (logger.isLoggable(Level.INFO)) {
			logger.fine(String.format("%s received %s", ctx.channel(), request));
		}
		try {
			JSONObject jsonObj = JSONObject.parseObject(request);
			String reqCmd = jsonObj.getString("cmd");
			
			if(reqCmd.equals("getmsg")){
				int order_id = jsonObj.getIntValue("id");
				
				//先剔除以ctx+":order_id:"开头的channel，再添加channel
				List<String> strList = MapUtil.findKeysByLikeString(ctx + ":order_id:" + order_id, Global.pushCtxMap);
				if(null != strList && strList.size() > 0){
					Global.pushCtxMap.remove(strList.get(0));
				}
				Global.pushCtxMap.put(ctx + ":order_id:" + order_id, ctx);
				
				List<FotcWebChat> fotcWebChats = fotcWebChatService.findAllFotcWebChat("orderId", order_id);
				if(null == fotcWebChats || fotcWebChats.size() == 0){
					FotcOrder fotcOrder = fotcOrderService.queryById(order_id);
					if(null!=fotcOrder.getFotcAdvertisement().getRemark() && !"".equals(fotcOrder.getFotcAdvertisement().getRemark())){
						String msg = fotcOrder.getFotcAdvertisement().getRemark();
						fotcWebChats = new ArrayList<FotcWebChat>();
						FotcWebChat fotcWebChat = new FotcWebChat();
						fotcWebChat.setOrderId(order_id);
						fotcWebChat.setFromId(fotcOrder.getFotcAdvertisement().getUser().getFid());
						fotcWebChat.setToId(fotcOrder.getFuser().getFid());
						fotcWebChat.setContentType(1);
						fotcWebChat.setMsgType(2);
						fotcWebChat.setContent(msg);
						fotcWebChat.setSendTime(Utils.getTimestamp());
						fotcWebChatService.save(fotcWebChat);
						fotcWebChats.add(fotcWebChat);
					}
				}
				jsonObj.put("args", fotcWebChats); 
				String result = jsonObj.toJSONString();
				
				TextWebSocketFrame tws = new TextWebSocketFrame(result);
				ctx.channel().writeAndFlush(tws);
			}else if(reqCmd.equals("sendmsg")) {
				JSONArray reqArgs = jsonObj.getJSONArray("args");
				int from = reqArgs.getIntValue(0);
				int to = reqArgs.getIntValue(1);
				int type = reqArgs.getIntValue(2);
				long date = reqArgs.getLongValue(3);
				String content = reqArgs.getString(4);
				
				int order_id = jsonObj.getIntValue("id");
				
				//先剔除以ctx+":order_id:"开头的channel，再添加channel
				List<String> strList = MapUtil.findKeysByLikeString(ctx + ":order_id:" + order_id, Global.pushCtxMap);
				if(null != strList && strList.size() > 0){
					Global.pushCtxMap.remove(strList.get(0));
				}
				Global.pushCtxMap.put(ctx + ":order_id:" + order_id, ctx);
				
				FotcWebChat fotcWebChat = new FotcWebChat();
				fotcWebChat.setOrderId(order_id);
				fotcWebChat.setFromId(from);
				fotcWebChat.setToId(to);
				fotcWebChat.setContentType(type);
				fotcWebChat.setMsgType(1);
				fotcWebChat.setContent(content);
				fotcWebChat.setSendTime(new Timestamp(date));
				if(fotcWebChatService.save(fotcWebChat) > -1){
					for(String key : Global.pushCtxMap.keySet()){
						TextWebSocketFrame tws = new TextWebSocketFrame(request).retain();
						System.out.println(key+":"+Global.pushCtxMap.get(key));
						if(key.endsWith("order_id:" + order_id)){
							Global.pushCtxMap.get(key).writeAndFlush(tws);
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		// 如果HTTP解码失败，返回HHTP异常
		if (!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
		// 获取url后置参数
		HttpMethod method = req.getMethod();
		String uri = req.getUri();
		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
		
		Map<String, List<String>> parameters = queryStringDecoder.parameters();
		
		//System.out.println(parameters.get("request").get(0));
		if (method == HttpMethod.GET && "/webssss".equals(uri)) {
			// ....处理
			ctx.attr(AttributeKey.valueOf("type")).set("anzhuo");
		} else if (method == HttpMethod.GET && "/websocket".equals(uri)) {
			// ...处理
			ctx.attr(AttributeKey.valueOf("type")).set("live");
		} else if (method == HttpMethod.GET && "/fulldepth".equals(uri)) {
			// ...处理
			ctx.attr(AttributeKey.valueOf("type")).set("fulldepth");
		} else if (method == HttpMethod.GET && "/market2".equals(uri)) {
			// ...处理
			ctx.attr(AttributeKey.valueOf("type")).set("market2");
		}
		
		// 构造握手响应返回，本机测试
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://" + req.headers().get(HttpHeaders.Names.HOST) + uri,
				null, false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx,
			FullHttpRequest req, DefaultFullHttpResponse res) {
		// 返回应答给客户端
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
		}
		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	/**
	 * exception 异常处理，比如打印日志、关闭链接
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	/**
	 * 单发
	 * */
	public static final void push(final ChannelHandlerContext ctx,final String message){	
		TextWebSocketFrame tws = new TextWebSocketFrame(message);
		ctx.channel().writeAndFlush(tws);
	}
	
	/**
	 * 群发
	 * */
	public static final void push(final ChannelGroup group,final String message){
		TextWebSocketFrame tws = new TextWebSocketFrame(message);
		group.writeAndFlush(tws);
	}
	
	
}
