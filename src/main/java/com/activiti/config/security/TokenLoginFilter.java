//package com.activiti.config.security;
//
//
//import com.activiti.entity.SysUser;
//import com.cff.springbootwork.token.manager.TokenManager;
//import com.cff.springbootwork.token.model.LoginUserReq;
//import com.cff.springbootwork.token.model.LoginUserRes;
//import com.cff.springbootwork.token.model.ResultCode;
//import com.cff.springbootwork.token.model.ResultModel;
//import com.cff.springbootwork.token.model.TokenUserDetails;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.ProviderNotFoundException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
//    private AuthenticationManager authenticationManager;
//    private TokenManager tokenManager;
//
//    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager) {
//        this.authenticationManager = authenticationManager;
//        this.tokenManager = tokenManager;
//        this.setPostOnly(false);
//        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login"));
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
//            throws AuthenticationException {
//        try {
//            SysUser user = new ObjectMapper().readValue(req.getInputStream(), SysUser.class);
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), new ArrayList<>()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
//            Authentication auth) throws IOException, ServletException {
//        TokenUserDetails user = (TokenUserDetails) auth.getPrincipal();
//
//        String token = tokenManager.createToken(user.getUsername());
//
//        SysUser loginUserRes = new SysUser();
//        loginUserRes.setUsername(user.getUsername());
//        loginUserRes.setToken(token);
//        ResultModel rm = new ResultModel(ResultCode.CODE_00000);
//        rm.setData(loginUserRes);
//
//        ObjectMapper mapper = new ObjectMapper();
//        res.setStatus(HttpStatus.OK.value());
//        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        mapper.writeValue(res.getWriter(), rm);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//            AuthenticationException e) throws IOException, ServletException {
//        response.setStatus(HttpStatus.OK.value());
//        ObjectMapper mapper = new ObjectMapper();
//        ResultModel rm = null;
//        if (e instanceof BadCredentialsException) {
//            rm = new ResultModel(ResultCode.CODE_00001.getCode(), e.getMessage());
//        } else if (e instanceof UsernameNotFoundException) {
//            rm = new ResultModel(ResultCode.CODE_00011);
//        } else if (e instanceof AuthenticationCredentialsNotFoundException) {
//            rm = new ResultModel(ResultCode.CODE_00003);
//        } else if (e instanceof ProviderNotFoundException) {
//            rm = new ResultModel(ResultCode.CODE_10000, e.getMessage());
//        } else {
//            rm = new ResultModel(ResultCode.CODE_00013);
//        }
//
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        mapper.writeValue(response.getWriter(), rm);
//
//    }
//}
