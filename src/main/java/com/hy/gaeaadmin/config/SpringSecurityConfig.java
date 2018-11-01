/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.config;

import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ndry93
 */
@Configuration
public class SpringSecurityConfig {}
//        extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
//
//    // roles admin allow to access /admin/**
//    // roles user allow to access /user/**
//    // custom 403 access denied handler
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//                .authorizeRequests()
//					.antMatchers("/", "/home", "/about").permitAll()
//					.antMatchers("/admin/**").hasAnyRole("ADMIN")
//					.antMatchers("/user/**").hasAnyRole("USER")
//					.anyRequest().authenticated()
//                .and()
//                .formLogin()
//					.loginPage("/login")
//					.permitAll()
//					.and()
//                .logout()
//					.permitAll()
//					.and()
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
//    }
//
//    // create two users, admin and user
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("password").roles("ADMIN");
//    }
//}