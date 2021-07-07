package club.mysql.mybatis.demo.mapper;

import club.mysql.mybatis.demo.entity.LongMyBatisEntity;
import club.mysql.mybatis.mapper.AbstractMyBatisMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 自定义实体mapper
 *
 * @author riven
 * @date 2021-07-07 16:05
 */
public interface LongMapper extends AbstractMyBatisMapper<LongMyBatisEntity> {


    @Select("select * from mybatis_long_entity where name = #{name}")
    List<LongMyBatisEntity> myQuery(@Param("name") String name);

    @Select("select * from mybatis_long_entity")
    IPage<LongMyBatisEntity> pageQuery(IPage<LongMyBatisEntity> page);
}
