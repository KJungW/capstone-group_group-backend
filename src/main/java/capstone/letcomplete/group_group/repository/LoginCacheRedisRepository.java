package capstone.letcomplete.group_group.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class LoginCacheRedisRepository {
    @Value("${jwt.expiration_time}")
    private Long cacheValidTime;
    private final StringRedisTemplate redisTemplate;
    @Autowired
    public LoginCacheRedisRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /*
     * 로그인 캐시 저장
     */
    public void saveLoginCache(Long memberId, String token) {
        redisTemplate.opsForValue().set(memberId.toString(), token, Duration.ofSeconds(cacheValidTime));
    }

    /*
     * 로그인 캐시 조회
     */
    public String getLoginCache(Long memberId) {
        return redisTemplate.opsForValue().get(memberId.toString());
    }

    /*
     * 로그인 캐시 제거
     */
    public void removeLoginCache(Long memberId) {
        redisTemplate.delete(memberId.toString());
    }

    /*
     * 로그인 캐시 존재여부 확인
     */
    public boolean hasKey(Long memberId) {
        Boolean keyExists = redisTemplate.hasKey(memberId.toString());
        return keyExists != null && keyExists;
    }

}
