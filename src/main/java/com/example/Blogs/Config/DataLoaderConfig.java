package com.example.Blogs.Config;

import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Services.UserService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Configuration
public class DataLoaderConfig {
    @Autowired
    private UserService userService;


    @Bean
    public DataLoader<Long, UserDTO> userDataLoader() {
        return DataLoaderFactory.newDataLoader((userIds -> {
            List<UserDTO> users = userService.findByIds(userIds);
            return CompletableFuture.completedFuture(users);
        }));
    }


}
