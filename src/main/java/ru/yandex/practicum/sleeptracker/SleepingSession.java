package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    LocalDateTime start;
    LocalDateTime end;
    SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime start, LocalDateTime end, SleepQuality sleepQuality) {
        this.start = start;
        this.end = end;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }
}
