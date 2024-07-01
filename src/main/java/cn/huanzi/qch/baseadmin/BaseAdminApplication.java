package cn.huanzi.qch.baseadmin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync//开启异步调用
@SpringBootApplication
@ComponentScan(basePackages = {"cn.huanzi.qch.baseadmin.medicine.**"})
public class BaseAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseAdminApplication.class, args);
        System.out.println("启动成功");
    }

}
