package com.thuydieutran.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // To expose a directory on the file system - to be accessible by the clients

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "users-photos";
        Path userPhotoDir = Paths.get(dirName);

        String userPhotosPath = userPhotoDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:" + userPhotosPath + "/");
    }
}
