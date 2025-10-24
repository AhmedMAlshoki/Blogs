package com.example.Blogs.DataLoaders;

import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.Services.CommentService;
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
public class CommentDataLoader {
    private final CommentService commentService;

    @Autowired
    public CommentDataLoader(CommentService commentService) {
        this.commentService = commentService;
    }

    public DataLoader<Long, List<CommentDTO>> commentDataLoader() {
        return DataLoaderFactory.newDataLoader(postIds -> {
            List<CommentDTO> comments = commentService.findByMultiplePosts(postIds);

            // Group comments by postId
            Map<Long, List<CommentDTO>> commentsByPostId = comments.stream()
                    .collect(Collectors.groupingBy(CommentDTO::getPostId));
            List<List<CommentDTO>> result = postIds.stream()
                    .map(postId -> commentsByPostId.getOrDefault(postId, Collections.emptyList()))
                    .collect(Collectors.toList());

            return CompletableFuture.completedFuture(result);
        });
    }
}
