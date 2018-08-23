package com.ry.fu.followup.test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ry.fu.followup.FollowupServiceApplication;
import com.ry.fu.followup.pwp.mapper.AccountMapper;
import com.ry.fu.followup.pwp.model.Account;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/23 20:22
 * @description 测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FollowupServiceApplication.class)
@WebAppConfiguration
public class ApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    /**
     * 测试JPA和jdbcTemplate
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = true)
    @Rollback
    public void dbTest() throws Exception {
        Account account = accountMapper.findById(105L);
        logger.info(account.getAccountName());

        Account account2 = accountMapper.selectAccount(13276L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(account2.getAccountInvDate());
        logger.info(account2.getAccountName() + dateStr);

        Account selectAccount = new Account();
        selectAccount.setAccountId(13276L);

        selectAccount = accountMapper.selectOne(selectAccount);
        logger.info(selectAccount.getAccountCode() + "----------->"+selectAccount.getAccountName()+"---->"+selectAccount.getAccountInvDate());

        Map<String, Object> account3 = accountMapper.selectAccount1(13276L);
        logger.info(account3.get("accountId").toString() + "----------" + account3.get("accountName").toString()
                + "-------------------" + account3.get("accountInvDate").toString());
    }

    /**
     * 测试Redis
     */
    @Test
    public void redisTest() {
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
     * 测试Mybatis新增功能
     */
    @Test
    @Transactional
    @Rollback(true)
    public void mapperTest() {
        List<Account> accountList = new ArrayList<Account>();

        Account account1 = new Account();
        account1.setAccountId(17178L);
        account1.setAccountName("张三1");
        account1.setAccountCode("zhangsan1");
        accountList.add(account1);

        Account account2 = new Account();
        account2.setAccountId(17179L);
        account2.setAccountName("张三2");
        account2.setAccountCode("zhangsan2");
        accountList.add(account2);

        if(accountMapper.insertAccounts(accountList) > 0) {
            Account account3 = accountMapper.selectById(17178L);
            Assert.assertEquals("zhangsan1", account3.getAccountCode());

            Account account4 = accountMapper.selectById(17179L);
            Assert.assertEquals("zhangsan2", account4.getAccountCode());
        }
    }

    /**
     * 测试分页插件分页效果
     */
    @Transactional
    @Rollback(true)
    @Test
    public void pageHelperTest() {
        //分页，通过sqlSession来获取
        List<Account> accounts1 = sqlSession.selectList("com.ry.fum.followup.pwp.mapper.AccountMapper.selectAll", null, new RowBounds(0, 10));
        for(Account acc : accounts1) {
            logger.info(acc.getAccountId() + "---------------" + acc.getAccountCode() + "----------------" + acc.getAccountName() );
        }

        //PageHelper分页
        PageHelper.startPage(1, 10);
        List<Account> accounts2 = accountMapper.selectAll();
        for(Account acc : accounts2) {
            logger.info(acc.getAccountId() + "---------------" + acc.getAccountCode() + "----------------" + acc.getAccountName() );
        }

        //使用JDK8中的Lambda表达式
        Page<Account> page = PageHelper.startPage(1, 10).doSelectPage(()-> accountMapper.selectAll());
    }

    /**
     * 测试Controller
     */
    /*@Test
    public void controllerTest() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mvc.perform(MockMvcRequestBuilders.get("/pwp/api")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("name", "nickwu")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Success_nickwu")));
    }*/

  @Test
    public void testTest(){

        logger.info("8888888888testsettsfghjkl;kjhgfdfghjkl;lkjhgvfcvbnm,.;888888888");
  }
}
