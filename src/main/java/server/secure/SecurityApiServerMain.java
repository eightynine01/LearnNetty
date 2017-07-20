package server.secure;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import server.secure.config.SecurityApiServerConfig;

/**
 * Created by TaeHwan
 * 2017. 7. 19. PM 7:57
 */
public class SecurityApiServerMain {
    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext springContext = null;

        springContext = new AnnotationConfigApplicationContext(SecurityApiServerConfig.class);
        springContext.registerShutdownHook();

        SecuriyApiServer server = springContext.getBean(SecuriyApiServer.class);
        server.start();

        springContext.close();
    }
}
