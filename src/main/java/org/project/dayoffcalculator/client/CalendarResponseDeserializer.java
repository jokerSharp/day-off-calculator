package org.project.dayoffcalculator.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalendarResponseDeserializer extends JsonDeserializer<CalendarResponse> {

    @Override
    public CalendarResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        int yearValue = node.get("year").asInt();
        JsonNode monthsNode = node.get("months");

        List<LocalDate> dates = new ArrayList<>();
        for (JsonNode monthNode : monthsNode) {
            int monthValue = monthNode.get("month").asInt();
            String daysInMonthString = monthNode.get("days").asText();
            int[] days = parseDayArray(daysInMonthString);
            Arrays.stream(days)
                    .mapToObj(dayValue -> LocalDate.of(yearValue, monthValue, dayValue))
                    .forEach(dates::add);
        }

        CalendarResponse calendarResponse = new CalendarResponse();
        calendarResponse.setDates(dates);

        return calendarResponse;
    }

    private int[] parseDayArray(String daysInMonthString) {
        String[] daysString = daysInMonthString.split(",");

        return Arrays.stream(daysString)
                .mapToInt(s -> Integer.parseInt(s.replaceAll("[*+]", "")))
                .toArray();
    }
}
