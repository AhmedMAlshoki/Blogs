package com.example.Blogs.DAOs;

import com.example.Blogs.DAOs.DAOUtilities.DAOUtilities;
import com.example.Blogs.DAOs.SqlQueries.UserQueries;
import com.example.Blogs.Exceptions.ExictingUserException;
import com.example.Blogs.Exceptions.UserNotFoundException;
import com.example.Blogs.Models.User;
import com.example.Blogs.ResultSetExtractors.ProfileResultSetExtractor;
import com.example.Blogs.ResultSetExtractors.UserResultSetExtractor;
import com.example.Blogs.RowMappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public class UserDAOImplement extends DAO_Implementaion implements UserDAO  {


    private UserQueries userQueries;;
    @Autowired
    public UserDAOImplement(JdbcTemplate jdbcTemplate, UserQueries userQueries) {
        super(jdbcTemplate);
        this.userQueries = userQueries;
    }

    @Override
    public Optional<User> findByUsername(String username) throws UserNotFoundException {
        if (existsByUsername(username)) {
            String sql = userQueries.findByUsername();
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
            return Optional.ofNullable(user);
        }
        else throw new UserNotFoundException("User not found");
    }

    @Override
    public Optional<User> findByEmail(String email) throws UserNotFoundException{
        if (existsByEmail(email)){
            String sql = userQueries.findByEmail();
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);
            return Optional.ofNullable(user);
        }
        else throw new UserNotFoundException("User not found");
    }

    @Override
    public Optional<User> findById(Long id) throws UserNotFoundException {
        if (existsById(id)) {
            String sql = userQueries.findById();
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
            return Optional.ofNullable(user);
        }
        else throw new UserNotFoundException("User not found");
    }

    @Override
    public List<User> findFollowers(Long userId)throws UserNotFoundException {
        if (existsById(userId)) {
            String sql = userQueries.findFollowers();
            return jdbcTemplate.query(sql, new UserResultSetExtractor(), userId);
        }
        else throw new UserNotFoundException("User not found");
    }

    @Override
    public List<User> findFollowing(Long userId) throws UserNotFoundException {
        if (existsById(userId)) {
            String sql = userQueries.findFollowing();
            return jdbcTemplate.query(sql, new UserResultSetExtractor(), userId);
        }
        else throw new UserNotFoundException("User not found");
    }


    @Override
    public User saveNewUser(User user) throws ExictingUserException {
        if (existsByUsername(user.getUsername())||existsByEmail(user.getEmail())) {
            throw new ExictingUserException("User already exists with that username or email");
        }
        else{
            String sql = userQueries.insertQuery();
            jdbcTemplate.update(sql,
                    user.getUsername(),
                    user.getDisplayName(),
                    user.getEmail(),
                    user.getPassword());
            return user;
        }
    }

    @Override
    public String deleteById(Long id) throws UserNotFoundException {
        if (existsById(id))
        {
            String sql = userQueries.deleteQuery();
            jdbcTemplate.update(sql, id);
            return "User deleted successfully";
        }
        else throw new UserNotFoundException("User not found");
    }

    @Override
    public boolean existsById(Long id) {
        String sql = userQueries.existsById();
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, Boolean.class, id)
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = userQueries.existsByUsername();
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, Boolean.class, username)
        );
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = userQueries.existsByEmail();
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, Boolean.class, email)
        );
    }


    @Override
    public User update(User user) {
        if (existsById(user.getId())) {
            String sql = userQueries.updateQuery();
            jdbcTemplate.update(sql,
                    user.getDisplayName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getId());
            return user;
        }
        else throw new UserNotFoundException("User not found");
    }

    @Override
    public User getUserWithPosts(Long userId) throws UserNotFoundException {
        if (existsById(userId)) {
            String sql = userQueries.getUserProfile();
            return jdbcTemplate.query(sql, new ProfileResultSetExtractor(), userId);
        }
        else throw new UserNotFoundException("User not found");
    }

    @Override
    public User getUserCredential(String email) throws UserNotFoundException {
        if (existsByEmail(email)) {
            String sql = userQueries.getUserCredential();
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }
}
