package server.secure;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * Created by TaeHwan
 * 2017. 7. 20. PM 1:17
 */
public class SecurityApiServerInitializer extends ChannelInitializer<SocketChannel>{
    private final SslContext sslCtx;
    public SecurityApiServerInitializer(SslContext sslCtx){
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch){
        ChannelPipeline p = ch.pipeline();
        if(sslCtx != null){
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpRequestDecoder());
        p.addLast(new HttpObjectAggregator(65536));
        p.addLast(new HttpResponseEncoder());
        p.addLast(new HttpContentCompressor());
        p.addLast(new SecurityApiRequestParser());
    }
}
