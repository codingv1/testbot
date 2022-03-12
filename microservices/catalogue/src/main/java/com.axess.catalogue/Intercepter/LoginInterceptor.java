package com.axess.catalogue.Intercepter;


import com.axess.catalogue.config.restapi.ApiErrorResponse;
import com.axess.catalogue.dto.TokenDto;
import com.axess.catalogue.exception.JupiterException;
import com.axess.catalogue.service.VerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *  LoginInterceptor
 *  @author  Yao
 *
 */

@Component
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    public VerifyService verifyService;
    /**
     * Called before request processing (before Controller method call)
     * URL-based interceptor
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        boolean flag = false;
        if (Const.NO_INTERCEPTOR_PATH.indexOf(path)>-1) {
            ////Do not need to intercept
            return true;
        } else {
            Map<String,String[]> reqMap= request.getParameterMap();
            log.info("request.getIntHeader:{}",request.getRemoteUser());
            log.info("request.getParameterMap:{}",reqMap.toString());
            if(!reqMap.containsKey("token")){
                ApiErrorResponse res = new ApiErrorResponse();
                res.setMessage("token is illegal");
                res.setHttpStatus(HttpStatus.BAD_REQUEST);
                res.setHttpStatusCode(400);
                res.setError(true);
                flag = false;
                throw new JupiterException(400,"need the token in request parameter");
            }else{
                TokenDto tokenDto = new TokenDto();
                tokenDto.setToken(reqMap.get("token")[0]);

                flag= verifyService.verifyToken(tokenDto);
                if(!flag){
                    throw new JupiterException(400,"token is not valid, pls login again! ");
                }
                return flag;

            }
        }
    }
}