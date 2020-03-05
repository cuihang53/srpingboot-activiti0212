//package com.activiti.config.security;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//
//import com.activiti.common.JsonResult;
//import com.activiti.common.ResultCode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class TokenLogoutHandler implements LogoutHandler {
//    private TokenManager tokenManager;
//
//    public TokenLogoutHandler(TokenManager tokenManager) {
//        this.tokenManager = tokenManager;
//    }
//
//    @Override
//    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        String token = request.getHeader("token");
//        if (token != null) {
//            tokenManager.removeToken(token);
//        }
//
//        JsonResult<?> result = new JsonResult<>(true,ResultCode.OK);
//        
//        
//        ObjectMapper mapper = new ObjectMapper();
//        response.setStatus(HttpStatus.OK.value());
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        try {
//            mapper.writeValue(response.getWriter(), result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
