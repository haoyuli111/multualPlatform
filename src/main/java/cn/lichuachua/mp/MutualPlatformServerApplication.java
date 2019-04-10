package cn.lichuachua.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 李歘歘
 * 运行类
 */
@EnableSwagger2
@SpringBootApplication
public class MutualPlatformServerApplication {

    public static void main(String[] args){
        SpringApplication.run(MutualPlatformServerApplication.class, args);
    }
}
