package org.project.dayoffcalculator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DayOffCalculatorService {

    @Value("${application.dayoff.amount}")
    private int daysOffPerYear;

    public BigDecimal calculateDaysOffPayment(BigDecimal salary, int daysOff) {
        BigDecimal paymentRate = BigDecimal.valueOf(daysOff).divide(BigDecimal.valueOf(daysOffPerYear), 2, RoundingMode.HALF_UP);
        return salary.multiply(paymentRate).setScale(2, RoundingMode.HALF_UP);
    }
}
