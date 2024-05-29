package coderookie.plogging.config;

import coderookie.plogging.filter.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors  // CORS 설정을 구성
                        .configurationSource(corsConfigurationSource())
                )
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호를 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)  // HTTP Basic 인증을 비활성화
                .sessionManagement(sessionManageMent -> sessionManageMent  // 세션 관리 설정을 구성
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션을 상태없음(stateless)으로 설정
                )
                .authorizeHttpRequests(request -> request  // HTTP 요청에 대한 인가 규칙을 설정
                        .requestMatchers("/", "/api/v1/auth/**", "/api/v1/search/**", "/file/**",
                                "/swagger-ui/**", "/v3/api-docs/**").permitAll()  // 특정 경로에 대한 접근을 허용
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/**", "/api/v1/user/*", "/check").permitAll()  // GET 메서드에 대한 특정 경로 접근을 허용
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/posts/*/viewCount").permitAll()
                        .anyRequest().authenticated()  // 그 외 모든 요청은 인증을 요구
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling  // 예외 처리 설정을 구성
                        .authenticationEntryPoint(new FailedAuthenticationEntryPoint())  // 인증 실패 시의 엔트리 포인트를 설정
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가

        return httpSecurity.build();  // 보안 필터 체인을 빌드
    }

    static class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {  // 인증 실패 시의 동작을 정의하는 클래스입니다.

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {

            response.setContentType("application/json");  // 응답의 콘텐츠 타입을 JSON으로 설정
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 응답 상태 코드를 403 (Forbidden)으로 설정
            response.getWriter().write("{ \"code\": \"NP\", \"message\": \"Do not have permission.\" }");  // 응답 본문에 JSON 형식의 에러 메시지를 씁니다.
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {  // CORS 설정을 구성하는 메서드입니다.
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.addExposedHeader("Authorization");

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
