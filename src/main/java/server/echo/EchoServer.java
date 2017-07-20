package server.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by TaeHwan
 * 2017. 7. 19. PM 4:22
 */
public final class EchoServer {
    // 서버 소켓 포트 번호를 지정합니다.
    private static final int PORT = 8080;

    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup(1);
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline cp = sc.pipeline();
                            cp.addLast(new EchoServerHandler());
                        }
                    });

            // 인커밍 커넥션을 액세스하기 위해 바인드하고 시작합니다.
            ChannelFuture cf = sb.bind(PORT).sync();

            // 서버 소켓이 닫힐때까지 대기합니다.
            cf.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }


}
