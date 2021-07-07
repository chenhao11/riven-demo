package club.cache.redisson.controller;

import club.cache.redisson.controller.model.Student;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 简单测试
 *
 * @author riven
 * @date 2021-07-07 14:28
 */
@RestController
@RequestMapping("/redisson")
public class RedissonController {

    public static final String BUCKET_TEST_KEY = "BUCKET_TEST_KEY";

    @Resource
    RedissonClient client;


    @GetMapping("/bucket")
    public Object get() {
        RBucket<Student> rBucket = client.getBucket(BUCKET_TEST_KEY);
        rBucket.set(new Student("riven", 24));
        return rBucket.get();
    }

}
