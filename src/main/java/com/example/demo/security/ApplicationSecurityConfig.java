package com.example.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.demo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/" , "index" , "/css/*" , "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/courses" , true)
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                .rememberMe() //Default is 2 weeks.....
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("Thisisthekeytobeused")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout" , "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID" , "remember-me")
                    .logoutSuccessUrl("/login");

    }

    @Override
    @Bean
    protected org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {

        UserDetails torresUser = User.builder()
                .username("torres")
                .password(passwordEncoder.encode("torres"))
                .roles(STUDENT.name())
                .build();

        UserDetails kiranUser = User.builder()
                .username("kiran")
                .password(passwordEncoder.encode("kiran"))
                .roles(ADMIN.name())
                .build();

        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("tom"))
                .roles(ADMINTRAINEE.name())
                .build();

        return new InMemoryUserDetailsManager(
                torresUser,
                kiranUser,
                tomUser
        );




    }
}
