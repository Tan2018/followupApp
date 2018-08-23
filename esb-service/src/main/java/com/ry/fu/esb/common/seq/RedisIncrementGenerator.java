package com.ry.fu.esb.common.seq;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.seq.annotations.LockAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/13 19:02
 * @description Redis增量生成器。如果有值，取出来+1，如果没值设置redis时间毫秒数为初始值
 */
@Component
public class RedisIncrementGenerator {

    private StringRedisSerializer serializer = new StringRedisSerializer();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取redis增量数,会进行同步锁操作
     * @param key  Key的名称 规范为 SQUENCES.M_ACCOUNT_SEQ
     *             已放弃使用setIfAbsent，采用Lua脚本来实现原子性
     * @param count
     * @return
     */
    @LockAction
    public long increment(final String key, final int count){
        //加锁并设置超时时间
//        String lockKey = key.indexOf(".") > 0 ? key.substring(key.indexOf(".") + 1) : Constants.SQUENCES;
//        stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "lock");
//        stringRedisTemplate.expire(lockKey, 10000, TimeUnit.MILLISECONDS);

        //redis存放序列号的key
        long sequence = stringRedisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                //查询redis是否有序列号
                String value = get(redisConnection, key);
                Long id = null;
                if(StringUtils.hasText(value)){
                    id = Long.valueOf(value);
                } else {
                    id = redisConnection.time();
                }
                //设置下一个线程获取序列号的对象
                set(redisConnection, key, (id.longValue() + count) + "");
                return id;
            }
        });
        //释放锁资源
//        stringRedisTemplate.delete(lockKey);
        return sequence;
    }

    private void set(RedisConnection redisConnection, String key, String velue){
        byte[] bkey = serializer.serialize(key);
        byte[] bvalue = serializer.serialize(velue);
        redisConnection.set(bkey, bvalue);
    }

    private String get(RedisConnection redisConnection, String key){
        byte[] bkey = serializer.serialize(key);
        byte[] bvalue = redisConnection.get(bkey);
        if(bvalue == null){
            return null;
        }
        String value = serializer.deserialize(bvalue);
        return value;
    }

}
