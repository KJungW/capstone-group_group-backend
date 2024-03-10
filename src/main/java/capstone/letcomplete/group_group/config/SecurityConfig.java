package capstone.letcomplete.group_group.config;

import capstone.letcomplete.group_group.filter.JwtAuthFilter;
import capstone.letcomplete.group_group.service.userdetail.ManagerUserDetailService;
import capstone.letcomplete.group_group.service.userdetail.MemberUserDetailService;
import capstone.letcomplete.group_group.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity 
@RequiredArgsConstructor
public class SecurityConfig {
    private final ManagerUserDetailService managerUserDetailService;
    private final MemberUserDetailService memberUserDetailService;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        // CSRF, CORS 세팅
        http.csrf((csrf) -> csrf.disable());
        http.cors(Customizer.withDefaults());

        // 세션관리 사용x
        http.sessionManagement(sessionManagement
                -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        // FormLogin, BasicHttp비활성화
        http.formLogin((form)->form.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        // jwt토큰 검증 필터를 추가
        http.addFilterBefore(
                new JwtAuthFilter(managerUserDetailService, memberUserDetailService, jwtUtil),
                UsernamePasswordAuthenticationFilter.class
        );

        // http 요청에 대한 인가규칙 설정 (현재는 모두 허용, 이후 메서드별로 규칙설정)
        http.authorizeHttpRequests(
                authorize -> authorize.anyRequest().permitAll()
        );

        return http.build();

    }
}
