package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepAnalysisResult <T extends Function<List<SleepingSession>, ?>> {
    private final String description;
    private final T SleepAnalysisFunction;

    public SleepAnalysisResult(String description, T SleepAnalysisFunction) {
        this.description = description;
        this.SleepAnalysisFunction = SleepAnalysisFunction;
    }

    public String getDescription() {
        return description;
    }

    public T getSleepingSession() {
        return SleepAnalysisFunction;
    }
}
