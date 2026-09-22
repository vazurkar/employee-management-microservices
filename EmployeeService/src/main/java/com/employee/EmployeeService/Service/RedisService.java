package com.employee.EmployeeService.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T getEmployee(long id, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(String.valueOf(id));
            return o != null ? entityClass.cast(o) : null;
        } catch (Exception e) {
            log.error("Exception fetching employee {} from cache", id, e);
            return null;
        }
    }

    public void setEmployee(long id, Object o, Long ttl) {
        try {
            redisTemplate.opsForValue().set(String.valueOf(id), o, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception caching employee {}", id, e);
        }
    }
}