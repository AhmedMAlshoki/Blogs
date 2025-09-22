package com.example.Blogs.Config;

import com.example.Blogs.AuthenticationProviders.CustomAuthenticationProvider;
import com.example.Blogs.ExceptionHandler.GraphQLFilterExceptionHandler;
import com.example.Blogs.Filters.*;
import com.example.Blogs.Services.Security.UserDetailsImpl;
import com.example.Blogs.Services.Security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailsServiceImpl  userDetailsService;
    @Autowired
    private RequestCachingFilter requestCachingFilter;
    @Autowired
    private GraphQLFilterExceptionHandler graphQLFilterExceptionHandler;
    @Autowired
    private ClientApiExtractionFilter clientApiExtractionFilter;
    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean EmailPasswordAuthenticationFilter emailPasswordAuthenticationFilter() {
        return new EmailPasswordAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->{
                            auth.requestMatchers("/graphql", "/graphiql").authenticated();
                            auth.anyRequest().permitAll();
                        }
                ).httpBasic(withDefaults());
        http.addFilterBefore(exceptionHandlerFilter, CorsFilter.class);
        http.addFilterAfter(requestCachingFilter, ExceptionHandlerFilter.class);
        http.addFilterAt(emailPasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(clientApiExtractionFilter, JwtAuthenticationFilter.class);
        return http.build();
    }
}
