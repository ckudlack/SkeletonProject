package com.cdk.skeletonproject.network.adapter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

public class LocalTimeAdapter {

    @FromJson
    public LocalTime fromJson(String value) {
        return LocalTime.parse(value, DateTimeFormat.forPattern("HH:mm"));
    }

    @ToJson
    public String toJson(LocalTime localTime) {
        return localTime.toString(DateTimeFormat.forPattern("HH:mm"));
    }
}
