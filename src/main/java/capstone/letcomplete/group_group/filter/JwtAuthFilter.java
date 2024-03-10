package capstone.letcomplete.group_group.filter;

import capstone.letcomplete.group_group.entity.enumtype.AccountType;
import capstone.letcomplete.group_group.service.userdetail.ManagerUserDetailService;
import capstone.letcomplete.group_group.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final ManagerUserDetailService managerUserDetailService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(checkAuthHeader(authHeader)) {
            String token = authHeader.substring(7);
            executeJwtAuth(token);
        }
        filterChain.doFilter(request, response);
    }

    private Boolean checkAuthHeader(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    private void executeJwtAuth(String token) {
        if (jwtUtil.validateToken(token)) {

            Claims claims = jwtUtil.getClaims(token);
            Long id = claims.get("id", Long.class);
            AccountType accountType = AccountType.valueOf(claims.get("role", String.class));

            try {
                UserDetails userDetails = makeUserDetail(id, accountType);
                UsernamePasswordAuthenticationToken usernamePasswordToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordToken);
            } catch (UsernameNotFoundException e) {
                log.error("인가 중 유저정보를 찾지 못햇습니다.", e);
            }
        }
    }

    private UserDetails makeUserDetail(Long id, AccountType accountType) {
        UserDetails userDetails;
//        if(accountType == AccountType.MANAGER) {
//            userDetails = managerUserDetailService.loadUserByUsername(id.toString());
//        } else {
//            //userDetails = customUserDetailService.loadUserByUsername(id.toString());
//        }
        userDetails = managerUserDetailService.loadUserByUsername(id.toString());
        return userDetails;
    }
}

