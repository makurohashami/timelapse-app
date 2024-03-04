package com.kotyk.timelapse

import groovy.util.logging.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@Slf4j
@SpringBootApplication
class TimelapseApplication implements CommandLineRunner {

    static void main(String[] args) {
        SpringApplication.run(TimelapseApplication, args)
    }

    @Override
    void run(String... args) throws Exception {
        log.info("APPLICATION STARTED")
    }
}
