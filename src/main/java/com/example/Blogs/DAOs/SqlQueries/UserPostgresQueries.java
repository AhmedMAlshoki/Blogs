package com.example.Blogs.DAOs.SqlQueries;

public class UserPostgresQueries extends UserQueries{
    @Override
    public String existsById() {
        return "SELECT EXISTS(SELECT 1 FROM users WHERE id = $1 )";
    }

    @Override
    public String existsByUsername() {
        return "SELECT EXISTS(SELECT 1 FROM users WHERE username = $1 )";
    }

    @Override
    public String existsByEmail() {
        return "SELECT EXISTS(SELECT 1 FROM users WHERE email = $1 )";
    }

    @Override
    public String insertQuery() {
        return "INSERT INTO users (username, password, email, display_name, created_timezone) VALUES ($1 , $2, $3, $4, $5);";
    }

    @Override
    public String updateQuery() {
        return "UPDATE users SET username = $1, password = $2, email = $3, display_name = $4, updated_timezone = $5" +
                " WHERE id = $6;";
    }

    @Override
    public String deleteQuery() {
        return "DELETE FROM users WHERE id = $1;";
    }

    @Override
    public String findById() {
        return "SELECT id, username, display_name FROM users WHERE id = $1;";
    }

    @Override
    public String findByUsername() {
        return "SELECT id, username, display_name FROM users WHERE username = $1;";
    }

    @Override
    public String findByEmail() {
        return "SELECT id, email, password FROM users WHERE email = $1;";
    }

    @Override
    public String findFollowers() {
        return "SELECT u.id, u.username, u.display_name FROM users u " +
                "INNER JOIN relationships r ON  u.id = r.following_id " +
                "WHERE r.following_id = $1;";
    }

    @Override
    public String findFollowing() {
        return "SELECT u.id, u.username, u.display_name FROM users u " +
                "INNER JOIN relationships r ON  u.id = r.follower_id " +
                "WHERE r.follower_id = $1;";
    }

    @Override
    public String getUserProfile() {
        return "SELECT u.id AS user_id, u.username, u.display_name, u.created_at ," +
                " p.id AS post_id, p.title, p.body, p.created_at AS published_at FROM users u " +
                "LEFT JOIN  posts p ON u.id = p.user_id WHERE u.id = $1 ORDER BY p.created_at DESC";
    }

    @Override
    public String getUserCredential() {
        return "SELECT id, email, password FROM users WHERE email = $1;";
    }

    @Override
    public String getMultipleUsers() {
        return "SELECT id,username,display_name,created_at FROM users WHERE id = ANY($1);";
    }

    @Override
    public String followUser() {
        return "INSERT INTO relationships (follower_id, following_id) VALUES ($1, $2);";
    }

    @Override
    public String unfollowUser() {
        return "DELETE FROM relationships WHERE follower_id = $1 AND following_id = $2;";
    }

    @Override
    public String findByIdUserDetails() {
        return "SELECT id, email, password FROM users WHERE id = $1;";
    }
}
