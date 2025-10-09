package com.example.Blogs.DAOs.SqlQueries;

public class UserPostgresQueries extends UserQueries{
    @Override
    public String existsById() {
        return "SELECT EXISTS(SELECT 1 FROM users WHERE id = ? )";
    }

    @Override
    public String existsByUsername() {
        return "SELECT EXISTS(SELECT 1 FROM users WHERE username = ? )";
    }

    @Override
    public String existsByEmail() {
        return "SELECT EXISTS(SELECT 1 FROM users WHERE email = ? )";
    }

    @Override
    public String insertQuery() {
        return "INSERT INTO users (username, password, email, display_name, created_timezone) VALUES (? , ?, ?, ?, ?);";
    }

    @Override
    public String updateQuery() {
        return "UPDATE users SET username = ?, password = ?, email = ?, display_name = ?, updated_timezone = ?" +
                " WHERE id = ?;";
    }

    @Override
    public String deleteQuery() {
        return "DELETE FROM users WHERE id = ?;";
    }

    @Override
    public String findById() {
        return "SELECT id, username, display_name FROM users WHERE id = ?;";
    }

    @Override
    public String findByUsername() {
        return "SELECT id, username, display_name FROM users WHERE username = ?;";
    }

    @Override
    public String findByEmail() {
        return "SELECT id, email, password FROM users WHERE email = ?;";
    }

    @Override
    public String findFollowers() {
        return "SELECT u.id, u.username, u.display_name FROM users u " +
                "INNER JOIN relationships r ON  u.id = r.following_id " +
                "WHERE r.following_id = ?;";
    }

    @Override
    public String findFollowing() {
        return "SELECT u.id, u.username, u.display_name FROM users u " +
                "INNER JOIN relationships r ON  u.id = r.follower_id " +
                "WHERE r.follower_id = ?;";
    }

    @Override
    public String getUserProfile() {
        return "SELECT u.id AS user_id, u.username, u.display_name, u.created_at ," +
                " p.id AS post_id, p.title, p.body, p.created_at AS published_at FROM users u " +
                "LEFT JOIN  posts p ON u.id = p.user_id WHERE u.id = ? ORDER BY p.created_at DESC";
    }

    @Override
    public String getUserCredential() {
        return "SELECT id, email, password FROM users WHERE email = ?;";
    }

    @Override
    public String getMultipleUsers() {
        return "SELECT id,username,display_name,created_at FROM users WHERE id = ANY(?);";
    }

    @Override
    public String followUser() {
        return "INSERT INTO relationships (follower_id, following_id) VALUES (?, ?);";
    }

    @Override
    public String unfollowUser() {
        return "DELETE FROM relationships WHERE follower_id = ? AND following_id = ?;";
    }

    @Override
    public String findByIdUserDetails() {
        return "SELECT id, email, password FROM users WHERE id = ?;";
    }
}
