package com.chentong.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
// @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

/**
 * Failed to configure a DataSource: 'url' attribute is not specified and no
 * embedded datasource could be configured. 添加@EnableAutoConfiguration解决问题
 */

// 设置编码的格式 Window -> Preferences -> Expand General and click Workspace > UTF-8
// 配置代码自动格式化 Java > Editor > Save Actions > Format source code 
public class MyblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}

}
