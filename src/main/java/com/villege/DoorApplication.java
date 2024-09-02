package com.villege;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@Slf4j
@MapperScan("com.villege.dao.**")
@SpringBootApplication
public class DoorApplication {

    public static void main(String[] args) throws  Exception {
        ConfigurableApplicationContext application = SpringApplication.run(DoorApplication.class, args);

        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n==============================================================\n\t" +
                "Application park is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "\n\t" +
                "Network: \thttp://" + ip + ":" + port + "\n\t" +
                "Doc: \t\thttp://" + ip + ":" + port + "/doc.html\n\t" +
                "swagger-ui: http://" + ip + ":" + port + "/swagger-ui.html\n" +
                "==============================================================");
    }
}
