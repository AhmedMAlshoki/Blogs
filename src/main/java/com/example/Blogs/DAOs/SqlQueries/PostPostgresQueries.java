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
                " l.id AS like_id, l.user_id AS like_user_id, l.created_at AS like_created_at";
    }


    @Override
    public  String existsById() {
        return "SELECT EXISTS(SELECT 1 FROM posts WHERE id = $1 )";
    }


    @Override
    public  String SqlQueryForFindingAllPostsByUser() {
        return  theStandardSelectStatement() +
                " FROM posts p LEFT JOIN likes l ON p.id = l.post_id" +
                " WHERE p.user_id = $1 ORDER BY p.created_at DESC";
    }

    @Override
    public  String SqlQueryForFindingOnePostOrMultiple() {
        return  theStandardSelectStatement() +
                "FROM posts p " +
                "LEFT JOIN likes l ON p.id = l.post_id WHERE p.id = ANY($1)";
    }




    @Override
    public String SQLQueryForTopPosts() {
        return  theStandardSelectStatement() +
                "FROM posts p LEFT JOIN likes l ON p.id = l.post_id" +
                "WHERE p.created_at > NOW() - INTERVAL '1 week' " +
                "GROUP BY p.id ORDER BY COUNT(l.post_id) DESC OFFSET $1 * 20  LIMIT 20";
    }

    @Override
    public String SQLQueryForCurrentUserFollowingPosts() { //TODO: implement this SQLQueryForCurrentUserFollowing
        return  theStandardSelectStatement() +
                "FROM posts p LEFT JOIN likes l ON p.id = l.post_id" +
                "INNER JOIN relationships r ON p.user_id = r.follower_id" +
                "WHERE r.follower_id = $1 ORDER BY p.created_at DESC";
    }

    @Override
    public String SQLQueryForPostSearch() {
        return "SELECT search_articles($1, $2, $3, $4, $5, $6);";  //user defined function in schema.sql file
    }

    @Override
    public  String insertQuery() {
        return "INSERT INTO posts (user_id, body, title ,created_timezone) VALUES ($1 , $2, $3, $4);";
    }

    @Override
    public  String updateQuery() {
        return "UPDATE posts SET body = $1,title = $2, updated_timezone = $3 WHERE id = $4;" ;
    }

    @Override
    public  String deleteQuery() {
        return "DELETE FROM posts WHERE id = $1;";
    }

    @Override
    public String likePostQuery() {
        return "INSERT INTO likes (user_id, post_id, created_timezone) VALUES ($1, $2, $3);";
    }

    @Override
    public String dislikePostQuery() {
        return "DELETE FROM likes WHERE user_id = $1 AND post_id = $2;";
    }
}
