package com.cdk.skeletonproject.data;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class SongKickResults<T> {
    @Json(name = "event")
    public List<T> events = null;

    public List<T> getResultsList() {
        if (events == null) {
            events = new ArrayList<>();
        }
        return events;
    }
}
