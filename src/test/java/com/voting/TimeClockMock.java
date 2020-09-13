package com.voting;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class TimeClockMock extends Clock {
    private String currentTime = "2020-08-30T10:00:00.00Z";

    @Override
    public ZoneId getZone() {
        return ZoneId.of("UTC");
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return Clock.fixed(instant(), getZone());
    }

    @Override
    public Instant instant() {
        return Instant.parse(currentTime);
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
