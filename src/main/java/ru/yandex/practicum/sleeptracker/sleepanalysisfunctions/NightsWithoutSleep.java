package ru.yandex.practicum.sleeptracker.sleepanalysisfunctions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class NightsWithoutSleep implements Function<List<SleepingSession>, Integer> {

    @Override
    public Integer apply(List<SleepingSession> sleepingSessions) {
        //Вычисляем начало и конец
        LocalDateTime startOfPeriod = sleepingSessions.getFirst().getStart();
        LocalDateTime endOfPeriod = sleepingSessions.getLast().getEnd();
        //Вычисляем всего дней (без хвоста)
        //int totalDays = Period.between(startOfPeriod.toLocalDate(), endOfPeriod.toLocalDate()).getDays();
        long longDays = endOfPeriod.toLocalDate().toEpochDay() - startOfPeriod.toLocalDate().toEpochDay();
        int totalDays = (int) longDays;
        //Находим дополнительное число ночей, зависящее от часа
        int additionalNights;
        //Если время логирования начинается до полудня, а заканчивается после, добавляем 2 ночи
        if (startOfPeriod.isBefore(startOfPeriod.withHour(12).withMinute(0))
                && endOfPeriod.isAfter(endOfPeriod.withHour(12).withMinute(0))) {
            additionalNights = 2;
            //Если выполняется только одно условие добавляем 1 ночь
        } else if (startOfPeriod.isBefore(startOfPeriod.withHour(12).withMinute(0))
                || endOfPeriod.isAfter(endOfPeriod.withHour(12).withMinute(0))) {
            additionalNights = 1;
            //Если условия не выполняются (то-есть начало логирования после 12, а конец до 12), то число ночей будет равным числу дней
        } else {
            additionalNights = 0;
        }
        int totalNights = totalDays + additionalNights;

        //Фильтруем по ночам, убираем одинаковости, считаем количество, конвертируем в инт
        int nightsWithSleep = (int) sleepingSessions.stream()
                .filter(sleepingSession ->
                        sleepingSession.getStart().isBefore(sleepingSession.getEnd().withHour(6).withMinute(0))
                                && sleepingSession.getEnd().isAfter(sleepingSession.getEnd().withHour(0).withMinute(0)))
                //Это короче что-то типа хеш суммы ночи из номера дня в году и последних двух цифр года
                //Так мы получаем уникальный ИД ночи за столетний период
                .mapToLong(sleepingSession -> sleepingSession.getEnd().getDayOfYear() + (366 * (sleepingSession.getEnd().getYear() % 100)))
                //Выбрасываем неуникальные значения
                .distinct()
                //Считаем количество
                .count();

        return totalNights - nightsWithSleep;
    }
}
