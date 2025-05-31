package com.example.Blogs.DAOs;

import com.example.Blogs.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
@Repository
public class UserDAOImplement extends DAO_Implementaion implements UserDAO  {


    @Autowired
    public UserDAOImplement(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Map<Long, User> findFollowers() {
        return Map.of();
    }

    @Override
    public Map<Long, User> findFollowing() {
        return Map.of();
    }

    //to use to get following' posts using an array of their ids in post dao
    @Override
    public Long[] findFollowingIds(Long userId) {
        return new Long[0];
    }

    @Override
    public User saveNewUser(User user) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE id = ?)";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, Boolean.class, id)
        );
    }

    @Override
    public User update(User user) {
        return null;
    }
}
