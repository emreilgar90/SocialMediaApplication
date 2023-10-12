package com.emreilgar.config.security;

import com.emreilgar.exception.AuthManagerException;
import com.emreilgar.exception.ErrorType;
import com.emreilgar.utillity.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired  //construcktur boş olmalı
    private JwtUserDetails jwtUserDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader= request.getHeader("Authorization");

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token= authorizationHeader.substring(7); //gelen header da substring ile ayırıyoruz
            Optional<Long> id = jwtTokenManager.getUserId(token);  //jwt manager ile token dan id yi ayırıyoruz
            if(id.isPresent()){
                UserDetails userDetails= jwtUserDetails.loadUserById(id.get());

                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); //ürettiğim authenticationToken'ı içerisine verdim.
            }else{
                throw new AuthManagerException(ErrorType.INVALID_TOKEN);
            }
        }
        filterChain.doFilter(request,response);

    }
}
