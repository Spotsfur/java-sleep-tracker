package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    LocalDateTime START;
    LocalDateTime END;
    SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime START, LocalDateTime END, SleepQuality sleepQuality) {
        this.START = START;
        this.END = END;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getSTART() {
        return START;
    }

    public LocalDateTime getEND() {
        return END;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }
}
