package com.nnk.springboot.config.security;

import com.nnk.springboot.filter.JwtRequestFilter;
import com.nnk.springboot.services.impl.AuthenticationUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationUserDetailService authenticationUserDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtRequestFilter jwtRequestFilter;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .addFilterBefore(jwtRequestFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/css/**", "/login", "/authenticate", "/api/**","/actuator/**", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .logout()
                .logoutUrl("/logout");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler(){

            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                response.sendRedirect(request.getContextPath() + "/error");
            }
        };
    }
}
