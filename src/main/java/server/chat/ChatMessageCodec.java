package server.chat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.List;

/**
 * Created by TaeHwan
 * 2017. 7. 19. PM 4:11
 */
//public class ChatMessageCodec extends MessageToMessageCodec<String, ChatMessage> {
//    @Override
//    protected void encode(ChannelHandlerContext ctx, ChatMessage msg, List<Object> out){
//        out.add(msg.toString() + "\n");
//    }
//    @Override
//    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out){
//        out.add(ChatMessage.parse(line) + "\n");
//    }
//}
