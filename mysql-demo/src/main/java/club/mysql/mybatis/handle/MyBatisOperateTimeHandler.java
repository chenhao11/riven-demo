package club.mysql.mybatis.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 指定insert/update填充字段
 *
 * @author riven
 * @date 2021-07-07 16:01
 */
@Component
public class MyBatisOperateTimeHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter("created")) {
            strictInsertFill(metaObject, "created", LocalDateTime.class, LocalDateTime.now());
        }
        if (metaObject.hasSetter("updated")) {
            strictInsertFill(metaObject, "updated", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter("updated")) {
            strictUpdateFill(metaObject, "updated", LocalDateTime.class, LocalDateTime.now());
        }
    }
}
