package bioskopi.rs.domain.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        try {
            String path = new File(".").getCanonicalPath() + File.separator + "src" + File.separator + "main" +
                    File.separator + "upload";
            registry.addResourceHandler("/upload/**").addResourceLocations("/upload/","file:/home/themaniac/Documents/Internet softverske arhitekture/Proba/bioskopi/src/main/upload/");
            //registry.addResourceHandler("/upload/**").addResourceLocations("/upload/","file:/home/themaniac/Documents/Internet softverske arhitekture/Proba/upload");
        }catch (IOException e){

        }

        //registry.addResourceHandler("/upload/**").addResourceLocations("file:///Users/Demo/src/main/upload/");
    }
}
