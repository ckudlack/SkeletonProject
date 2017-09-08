package com.cdk.skeletonproject.network.adapter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class LocalDateAdapter {

    @FromJson
    public LocalDate fromJson(String value) {
        return LocalDate.parse(value, DateTimeFormat.forPattern("yyyy-MM-dd"));
    }

    @ToJson
    public String toJson(LocalDate localDate) {
        return localDate.toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
    }
}
