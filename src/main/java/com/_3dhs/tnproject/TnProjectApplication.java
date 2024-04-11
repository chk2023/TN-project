package com._3dhs.tnproject;

import com._3dhs.tnproject.search.controller.SearchController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ComponentScan("com._3dhs.tnproject")
@Slf4j
public class TnProjectApplication implements CommandLineRunner {
    private final SearchController searchController;

    public TnProjectApplication(SearchController searchController) {
        this.searchController = searchController;
    }

    public static void main(String[] args) {
        SpringApplication.run(TnProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("run 실행됨...");
        searchController.insertDoc();
        log.info("run 실행종료...");
    }
}
