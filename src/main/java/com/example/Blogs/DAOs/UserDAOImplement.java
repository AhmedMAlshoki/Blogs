package com.example.Blogs.DAOs;

import com.example.Blogs.DAOs.SqlQueries.UserQueries;
import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Exceptions.ExictingUserException;
import com.example.Blogs.Exceptions.UserNotFoundException;
import com.example.Blogs.Models.User;
import com.example.Blogs.Mappers.ResultSetExtractors.ProfileResultSetExtractor;
import com.example.Blogs.Mappers.ResultSetExtractors.UserResultSetExtractor;
import com.example.Blogs.Mappers.RowMappers.UserRowMapper;
import com.example.Blogs.Utils.DAOUtilities.DAOUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class UserDAOImplement extends DAO_Implementation implements UserDAO  {


    private UserQueries userQueries;;
    private DAOUtilities daoUtilities;
    @Autowired
    public UserDAOImplement(JdbcTemplate jdbcTemplate, UserQueries userQueries
                            , DAOUtilities daoUtilities) {
        super(jdbcTemplate);
        this.userQueries = userQueries;
        this.daoUtilities = daoUtilities;
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
    public String saveNewUser(User user, Timezone timezone) throws ExictingUserException {
        if (existsByUsername(user.getUsername())||existsByEmail(user.getEmail())) {
            throw new ExictingUserException("User already exists with that username or email");
        }
        else{
            String sql = userQueries.insertQuery();
            jdbcTemplate.update(sql,
                    user.getUsername(),
                    user.getDisplayName(),
                    user.getEmail(),
                    user.getPassword(),
                    timezone.toString());
            return "User have registered successfully";
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
    public List<User> getByUserIds(List<Long> ids) {
        String sql = userQueries.getMultipleUsers();
        Object[] params = daoUtilities.preparingParamForTheQuery(ids);
        List<User> users = jdbcTemplate.query(sql, new UserResultSetExtractor(), params);
        assert users != null;
        if (!users.isEmpty()) return users;
        return List.of();
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
    public User update(User user, Timezone timezone) {
        if (existsById(user.getId())) {
            String sql = userQueries.updateQuery();
            jdbcTemplate.update(sql,
                    user.getDisplayName(),
                    user.getEmail(),
                    user.getPassword(),
                    timezone.toString(),
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

    @Override
    public String follow(Long followerId, Long followingId) {
        String sql = userQueries.followUser();
        try {
            jdbcTemplate.update(sql, followerId, followingId);
            return "User followed successfully";
        } catch (Exception e) {
            return "User already followed";
        }
    }

    @Override
    public String unfollow(Long followerId, Long followingId) {
        String sql = userQueries.unfollowUser();
        int result = jdbcTemplate.update(sql, followerId, followingId);
        if (result == 1) return "User unfollowed successfully";
        else return "No relationship found";
    }
}
