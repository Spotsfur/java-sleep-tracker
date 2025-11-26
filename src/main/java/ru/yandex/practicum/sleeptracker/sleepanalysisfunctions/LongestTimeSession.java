package ru.yandex.practicum.sleeptracker.sleepanalysisfunctions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.OptionalLong;
import java.util.function.Function;

public class LongestTimeSession implements Function<List<SleepingSession>, Integer> {

    @Override
    public Integer apply(List<SleepingSession> sleepingSessions) {
        //Делаем стрим OptionalLong, потому что max хочет Optional, а Duration.between хочет long
        OptionalLong optionalMaxMinutes = sleepingSessions.stream()
                //Превращаем каждый элемент в long minutes, сравнивая поля start и end
                .mapToLong(session -> Duration.between(session.getStart(), session.getEnd()).toMinutes())
                //Терминальная операция - ищем максимальный элемент
                .max();

        //Проверка на пустой Опционал
        if (optionalMaxMinutes.isPresent()) {
            long longMaxMinutes = optionalMaxMinutes.getAsLong();
            //Нельзя столько спать, ты чё, в коме?
            if (longMaxMinutes < 600000000) {
                return (int) longMaxMinutes;
            }
        }
        return 0;
    }
}
