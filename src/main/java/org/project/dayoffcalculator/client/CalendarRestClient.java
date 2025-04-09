package org.project.dayoffcalculator.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Component
public class CalendarRestClient {

    @Value("${application.calendar.url}")
    private String calendarUrl;

    private final RestTemplate restTemplate;

    public CalendarRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LocalDate> getNonWorkingDayList() {
        return restTemplate.getForObject(calendarUrl, CalendarResponse.class).getDates();
    }
}
