package org.zerock.api01.controller.filter;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.api01.controller.filter.exception.AccessTokenException;
import org.zerock.api01.util.JWTUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        //다른 경로인 경우에는 패스
        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("=================================");
        log.info("Token Check Filter...............");
        log.info("---------------------------------");

        log.info("Token check.................");

        try {
            //토큰 검증등이 필요한 부분
            validateAccessToken(request);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());

        }

    }

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {

        String headerStr = request.getHeader("Authorization");

        if(headerStr == null  || headerStr.length() < 8){
            throw new AccessTokenException("Token length too short");
        }


        //Bearer 생략
        String tokenType = headerStr.substring(0,6);
        String tokenStr =  headerStr.substring(7);
        if(tokenType.equalsIgnoreCase("Bearer") == false){
            throw new AccessTokenException("Bad Type Token");
        }

        try{
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);

            return values;
        }catch(JwtException jwtException){
            log.error("---------------------------");
            jwtException.printStackTrace(); //예외 확인을 위해서 임시로
            throw new AccessTokenException(jwtException.getMessage());
        }

    }

}
