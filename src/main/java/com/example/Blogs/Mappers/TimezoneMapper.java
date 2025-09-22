package com.example.Blogs.Mappers;

import com.example.Blogs.Enums.Timezone;
import java.util.Arrays;
import java.util.Optional;

public class TimezoneMapper {

    public static Optional<Timezone> mapMaxMindTimezoneToEnum(String maxMindTimezone) {
        if (maxMindTimezone == null || maxMindTimezone.isEmpty()) {
            return Optional.of(Timezone.UTC);
        }
        // MaxMind timezones are typically IANA Time Zone Database identifiers (e.g., "America/New_York").
        // Our custom enum also uses these, so a direct string comparison is often sufficient.
        return Arrays.stream(Timezone.values())
                .filter(tz -> tz.getZoneId().equalsIgnoreCase(maxMindTimezone))
                .findFirst();
    }
}
