package server.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by TaeHwan
 * 2017. 7. 19. PM 4:15
 */
//public class ChatServerHandler extends SimpleChannelInboundHandler<ChatMessage> {
//    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//    public void channelActive(ChannelHandlerContext ctx) {
//        helo(ctx.channel());
//    }
//
//    public void channelInactive(final ChannelHandlerContext ctx) {
//        channels.remove(ctx.channel());
//        channels.writeAndFlush(M("LEFT", nickname(ctx)));
//        nicknameProvider.release(nickname(ctx));
//    }
//    public void helo(Channel ch) {
//        if (nickname(ch) != null) return; // already done;
//        String nick = nicknameProvider.reserve();
//        if (nick == null) {
//            ch.writeAndFlush(M("ERR", "sorry, no more names for you"))
//                    .addListener(ChannelFutureListener.CLOSE);
//        } else {
//            bindNickname(ch, nick);
//            channels.forEach(c -> ch.write(M("HAVE", nickname(c))));
//            channels.writeAndFlush(M("JOIN", nick));
//            channels.add(ch);
//            ch.writeAndFlush(M("HELO", nick));
//        }
//    }
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, ChatMessage msg) throws Exception {
//        if ("PING".equals(msg.command)) {
//            // TODO: [실습3-1] PING 명령어에 대한 응답을 내보냅니다
//        } else if ("QUIT".equals(msg.command)) {
//            // TODO: [실습3-2] QUIT 명령어를 처리하고 BYE를 응답합니다. 연결도 끊습니다.
//        } else if ("SEND".equals(msg.command)) {
//            // TODO: [실습3-3] 클라이언트로부터 대화 텍스트가 왔습니다. 모든 채널에 방송합니다.
//        } else if ("NICK".equals(msg.command)) {
//            changeNickname(ctx, msg);
//        } else {
//            ctx.write(M("ERR", null, "unknown command -> " + msg.command));
//        }
//    }
//}
