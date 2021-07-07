package club.mq.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author riven
 * @date 2021-07-07 15:32
 */
@Data
@AllArgsConstructor
public class BaseProjectMq implements Serializable {

    /**
     * 数据
     */
    private Object data;

    /**
     * id
     */
    private String commonId;

}
