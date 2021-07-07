package club.cache.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author riven
 * @date 2021-07-07 14:15
 */
@Configuration
public class RedissonConfiguration {

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
