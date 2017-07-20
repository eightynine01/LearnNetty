package server.secure;

import io.netty.handler.codec.json.JsonObjectDecoder;

/**
 * Created by TaeHwan
 * 2017. 7. 20. PM 1:52
 */
public interface SecurityApiRequest {
//    void requestParamValidation() throws RequestParamException;
//    void service() throws ServiceException;
    void executeService();
    JsonObjectDecoder getApiResult();
}
