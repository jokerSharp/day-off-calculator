package org.project.dayoffcalculator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        int daysOff = daysOffPerYear;
        BigDecimal payment = dayOffCalculatorService.calculateDaysOffPayment(monthlySalary, daysOff);
        assertEquals(monthlySalary, payment);
    }

    @Test
    void zeroDaysVacation_getNoPayment() {
        BigDecimal monthlySalary = BigDecimal.valueOf(123000.00).setScale(2, RoundingMode.HALF_UP);
        int daysOff = 0;
        BigDecimal payment = dayOffCalculatorService.calculateDaysOffPayment(monthlySalary, daysOff);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), payment);
    }

    @Test
    void zeroSalary_getNoPayment() {
        BigDecimal monthlySalary = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        int daysOff = daysOffPerYear;
        BigDecimal payment = dayOffCalculatorService.calculateDaysOffPayment(monthlySalary, daysOff);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), payment);
    }

}