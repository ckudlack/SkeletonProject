package com.cdk.skeletonproject.data;

public class SongKickResultsPage<T> {

    public String status;
    public SongKickResults<T> results;
    public int perPage;
    public int page;
    public int totalEntries;

    public String getStatus() {
        return status;
    }

    public SongKickResults<T> getResults() {
        return results;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getPage() {
        return page;
    }

    public int getTotalEntries() {
        return totalEntries;
    }
}
