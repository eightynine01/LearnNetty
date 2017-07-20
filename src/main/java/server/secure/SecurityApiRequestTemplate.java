package server.secure;

import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by TaeHwan
 * 2017. 7. 20. PM 1:56
 */
@Slf4j
public abstract class SecurityApiRequestTemplate implements SecurityApiRequest{
//    protected Logger logger;
    protected Map<String ,String> reqData;
    protected JsonObjectDecoder apiResult;

    public SecurityApiRequestTemplate(Map<String ,String > reqData){
        this.apiResult = new JsonObjectDecoder();
        this.reqData = reqData;
        log.info("request data : " + this.reqData);
    }

    public void executeService(){
//        this.requestParamValidation();
//        this.service();
    }
    public JsonObjectDecoder getApiResult(){
        return this.apiResult;
    }
}
