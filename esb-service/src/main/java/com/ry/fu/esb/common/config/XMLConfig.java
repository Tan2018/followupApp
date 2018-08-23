package com.ry.fu.esb.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 引入XML配置文件，为了查找方便，使用自定义XMLConfig，使整体代码干净整洁
 * @Autor : Nick
 * @Date : 2017-12-16
 */
@ImportResource("classpath:/conf/spring-platform.xml")  //导入xml配置项
@Configuration
public class XMLConfig {

}
