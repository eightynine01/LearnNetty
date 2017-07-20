package server.secure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.net.InetSocketAddress;

/**
 * Created by TaeHwan
 * 2017. 7. 20. PM 1:07
 */
@Configuration
public class SecurityApiServerConfig{
    private int bossThreadCount = 1;
    private int workerThreadCount = 10;
    private int tcpPort = 8080;

    @Bean
    public int getBossThreadCount(){
        return bossThreadCount;
    }

    @Bean
    public int getWorkerThreadCount(){
        return workerThreadCount;
    }

    public int getTcpPort(){
        return tcpPort;
    }

    @Bean
    public InetSocketAddress tcpPort(){
        return new InetSocketAddress(tcpPort);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
