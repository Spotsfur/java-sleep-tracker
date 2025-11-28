package ru.yandex.practicum.sleeptracker.sleepanalysisfunctions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class TotalSessions implements Function<List<SleepingSession>, Integer> {

    @Override
    public Integer apply(List<SleepingSession> sleepingSessions) {
        return sleepingSessions.size();
    }
}
