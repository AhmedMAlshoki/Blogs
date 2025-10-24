package com.example.Blogs.DataLoaders;

import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Services.UserService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class UserDataLoader {

    private final UserService userService;

    @Autowired
    public UserDataLoader(UserService userService) {
        this.userService = userService;
    }


    public DataLoader<Long, UserDTO> userDataLoader() {
        return DataLoaderFactory.newDataLoader(userIds -> {
            List<UserDTO> users = userService.findByIds(userIds);

            // Create map for quick lookup
            Map<Long, UserDTO> userMap = users.stream()
                    .collect(Collectors.toMap(UserDTO::getId, user -> user));

            // ✅ Return users in SAME ORDER as input userIds
            List<UserDTO> result = userIds.stream()
                    .map(userMap::get)
                    .collect(Collectors.toList());

            return CompletableFuture.completedFuture(result);
        });
    }
}
