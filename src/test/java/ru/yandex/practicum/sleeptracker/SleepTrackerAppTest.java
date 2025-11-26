package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.sleepanalysisfunctions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SleepTrackerAppTest {

    @Test //Одна запись - одна сессия сна
    void oneSleepSessionIsOne() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 0, 0), SleepQuality.GOOD)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Всего сессий сна", new TotalSessions());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("1"));
    }

    @Test //Три записи - три сессии сна
    void threeSleepSessionInOneDayIsThree() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 5, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 8, 0), LocalDateTime.of(2025, 1, 1, 14, 0), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 1, 5, 15, 0), LocalDateTime.of(2025, 1, 5, 18, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Всего сессий сна", new TotalSessions());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("3"));
    }

    @Test //Наиболее длинная сессия рассчитывается верно, даже если она в середине списка
    void theLongestSessionFromCurrentPosition() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 5, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 7, 25), LocalDateTime.of(2025, 1, 1, 23, 15), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 2, 0), LocalDateTime.of(2025, 1, 2, 8, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 9, 30), LocalDateTime.of(2025, 1, 2, 18, 0), SleepQuality.BAD)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Наиболее длинная сессия (в минутах)", new LongestTimeSession());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("950"));
    }

    @Test //Наиболее длинная сессия рассчитывается верно, даже если существует сессия, что переходит между месяцами
    void theLongestSessionFromCurrentPositionEvenIfMonth() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 5, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 1, 31, 23, 25), LocalDateTime.of(2025, 2, 1, 0, 15), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 2, 2, 2, 0), LocalDateTime.of(2025, 2, 2, 8, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 2, 2, 9, 30), LocalDateTime.of(2025, 2, 2, 18, 0), SleepQuality.BAD)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Наиболее длинная сессия (в минутах)", new LongestTimeSession());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("510"));
    }

    @Test //Наиболее короткая сессия рассчитывается верно, даже если она в середине списка
    void theShortestSessionFromCurrentPosition() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 9, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 10, 25), LocalDateTime.of(2025, 1, 1, 23, 15), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 2, 0), LocalDateTime.of(2025, 1, 2, 7, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 9, 30), LocalDateTime.of(2025, 1, 2, 18, 0), SleepQuality.BAD)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Наиболее длинная сессия (в минутах)", new ShortestTimeSession());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("300"));
    }

    @Test //Наиболее короткая сессия рассчитывается верно, даже если у неё разные месяца
    void theShortestSessionFromCurrentPositionEvenIfMonth() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 5, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 1, 31, 23, 25), LocalDateTime.of(2025, 2, 1, 0, 15), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 2, 2, 2, 0), LocalDateTime.of(2025, 2, 2, 8, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 2, 2, 9, 30), LocalDateTime.of(2025, 2, 2, 18, 0), SleepQuality.BAD)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Наиболее длинная сессия (в минутах)", new ShortestTimeSession());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("50"));
    }

    @Test //Среднее время сессии вычисляется верно
    void theAverageTimeSessionsFromABunch() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 1, 45), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 4, 0), LocalDateTime.of(2025, 1, 1, 9, 15), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 11, 0), LocalDateTime.of(2025, 1, 2, 13, 20), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 17, 0), LocalDateTime.of(2025, 1, 2, 21, 40), SleepQuality.BAD)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Средняя продолжительность сессии (в минутах)", new AverageTimeSession());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("210"));
    }

    @Test //Среднее время сессии вычисляется верно, использования перехода на другой месяц не мешает
    void theAverageTimeSessionsFromABunchWithMonth() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 1, 45), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 4, 0), LocalDateTime.of(2025, 1, 1, 9, 15), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 1, 31, 23, 0), LocalDateTime.of(2025, 2, 1, 1, 20), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 2, 2, 17, 0), LocalDateTime.of(2025, 2, 2, 21, 40), SleepQuality.BAD)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Средняя продолжительность сессии (в минутах)", new AverageTimeSession());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("210"));
    }

    @Test //Находим сессию с плохим качеством
    void oneBadSleep() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 1, 45), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 4, 0), LocalDateTime.of(2025, 1, 1, 9, 15), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 11, 0), LocalDateTime.of(2025, 1, 2, 13, 20), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 17, 0), LocalDateTime.of(2025, 1, 2, 21, 40), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Сессий с плохим качеством сна", new BadSleepSession());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("1"));
    }

    @Test //Находим три сессии с плохим качеством
    void threeBadSleeps() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 1, 45), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 4, 0), LocalDateTime.of(2025, 1, 1, 9, 15), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 11, 0), LocalDateTime.of(2025, 1, 2, 13, 20), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 17, 0), LocalDateTime.of(2025, 1, 2, 21, 40), SleepQuality.BAD)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Сессий с плохим качеством сна", new BadSleepSession());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("3"));
    }

    @Test //Одна сессия, что началась чуть раньше 12 и закончилась чуть позже 12 - это 2 ночи без сна
    void thereIsTwoNightsWithoutSleepWithOneSession() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 11, 0), LocalDateTime.of(2025, 1, 1, 13, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Ночей без сна", new NightsWithoutSleep());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("2"));
    }

    @Test //Если сессия начинается утром и заканчивается днём, ночью спали
    void thereIsZeroNightsWithoutSleepCauseMorning() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 5, 0), LocalDateTime.of(2025, 1, 1, 7, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Ночей без сна", new NightsWithoutSleep());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("0"));
    }

    @Test //Если сессия начинается вечером и заканчивается ночью, ночью спали
    void thereIsZeroNightsWithoutSleepCauseEvening() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 20, 0), LocalDateTime.of(2025, 1, 2, 4, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Ночей без сна", new NightsWithoutSleep());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("0"));
    }

    @Test //Если легли после полуночи и проснулись до 6, то мы спали
    void shortNightSleep() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 1, 0), LocalDateTime.of(2025, 1, 1, 2, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Ночей без сна", new NightsWithoutSleep());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("0"));
    }

    @Test //Две совы и один жаворонок, мы - сова
    void theOwlTest() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 1, 0), LocalDateTime.of(2025, 1, 1, 10, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 20, 0), LocalDateTime.of(2025, 1, 2, 6, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 23, 30), LocalDateTime.of(2025, 1, 3, 11, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Твой тип сна", new TypeOfBird());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("Сова"));
    }

    @Test //Одна сова и два жаворонка, мы - жаворонок
    void theLarkTest() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 21, 0), LocalDateTime.of(2025, 1, 2, 6, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 19, 0), LocalDateTime.of(2025, 1, 3, 5, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 4, 4, 30), LocalDateTime.of(2025, 1, 4, 10, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Твой тип сна", new TypeOfBird());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("Жаворонок"));
    }

    @Test //Если легли позже совы, а встали раньше жаворонка, мы - голубь
    void thePigeonShortSession() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 1, 30), LocalDateTime.of(2025, 1, 1, 5, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Твой тип сна", new TypeOfBird());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("Голубь"));
    }

    @Test //Если легли раньше жаворонка, а встали позже совы, мы - голубь
    void thePigeonLongSession() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 20, 0), LocalDateTime.of(2025, 1, 2, 11, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Твой тип сна", new TypeOfBird());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("Голубь"));
    }

    @Test //Если мы соответствуем и сове, и жаворонку в ночь, то мы - голубь
    void theOwlAndTheLarkTheSameTimeIsPigeon() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 20, 0), LocalDateTime.of(2025, 1, 2, 1, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 2, 0), LocalDateTime.of(2025, 1, 2, 11, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Твой тип сна", new TypeOfBird());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("Голубь"));
    }

    @Test //Если сов, жаворонков и голубей одинаковое количество, то мы - голубь
    void theConflictOfBirdsIsPigeon() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 1, 0), LocalDateTime.of(2025, 1, 1, 5, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 21, 0), LocalDateTime.of(2025, 1, 2, 6, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 1, 3, 2, 0), LocalDateTime.of(2025, 1, 3, 11, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>> sleepAnalysisResult = new SleepAnalysisResult<>("Твой тип сна", new TypeOfBird());
        assertTrue(sleepAnalysisResult.getSleepingSession().apply(sleepingSessions).toString().equals("Голубь"));
    }
}