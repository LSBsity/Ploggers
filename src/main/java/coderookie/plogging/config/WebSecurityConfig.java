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

@Configuration  // 이 클래스가 스프링 설정 클래스임을 나타냅니다.
@EnableWebSecurity  // 웹 보안을 활성화합니다.
@RequiredArgsConstructor  // Lombok을 사용하여 final 필드에 대한 생성자를 자동으로 생성합니다.
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;  // JWT 인증 필터를 주입받습니다.


    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {  // 보안 필터 체인을 구성하는 메서드입니다.
        httpSecurity
                .cors(cors -> cors  // CORS 설정을 구성합니다.
                        .configurationSource(corsConfigurationSource())
                )
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호를 비활성화합니다.
                .httpBasic(AbstractHttpConfigurer::disable)  // HTTP Basic 인증을 비활성화합니다.
                .sessionManagement(sessionManageMent -> sessionManageMent  // 세션 관리 설정을 구성합니다.
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션을 상태없음(stateless)으로 설정합니다.
                )
                .authorizeHttpRequests(request -> request  // HTTP 요청에 대한 인가 규칙을 설정합니다.
                        .requestMatchers("/", "/api/v1/auth/**", "/api/v1/search/**", "/file/**").permitAll()  // 특정 경로에 대한 접근을 허용합니다.
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/**", "/api/v1/user/*").permitAll()  // GET 메서드에 대한 특정 경로 접근을 허용합니다.
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/posts/*/viewCount").permitAll()
                        .anyRequest().authenticated()  // 그 외 모든 요청은 인증을 요구합니다.
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling  // 예외 처리 설정을 구성합니다.
                        .authenticationEntryPoint(new FailedAuthenticationEntryPoint())  // 인증 실패 시의 엔트리 포인트를 설정합니다.
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가합니다.

        return httpSecurity.build();  // 보안 필터 체인을 빌드합니다.
    }

    static class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {  // 인증 실패 시의 동작을 정의하는 클래스입니다.

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {

            response.setContentType("application/json");  // 응답의 콘텐츠 타입을 JSON으로 설정합니다.
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 응답 상태 코드를 403 (Forbidden)으로 설정합니다.
            response.getWriter().write("{ \"code\": \"NP\", \"message\": \"Do not have permission.\" }");  // 응답 본문에 JSON 형식의 에러 메시지를 씁니다.
        }
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {  // CORS 설정을 구성하는 메서드입니다.
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");  // 모든 도메인에서의 접근을 허용합니다.
        configuration.addAllowedOrigin("http://localhost:5173"); // 허용할 origin 설정
        configuration.addAllowedMethod("*");  // 모든 HTTP 메서드를 허용합니다.
        configuration.addExposedHeader("*");  // 모든 헤더를 노출합니다.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 대해 CORS 설정을 등록합니다.
        return source;
    }
}
