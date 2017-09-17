package com.stardust.renai.survey.conf;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.stardust.renai.survey.dao.router.MultiTenantMongoDbFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@PropertySource("classpath:application.properties")
@Configuration
public class AppConf {
    @Value("${app.db.server}")
    private String dbServer;

    @Value("${app.db.user}")
    private String dbUser;

    @Value("${app.db.password}")
    private String dbPassword;

    @Bean
    public MongoTemplate mongoTemplate(final Mongo mongo) throws Exception {
        return new MongoTemplate(mongoDbFactory(mongo));
    }

    @Bean
    public MultiTenantMongoDbFactory mongoDbFactory(final Mongo mongo) throws Exception {
        return new MultiTenantMongoDbFactory(mongo, "survey");
    }

    @Bean
    public Mongo mongo() throws Exception {
        if (dbUser == null || dbUser.isEmpty()) {
            return new MongoClient(dbServer);
        }
        List<ServerAddress> hosts = new ArrayList();
        List<MongoCredential> credentials = new ArrayList();
        hosts.add(new ServerAddress(dbServer, 3717));
        credentials.add(MongoCredential.createCredential(dbUser, "admin", dbPassword.toCharArray()));
        Mongo mongo = new MongoClient(hosts, credentials);
        return mongo;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .allowedMethods("GET","PUT","POST","DELETE","HEAD","OPTIONS");
            }
        };
    }
}