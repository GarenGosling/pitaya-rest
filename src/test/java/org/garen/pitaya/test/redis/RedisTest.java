package org.garen.pitaya.test.redis;


import org.garen.pitaya.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <B>文件名称：</B>RedisTest<BR>
 * <B>文件描述：</B><BR>
 * <BR>
 * <B>版权声明：</B>(C)2016-2018<BR>
 * <B>公司部门：</B>东方银谷 研发二部 CBG<BR>
 * <B>创建时间：</B>2017/01/09<BR>
 *
 * @author 吕宏业  lvhongye@yingu.com
 * @version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void test(){
        redisService.set("lxl", "Hello liuxueliang!!!");
        System.out.println(redisService.get("lxl"));
    }

}
