package org.project.dayoffcalculator.service;

import org.junit.jupiter.api.Test;
import org.project.dayoffcalculator.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DayOffCalculatorServiceTest {

    @Autowired
    DayOffCalculatorService dayOffCalculatorService;

    @Value("${application.dayoff.amount}")
    int daysOffPerYear;

    @Test
    void fullVacation_getFullSalary() {
        BigDecimal monthlySalary = BigDecimal.valueOf(123000.00).setScale(2, RoundingMode.HALF_UP);
        List<LocalDate> fullMonth = DateUtil.generateWorkDayList(daysOffPerYear);
        BigDecimal payment = dayOffCalculatorService.calculateDaysOffPayment(monthlySalary, fullMonth);
        assertEquals(monthlySalary, payment);
    }

    @Test
    void holidayVacation_getNoPayment() {
        BigDecimal monthlySalary = BigDecimal.valueOf(123000.00).setScale(2, RoundingMode.HALF_UP);
        BigDecimal payment = dayOffCalculatorService.calculateDaysOffPayment(monthlySalary, List.of(LocalDate.of(2025, 1, 1)));
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), payment);
    }

    @Test
    void zeroDaysVacation_getNoPayment() {
        BigDecimal monthlySalary = BigDecimal.valueOf(123000.00).setScale(2, RoundingMode.HALF_UP);
        BigDecimal payment = dayOffCalculatorService.calculateDaysOffPayment(monthlySalary, Collections.emptyList());
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), payment);
    }

    @Test
    void zeroSalary_getNoPayment() {
        BigDecimal monthlySalary = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal payment = dayOffCalculatorService.calculateDaysOffPayment(monthlySalary, List.of(LocalDate.now()));
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), payment);
    }
}