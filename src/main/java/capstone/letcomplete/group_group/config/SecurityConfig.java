package capstone.letcomplete.group_group.config;

import capstone.letcomplete.group_group.exception.handler.JwtAccessDeniedHandler;
import capstone.letcomplete.group_group.exception.handler.JwtAuthenticationEntryPoint;
import capstone.letcomplete.group_group.filter.JwtAuthFilter;
import capstone.letcomplete.group_group.repository.LoginCacheRedisRepository;
import capstone.letcomplete.group_group.service.userdetail.ManagerUserDetailService;
import capstone.letcomplete.group_group.service.userdetail.MemberUserDetailService;
import capstone.letcomplete.group_group.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity 
@RequiredArgsConstructor
public class SecurityConfig {
    private final ManagerUserDetailService managerUserDetailService;
    private final MemberUserDetailService memberUserDetailService;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;
    private final LoginCacheRedisRepository loginCacheRedisRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF, CORS 세팅
        http.csrf((csrf) -> csrf.disable());
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource));

        // 세션관리 사용x
        http.sessionManagement(sessionManagement
                -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        // FormLogin, BasicHttp비활성화
        http.formLogin((form)->form.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        // jwt토큰 검증 필터를 추가
        http.addFilterBefore(
                new JwtAuthFilter(managerUserDetailService, memberUserDetailService, jwtUtil, loginCacheRedisRepository),
                UsernamePasswordAuthenticationFilter.class
        );

        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        // 인증은 되었지만 접근 권한이 없을 경우
                        .accessDeniedHandler(accessDeniedHandler)
                        // 인증에 실패할 경우
                        .authenticationEntryPoint(authenticationEntryPoint)
        );
        // http 요청에 대한 인가규칙 설정 (현재는 모두 허용, 이후 메서드별로 규칙설정)
        http.authorizeHttpRequests(
                authorize -> authorize.anyRequest().permitAll()
        );

        return http.build();
    }

}



