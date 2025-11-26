package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepAnalysisResult<T extends Function<List<SleepingSession>, ?>> {
    private final String description;
    private final T sleepAnalysisFunction;

    public SleepAnalysisResult(String description, T SleepAnalysisFunction) {
        this.description = description;
        this.sleepAnalysisFunction = SleepAnalysisFunction;
    }

    public String getDescription() {
        return description;
    }

    public T getSleepingSession() {
        return sleepAnalysisFunction;
    }
}
