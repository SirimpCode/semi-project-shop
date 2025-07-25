package com.github.semiprojectshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
//웹요청과 응답이 시큐리티 필터체인을 거치게 해줌
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //악의적인 요청 방지 (토큰을 사용하여 같이 요청이들어와야함) 하지만 jwt 사용할것이므로 disable
                .csrf(c->c.disable())
//                .httpBasic(h->h.disable())
                //이 설정은 현재 페이지의 출처와 동일한 출처를 가진 프레임만 로드될 수 있도록
                //응답헤더에 x frame options 설정 클릭재킹 공격 방지
                .headers(h->h.frameOptions(f->f.sameOrigin()))
                .cors(c->c.configurationSource(corsConfigurationSource()))
//                // 보통 RESTful API에서 사용되며, JWT(Json Web Token)와 같은 토큰 기반의 인증 방식을 사용할 때 유용
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(e->{
//                    e.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//                    e.accessDeniedHandler(new CustomAccessDeniedHandler());
//                })
                .authorizeHttpRequests(a->a
                        .anyRequest().permitAll()
//                        .requestMatchers("/api/auth/authorize-test").hasRole("ADMIN")
//                        .requestMatchers("/api/auth/auth-test", "/api/account/*").authenticated()
//                        .requestMatchers("/","/index.html","/resources/**","/api/auth/*", "/api/email/*",
//                                "/error","/swagger-ui/**", "/v3/api-docs/**", "/amp-docs.html").permitAll()
//                        .requestMatchers("/api/oauth/**").anonymous()
//                        .anyRequest().authenticated()
                )
//                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)//인증이전 실행
                .build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        //set과 add의 차이 add는 하나씩추가 set은 통째로 설정
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:*"));
        //응답에 노출되는 헤더
        corsConfiguration.addExposedHeader("Authorization");
        //요청에 허용되는 헤더
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowedMethods(List.of("GET","PUT","POST","PATCH","DELETE","OPTIONS"));
        //사전요청 캐시 시간 보통 1시간으로 설정함
        corsConfiguration.setMaxAge(1000L*60*60);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                // 정적클래스들을 security 가 무시할수 있도록 설정 그래도 필터는 실행이됨
//                .requestMatchers("/resources/**", "/api/auth/sign-up");
//    }


}
