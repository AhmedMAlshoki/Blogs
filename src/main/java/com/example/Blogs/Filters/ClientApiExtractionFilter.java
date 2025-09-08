package com.example.Blogs.Filters;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Filters.Wrappers.CachedBodyHttpServletRequest;
import com.example.Blogs.Mappers.TimezoneMapper;
import com.example.Blogs.Utils.ApiUtils.ClientApiInfo;
import com.example.Blogs.Utils.ApiUtils.IPExtractor;
import com.example.Blogs.Services.GeolocationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Optional;

@Order(2)
public class ClientApiExtractionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ClientApiExtractionFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    Authentication authentication;

    @Autowired
    private GeolocationService geolocationService;

    private static final String[] CLIENT_API_HEADERS = {
            "X-Client-Api",
            "X-Api-Client",
            "Client-Api",
            "Api-Version",
            "X-Client-Version",
            "User-Agent"
    };
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isGraphQLRequest(httpRequest)) {
            try {
                // Extract client API information
                ClientApiInfo clientApiInfo = extractClientApiInfo(httpRequest);

                if (clientApiInfo != null) {
                    // Log the client API information
                    logger.info("GraphQL Request - Client API Info: {}", clientApiInfo);

                    // Add client API info as request attribute for downstream processing
                    httpRequest.setAttribute("CLIENT_API_INFO", clientApiInfo);

                    // Optionally add response headers
                    httpResponse.setHeader("X-Processed-By", "ClientApiExtractionFilter");
                }
                authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication instanceof AdvancedEmailPasswordToken advancedToken) {
                    advancedToken.setClientApiInfo(clientApiInfo);
                    logger.debug("Enhanced authentication token with client API info for user: {}",
                            authentication.getPrincipal());
                } else {
                    logger.warn("Authentication is not AdvancedEmailPasswordToken type: {}",
                            authentication != null ? authentication.getClass().getSimpleName() : "null");
                }

            } catch (Exception e) {
                logger.error("Error extracting client API information", e);
                // Continue with the filter chain even if extraction fails
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    private boolean isGraphQLRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        String requestURI = request.getRequestURI();

        return "POST".equals(request.getMethod()) &&
                (requestURI.contains("/graphql") || requestURI.contains("/api/graphql")) &&
                (contentType != null && contentType.contains("application/json"));
    }

    private ClientApiInfo extractClientApiInfo(HttpServletRequest request) throws IOException {
        ClientApiInfo.Builder builder = new ClientApiInfo.Builder();

        // 1. Extract from HTTP headers
        extractFromHeaders(request, builder);

        // 2. Extract from GraphQL request body (if it\'s already cached)
        if (request instanceof CachedBodyHttpServletRequest cachedRequest) {
            extractFromRequestBody(cachedRequest, builder);
        }

        // 3. Extract from query parameters (if any)
        extractFromQueryParams(request, builder);

        // 4. Extract IP address and perform geolocation
        String clientIp = IPExtractor.getClientIpAddress(request);
        builder.ipAddress(clientIp);
        if (clientIp != null && !clientIp.isEmpty()) {
            CityResponse geoResponse = geolocationService.geolocate(clientIp);
            if (geoResponse != null) {
                if (geoResponse.getCountry() != null && geoResponse.getCountry().getName() != null) {
                    builder.country(geoResponse.getCountry().getName());
                }
                if (geoResponse.getCity() != null && geoResponse.getCity().getName() != null) {
                    builder.country(geoResponse.getCity().getName());
                }
                if (geoResponse.getLocation() != null && geoResponse.getLocation().getTimeZone() != null) {
                    Optional<Timezone> mappedTimezone = TimezoneMapper.mapMaxMindTimezoneToEnum(geoResponse.getLocation().getTimeZone());
                    mappedTimezone.ifPresent(builder::timezone);
                }
            }
        }

        return builder.build();
    }

    private void extractFromHeaders(HttpServletRequest request, ClientApiInfo.Builder builder) {
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

        // Extract additional custom headers that start with X-Client-
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName.toLowerCase().startsWith("x-client-")) {
                builder.customHeader(headerName, request.getHeader(headerName));
            }
        }
    }

    private void extractFromRequestBody(CachedBodyHttpServletRequest request, ClientApiInfo.Builder builder)
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

    private void extractFromQueryParams(HttpServletRequest request, ClientApiInfo.Builder builder) {
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