package com.ry.fu.esb.common.test;

import com.ry.fu.esb.EsbServiceApplication;
import com.ry.fu.esb.common.transformer.Transformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EsbServiceApplication.class)
public class MockMvcTests {

    private static Logger logger = LoggerFactory.getLogger(MockMvcTests.class);

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate <String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private Transformer transformer;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc(){
         mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    /**
     * 测试JPA和jdbcTemplate
     *
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
        Map <String, Object> obj1 = new HashMap <String, Object>();
        obj1.put("name", "fus");
        obj1.put("value", "FollowUp System");
        redisTemplate.opsForValue().set("test", obj1);

        Map <String, Object> obj2 = (Map <String, Object>) redisTemplate.opsForValue().get("test");
        Assert.assertEquals("fus", String.valueOf(obj2.get("name")));
        logger.info("-------------" + String.valueOf(obj2.get("name")));

        String obj3 = stringRedisTemplate.opsForValue().get("redis");
        logger.info("-------------" + String.valueOf(obj3));
    }

    /**
     * 测试xml解析
     *
     * @throws Exception
     */
    @Test
    public void testXmlTransformer() throws Exception {

    }

    @Test
    public void getTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("localhost.2017/esb/v1/medicalJournal/payMed/getPatientMessage?patientId="+"1522479651542"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", is(empty()))).andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(mvcResult.getModelAndView().getModel().get("user"));
    }

    @Test
    public void pistTest() throws Exception {
        mockMvc.perform(post("")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title","BookTittle")
                .param("author","BookAuthor"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","readingList")

        );

    }

   /* @Test
    public void payTestNemu(){
        String msg = PayTypeEnum.alipay_app.getMsg();
        String s = PayTypeEnum.sunshier_instcard.toString();
        System.out.println(msg+"-"+s);


    }*/

}
