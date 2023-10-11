package com.emreilgar.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthServiceSecurityConfig {

    @Bean //spring yöneteceği için Bean yaratıyoruz
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable();
        //2:53->19.01
        /**httpSecurity.authorizeHttpRequests().antMatchers().permitAll()-> içeride eşleşenlere izin ver*/
        /**.anyRequest().authenticated()-> bunları haricinde authenticated uygula */
        httpSecurity.authorizeHttpRequests().antMatchers("/v3/api-docs/**","/swagger-ui/**","/api/v1/auth/login"
                        ,"/api/v1/auth/register").permitAll() //-> buraya yazılan endpointleri dışarıya açtık
                .anyRequest().authenticated();

        return httpSecurity.build();

    }
}
