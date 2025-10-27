package com.example.Blogs.Utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Data
public class TimeDateUtil {
    private DateTimeFormatter formatter;
    public TimeDateUtil() {
        this.formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .optionalStart()
                .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                .optionalEnd()
                .toFormatter();
    }

    public LocalDateTime formatLocalDateTime(String date){
        return LocalDateTime.parse(date, formatter);
    }

    public Instant formatInstant(String date){
        return Instant.parse(date);
    }

    public OffsetDateTime formatOffsetDateTime(String date){
        try{
            LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
            return OffsetDateTime.of(localDateTime, ZoneId.systemDefault().getRules().getOffset(localDateTime));
        }catch (Exception e){
            Instant instant =formatInstant(date);
            return OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }
}
