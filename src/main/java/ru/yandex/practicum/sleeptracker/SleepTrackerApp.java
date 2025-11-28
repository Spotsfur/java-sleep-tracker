package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.sleepanalysisfunctions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class SleepTrackerApp {
    static List<SleepingSession> sleepingSessions;
    static List<SleepAnalysisResult<? extends Function<List<SleepingSession>, ?>>> sleepAnalysisFunctions = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //Загружаем файл
        sleepingSessions = loadFile(args);

        //Добавляем экземпляр функции в лист
        sleepAnalysisFunctions.add(new SleepAnalysisResult<>("Всего сессий сна", new TotalSessions()));
        sleepAnalysisFunctions.add(new SleepAnalysisResult<>("Наиболее длинная сессия (в минутах)", new LongestTimeSession()));
        sleepAnalysisFunctions.add(new SleepAnalysisResult<>("Наиболее короткая сессия (в минутах)", new ShortestTimeSession()));
        sleepAnalysisFunctions.add(new SleepAnalysisResult<>("Средняя продолжительность сессии (в минутах)", new AverageTimeSession()));
        sleepAnalysisFunctions.add(new SleepAnalysisResult<>("Сессий с плохим качеством сна", new BadSleepSession()));
        sleepAnalysisFunctions.add(new SleepAnalysisResult<>("Ночей без сна", new NightsWithoutSleep()));
        sleepAnalysisFunctions.add(new SleepAnalysisResult<>("Твой тип сна", new TypeOfBird()));

        //Читаем лист, выводим результаты
        sleepAnalysisFunctions.forEach(sleepAnalysisResult ->
                System.out.println(sleepAnalysisResult.getDescription() + ": " + sleepAnalysisResult.getSleepingSession().apply(sleepingSessions)));
    }

    private static List<SleepingSession> loadFile(String[] args) throws IOException {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        if (args.length > 0) {
            //Принимаем аргумент - "D:\Java\Java Practicum\My Projects\java-sleep-tracker\src\main\resources\sleep_log.txt"
            //Избавляемся от виндузиатских слешей
            String filePath = args[0].replace("/", "\\");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

            try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
                lines.forEach(line -> {
                    String[] parts = line.split(";");
                    //Распихиваем данные по переменным
                    LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
                    LocalDateTime end = LocalDateTime.parse(parts[1], formatter);
                    SleepQuality quality = SleepQuality.valueOf(parts[2]);
                    //Суём переменные в конструктор
                    sleepingSessions.add(new SleepingSession(start, end, quality));
                });
            } catch (IOException e) {
                throw new IOException("Не удалось прочитать файл");
            }
        } else {
            throw new IOException("Не передан путь к файлу");
        }
        return new ArrayList<>(sleepingSessions);
    }
}