package server.echo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by TaeHwan
 * 2017. 7. 20. PM 4:58
 */
@Slf4j
public class NonBlockingServer {
    private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>();
    private ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);
    private void startEchoServer(){
        try(
                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open())
        {
            if((serverSocketChannel.isOpen()) && (selector.isOpen())){
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.bind(new InetSocketAddress(8888));
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                log.info("접속 대기중");
                while (true){
                    selector.select();
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()){
                        SelectionKey key = keys.next();
                        keys.remove();
                        if(!key.isValid())
                            continue;
                        if(key.isAcceptable())
                            this.acceptOP(key, selector);
                        else if (key.isReadable())
                            this.readOP(key);
                        else if (key.isWritable())
                            this.writeOP(key);
                    }
                }

            }
            else
                log.info("서버 소켓을 생성하지 못했습니다");


        }
        catch (IOException ex){
            log.error("error : ", ex);
        }
    }

    private void writeOP(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        List<byte[]> channelData = keepDataTrack.get(socketChannel);
        Iterator<byte[]> its = channelData.iterator();

        while (its.hasNext()){
            byte[] it = its.next();
            its.remove();
            socketChannel.write(ByteBuffer.wrap(it));
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    private void readOP(SelectionKey key){
        try{
            SocketChannel socketChannel = (SocketChannel) key.channel();
            buffer.clear();
//            int numRead = -1;

            int numRead = socketChannel.read(buffer);

//            catch (IOException e) {
//                log.error("데이터 읽기 에러");
//            }
            if(numRead == -1){
                this.keepDataTrack.remove(socketChannel);
                log.info("클라이언트 연결 종료 : " + socketChannel.getRemoteAddress());
                socketChannel.close();
                key.cancel();
                return;
            }
            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(), 0, data, 0, numRead);
            log.info(new String(data, "UTF-8") + " from " + socketChannel.getRemoteAddress());

            doEchoJob(key,data);
        }catch (IOException ex){
            log.error(String.valueOf(ex));
        }
    }

    private void doEchoJob(SelectionKey key, byte[] data) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        List<byte[]> channelData = keepDataTrack.get(socketChannel);
        channelData.add(data);
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void acceptOP(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        log.info("클라이언트 연결됨 : " + socketChannel.getRemoteAddress());

        keepDataTrack.put(socketChannel, new ArrayList<>());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        NonBlockingServer main = new NonBlockingServer();
        main.startEchoServer();
    }
}
