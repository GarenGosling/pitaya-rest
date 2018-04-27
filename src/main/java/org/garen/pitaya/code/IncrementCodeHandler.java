package org.garen.pitaya.code;

import lombok.extern.java.Log;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.exception.BusinessException;
import org.garen.pitaya.serializable.SerializedObjHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 自增编码生成器
 *
 * @author Garen Gosling
 * @create 2018-04-28 03:14
 * @since v1.0
 */
@Log
@Component
public class IncrementCodeHandler {

    @Autowired
    SerializedObjHandler<IncrementCode> serializedObjHandler;


    public void save(IncrementCode incrementCode){
        try {
            if(incrementCode.getHasLock()){    // 使用锁
                incrementCode.setIsLock(false);
            }
            serializedObjHandler.store(incrementCode);
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "create", e);
            throw new BusinessException("创建自增编码异常");
        }
    }

    /**
     * 删除
     * @param name
     * @throws URISyntaxException
     */
    public void delete(String name) throws URISyntaxException {
        serializedObjHandler.destroy(name);
    }

    /**
     * 查询
     * @param name
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public IncrementCode get(String name) throws URISyntaxException, IOException, ClassNotFoundException {
        IncrementCode incrementCode = serializedObjHandler.load(name);
        if(incrementCode.getHasLock()){         // 使用锁
            if(incrementCode.getIsLock()){
                throw new BadRequestException("编码生成器正在被占用，请稍后重试");
            }else{
                incrementCode.setIsLock(true);      // 已上锁
            }
        }
        return incrementCode;
    }

    /**
     * 消费
     * @param name
     * @throws Exception
     */
    public void consume(String name) throws Exception {
        IncrementCode incrementCode = get(name);
        Integer id = incrementCode.getId();
        id ++;
        incrementCode.setId(id);
        int lackSize = incrementCode.getSize() - id.toString().length();
        String code = id.toString();
        if(lackSize > 0){
            for(int i=0;i<lackSize;i++){
                code = "0" + code;
            }
        }
        incrementCode.setCode(code);
        if(incrementCode.getHasLock()){             // 使用锁
            incrementCode.setIsLock(false);         // 解锁
        }
        serializedObjHandler.store(incrementCode);
    }

    /**
     * 解锁
     * @param name
     * @throws Exception
     */
    public void unLock(String name) throws Exception {
        IncrementCode incrementCode = get(name);
        if(incrementCode.getHasLock()){             // 使用锁
            incrementCode.setIsLock(false);         // 解锁
            serializedObjHandler.store(incrementCode);
        }else{
            throw new BusinessException("未使用锁");
        }
    }


}
