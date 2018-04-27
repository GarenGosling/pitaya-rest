package org.garen.pitaya.code;

import lombok.Data;
import org.garen.pitaya.serializable.SerializedObj;

/**
 * 编码父类
 *
 * @author Garen Gosling
 * @create 2018-04-28 03:15
 * @since v1.0
 */
@Data
public class Code extends SerializedObj {
    private String code;        // 字符串编码
    private Integer size;       // 长度
    private String label;       // 中文名称
}
