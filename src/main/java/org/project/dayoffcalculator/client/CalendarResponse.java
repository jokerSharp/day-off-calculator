package org.project.dayoffcalculator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.util.List;

@JsonDeserialize(using = CalendarResponseDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendarResponse {

    private List<LocalDate> dates;

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }
}
