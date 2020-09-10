package com.voting;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class TimeClockMock extends Clock {
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
        return Instant.parse("2020-08-30T10:00:00.00Z");
    }
}
