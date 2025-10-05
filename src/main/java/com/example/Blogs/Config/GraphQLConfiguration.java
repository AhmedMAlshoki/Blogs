package com.example.Blogs.Config;


import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Configuration
public class GraphQLConfiguration {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        GraphQLScalarType scalarType = offsetDateTimeScalar();
        return wiringBuilder -> wiringBuilder
                .scalar(scalarType);
    }

    @Bean
    public GraphQLScalarType offsetDateTimeScalar() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("OffsetDateTime as a GraphQL scalar.")
                .coercing(new Coercing<OffsetDateTime, String>() {
                    @Override
                    public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext context, @NotNull Locale locale) {
                        if (dataFetcherResult instanceof OffsetDateTime dateTime) {
                            return formatter.format(dateTime);
                        }
                        throw new CoercingSerializeException("Expected a OffsetDateTime object.");
                    }
                    @Override
                    public OffsetDateTime parseValue(@NotNull Object input, @NotNull GraphQLContext context, @NotNull Locale locale) {
                        if (input instanceof String dateString) {
                            try {
                                return OffsetDateTime.parse(dateString, formatter);
                            } catch (DateTimeParseException e) {
                                throw new CoercingParseValueException("Invalid date format: " + dateString, e);
                            }
                        }
                        throw new CoercingParseValueException("Expected a String");
                    }
                    @Override
                    public OffsetDateTime parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables, @NotNull GraphQLContext context, @NotNull Locale locale) {
                        if (input instanceof StringValue stringValue) {
                            try {
                                return OffsetDateTime.parse(stringValue.getValue(), formatter);
                            } catch (DateTimeParseException e) {
                                throw new CoercingParseLiteralException("Invalid date literal: " + stringValue.getValue(), e);
                            }
                        }
                        throw new CoercingParseLiteralException("Expected a StringValue.");
                    }
                }).build();
    }
}
