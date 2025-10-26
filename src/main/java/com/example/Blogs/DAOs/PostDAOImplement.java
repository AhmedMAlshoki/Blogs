package com.example.Blogs.DAOs;

import com.example.Blogs.CustomResponses.SearchQueryResult;
import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Mappers.ResultSetExtractors.PostResultSetExtractor;
import com.example.Blogs.Mappers.ResultSetExtractors.SearchQueryPostsResultSetExtractor;
import com.example.Blogs.Utils.DAOUtilities.DAOUtilities;
import com.example.Blogs.DAOs.SqlQueries.PostQueries;
import com.example.Blogs.Exceptions.PostNotFoundException;
import com.example.Blogs.Exceptions.UserNotFoundException;
import com.example.Blogs.Models.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.HashMap;

@Repository
@Slf4j
public class PostDAOImplement extends DAO_Implementation implements PostDAO {

    private  UserDAO userDAO;
    private PostQueries postQueries;
    private DAOUtilities daoUtilities;
    @Autowired
    public PostDAOImplement(JdbcTemplate jdbcTemplate, UserDAO userDAO,
                            PostQueries postQueries,  DAOUtilities daoUtilities) {
        super(jdbcTemplate);
        this.userDAO = userDAO;
        this.postQueries = postQueries;
        this.daoUtilities = daoUtilities;
    }


    //Utility Middle/Low Level Methods

