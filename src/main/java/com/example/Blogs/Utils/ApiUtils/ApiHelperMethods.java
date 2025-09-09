package com.example.Blogs.Utils.ApiUtils;


import com.example.Blogs.Exceptions.HandlingRequestException;
import com.example.Blogs.Filters.ClientApiExtractionFilter;
import com.example.Blogs.Filters.Wrappers.CachedBodyHttpServletRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.GraphQlRequest;
import org.springframework.graphql.support.DefaultGraphQlRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;

//yo contain all helper methods for api , like isGraphRequest , getRequestHeader etc
public class ApiHelperMethods {


    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ClientApiExtractionFilter.class);
    private static final String[] CLIENT_API_HEADERS = {
            "X-Client-Api",
            "X-Api-Client",
            "Client-Api",
            "Api-Version",
            "X-Client-Version",
            "User-Agent"
    };

    public   boolean isGraphQLRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        String requestURI = request.getRequestURI();

        return "POST".equals(request.getMethod()) &&
                (requestURI.contains("/graphql") || requestURI.contains("/api/graphql")) &&
                (contentType != null && contentType.contains("application/json"));
    }

    public  String getRequestBody(HttpServletRequest request) throws IOException {
        // Create a custom wrapper to read the body multiple times
        if (!(request instanceof CachedBodyHttpServletRequest cachedRequest)) {
            throw new IllegalArgumentException("Request must be wrapped with CachedBodyHttpServletRequest");
        }

        return new String(cachedRequest.getCachedBody(), StandardCharsets.UTF_8);
    }

    public boolean isLoginMutation(String requestBody) {
        try {
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            String query = jsonNode.path("query").asText();

            // Check if the query contains the login mutation
            // This is a simple check - you might want to make it more sophisticated
            return query.contains("mutation") &&
                    (query.contains("login(") || query.contains("login "));

        } catch (Exception e) {
            return false;
        }
    }

    public  GraphQlRequest parseGraphQLRequest(String requestBody) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(requestBody);

        String document = jsonNode.path("query").asText();
        Map<String, Object> variables =
                objectMapper.convertValue(jsonNode.path("variables"),
                        new TypeReference<Map<String, Object>>() {});
        String operationName = jsonNode.path("operationName").asText(null);

        return new DefaultGraphQlRequest(document, operationName, variables , null);
    }

    public  String extractEmail(GraphQlRequest request) throws HandlingRequestException {
        try {
            Map<String, Object> variables = request.getVariables();
            return (String) variables.get("email");
        } catch (Exception e) {
            throw new HandlingRequestException("Failed to extract email from request");
        }
    }

    public  String extractPassword(GraphQlRequest request) throws HandlingRequestException {
        try {
            Map<String, Object> variables = request.getVariables();
            return (String) variables.get("password");
        } catch (Exception e) {
            throw new HandlingRequestException("Failed to extract password from request");
        }
    }

    public void extractFromHeaders(HttpServletRequest request, ClientApiInfo.Builder builder) {
        for (String headerName : CLIENT_API_HEADERS) {
            String headerValue = request.getHeader(headerName);
            if (headerValue != null && !headerValue.trim().isEmpty()) {
                switch (headerName.toLowerCase()) {
                    case "x-client-api":
                    case "x-api-client":
                    case "client-api":
                        builder.clientApi(headerValue);
                        break;
                    case "api-version":
                    case "x-client-version":
                        builder.version(headerValue);
                        break;
                    case "user-agent":
                        builder.userAgent(headerValue);
                        break;
                }
            }
        }
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName.toLowerCase().startsWith("x-client-")) {
                builder.customHeader(headerName, request.getHeader(headerName));
            }
        }
    }

    public void extractFromRequestBody(CachedBodyHttpServletRequest request, ClientApiInfo.Builder builder)
            throws IOException {
        try {
            String requestBody = new String(request.getCachedBody(), StandardCharsets.UTF_8);
            JsonNode jsonNode = objectMapper.readTree(requestBody);

            // Check for client API info in the extensions field (common GraphQL practice)
            JsonNode extensions = jsonNode.path("extensions");
            if (!extensions.isMissingNode()) {
                JsonNode clientInfo = extensions.path("clientInfo");
                if (!clientInfo.isMissingNode()) {
                    if (clientInfo.has("api")) {
                        builder.clientApi(clientInfo.path("api").asText());
                    }
                    if (clientInfo.has("version")) {
                        builder.version(clientInfo.path("version").asText());
                    }
                    if (clientInfo.has("platform")) {
                        builder.platform(clientInfo.path("platform").asText());
                    }
                }
            }

            // Check variables for client info
            JsonNode variables = jsonNode.path("variables");
            if (!variables.isMissingNode()) {
                if (variables.has("clientApi")) {
                    builder.clientApi(variables.path("clientApi").asText());
                }
                if (variables.has("clientVersion")) {
                    builder.version(variables.path("clientVersion").asText());
                }
            }

        } catch (Exception e) {
            logger.warn("Failed to parse GraphQL request body for client API info", e);
        }
    }

    public void extractFromQueryParams(HttpServletRequest request, ClientApiInfo.Builder builder) {
        String clientApi = request.getParameter("clientApi");
        if (clientApi != null) {
            builder.clientApi(clientApi);
        }

        String version = request.getParameter("version");
        if (version != null) {
            builder.version(version);
        }

        String platform = request.getParameter("platform");
        if (platform != null) {
            builder.platform(platform);
        }
    }
}
