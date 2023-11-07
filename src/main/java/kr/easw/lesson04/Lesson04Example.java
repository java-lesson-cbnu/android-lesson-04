package kr.easw.lesson04;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Lesson04Example {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Lesson04Example.class)
                .registerShutdownHook(true)
                .run(args);
    }
}
