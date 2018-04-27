package org.garen.pitaya.code;

import lombok.Data;

/**
 * 自增编码类
 *
 * @author Garen Gosling
 * @create 2018-04-28 03:23
 * @since v1.0
 */
@Data
public class IncrementCode extends Code{
    private Integer id;         // 数字编码
    private Boolean hasLock;    // 是否使用锁
    private Boolean isLock;     // 是否已锁定
}
