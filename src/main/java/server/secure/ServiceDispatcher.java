package server.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by TaeHwan
 * 2017. 7. 20. PM 3:40
 */
@Component
public class ServiceDispatcher {
    private static ApplicationContext springContext;

    @Autowired
    public void init(ApplicationContext springContext){
        ServiceDispatcher.springContext = springContext;
    }

    public static SecurityApiRequest dispatch(Map<String ,String> requestMap){
        String serviceUri = requestMap.get("REQUEST_URI");
        String beanName = null;
        if(serviceUri == null)
            beanName = "notFound";
        if (serviceUri.startsWith("/tokens")){
            String httpMethod = requestMap.get("REQUEST_METHOD");
            switch (httpMethod){
                case "POST":
                    beanName = "tokenIssue"; break;
                case "DELETE":
                    beanName = "tokenExpier"; break;
                case "GET":
                    beanName = "tokenVerify"; break;
                default:
                    beanName = "notFound"; break;
            }
        }
        else if(serviceUri.startsWith("/users")){
            beanName = "users";
        }
        else {
            beanName = "notFound";
        }
        SecurityApiRequest service = null;

        return service = (SecurityApiRequest) springContext.getBean(beanName, requestMap);
    }
}
