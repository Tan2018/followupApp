package com.ry.fu.esb.test;

import com.ry.fu.esb.EsbServiceApplication;
import com.ry.fu.esb.common.transformer.Transformer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/23 20:22
 * @description 测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EsbServiceApplication.class)
public class ApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private Transformer transformer;

    /**
     * 测试JPA和jdbcTemplate
     * @throws Exception
     */
    @Test
    public void testDB() throws Exception {
    }

    /**
     * 测试Redis
     */
    @Test
    public void testRedis() {
        Map<String, Object> obj1 = new HashMap<String, Object>();
        obj1.put("name", "fus");
        obj1.put("value", "FollowUp System");
        redisTemplate.opsForValue().set("test", obj1);

        Map<String, Object> obj2 = (Map<String, Object>) redisTemplate.opsForValue().get("test");
        Assert.assertEquals("fus", String.valueOf(obj2.get("name")));
        logger.info("-------------" + String.valueOf(obj2.get("name")));

        String obj3 = stringRedisTemplate.opsForValue().get("redis");
        logger.info("-------------" + String.valueOf(obj3));
    }

    /**
     * 测试xml解析
     * @throws Exception
     */
    @Test
    public void testXmlTransformer() throws Exception{

    }


}
