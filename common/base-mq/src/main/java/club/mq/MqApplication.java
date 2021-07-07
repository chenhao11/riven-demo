package club.mq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author riven
 * @date 2021-07-07 15:10
 */
@SpringBootApplication
public class MqApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MqApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("mq跑起来了 冲冲冲");
    }
}
