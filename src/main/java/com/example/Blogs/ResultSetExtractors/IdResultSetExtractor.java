package com.example.Blogs.ResultSetExtractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IdResultSetExtractor implements ResultSetExtractor<List<Long>> {
    @Override
    public List<Long> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Long> ids = new ArrayList<>();
        while (rs.next()) {
            ids.add(rs.getLong("id"));
        }
        return ids;
    }
}
