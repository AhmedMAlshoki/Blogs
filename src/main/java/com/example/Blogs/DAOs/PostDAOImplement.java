package com.example.Blogs.DAOs;

import com.example.Blogs.Exceptions.PostNotFoundException;
import com.example.Blogs.Models.Post;
import com.example.Blogs.ResultSetExtractors.CommentResultSetExtractor;
import com.example.Blogs.ResultSetExtractors.IdResultSetExtractor;
import com.example.Blogs.ResultSetExtractors.LikeResultSetExtractor;
import com.example.Blogs.ResultSetExtractors.PostResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PostDAOImplement extends DAO_Implementaion implements PostDAO {

    private  UserDAO userDAO;

    @Autowired
    public PostDAOImplement(JdbcTemplate jdbcTemplate, UserDAOImplement userDAO) {
        super(jdbcTemplate);
        this.userDAO = userDAO;
    }


    //Utility Methods
    private String DynamicINSql(Integer size) {
        StringBuilder sql =new StringBuilder("IN (");
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                sql.append("?");
            } else {
                sql.append("?,");
            }
        }
        sql.append(")");
        return sql.toString();
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM posts WHERE id = ?)";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, Boolean.class, id)
        );
    }

    private Map<Long, Post> MapPosts (String sql,Long id) {
        Map<Long, Post> postsMap = jdbcTemplate.query(sql, new PostResultSetExtractor(), id);
        assert postsMap != null;
        Map<Long, Post> postsWithLikesMap = jdbcTemplate.query(sql, new LikeResultSetExtractor(postsMap));
        return jdbcTemplate.query(sql, new CommentResultSetExtractor(postsWithLikesMap));
    }

    private Map<Long, Post> MapPosts (String sql,List<Long> ids) {
        Map<Long, Post> postsMap = jdbcTemplate.query(sql, new PostResultSetExtractor(), (Object) ids.toArray(new Long[0]));
        Map<Long, Post> postsWithLikesMap = jdbcTemplate.query(sql, new LikeResultSetExtractor(postsMap));
        return jdbcTemplate.query(sql, new CommentResultSetExtractor(postsWithLikesMap));
    }
    // /// ///
    //QUERIES
    private String SqlQueryForFindingASinglePost() {
        return "WITH likes AS (SELECT p.id AS post_id, " +
                "                           p.user_id AS post_user_id," +
                "                           p.body," +
                "                           p.text," +
                "                           p.created_at, " +
                "                           p.number_of_likes," +
                "                           p.number_of_comments, " +
                "                           l.id AS like_id, " +
                "                           l.user_id AS like_user_id," +
                "                           l.created_at AS like_created_at ," +
                "                           ROW_NUMBER() OVER (ORDER BY l.id) AS rn" +
                "                    FROM posts p LEFT OUTER JOIN likes l ON p.id = l.post_id WHERE p.id = ?)"+
                ",comments AS (SELECT c.id AS comment_id,  " +
                "                         c.user_id AS comment_user_id, " +
                "                         c.body," +
                "                         c.created_at AS comment_created_at," +
                "                         ROW_NUMBER() OVER (ORDER BY c.id) AS rn " +
                "                 FROM comments c WHERE c.post_id = ? )"+
                "SELECT " +
                "    post_user_id," +
                "    p.body," +
                "    p.text," +
                "    p.created_at, " +
                "    p.number_of_likes," +
                "    p.number_of_comments, " +
                "    l.like_id," +
                "    l.like_user_id," +
                "    l.like_created_at," +
                "    c.comment_id," +
                "    c.comment_user_id," +
                "    c.comment_body," +
                "    c.comment_created_at" +
                "FROM likes l" +
                "FULL OUTER JOIN comments c ON l.rn = c.rn;";
    }

    private String SqlQueryForFindingAllPostsIdsByUser() {
        return "SELECT id FROM posts WHERE user_id = ?";
    }

    private String SqlQueryForFindingAllPostsByUser(List<Long> ids) {
        return "SELECT * FROM posts p WHERE p.id " + DynamicINSql(ids.size());
    }







    @Override
    public Post findById(Long id) throws PostNotFoundException {
        if (existsById(id)) {
            String sql = SqlQueryForFindingASinglePost();
            Map<Long, Post> postsMap = MapPosts(sql,id);
            assert postsMap != null;
            return postsMap.get(id);
        } else {
            throw new PostNotFoundException("Post not found");
        }
    }

    private List<Post> findByPostsIds (List<Long> ids,String sql) {
        Map<Long, Post> postsMap = jdbcTemplate.query(sql, new PostResultSetExtractor(), (Object) ids.toArray(new Long[0]));
        Map<Long, Post> postsWithLikesMap = jdbcTemplate.query(sql, new LikeResultSetExtractor(postsMap));
        Map<Long, Post> postsWithCommentsMap = jdbcTemplate.query(sql, new CommentResultSetExtractor(postsWithLikesMap));
        assert postsWithCommentsMap != null;
        return postsWithCommentsMap.values().stream().toList();
    }

    @Override
    public List<Post> findByUser(Long userId) {
        if (userDAO.existsById(userId)) {
            String sqlForPostsIds = SqlQueryForFindingAllPostsIdsByUser();
            List<Long> ids= jdbcTemplate.query(sqlForPostsIds, new IdResultSetExtractor(), userId);
            assert ids != null;
            if (ids.isEmpty()) {
                return List.of();
            }
            return findByPostsIds(ids, SqlQueryForFindingAllPostsByUser(ids));
        }
        return null;
    }

    @Override
    public List<Post> findFollowingUsersPosts(Long userId) {
        return List.of();
    }



    @Override
    public int saveNewPost(Post post) {
        String sql = """
           INSERT into posts(body,title,user_id)
           VALUES (?,?,?);
           """;
        return jdbcTemplate.update(
                sql,
                post.getBody(),
                post.getTitle(),
                post.getUserId()
        );
    }

    @Override
    public Post updatePost(Post post) throws PostNotFoundException {
        String sql = """
                UPDATE posts
                SET body = ?,
                    title = ?
                WHERE id = ?;
                """;
        int rowsAffected = jdbcTemplate.update(sql, post.getBody(), post.getTitle(), post.getId());
        if (rowsAffected == 0) {
            throw new PostNotFoundException("Post with ID " + post.getId() + " not found.");
        }
        return post;
    }

    @Override
    public void deleteById(Long id) throws PostNotFoundException {

        String sql = "DELETE FROM posts WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new PostNotFoundException("Post with ID " + id + " not found.");
        }
    }




    @Override
    public List<Post> findPostsBySearchQuery(String searchQuery, List<Long> authorFilter, String minDate, String maxDate) {
        return List.of();
    }

    @Override
    public List<Post> findTopPostsDaily() {
        return List.of();
    }
}
