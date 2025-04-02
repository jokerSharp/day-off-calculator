package org.project.dayoffcalculator.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class DayOffCalculatorControllerTest {

    private static final String BASE_URL = "/api/calculate";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void fullVacation_getFullSalary() throws Exception {
        BigDecimal expectedPayment = BigDecimal.valueOf(123000.00);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .param("salary", "123000.00")
                        .param("daysOff", "28"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedPayment.toString()));
    }

    @Test
    void halfVacation_getHalfSalary() throws Exception {
        BigDecimal expectedPayment = BigDecimal.valueOf(61500.05);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .param("salary", "123000.10")
                        .param("daysOff", "14"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedPayment.toString()));
    }

    @Test
    void zeroDaysVacation_getValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .param("salary", "123000.00")
                        .param("daysOff", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("Your vacation should be at least 1 day")));
    }

    @Test
    void negativeSalary_getValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .param("salary", "-123000.00")
                        .param("daysOff", "28"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("The salary should be a positive number")));
    }

    @Test
    void wrongScaleInSalary_getValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .param("salary", "123000.123")
                        .param("daysOff", "28"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers
                        .containsString("numeric value out of bounds (<10 digits>.<2 digits> expected)")));
    }

    @Test
    void missingSalary_getMissingParameterException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .param("daysOff", "28"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("Required request parameter 'salary' for method parameter type BigDecimal is not present")));
    }

    @Test
    void missingDaysOff_getMissingParameterException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .param("salary", "123000.123"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("Required request parameter 'daysOff' for method parameter type int is not present")));
    }
}