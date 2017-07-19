package server.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by TaeHwan
 * 2017. 7. 19. PM 12:59
 */
public class DiscardServer {
//    private int port;
//
//    public DiscardServer(int port) {
//        this.port = port;
//    }
//
//    public void run() throws Exception {
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//        ServerBootstrap b = new ServerBootstrap();
//        b.group(bossGroup, workerGroup)
//                .channel(NioServerSocketChannel.class)
//                .handler(new LoggingHandler((LogLevel.INFO)))
//                .childHandler(new DiscardServerHandler());
//
////                .childHandler(new ChannelInitializer<SocketChannel>(){
////                    @Override
////                    public void initChannel(SocketChannel ch) throws Exception {
////                        ch.pipeline().addLast(new DiscardServerHandler());
////                    }
////                })
////                .option(ChannelOption.SO_BACKLOG, 128)
////                .childOption(ChannelOption.SO_KEEPALIVE, true);
//
//        // Bind and start to accept incoming connections.
//        ChannelFuture f = b.bind(port).sync();
//
//        // Wait until the server socket is closed.
//        // In this example, this does not happen, but you can do that to gracefully
//        // shut down your server.
//        f.channel().closeFuture().sync();
//
//        workerGroup.shutdownGracefully();
//        bossGroup.shutdownGracefully();
//    }

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler((LogLevel.INFO)))
                .childHandler(new DiscardServerHandler());

//                .childHandler(new ChannelInitializer<SocketChannel>(){
//                    @Override
//                    public void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new DiscardServerHandler());
//                    }
//                })
//                .option(ChannelOption.SO_BACKLOG, 128)
//                .childOption(ChannelOption.SO_KEEPALIVE, true);

        // Bind and start to accept incoming connections.
        ChannelFuture f = b.bind(8080).sync();

        // Wait until the server socket is closed.
        // In this example, this does not happen, but you can do that to gracefully
        // shut down your server.
        f.channel().closeFuture().sync();

        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
//        int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8080;
//        }
//        new DiscardServer(port).run();
    }

}
