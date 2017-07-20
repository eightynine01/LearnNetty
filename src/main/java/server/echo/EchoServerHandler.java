package server.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * Created by TaeHwan
 * 2017. 7. 19. PM 4:23
 */
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        StringBuilder builder = new StringBuilder();
        builder.append("수신한 문자열 [");
        builder.append(msg.toString());
        builder.append(']');
//        String message = "수신한 문자열 [" + ((ByteBuf) msg).toString(Charset.defaultCharset()) + ']';
        log.info(builder.toString());
        ctx.write(builder.toString());
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
