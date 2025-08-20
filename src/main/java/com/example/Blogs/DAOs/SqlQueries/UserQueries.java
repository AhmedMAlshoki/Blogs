package com.example.Blogs.DAOs.SqlQueries;

public abstract class UserQueries {
    public abstract String existsById();
    public abstract String existsByUsername();
    public abstract String existsByEmail();
    public abstract String insertQuery();
    public abstract String updateQuery();
    public abstract String deleteQuery();
    public abstract String findById();
    public abstract String findByUsername();
    public abstract String findByEmail();
    public abstract String findFollowers();
    public abstract String findFollowing();
    public abstract String getUserProfile();
    public abstract String getUserCredential ();
    public abstract String getMultipleUsers();
}
