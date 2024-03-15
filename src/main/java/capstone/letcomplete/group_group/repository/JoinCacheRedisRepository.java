package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.dto.logic.JoinCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class JoinCacheRedisRepository {
    @Value("${join.cache.valid_time}")
    private Long joinCacheValidTime;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StringRedisTemplate redisTemplate;
    @Autowired
    public JoinCacheRedisRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*
     * 회원가입 데이터 저장
     */
    public void saveJoinCache(JoinCache joinCache) throws JsonProcessingException {
        String key = joinCache.getSignupMemberInput().getEmail();
        String value = objectMapper.writer().writeValueAsString(joinCache);
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(joinCacheValidTime));
    }

    /*
     * 회원가입 데이터 조회
     */
    public String getJoinCache(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    /*
     * 회원가입 데이터 제거
     */
    public void removeJoinCache(String email) {
        redisTemplate.delete(email);
    }

    /*
     * 회원가입 데이터 데이터 존재여부 확인
     */
    public boolean hasKey(String email) {
        Boolean keyExists = redisTemplate.hasKey(email);
        return keyExists != null && keyExists;
    }
}
