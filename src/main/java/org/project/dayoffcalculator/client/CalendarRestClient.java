package org.project.dayoffcalculator.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class CalendarRestClient {

    private static final Logger log = LoggerFactory.getLogger(CalendarRestClient.class);

    @Value("${application.calendar.url}")
    private String calendarUrl;

    private final RestTemplate restTemplate;

    public CalendarRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LocalDate> getNonWorkingDayList() {
        log.info("Getting non-working day list from {}", calendarUrl);
        CalendarResponse response = restTemplate.getForObject(calendarUrl, CalendarResponse.class);
        try {
            return Objects.requireNonNull(response).getDates();
        } catch (NullPointerException e) {
            log.error("Error while getting calendar {}", e.getMessage());
        }
        return Collections.emptyList();
    }
}
