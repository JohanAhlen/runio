package com.johanahlen.runsync.runkeeper.activity;

import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalDateTime;

public class RunKeeperActivitySummary {

    @SerializedName("has_map")
    boolean hasMap;
    double duration;
    @SerializedName("total_distance")
    double totalDistance;
    @SerializedName("entry_mode")
    String entryMode;
    String source;
    @SerializedName("start_time")
    LocalDateTime startTime;
    String type;
    String uri;

    public boolean isHasMap() {
        return hasMap;
    }

    public double getDuration() {
        return duration;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public String getEntryMode() {
        return entryMode;
    }

    public String getSource() {
        return source;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
