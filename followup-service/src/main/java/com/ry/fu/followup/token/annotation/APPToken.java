package com.ry.fu.followup.token.annotation;

import java.lang.annotation.*;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/5 8:06
 * @description 自定义注解，@APPToken，用来注解方法，接口授权登录
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented //javadoc包含注解
public @interface APPToken {

}
