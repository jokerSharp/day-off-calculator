package org.project.dayoffcalculator.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DateUtil {

    private static final Set<Integer> holidays = Set.of(
            1, 2, 3, 6, 7, 8,
            121, 122, 127, 128,
            163, 164,
            307, 308,
            365
    );

    private DateUtil() {
    }

    public static boolean isHoliday(LocalDate date) {
        return holidays.contains(date.getDayOfYear());
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static List<LocalDate> generateWorkDayList(int count) {
        List<LocalDate> workDays = new ArrayList<>();
        LocalDate currentDate = LocalDate.of(2025, Month.JULY, 1);

        while (workDays.size() < count) {
            if (!isWeekend(currentDate)) {
                workDays.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }

        return workDays;
    }

    public static String generateWorkDayString(int count) {
        List<LocalDate> localDates = generateWorkDayList(count);
        StringBuilder stringBuilder = new StringBuilder();

        localDates.forEach(localDate -> stringBuilder.append(localDate).append(","));

        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
