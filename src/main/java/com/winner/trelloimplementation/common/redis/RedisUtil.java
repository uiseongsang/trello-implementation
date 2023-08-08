package com.winner.trelloimplementation.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    // key를 통해 value 가져옴
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    // 만료 기간동안 (key, value)를 저장
    // key를 UUID로 랜덤으로 지정하고, value를 이메일로 하면 될 듯
    public void setDataExpire(String key, String value, long duration) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofMillis(duration);
        valueOperations.set(key, value, expireDuration);

    }
    // 데이터 삭제
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}