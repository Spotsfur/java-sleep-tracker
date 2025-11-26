package ru.yandex.practicum.sleeptracker.sleepanalysisfunctions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.OptionalLong;
import java.util.function.Function;

public class ShortestTimeSession implements Function<List<SleepingSession>, Integer> {

    @Override
    public Integer apply(List<SleepingSession> sleepingSessions) {
        //Делаем стрим OptionalLong, потому что min хочет Optional, а Duration.between хочет long
        OptionalLong optionalMinMinutes = sleepingSessions.stream()
                //Превращаем каждый элемент в long minutes, сравнивая поля start и end
                .mapToLong(session -> Duration.between(session.getSTART(), session.getEND()).toMinutes())
                //Терминальная операция - ищем минимальный элемент
                .min();

        //Проверка на пустой Опционал
        if (optionalMinMinutes.isPresent()) {
            long longMinMinutes = optionalMinMinutes.getAsLong();
            //Нельзя столько спать, ты чё, в коме?
            if (longMinMinutes < 600000000) {
                return (int) longMinMinutes;
            }
        }
        return 0;
    }
}
