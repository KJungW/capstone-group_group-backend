package capstone.letcomplete.group_group.util;

import capstone.letcomplete.group_group.dto.logic.MemberInfoDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;  // JWT 토큰에 넣을 Signature Key
    private final long accessTokenExpireTime;  // JWT 토큰 유효기간

    public JwtUtil(
            @Value("${jwt.secret}") String secreteKey,
            @Value("${jwt.expiration_time}") long accessTokenExpTime
    ) {
        byte[] decodeData = Decoders.BASE64.decode(secreteKey);
        this.key = Keys.hmacShaKeyFor(decodeData);
        this.accessTokenExpireTime = accessTokenExpTime;
    }

    /*
     * Access Token 생성
     */
    public String makeAccessToken(MemberInfoDto member) {
        return makeToken(member, accessTokenExpireTime);
    }

    /*
     * JWT 토큰 생성
     */
    private String makeToken(MemberInfoDto member, long expireTime) {
        // JWT 토큰에 넣을 claims 생성
        Claims claims = Jwts.claims();
        claims.put("memberId", member.getId());
        claims.put("email", member.getEmail());
        claims.put("role", member.getRole());

        // JWT 토큰의 유효기간 세팅
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        // JWT 생성
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * JWT 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            // Jwt Parser객체를 통해 토큰의 유효성 검증 + 토큰 해석
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(key).build();
            jwtParser.parseClaimsJws(token);
            return true;

        } catch (
                io.jsonwebtoken.security.SecurityException // 서명이 올바르지 않거나, 서명 키가 유효하지 않은 경우에 발생
                 | MalformedJwtException e // JWT가 손상되었거나 형식에 맞지 않는 경우에 발생
        ) {
            log.info("토큰이 유효하지 않습니다.", e);
        } catch (ExpiredJwtException e) { // 이미 만료된 jwt토큰
            log.info("유효기간이 만료된 토큰입니다.", e);
        } catch (UnsupportedJwtException e) { // 지원하지 않는 JWT 토큰 형식
            log.info("지원하지 않는 형식의 토큰입니다.", e);
        } catch (IllegalArgumentException e) { // JWT 클레임 문자열이 비어 있거나 잘못된 경우
            log.info("JWT claims이 비어있습니다.", e);
        }
        return false;
    }

    /*
     * JWT token에서 memberId 추출
     */
    public Long getMemberIdFromToken(String token) {
        return getClaims(token).get("memberId", Long.class);
    }

    /*
     * JWT Claim 추출
     */
    public Claims getClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}