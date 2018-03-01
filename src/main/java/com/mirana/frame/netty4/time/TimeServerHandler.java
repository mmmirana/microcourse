package com.mirana.frame.netty4.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 时间服务器
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年12月13日上午1:58:45
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive (final ChannelHandlerContext ctx) { // (1)
		final ByteBuf time = ctx.alloc().buffer(4); // (2)
		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

		final ChannelFuture f = ctx.writeAndFlush(time); // (3)
		// 1、关闭 Channel
		// f.addListener(ChannelFutureListener.CLOSE);
		// 2、关闭 Channel
		f.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete (ChannelFuture future) {
				assert f == future;
				ctx.close();
			}
		}); // (4)
	}

	@Override
	public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}