package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.dto.logic.JoinCacheDto;
import capstone.letcomplete.group_group.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class JoinCacheRedisRepository {
    @Value("${join.cache.valid_time}")
    private Long joinCacheValidTime;
    private final JsonUtil jsonUtil;
    private final StringRedisTemplate redisTemplate;
    @Autowired
    public JoinCacheRedisRepository(JsonUtil jsonUtil, StringRedisTemplate redisTemplate) {
        this.jsonUtil = jsonUtil;
        this.redisTemplate = redisTemplate;
    }

    /*
     * 회원가입 데이터 저장
     */
    public void saveJoinCache(JoinCacheDto joinCacheDto) throws JsonProcessingException {
        String key = joinCacheDto.getSignupMemberInput().getEmail();
        String value = jsonUtil.convertObjectToJson(joinCacheDto);
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(joinCacheValidTime));
    }

    /*
     * 회원가입 데이터 조회
     */
    public JoinCacheDto getJoinCache(String email) throws JsonProcessingException {
        String joinCacheJson = redisTemplate.opsForValue().get(email);
        return jsonUtil.convertJsonToObject(joinCacheJson, JoinCacheDto.class);
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
