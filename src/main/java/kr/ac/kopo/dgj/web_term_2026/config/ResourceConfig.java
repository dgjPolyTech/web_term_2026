package kr.ac.kopo.dgj.web_term_2026.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///" + fileDir);
        // .setCachePeriod(60 * 60 * 24 * 365); // Uncomment to enable caching
    }
}
