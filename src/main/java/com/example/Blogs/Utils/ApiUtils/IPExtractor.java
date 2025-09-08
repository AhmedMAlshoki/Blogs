package com.example.Blogs.Utils.ApiUtils;

import jakarta.servlet.http.HttpServletRequest;

public class IPExtractor {

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                // For X-Forwarded-For, it can be a comma-separated list of IPs. The first one is the client's IP.
                if (header.equalsIgnoreCase("X-Forwarded-For")) {
                    int commaIndex = ip.indexOf(',');
                    if (commaIndex > 0) {
                        return ip.substring(0, commaIndex).trim();
                    }
                }
                return ip.trim();
            }
        }
        return request.getRemoteAddr();
    }
}
