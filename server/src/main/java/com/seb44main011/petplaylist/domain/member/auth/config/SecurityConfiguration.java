package com.seb44main011.petplaylist.domain.member.auth.config;

import com.seb44main011.petplaylist.domain.member.auth.handler.authErrorHandler.MemberAuthenticationEntryPoint;
import com.seb44main011.petplaylist.domain.member.auth.handler.oauthHandler.OAuth2SuccessHandler;
import com.seb44main011.petplaylist.domain.member.auth.jwt.DelegateTokenService;
import com.seb44main011.petplaylist.domain.member.auth.jwt.JwtTokenizer;
import com.seb44main011.petplaylist.domain.member.auth.util.service.CustomOAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomOAuth2MemberService oAuth2MemberService;
    private final DelegateTokenService delegateTokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .and()
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/public/oauth/login")
                        .userInfoEndpoint()
                        .userService(oAuth2MemberService)
                        .and()
                        .successHandler(new OAuth2SuccessHandler(delegateTokenService))
                )
                .apply(customFilterConfigurers())
                .and()
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .antMatchers("/public/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/api/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("USER")
                        .antMatchers("/api/members/**").authenticated()
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CustomFilterConfigurer customFilterConfigurers() {
        return new CustomFilterConfigurer(jwtTokenizer, delegateTokenService);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:3000", "http://localhost:5173"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}