package com.example.Blogs.DAOs;

import com.example.Blogs.Utils.DAOUtilities.DAOUtilities;
import com.example.Blogs.DAOs.SqlQueries.PostQueries;
import com.example.Blogs.Exceptions.PostNotFoundException;
import com.example.Blogs.Exceptions.UserNotFoundException;
import com.example.Blogs.Models.Post;
import com.example.Blogs.Mappers.ResultSetExtractors.PostResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PostDAOImplement extends DAO_Implementaion implements PostDAO {

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




    private Map<Long, Post> MapPosts (String sql,List<Long> ids) {
        Object[] params = daoUtilities.preparingParamForTheQuery(ids);
        return jdbcTemplate.query(sql, new PostResultSetExtractor(), params);
    }

    private Map<Long, Post> MapPosts (String sql,String searchQuery, List<Long> authorFilter, String minDate, String maxDate) {
        return jdbcTemplate.query(sql, new PostResultSetExtractor(),searchQuery, authorFilter, minDate, maxDate);
    }

    private Map<Long, Post> MapPosts (String sql,Integer offset) {
        return jdbcTemplate.query(sql, new PostResultSetExtractor(), offset);
    }

    private Map<Long, Post> MapPosts (String sql,Long id) {
        return jdbcTemplate.query(sql, new PostResultSetExtractor(), id);
    }




    //High Level Methods
    @Override
    public Post findById(Long id) throws PostNotFoundException {
        if (existsById(id)) {
            List<Long> ids = List.of(id);
            String sql = postQueries.SqlQueryForFindingOnePostOrMultiple();
            Map<Long, Post> postsMap = MapPosts(sql,ids);
            assert postsMap != null;
            return postsMap.get(id);
        } else {
            throw new PostNotFoundException("Post not found");
        }
    }



    @Override
    public List<Post> findByUser(Long userId) throws UserNotFoundException {
        if (userDAO.existsById(userId)) {
            String sqlForPostsByUser = postQueries.SqlQueryForFindingAllPostsByUser();
            Map<Long, Post> postsMap =
                    MapPosts(sqlForPostsByUser,userId);
            return postsMap.values().stream().toList();
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public List<Post> findFollowingUsersPosts(Long userId) throws UserNotFoundException {
        if (userDAO.existsById(userId)) {
            String sqlForFollowingPosts = postQueries.SQLQueryForCurrentUserFollowingPosts();
            Map<Long, Post> postsMap =
                    MapPosts(sqlForFollowingPosts,userId);
            return postsMap.values().stream().toList();
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }


    @Override
    public List<Post> findPostsBySearchQuery(String searchQuery, List<Long> authorFilter, String minDate, String maxDate) {
        String sql = postQueries.SQLQueryForPostSearch();
        Map<Long, Post> postsMap = MapPosts(sql,searchQuery, authorFilter, minDate, maxDate);
        assert postsMap != null;
        if (!postsMap.isEmpty()) {
            return postsMap.values().stream().toList();
        }
        return  List.of();
    }

    @Override
    public List<Post> findTopPosts(Integer Offset) {
        String sqlForTopPosts = postQueries.SQLQueryForTopPosts();
        Map<Long, Post> postsMap = MapPosts(sqlForTopPosts,Offset);
        if (!postsMap.isEmpty()) {
            return postsMap.values().stream().toList();
        }
        return List.of();
    }

    @Override
    public Post saveNewPost(Post post) {
        int rowsAffected = jdbcTemplate.update(
                postQueries.insertQuery(),
                post.getUserId(),
                post.getBody(),
                post.getTitle());
        if (rowsAffected == 0) {
            throw new PostNotFoundException("Post not saved");
        }
        return post;
    }

    @Override
    public Post updatePost(Post post) throws PostNotFoundException {
        int rowsAffected = jdbcTemplate.update(postQueries.updateQuery(), post.getBody(), post.getTitle(), post.getId());
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
