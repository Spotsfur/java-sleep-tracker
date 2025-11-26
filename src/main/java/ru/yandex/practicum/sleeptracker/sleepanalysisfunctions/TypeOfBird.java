package ru.yandex.practicum.sleeptracker.sleepanalysisfunctions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class TypeOfBird implements Function<List<SleepingSession>, String> {

    @Override
    public String apply(List<SleepingSession> sleepingSessions) {
        //Находим ночи, в которых мы спали
        int nightsWithSleep = (int) sleepingSessions.stream()
                .filter(sleepingSession ->
                        sleepingSession.getSTART().isBefore(sleepingSession.getEND().withHour(6).withMinute(0))
                                && sleepingSession.getEND().isAfter(sleepingSession.getEND().withHour(0).withMinute(0)))
                //Это короче что-то типа хеш суммы ночи из номера дня в году и последних двух цифр года
                //Так мы получаем уникальный ИД ночи за столетний период
                .mapToLong(sleepingSession -> sleepingSession.getEND().getDayOfYear() + (366 * (sleepingSession.getEND().getYear() % 100)))
                //Выбрасываем неуникальные значения
                .distinct()
                //Считаем количество
                .count();

        //Лист ночей сов
        List<Integer> theOwlNights = sleepingSessions.stream()
                //Убеждаемся, что эта сессия считается ночной
                .filter(sleepingSession ->
                        sleepingSession.getSTART().isBefore(sleepingSession.getEND().withHour(6).withMinute(0))
                                && sleepingSession.getEND().isAfter(sleepingSession.getEND().withHour(0).withMinute(0)))
                //Возможные совы
                .filter(sleepingSession ->
                        sleepingSession.getSTART().isAfter(sleepingSession.getEND().withHour(23).withMinute(0).minusDays(1))
                                && sleepingSession.getEND().isAfter(sleepingSession.getEND().withHour(9).withMinute(0))
                                //Убеждаемся, что сова не спала больше суток
                                && sleepingSession.getEND().isBefore(sleepingSession.getSTART().withHour(23).withMinute(59).plusDays(1)))
                //Это короче что-то типа хеш суммы ночи из номера дня в году и последних двух цифр года
                //Так мы получаем уникальный ИД ночи за столетний период
                .mapToInt(sleepingSession -> sleepingSession.getEND().getDayOfYear() + (366 * (sleepingSession.getEND().getYear() % 100)))
                //Выбрасываем неуникальные значения
                .distinct()
                //Считаем количество
                .boxed().toList();

        //Лист ночей жаворонков
        List<Integer> theLarkNights = sleepingSessions.stream()
                //Убеждаемся, что эта сессия считается ночной
                .filter(sleepingSession ->
                        sleepingSession.getSTART().isBefore(sleepingSession.getEND().withHour(6).withMinute(0))
                                && sleepingSession.getEND().isAfter(sleepingSession.getEND().withHour(0).withMinute(0)))
                //Возможные жаворонки
                .filter(sleepingSession ->
                        sleepingSession.getSTART().isBefore(sleepingSession.getSTART().withHour(22).withMinute(0))
                                && sleepingSession.getEND().isBefore(sleepingSession.getSTART().withHour(7).withMinute(0).plusDays(1))
                                //Убеждаемся, что жаворонок не проснулся до наступления ночи
                                && sleepingSession.getEND().isAfter(sleepingSession.getSTART().withHour(0).withMinute(0).plusDays(1))
                                //Убеждаемся, что жаворонок лёг после 12:00, иначе это другая ночь
                                && sleepingSession.getSTART().isAfter(sleepingSession.getSTART().withHour(12).withMinute(0)))
                //Это короче что-то типа хеш суммы ночи из номера дня в году и последних двух цифр года
                //Так мы получаем уникальный ИД ночи за столетний период
                .mapToInt(sleepingSession -> sleepingSession.getEND().getDayOfYear() + (366 * (sleepingSession.getEND().getYear() % 100)))
                //Выбрасываем неуникальные значения
                .distinct()
                //Считаем количество
                .boxed().toList();

        //Сопоставляем листы, находим число спорных ночей (когда и сова, и жаворонок)
        int theWerebirdCase = (int) theOwlNights.stream()
                .filter(theLarkNights::contains)
                .count();

        //Считаем сов
        int theOwlCase = theOwlNights.size() - theWerebirdCase;

        //Считаем жаворонков
        int theLarkCase = theLarkNights.size() - theWerebirdCase;

        //Из суммы ночей с кейсами, трактующимися неоднозначно, вычитаем сов и жаворонков, получая голубей
        int thePigeonCase = nightsWithSleep - theOwlCase - theLarkCase;

        //System.out.println(nightsWithSleep + " nightsWithSleep");
        //System.out.println(theWerebirdCase + " theWerebirdCase");
        //System.out.println(theOwlCase + " theOwlCase");
        //System.out.println(theLarkCase + " theLarkCase");
        //System.out.println(thePigeonCase + " thePigeonCase");

        if (theOwlCase > theLarkCase && theOwlCase > thePigeonCase) {
            return "Сова";
        } else if (theLarkCase > theOwlCase && theLarkCase > thePigeonCase) {
            return "Жаворонок";
        }
        return "Голубь";
    }
}
