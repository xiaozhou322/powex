package com.gt.websocket;

import org.springframework.stereotype.Service;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * ClassName:ChildChannelHandler初始化连接时的各个组件 Function: TODO ADD FUNCTION.
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel e) throws Exception {
		// 设置30秒没有读到数据，则触发一个READER_IDLE事件。
		// pipeline.addLast(new IdleStateHandler(30, 0, 0));
		// HttpServerCodec：将请求和应答消息解码为HTTP消息
		e.pipeline().addLast("http-codec", new HttpServerCodec());
		// HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
		e.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
		// ChunkedWriteHandler：向客户端发送HTML5文件
		e.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
		// 在管道中添加我们自己的接收数据实现方法
		e.pipeline().addLast("handler", new MyWebSocketServerHandler());
	}
}
