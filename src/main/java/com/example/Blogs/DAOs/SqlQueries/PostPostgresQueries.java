package com.example.Blogs.DAOs.SqlQueries;

import com.example.Blogs.DAOs.DAOUtilities.DAOUtilities;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public  class  PostPostgresQueries extends PostQueries {

    private DAOUtilities daoUtilities;
    @Autowired
    public PostPostgresQueries(DAOUtilities daoUtilities) {
        super();
        this.daoUtilities=daoUtilities;
    }


    @Override
    public  String existsById(Long id) {
        return "SELECT EXISTS(SELECT 1 FROM posts WHERE id = $1 )";
    }

    @Override
    public  String SqlQueryForFindingAllPostsIdsByUser() {
        return "SELECT id FROM posts WHERE user_id = $1 ";
    }

    @Override
    public  String SqlQueryForFindingMultiplePosts(Object[] ids) {
        return "WITH posts_filtered AS (\n" +
                "    SELECT * FROM posts WHERE id =ANY($1) " +
                "),\n" +
                "likes_ AS (\n" +
                "    SELECT * FROM likes WHERE post_id  IN (SELECT id FROM posts_filtered) " +
                ")\n" +
                "SELECT posts_filtered.id, posts_filtered.user_id,posts_filtered.body,posts_filtered.title,posts_filtered.created_at,\n" +
                "likes_.user_id AS likes_user_id , DENSE_RANK() OVER (PARTITION BY posts_filtered.id ORDER BY likes_.created_at) AS rank\n" +
                "FROM posts_filtered  LEFT JOIN likes_  ON posts_filtered.id = likes_.post_id\n";
    }


    @Override
    public String SQLQueryForTopPostsIds(Integer Offset) {
        return "SELECT p.id FROM posts p LEFT JOIN likes l ON p.id = l.post_id" +
                " WHERE p.created_at > NOW() - INTERVAL '1 week'  GROUP BY p.id ORDER BY COUNT(l.post_id) DESC OFFSET $1 * 20  LIMIT 20";
    }

    @Override
    public String SQLQueryForCurrentUserFollowingPosts(Long id) { //TODO: implement this SQLQueryForCurrentUserFollowing
        return "SELECT p.id FROM post p INNER JOIN relationships r ON p.user_id = r.following_id " +
                "WHERE r.follower_id = $1 GROUP BY p.id ORDER BY p.created_at DESC";
    }

    @Override
    public String SQLQueryForPostSearch(String searchQuery, List<Long> authorFilter, String minDate, String maxDate,
                                        Integer limit, Integer offset) {
        return "SELECT search_articles($1, $2, $3, $4, $5, $6);";  //user defined function in schema.sql file
    }

    @Override
    public  String insertQuery() {
        return "INSERT INTO posts (user_id, body, title) VALUES ($1 , $2, $3);";
    }

    @Override
    public  String updateQuery() {
        return "UPDATE posts SET body = $1,title = $2,WHERE id = $3;" ;
    }

    @Override
    public  String deleteQuery() {
        return "DELETE FROM posts WHERE id = $1;";
    }
}
