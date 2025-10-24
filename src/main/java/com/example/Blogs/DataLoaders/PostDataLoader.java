package com.example.Blogs.DataLoaders;
import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.Services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PostDataLoader {

    private final PostService postService;

    @Autowired
    public PostDataLoader(PostService postService) {
        this.postService = postService;
    }

    public DataLoader<Long, List<PostDTO>> postDataLoader() {
        log.info("Creating PostDataLoader instance");
        return DataLoaderFactory.newDataLoader(userIds -> {
            log.info("Batch function executing for {} users", userIds.size());
            List<PostDTO> posts = postService.findByUserIds(userIds);

            // Group posts by userId
            Map<Long, List<PostDTO>> postsByUserId = posts.stream()
                    .collect(Collectors.groupingBy(PostDTO::getUserId));
            List<List<PostDTO>> result = userIds.stream()
                    .map(userId -> postsByUserId.getOrDefault(userId, Collections.emptyList()))
                    .collect(Collectors.toList());

            return CompletableFuture.completedFuture(result);
        });
    }
}
