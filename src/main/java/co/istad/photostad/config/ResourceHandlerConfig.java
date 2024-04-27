package co.istad.photostad.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandlerConfig implements WebMvcConfigurer {

    @Value("${file.server-path}")
    private String fileServerPath;
    @Value("${file.client-path}")
    private String fileClientPath;
    @Value("${classpath.server}")
    private String classPathServer;
    @Value("${classpath.client}")
    private String classPathClient;
    @Value("${file.server-path-util}")
    private String fileServerPathUtil;
    @Value("${file.server-path-image}")
    private String fileServerImage;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(fileClientPath)
                .addResourceLocations("file:" + fileServerImage);

        registry.addResourceHandler(classPathClient)
                .addResourceLocations(classPathServer);

    }
}
