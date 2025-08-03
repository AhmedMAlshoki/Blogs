package com.example.Blogs.DAOs;

import com.example.Blogs.DAOs.DAOUtilities.DAOUtilities;
import com.example.Blogs.DAOs.SqlQueries.PostQueries;
import com.example.Blogs.Exceptions.PostNotFoundException;
import com.example.Blogs.Exceptions.UserNotFoundException;
import com.example.Blogs.Models.Post;
import com.example.Blogs.ResultSetExtractors.IdResultSetExtractor;
import com.example.Blogs.ResultSetExtractors.PostResultSetExtractor;
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
        String sql = postQueries.existsById(id);
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


    //High Level Methods
    @Override
    public Post findById(Long id) throws PostNotFoundException {
        if (existsById(id)) {
            List<Long> ids = List.of(id);
            String sql = postQueries.SqlQueryForFindingMultiplePosts(daoUtilities.preparingParamForTheQuery(ids));
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
            String sqlForPostsIds = postQueries.SqlQueryForFindingAllPostsIdsByUser();
            List<Long> ids= jdbcTemplate.query(sqlForPostsIds, new IdResultSetExtractor(), userId);
            assert ids != null;
            if (ids.isEmpty()) {
                return List.of();
            }
            Object [] params = daoUtilities.preparingParamForTheQuery(ids);
            Map<Long, Post> postsMap =
                    MapPosts(postQueries.SqlQueryForFindingMultiplePosts(params),ids);
            return postsMap.values().stream().toList();
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public List<Post> findFollowingUsersPosts(Long userId) throws UserNotFoundException {
        if (userDAO.existsById(userId)) {
            String sqlForPostsIds = postQueries.SQLQueryForCurrentUserFollowingPosts(userId);
            List<Long> ids= jdbcTemplate.query(sqlForPostsIds, new IdResultSetExtractor(), userId);
            assert ids != null;
            if (ids.isEmpty()) {
                return List.of();
            }
            Object [] params = daoUtilities.preparingParamForTheQuery(ids);
            Map<Long, Post> postsMap =
                    MapPosts(postQueries.SqlQueryForFindingMultiplePosts(params),ids);
            return postsMap.values().stream().toList();
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }


    @Override
    public List<Post> findPostsBySearchQuery(String searchQuery, List<Long> authorFilter, String minDate, String maxDate) {
        String sql = postQueries.SQLQueryForPostSearch(searchQuery, authorFilter, minDate, maxDate, 10, 0);
        Map<Long, Post> postsMap = MapPosts(sql,searchQuery, authorFilter, minDate, maxDate);
        assert postsMap != null;
        if (!postsMap.isEmpty()) {
            return postsMap.values().stream().toList();
        }
        return  List.of();
    }

    @Override
    public List<Post> findTopPosts(Integer Offset) {
        String sqlForPostsIds = postQueries.SQLQueryForTopPostsIds(Offset);
        List<Long> ids= jdbcTemplate.query(sqlForPostsIds, new IdResultSetExtractor(), Offset);
        assert ids != null;
        if (ids.isEmpty()) {
            return List.of();
        }
        Object [] params = daoUtilities.preparingParamForTheQuery(ids);
        String sql = postQueries.SqlQueryForFindingMultiplePosts(params);
        Map<Long, Post> postsMap = MapPosts(sql,Offset);
        if (!postsMap.isEmpty()) {
            return postsMap.values().stream().toList();
        }
        return List.of();
    }

    @Override
    public int saveNewPost(Post post) {
        return jdbcTemplate.update(
                postQueries.insertQuery(),
                post.getBody(),
                post.getTitle(),
                post.getUserId()
        );
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
    public void deleteById(Long id) throws PostNotFoundException {
        int rowsAffected = jdbcTemplate.update(postQueries.deleteQuery(), id);
        if (rowsAffected == 0) {
            throw new PostNotFoundException("Post with ID " + id + " not found.");
        }
    }

}
