package ru.yandex.practicum.sleeptracker.sleepanalysisfunctions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageTimeSession implements Function<List<SleepingSession>, Integer> {

    @Override
    public Integer apply(List<SleepingSession> sleepingSessions) {
        //Делаем стрим long, потому что Duration.between хочет long
        long totalMinutes = sleepingSessions.stream()
                //Превращаем каждый элемент в long minutes, сравнивая поля start и end
                .mapToLong(session -> Duration.between(session.getStart(), session.getEnd()).toMinutes())
                //Терминальная операция - складываем
                .sum();

        long averageMinutes = 0;
        if (!sleepingSessions.isEmpty()) {
            averageMinutes = totalMinutes / sleepingSessions.size();
        }

        return (int) averageMinutes;
    }
}
