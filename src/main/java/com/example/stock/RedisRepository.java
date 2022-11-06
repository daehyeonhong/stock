package com.example.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String, String> redisTemplate;

    private static String generateKey(final Long key) {
        return String.valueOf(key);
    }

    public Boolean lock(final Long key) {
        return this.redisTemplate
                .opsForValue()
                .setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3_000));
    }

    public Boolean unlock(final Long key) {
        return this.redisTemplate.delete(generateKey(key));
    }
}
