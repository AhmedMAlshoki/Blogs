package com.example.Blogs.Mappers.ResultSetExtractors;


import com.example.Blogs.CustomResponses.SearchQueryResult;
import com.example.Blogs.Models.SearchQueryPost;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PGobject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class SearchQueryPostsResultSetExtractor implements ResultSetExtractor<SearchQueryResult> {
    @Override
    public SearchQueryResult extractData(ResultSet rs) throws SQLException, DataAccessException {
        SearchQueryResult postsFinalResult = new SearchQueryResult();
        //postsFinalResult.setTotal(rs.getLong("total_count"));
        List<SearchQueryPost> posts = new ArrayList<>();
        log.info("Start Extracting Search Query Posts ");
        log.info("the result set is : {}",rs.getMetaData().getColumnName(1));
        while (rs.next()){
            log.info("Extracting Post : {}", rs.getObject("search_articles",PGobject.class).getValue());
            PGobject pgobject = rs.getObject("search_articles",PGobject.class);
            String rowString = pgobject.getValue();
            Object[] fields = prepareFields(rowString);
            SearchQueryPost post = new SearchQueryPost(
                    (Long)fields[0],
                    (String)fields[1],
                    (String)fields[2],
                    (Long)fields[3],
                    (String) fields[4],
                    (OffsetDateTime)fields[5],
                    (Float)fields[6],
                    (String)fields[7]);
            posts.add(post);
        }
        postsFinalResult.setSearchQueryPosts(posts);
        postsFinalResult.setTotal(posts.size());
        log.info("The final Number of posts {} ",posts.size());
        log.info("the first post info {}",posts.getFirst());
        return postsFinalResult;
    }

    private  Object[] prepareFields(String rowString){
        log.info("the fields as String : {}", rowString);
        rowString = Objects.requireNonNull(rowString).substring(1,rowString.length()-1);
        log.info("the fields as String after excluding () : {}", rowString);
        String[] fields = rowString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        Object[] adjustedFields = new Object[8];
        adjustedFields[0] = Long.parseLong(fields[0]);
        adjustedFields[1] = fields[1];
        adjustedFields[2] = fields[2];
        adjustedFields[3] = Long.parseLong(fields[3]);
        adjustedFields[4] = fields[4];
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .optionalStart()
                .appendFraction(ChronoField.NANO_OF_SECOND, 0, 6, true)
                .optionalEnd()
                .toFormatter();
        LocalDateTime dataBaseDate = LocalDateTime.parse(fields[5].substring(1,fields[5].length()-1),
                 formatter);
        adjustedFields[5] = OffsetDateTime.of(dataBaseDate, ZoneId.systemDefault().getRules().getOffset(dataBaseDate));
        adjustedFields[6] = Float.parseFloat(fields[6]);
        adjustedFields[7] = fields[7];
        return adjustedFields;
    }

}

