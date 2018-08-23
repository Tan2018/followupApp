package com.ry.fu.esb.common.seq.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/31 16:52
 * @description Redis分布式AOP锁注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LockAction {

    /** 锁的资源，key。支持spring El表达式*/
    @AliasFor("key")
    String value() default "'default'";

    @AliasFor("value")
    String key() default "'default'";

    /** 持锁时间,单位毫秒*/
    long keepMills() default 30000;

    /** 当获取失败时候动作*/
    LockFailAction action() default LockFailAction.CONTINUE;

    public enum LockFailAction{
        /** 放弃 */
        GIVEUP,
        /** 继续 */
        CONTINUE;
    }

    /** 重试的间隔时间,设置GIVEUP忽略此项*/
    long sleepMills() default 200;

    /** 重试次数*/
    int retryTimes() default 5;
}