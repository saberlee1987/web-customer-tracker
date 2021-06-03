package com.saber.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.saber.site"
        , excludeFilters = @ComponentScan.Filter(Controller.class))
@EnableTransactionManagement
public class RootContextConfiguration {

}
