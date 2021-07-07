package test.mysql;

import club.mysql.MysqlApplication;
import club.mysql.model.MyEnum;
import club.mysql.mybatis.demo.entity.LongMyBatisEntity;
import club.mysql.mybatis.demo.mapper.LongMapper;
import club.mysql.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author riven
 * @date 2021-07-07 16:14
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MysqlApplication.class})
@Import(SpringBeanUtils.class)
public class MysqlTest {

    @Test
    public void mybatisPlusInsertTest() {
        LongMapper mapper = SpringBeanUtils.getBean(LongMapper.class);
        LongMyBatisEntity entity = new LongMyBatisEntity();
        entity.setName("憨憨");
        entity.setAge(22);
        entity.setMyEnum(MyEnum.B);
        int num = mapper.insert(entity);
        assert num == 1;
    }

}
