package com.example.test_redis_cache.service;


import com.example.test_redis_cache.model.User;
import com.example.test_redis_cache.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Cacheable(value = "usersCache", key = "'users'")
    public List<User> getAllUsers() {
        System.out.println("Fetching users from DB...");
        Instant start = Instant.now();
        List<User> users = userRepository.findAll();
        Instant end = Instant.now();
        System.out.println("DB fetch time: " + (end.toEpochMilli() - start.toEpochMilli()) + " ms");
        return users;
    }

    public List<User> getAllUsersFromCache() {
        Instant start = Instant.now();
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) redisTemplate.opsForValue().get("usersCache::users");
        Instant end = Instant.now();

        System.out.println("Cache fetch time: " + (end.toEpochMilli() - start.toEpochMilli()) + " ms");
        return users;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
}
