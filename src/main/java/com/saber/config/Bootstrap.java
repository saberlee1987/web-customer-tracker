package com.saber.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Bootstrap implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContextConfiguration= new AnnotationConfigWebApplicationContext();
        rootContextConfiguration.register(RootContextConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(rootContextConfiguration));

        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(WebServletConfig.class);

        ServletRegistration.Dynamic servletRegistration= servletContext.addServlet("SpringDispatcher",new DispatcherServlet(webApplicationContext));

        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/");

    }
}
