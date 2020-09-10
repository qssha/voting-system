package com.voting.web;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class TimeClock extends Clock {
    public TimeClock() {
    }

    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return Clock.systemDefaultZone();
    }

    @Override
    public Instant instant() {
        return Instant.now();
    }
}
