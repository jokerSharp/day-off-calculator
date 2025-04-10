package org.project.dayoffcalculator.service;

import org.project.dayoffcalculator.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class DayOffCalculatorService {

    private static final Logger log = LoggerFactory.getLogger(DayOffCalculatorService.class);

    @Value("${application.dayoff.amount}")
    private int daysOffPerYear;

    private final DateUtil dateUtil;

    public DayOffCalculatorService(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    public BigDecimal calculateDaysOffPayment(BigDecimal salary, List<LocalDate> daysOff) {
        long workDaysCounter = daysOff.stream()
                .filter(dateUtil::isBusinessDay)
                .count();
        BigDecimal paymentRate = BigDecimal.valueOf(workDaysCounter).divide(BigDecimal.valueOf(daysOffPerYear), 2, RoundingMode.HALF_UP);
        log.info("Work days counter={}, paymentRate={}", workDaysCounter, paymentRate);

        return salary.multiply(paymentRate).setScale(2, RoundingMode.HALF_UP);
    }
}
