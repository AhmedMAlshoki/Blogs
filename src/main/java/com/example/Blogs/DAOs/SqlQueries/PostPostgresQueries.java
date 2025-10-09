package com.example.Blogs.DAOs.SqlQueries;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public  class  PostPostgresQueries extends PostQueries {

    @Autowired
    public PostPostgresQueries() {
        super();
    }

    private String theStandardSelectStatement(){
        return "SELECT p.id AS post_id, p.user_id AS post_user_id, p.body, p.title, p.created_at," +
                " l.id AS like_id, l.user_id AS like_user_id, l.created_at AS like_created_at ";
    }




    @Override
    public  String existsById() {
        return "SELECT EXISTS(SELECT 1 FROM posts WHERE id = ? )";
    }


    @Override
    public  String SqlQueryForFindingAllPostsByUser() {
        return  theStandardSelectStatement() +
                " FROM posts p LEFT JOIN likes l ON p.id = l.post_id" +
                " WHERE p.user_id = ? ORDER BY p.created_at DESC";
    }

    @Override
    public  String SqlQueryForFindingOnePostOrMultiple() {
        return  theStandardSelectStatement() +
                "FROM posts p " +
                "LEFT JOIN likes l ON p.id = l.post_id WHERE p.id = ANY(?)";
    }




    @Override
    public String SQLQueryForTopPosts() {
        return  theStandardSelectStatement() +
                "FROM posts p LEFT JOIN likes l ON p.id = l.post_id " +
                "WHERE p.created_at > NOW() - INTERVAL '1 week' " +
                "GROUP BY p.id , l.id ORDER BY p.score DESC  LIMIT 2000";
    }

    @Override
    public String getTopPostsOffsetQuery() {
        return  theStandardSelectStatement() +
                "FROM posts p LEFT JOIN likes l ON p.id = l.post_id" +
                "WHERE p.created_at > NOW() - INTERVAL '1 week' " +
                "GROUP BY p.id , l.id ORDER BY p.score DESC  LIMIT 20 OFFSET ? * 20";
    }

    @Override
    public String SQLQueryForCurrentUserFollowingPosts() { //TODO: implement this SQLQueryForCurrentUserFollowing
        return  theStandardSelectStatement() +
                "FROM posts p LEFT JOIN likes l ON p.id = l.post_id" +
                "INNER JOIN relationships r ON p.user_id = r.follower_id " +
                "WHERE r.follower_id = ? GROUP BY p.id , l.id ORDER BY p.created_at DESC";
    }

    @Override
    public String SQLQueryForPostSearch() {
        return "SELECT search_articles(?, ?, ?, ?, ?, ?);";
    }

    @Override
    public  String insertQuery() {
        return "INSERT INTO posts (user_id, body, title ,created_timezone) VALUES (? , ?, ?, ?);";
    }

    @Override
    public  String updateQuery() {
        return "UPDATE posts SET body = ?,title = ?, updated_timezone = ? WHERE id = ?;" ;
    }

    @Override
    public  String deleteQuery() {
        return "DELETE FROM posts WHERE id = ?;";
    }

    @Override
    public String likePostQuery() {
        return "INSERT INTO likes (user_id, post_id, created_timezone) VALUES (?, ?, ?);";
    }

    @Override
    public String dislikePostQuery() {
        return "DELETE FROM likes WHERE user_id = ? AND post_id = ?;";
    }

    @Override
    public String getPostOwnerQuery() {
        return "SELECT user_id FROM posts WHERE id = ?;";
    }



}
