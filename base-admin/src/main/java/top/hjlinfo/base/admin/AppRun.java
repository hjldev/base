package top.hjlinfo.base.admin;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sting
 * @date 2018/11/15 9:20:19
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AppRun {

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }

}
