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
                .mapToLong(session -> Duration.between(session.getSTART(), session.getEND()).toMinutes())
                //Терминальная операция - складываем
                .sum();

        long averageMinutes = totalMinutes / sleepingSessions.size();

        //А у вас int большой
        if (averageMinutes < 600000000)
        {
            return (int) averageMinutes;
        }
        return 0;
    }
}
