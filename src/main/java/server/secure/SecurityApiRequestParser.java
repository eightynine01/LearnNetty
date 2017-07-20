package server.secure;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by TaeHwan
 * 2017. 7. 20. PM 1:24
 */
@Slf4j
public class SecurityApiRequestParser extends SimpleChannelInboundHandler<FullHttpMessage>{
    private HttpRequest request;
    private JsonObjectDecoder apiResult;

    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
    private HttpPostRequestDecoder decoder;

    private Map<String ,String> reqData = new HashMap<>();
    private static final Set<String > usingHeader = new HashSet<>();

    static{
        usingHeader.add("token");
        usingHeader.add("email");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) throws Exception {
        if(msg instanceof HttpRequest){
            this.request = (HttpRequest) msg;

            if(HttpUtil.is100ContinueExpected(request)){
//                send100Continue(ctx);
            }

            HttpHeaders headers = request.headers();
            if (!headers.isEmpty()){
                for (Map.Entry<String ,String > h: headers){
                    String key = h.getKey();
                    if(usingHeader.contains(key)){
                        reqData.put(key, h.getValue());
                    }
                }
            }
            reqData.put("REQUEST_URI", request.uri());
            reqData.put("REQUEST_METHOD", request.method().name());
        }
        if (msg instanceof HttpContent){
            HttpContent httpContent = msg;
            ByteBuf content = httpContent.content();

            if (msg instanceof LastHttpContent){
                log.debug("LastHttpContent message received!!" + request.uri());
                LastHttpContent trailer = msg;
                readPostData();
                SecurityApiRequest service = ServiceDispatcher.dispatch(reqData);

                service.executeService();
                apiResult = service.getApiResult();

                reqData.clear();
            }
//            if (!writeResponse(trailer, ctx)){
//                ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
//                        .addListener(ChannelFutureListener.CLOSE);
//            }
//            reset();
        }

    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        log.info("요청 처리 완료");
        ctx.flush();
    }
    private void readPostData() throws IOException {
        decoder = new HttpPostRequestDecoder(factory, request);
        for(InterfaceHttpData data: decoder.getBodyHttpDatas()){
            if(InterfaceHttpData.HttpDataType.Attribute == data.getHttpDataType()){
                Attribute attribute = (Attribute) data;
                reqData.put(attribute.getName(), attribute.getValue());
            } else
                log.info("BODY data : " + data.getHttpDataType().name() + ": " + data);
        }
        if(decoder != null){
            decoder.destroy();
        }
    }
}
