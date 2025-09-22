package com.example.Blogs.Enums;

import lombok.Getter;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;

public enum Timezone {
    UTC("UTC"),
    US_EASTERN("US/Eastern"),
    US_CENTRAL("US/Central"),
    US_MOUNTAIN("US/Mountain"),
    US_PACIFIC("US/Pacific"),
    EUROPE_LONDON("Europe/London"),
    EUROPE_PARIS("Europe/Paris"),
    EUROPE_BERLIN("Europe/Berlin"),
    EUROPE_ROME("Europe/Rome"),
    EUROPE_MOSCOW("Europe/Moscow"),
    ASIA_TOKYO("Asia/Tokyo"),
    ASIA_SHANGHAI("Asia/Shanghai"),
    ASIA_KOLKATA("Asia/Kolkata"),
    ASIA_DUBAI("Asia/Dubai"),
    AUSTRALIA_SYDNEY("Australia/Sydney"),
    AUSTRALIA_MELBOURNE("Australia/Melbourne"),
    AMERICA_NEW_YORK("America/New_York"),
    AMERICA_CHICAGO("America/Chicago"),
    AMERICA_DENVER("America/Denver"),
    AMERICA_LOS_ANGELES("America/Los_Angeles"),
    AMERICA_TORONTO("America/Toronto"),
    AMERICA_VANCOUVER("America/Vancouver"),
    AMERICA_SAO_PAULO("America/Sao_Paulo"),
    AMERICA_MEXICO_CITY("America/Mexico_City"),
    AFRICA_CAIRO("Africa/Cairo"),
    AFRICA_JOHANNESBURG("Africa/Johannesburg"),
    PACIFIC_AUCKLAND("Pacific/Auckland");

    @Getter
    private final String zoneId;
    @Getter
    private final ZoneId javaZoneId;

    Timezone(String zoneId) {
        this.zoneId = zoneId;
        this.javaZoneId = ZoneId.of(zoneId);
    }

    public static Optional<Timezone> fromString(String zoneIdString) {
        return Arrays.stream(values())
                .filter(tz -> tz.zoneId.equals(zoneIdString))
                .findFirst();
    }

    @Override
    public String toString() {
        return this.zoneId;
    }
}
