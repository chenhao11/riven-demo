package club.mysql;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author riven
 * @date 2021-07-07 16:08
 */
@Slf4j
@ComponentScan(value = {"com.gitee.sunchenbin.mybatis.actable.manager.*", "club.mysql.*"})
@MapperScan(basePackages = {"club.mysql.mybatis.demo.mapper", "com.gitee.sunchenbin.mybatis.actable.dao.*"})
@SpringBootApplication
public class MysqlApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MysqlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("系统已经跑起来了冲冲冲---->>>>>");
    }
}
