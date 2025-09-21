package com.example.Blogs.Utils.ApiUtils;

import com.example.Blogs.Enums.Timezone;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public  class ClientApiInfo {
    // Getters
    private final String clientApi;
    private final String version;
    private final String platform;
    private final String userAgent;
    private final Map<String, String> customHeaders;
    private final String country;
    private final String city;
    private final Timezone timezone;
    private final String ipAddress;

    private ClientApiInfo(Builder builder) {
        this.clientApi = builder.clientApi;
        this.version = builder.version;
        this.platform = builder.platform;
        this.userAgent = builder.userAgent;
        this.customHeaders = builder.customHeaders;
        this.country = builder.country;
        this.city = builder.city;
        this.timezone = builder.timezone;
        this.ipAddress = builder.ipAddress;
    }

    @Override
    public String toString() {
        return String.format("ClientApiInfo{clientApi=\\'%s\\', version=\\'%s\\', platform=\\'%s\\', userAgent=\\'%s\\',  country=\\'%s\\', city=\\'%s\\' timezone=%s, ipAddress=\\'%s\\', customHeaders=%s}",
                clientApi, version, platform, userAgent, country, city, timezone, ipAddress, customHeaders);
    }

    //public class displayIPasString() {
    //    return ipAddress;
    //}

    public static class Builder {
        private String clientApi;
        private String version;
        private String platform;
        private String userAgent;
        private String city;
        private String country;
        private Timezone timezone;
        private String ipAddress;
        private final Map<String, String> customHeaders = new HashMap<>();

        public Builder clientApi(String clientApi) {
            this.clientApi = clientApi;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder platform(String platform) {
            this.platform = platform;
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder city(String name) {
            this.city = name;
            return this;
        }

        public Builder timezone(Timezone timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder customHeader(String name, String value) {
            this.customHeaders.put(name, value);
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }


        public ClientApiInfo build() {
            // Only build if we have at least some information
            if (clientApi != null || version != null || platform != null ||
                    userAgent != null || !customHeaders.isEmpty() || country != null || city != null || timezone != null) {
                return new ClientApiInfo(this);
            }
            return null;
        }


    }
}
