package org.teamtuna.yaguroute.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisTestController {

    private final StringRedisTemplate redisTemplate;

    @GetMapping("{key}:{ops}")
    public String generateDummyData(
            @PathVariable("ops") String ops,
            @PathVariable("key") String key,
            @RequestParam(name = "val", required = false) String val
    )  {
        return switch(ops) {
            case "get" -> redisTemplate.opsForValue().get(key);
            case "set" -> {
                redisTemplate.opsForValue().set(key, val);
                yield "OK";
            }
            default -> "NoOps";
        };
    }

}