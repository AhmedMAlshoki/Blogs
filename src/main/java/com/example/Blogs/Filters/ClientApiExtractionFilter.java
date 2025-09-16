package com.example.Blogs.Filters;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Filters.Wrappers.CachedBodyHttpServletRequest;
import com.example.Blogs.Mappers.TimezoneMapper;
import com.example.Blogs.Utils.ApiUtils.ApiHelperMethods;
import com.example.Blogs.Utils.ApiUtils.ClientApiInfo;
import com.example.Blogs.Utils.ApiUtils.IPExtractor;
import com.example.Blogs.Services.GeolocationService;
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
import java.util.Optional;

@Order(4)
public class ClientApiExtractionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ClientApiExtractionFilter.class);
    private final ApiHelperMethods apiHelperMethods = new ApiHelperMethods();
    Authentication authentication;
    @Autowired
    private GeolocationService geolocationService;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (apiHelperMethods.isGraphQLRequest(httpRequest)) {
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

    private ClientApiInfo extractClientApiInfo(HttpServletRequest request) throws IOException {
        ClientApiInfo.Builder builder = new ClientApiInfo.Builder();

        // 1. Extract from HTTP headers
        apiHelperMethods.extractFromHeaders(request, builder);

        // 2. Extract from GraphQL request body (if it\'s already cached)
        if (request instanceof CachedBodyHttpServletRequest cachedRequest) {
            apiHelperMethods.extractFromRequestBody(cachedRequest, builder);
        }

        // 3. Extract from query parameters (if any)
        apiHelperMethods.extractFromQueryParams(request, builder);

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

}