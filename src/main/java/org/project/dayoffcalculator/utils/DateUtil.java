package org.project.dayoffcalculator.utils;

import org.project.dayoffcalculator.client.CalendarRestClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateUtil {

    private final List<LocalDate> dates;

    public DateUtil(CalendarRestClient calendarRestClient) {
        this.dates = calendarRestClient.getNonWorkingDayList();
    }

    public boolean isBusinessDay(LocalDate date) {
        return !dates.contains(date);
    }

    public List<LocalDate> generateBusinessDayList(int count) {
        List<LocalDate> workDays = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        while (workDays.size() < count) {
            if (isBusinessDay(currentDate)) {
                workDays.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }

        return workDays;
    }

    public String generateBusinessDayString(int count) {
        List<LocalDate> localDates = generateBusinessDayList(count);
        StringBuilder stringBuilder = new StringBuilder();

        localDates.forEach(localDate -> stringBuilder.append(localDate).append(","));

        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
