package com.cdk.skeletonproject.network.adapter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class LocalDateTimeAdapter {

    @FromJson
    public LocalDateTime fromJson(String value) {
        return LocalDateTime.parse(value, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
    }

    @ToJson
    public String toJson(LocalDateTime localDateTime) {
        return localDateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
    }
}
