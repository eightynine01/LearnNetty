package server.secure;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Created by TaeHwan
 * 2017. 7. 20. PM 1:13
 */
@Component
public final class SecuriyApiServer {

    private final InetSocketAddress address;
    private final int workerThreadCount;
    private final int bossThreadCount;

    @Autowired
    public SecuriyApiServer(int bossThreadCount, int workerThreadCount, InetSocketAddress address) {
        this.bossThreadCount = bossThreadCount;
        this.workerThreadCount = workerThreadCount;
        this.address = address;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreadCount);
        ChannelFuture channelFuture;

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new SecurityApiServerInitializer(null));

            Channel ch = b.bind(8080).sync().channel();
            channelFuture = ch.closeFuture();
            channelFuture.sync();

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
