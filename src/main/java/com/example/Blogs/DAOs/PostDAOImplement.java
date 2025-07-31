package com.example.Blogs.DAOs;

import com.example.Blogs.DAOs.DAOUtilities.DAOUtilities;
import com.example.Blogs.DAOs.SqlQueries.PostQueries;
import com.example.Blogs.Exceptions.PostNotFoundException;
import com.example.Blogs.Exceptions.UserNotFoundException;
import com.example.Blogs.Models.Comment;
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
    private CommentDAO commentDAO;
    @Autowired
    public PostDAOImplement(JdbcTemplate jdbcTemplate, UserDAOImplement userDAO,
                            PostQueries postQueries,  DAOUtilities daoUtilities,
                              CommentDAO commentDAO) {
        super(jdbcTemplate);
        this.userDAO = userDAO;
        this.postQueries = postQueries;
        this.daoUtilities = daoUtilities;
        this.commentDAO = commentDAO;
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

    /*private Map<Long,Post> MapCommentsWithPosts(Map<Long, Post> postsMap, List<Comment> comments) {
        for (Comment comment : comments) {
            Post post = postsMap.get(comment.getPostId());
            post.getComments().add(comment);
        }
        return postsMap;
    }*/

    private Map<Long, Post> findByPostsIds (List<Long> ids,String sql) {
        Map<Long, Post> postsMap = MapPosts(sql,ids);
        return  postsMap;
    }




    //High Level Methods
    @Override
    public Post findById(Long id) throws PostNotFoundException {
        if (existsById(id)) {
            List<Long> ids = List.of(id,id,id);
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
            Map<Long, Post> postsMap =   findByPostsIds(ids, postQueries.SqlQueryForFindingMultiplePosts(daoUtilities.preparingParamForTheQuery(ids)));
            //List<Comment> comments = commentDAO.findCommentsByPostIds(ids);
            //postsMap = MapCommentsWithPosts(postsMap,comments);
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
            Map<Long, Post> postsMap =   findByPostsIds(ids, postQueries.SqlQueryForFindingMultiplePosts(daoUtilities.preparingParamForTheQuery(ids)));
            //List<Comment> comments = commentDAO.findCommentsByPostIds(ids);
            //postsMap = MapCommentsWithPosts(postsMap,comments);
            return postsMap.values().stream().toList();
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }


    @Override
    public List<Post> findPostsBySearchQuery(String searchQuery, List<Long> authorFilter, String minDate, String maxDate) {
        String sql = postQueries.SQLQueryForPostSearch(searchQuery, authorFilter, minDate, maxDate, 10, 0);
        return  null;
    }

    @Override
    public List<Post> findTopPostsDaily() {
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
