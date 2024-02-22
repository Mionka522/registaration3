package com.example.registration.config;

import com.example.registration.service.MyUserDetailsService;
import org.apache.catalina.Context;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//Аннотация,позволяющая понять спрингу,что это кофигурационный класс для Spring Security
public class SecurityConfig{

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
}
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth

                    .requestMatchers("/","/static/**","/error","/auth/**").permitAll()
                    .requestMatchers("/user/**").authenticated())

            .formLogin(formLogin -> {
                        formLogin
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/process_login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .permitAll()
                                .defaultSuccessUrl("/user/profile", true)
                                .failureUrl("/auth/login?error=true")
                                .failureUrl("/auth/login?error");
                    }
            )
            .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/auth/login"))


            .build();
}

@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(passwordEncoder());

    return provider;
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ServletWebServerFactory servletContainer() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                Rfc6265CookieProcessor rfc6265Processor = new Rfc6265CookieProcessor();
                rfc6265Processor.setSameSiteCookies("None");
                context.setCookieProcessor(rfc6265Processor);
            }
        };
    }
}
