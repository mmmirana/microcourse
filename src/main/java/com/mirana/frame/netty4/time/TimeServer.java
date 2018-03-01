package com.mirana.frame.netty4.time;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
public class TimeServer extends ChannelInboundHandlerAdapter { // (1)

	@Override
	public void channelRead (ChannelHandlerContext ctx, Object msg) { // (2)
		// 1、默默地丢弃收到的数据
		// ((ByteBuf) msg).release(); // (3)
		// ---------- 通常使用下面的方法释放引用计数对象 ----------
		try {
			// Do something with msg
		} finally {
			// ReferenceCountUtil.release(msg);
		}

		// 2、打印接收到的数据-------------------------------
		// ByteBuf in = (ByteBuf) msg;
		// try {
		// // while (in.isReadable()) { // (1)
		// // System.out.print((char) in.readByte());
		// // System.out.flush();
		// // }
		// // ---------- 打印 ----------
		// System.out.print(in.toString(io.netty.util.CharsetUtil.US_ASCII));
		// } finally {
		// ReferenceCountUtil.release(msg); // (2)
		// }

		// 3、服务器响应接收到的数据-------------------------------
		ctx.write(msg); // (1)
		ctx.flush(); // (2)
	}

	@Override
	public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) { // (4)
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}