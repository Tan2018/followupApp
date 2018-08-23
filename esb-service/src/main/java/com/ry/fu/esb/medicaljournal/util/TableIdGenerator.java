package com.ry.fu.esb.medicaljournal.util;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 表主键生成工具类
 * @author : Walker
 */
@Component
public class TableIdGenerator {
    public static final Logger logger = LoggerFactory.getLogger(TableIdGenerator.class);

    @Autowired
    private  RedisIncrementGenerator redisIncrementGenerator;

    public  Long generateTableId(String tableSeq){
        Long id = redisIncrementGenerator.increment(tableSeq, 1);
        if (id != 0L) {
            return id;
        }
        return null;
    }
}
