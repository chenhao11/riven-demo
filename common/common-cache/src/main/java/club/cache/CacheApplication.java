package club.cache;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author riven
 * @date 2021-07-07 14:25
 */
@SpringBootApplication
public class CacheApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("跑起来了 冲冲冲");
    }

    @Bean
    public RedissonClient getClient() {
        Config config = new Config();
        config.setCodec(JsonJacksonCodec.INSTANCE);
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setDatabase(1)
                .setPassword("123456");

        return Redisson.create(config);
    }
}