    @Override
    public boolean existsById(Long id) {
        String sql = postQueries.existsById();
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, Boolean.class, id)
        );
    }




    private HashMap<Long, Post> HashMapPosts (String sql,List<Long> ids) {
        String inClauseParams = daoUtilities.preparingParamForTheQuery(ids);
        String modifiedSQL = String.format(sql,inClauseParams);
        return jdbcTemplate.query(modifiedSQL, new PostResultSetExtractor(), ids.toArray());
    }

    private SearchQueryResult HashMapPosts (String sql,String searchQuery, List<Long> authorFilter, OffsetDateTime minDate, OffsetDateTime maxDate, Integer limit, Integer offset) {
        return jdbcTemplate.query(sql, new SearchQueryPostsResultSetExtractor(),searchQuery, authorFilter, minDate, maxDate,limit,offset);
    }

    private HashMap<Long, Post> HashMapPosts (String sql,Integer offset) {
        return jdbcTemplate.query(sql, new PostResultSetExtractor(), offset);
    }

    private HashMap<Long, Post> HashMapPosts (String sql,Long id) {
        return jdbcTemplate.query(sql, new PostResultSetExtractor(), id);
    }

    private HashMap<Long, Post> HashMapPosts (String sql) {
        return jdbcTemplate.query(sql, new PostResultSetExtractor());
    }

    @Override
    public Post findById(Long id) throws PostNotFoundException {
        if (existsById(id)) {
            List<Long> ids = List.of(id);
            String sql = postQueries.SqlQueryForFindingOnePostOrMultiple();
            HashMap<Long, Post> postsHashMap = HashMapPosts(sql,ids);
            assert postsHashMap != null;
            return postsHashMap.get(id);
        } else {
            throw new PostNotFoundException("Post not found");
        }
    }



    @Override
    public List<Post> findByUser(Long userId) throws UserNotFoundException {
        if (userDAO.existsById(userId)) {
            String sqlForPostsByUser = postQueries.SqlQueryForFindingAllPostsByUser();
            HashMap<Long, Post> postsHashMap =
                    HashMapPosts(sqlForPostsByUser,userId);
            return postsHashMap.values().stream().toList();
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public List<Post> findFollowingUsersPosts(Long userId) throws UserNotFoundException {
        if (userDAO.existsById(userId)) {
            String sqlForFollowingPosts = postQueries.SQLQueryForCurrentUserFollowingPosts();
            HashMap<Long, Post> postsHashMap =
                    HashMapPosts(sqlForFollowingPosts,userId);
            return postsHashMap.values().stream().toList();
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }


    @Override
    public SearchQueryResult findPostsBySearchQuery(String searchQuery,
                                                    List<Long> authorFilter,
                                                    OffsetDateTime minDate,
                                                    OffsetDateTime maxDate,
                                                    Integer limit,
                                                    Integer offset) {
        log.info("Query Call in DAO");
        String sql = postQueries.SQLQueryForPostSearch();
        SearchQueryResult result = HashMapPosts(sql,searchQuery, authorFilter, minDate, maxDate, limit, offset);
        if (result.getTotal() == 0) {
            log.info("No posts found");
            return null;
        }
        log.info("Number of posts found : {}", result.getTotal());
        return result;
    }

    @Override
    public List<Post> findTopPosts() {
        String sqlForTopPosts = postQueries.SQLQueryForTopPosts();
        HashMap<Long, Post> postsHashMap = HashMapPosts(sqlForTopPosts);
        if (!postsHashMap.isEmpty()) {
            return postsHashMap.values().stream().toList();
        }
        return List.of();
    }

    @Override
    public List<Post> findTopPostsOffset(Integer offset) {
        String sqlForTopPosts = postQueries.getTopPostsOffsetQuery();
        HashMap<Long, Post> postsHashMap = HashMapPosts(sqlForTopPosts,offset);
        if (!postsHashMap.isEmpty()) {
            return postsHashMap.values().stream().toList();
        }
        return List.of();
    }

    @Override
    public String likePost(Long postId, Long userId, Timezone timezone) {
        String sql = postQueries.likePostQuery();
        if (existsById(postId))
        {
            try {
                jdbcTemplate.update(sql, userId, postId, timezone.toString());
                return "Post Liked";
            }
            catch (Exception  e) {
                return "User "+userId+" has already liked this post";
            }
        }
        else {
            return "Post not found";
        }
    }

    @Override
    public String dislikePost(Long postId, Long userId) {
        String sql = postQueries.dislikePostQuery();
        int rowsAffected = jdbcTemplate.update(sql, userId, postId);
        if (rowsAffected > 0) {
            return "Post Disliked";
        }
        else
           return "User "+userId+" has not liked this post";
    }

    @Override
    public Long getPostOwner(Long postId) {
        String sql = postQueries.getPostOwnerQuery();
        return jdbcTemplate.queryForObject(sql, Long.class, postId);
    }

    @Override
    public List<Post> getByUserIds(List<Long> userIds) {
        String sql = postQueries.getByUserIdsQuery();
        HashMap<Long, Post> postsHashMap = HashMapPosts(sql,userIds);
        if (!postsHashMap.isEmpty()) {
            return postsHashMap.values().stream().toList();
        }
        return List.of();
    }

    @Override
    public Post saveNewPost(Post post , Timezone timezone) {
        int rowsAffected = jdbcTemplate.update(
                postQueries.insertQuery(),
                post.getUserId(),
                post.getBody(),
                post.getTitle(),
                timezone.toString());
        if (rowsAffected == 0) {
            throw new PostNotFoundException("Post not saved");
        }
        return post;
    }

    @Override
    public Post updatePost(Post post , Timezone timezone) throws PostNotFoundException {
        int rowsAffected = jdbcTemplate.update(postQueries.updateQuery(), post.getBody(), post.getTitle(), timezone.toString(), post.getId());
        if (rowsAffected == 0) {
            throw new PostNotFoundException("Post with ID " + post.getId() + " not found.");
        }
        return post;
    }

    @Override
    public String deleteById(Long id) throws PostNotFoundException {
        int rowsAffected = jdbcTemplate.update(postQueries.deleteQuery(), id);
        if (rowsAffected == 0) {
            throw new PostNotFoundException("Post with ID " + id + " not found.");
        }
        return "Post with ID " + id + " deleted successfully.";
    }

}
