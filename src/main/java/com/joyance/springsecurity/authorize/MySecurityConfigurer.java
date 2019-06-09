package com.joyance.springsecurity.authorize;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MySecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private SystemUserSerivce userDetailsService;
	
    @Autowired
    private AuthenticationProvider securityProvider;
    
    @Override
    protected UserDetailsService userDetailsService() {
        //自定义用户信息类
        return this.userDetailsService;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义AuthenticationProvider  
        auth.authenticationProvider(securityProvider);
    }
    

    /**
     * 重写该方法，设定用户访问权限
     * 用户身份可以访问 订单相关API
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//        .antMatchers("/orders/**").hasAnyAuthority("USER")    //用户权限
        .antMatchers("/users/**").hasAnyAuthority("ADMIN")    //管理员权限
        .and()
        .formLogin()
        .loginPage("/login")    //跳转登录页面的控制器，该地址要保证和表单提交的地址一致！
        //成功处理
        .successHandler(new AuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
                    throws IOException, ServletException {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal != null && principal instanceof UserDetails) {
                    UserDetails user = (UserDetails) principal;
                    System.out.println("loginUser:"+user.getUsername());
                    //维护在session中
                    arg0.getSession().setAttribute("userDetail", user);
                    arg1.sendRedirect("/orders/findAll");
                } 
            }
        })
        //失败处理
        .failureHandler(new AuthenticationFailureHandler() {
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
                    throws IOException, ServletException {
                System.out.println("error"+authenticationException.getMessage());
                response.sendRedirect("/login");
            }
        })
        .permitAll()
        .and()
        .logout()
        .permitAll()
        .and()
        .csrf().disable();        //暂时禁用CSRF，否则无法提交表单
    }
    
//    /**
//     * 重写该方法，设定用户访问权限
//     * 用户身份可以访问 订单相关API
//     * */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//        .antMatchers("/orders/**").hasRole("USER")    //用户权限
//        .antMatchers("/users/**").hasRole("ADMIN")    //管理员权限
//        .antMatchers("/login").permitAll()
//        .and()
//        .formLogin();
//        
//        //super.configure(http);
//    }
//
//    /**
//     * 重写该方法，添加自定义用户
//     * */
//    @SuppressWarnings("deprecation")
//	@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//        .passwordEncoder(NoOpPasswordEncoder.getInstance())
//        .withUser("admin").password("admin").roles("ADMIN","USER")
//        .and()
//        .withUser("terry").password("terry").roles("USER")
//        .and()
//        .withUser("larry").password("larry").roles("USER");
//    }
}
