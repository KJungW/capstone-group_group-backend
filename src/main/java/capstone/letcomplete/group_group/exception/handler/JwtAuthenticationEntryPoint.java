package capstone.letcomplete.group_group.exception.handler;

import capstone.letcomplete.group_group.exception.ErrorResult;
import capstone.letcomplete.group_group.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final JsonUtil jsonUtil;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String content = jsonUtil.convertObjectToJson(
                new ErrorResult("UNAUTHORIZED","계정정보가 올바르지 않습니다. 다시 로그인을 해주세요"));

        response.getWriter().write(content);
    }
}
