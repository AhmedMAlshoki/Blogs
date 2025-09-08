package com.example.Blogs.Services;


import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import jakarta.annotation.PreDestroy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

@Service
public class GeolocationService {

    private static final Logger logger = LoggerFactory.getLogger(GeolocationService.class);
    private DatabaseReader dbReader;
    Authentication authentication;


    public GeolocationService(@Value("${geoip.database.path:classpath:GeoLite2-City.mmdb}") String geoIpDatabasePath) {
        try {
            File database = new File(geoIpDatabasePath);
            if (!database.exists()) {
                // Try to load from classpath if direct file path fails
                database = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("GeoLite2-City.mmdb")).getFile());
            }
            this.dbReader = new DatabaseReader.Builder(database).build();
            logger.info("GeoIP2 database loaded successfully from: {}", database.getAbsolutePath());
        } catch (IOException | NullPointerException e) {
            logger.error("Could not load GeoIP2 database. Please ensure GeoLite2-City.mmdb is in src/main/resources/", e);
            this.dbReader = null; // Ensure dbReader is null if loading fails
        }

    }

    public CityResponse geolocate(String ipAddress) {
        if (dbReader == null) {
            logger.warn("GeoIP2 database not loaded. Cannot perform geolocation for IP: {}", ipAddress);
            return null;
        }
        try {
            InetAddress ip = InetAddress.getByName(ipAddress);
            return dbReader.city(ip);
        } catch (IOException | GeoIp2Exception e) {
            logger.error("Error geolocating IP address: {}", ipAddress, e);
            return null;
        }

    }

    @PreDestroy
    public void cleanup() {
        if (dbReader != null) {
            try {
                dbReader.close();
            } catch (IOException e) {
                logger.error("Error closing GeoIP database", e);
            }
        }
    }
}
