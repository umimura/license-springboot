package kr.co.vci.license.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevDataSource {

    @Value("${jndi.driver-class-name}")
    private String driverClassName = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://127.0.0.1:5432/iotr2?autoReconnection=true";

    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {

            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                context.getNamingResources().addResource(getResource("devJndi",
                        url, "postgres", "ubay1339"));
            }
        };
    }

    public ContextResource getResource(String name, String url, String username,
            String password) {

        ContextResource resource = new ContextResource();
        resource.setName(name); // 사용될 jndi 이름
        resource.setType("javax.sql.DataSource");
        resource.setAuth("Container");
        resource.setProperty("factory",
                "org.apache.commons.dbcp2.BasicDataSourceFactory");

        // datasource 정보
        resource.setProperty("driverClassName", driverClassName);
        resource.setProperty("url", url);
        resource.setProperty("username", username);
        resource.setProperty("password", password);

        return resource;
    }
}
