package org.project.dayoffcalculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.project.dayoffcalculator.service.DayOffCalculatorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Validated
@RequestMapping("/api")
@RestController
public class DayOffCalculatorController {

    private final DayOffCalculatorService dayOffCalculatorService;

    public DayOffCalculatorController(DayOffCalculatorService dayOffCalculatorService) {
        this.dayOffCalculatorService = dayOffCalculatorService;
    }

    @Operation(summary = "Get a payment",
            description = "Returns a calculation of days-off payment based on the average salary and a number of days-off")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful calculation of days-off payment"),
            @ApiResponse(responseCode = "400", description = "Unsuccessful calculation of days-off payment")
    })
    @GetMapping("/calculate")
    public ResponseEntity<?> calculate(
            @Parameter(
                    name = "salary",
                    description = "The average salary used for calculation. Must be positive and has scale of 2",
                    required = true,
                    schema = @Schema(
                            type = "number",
                            format = "floating point number",
                            minimum = "0.0"
                    )
            )
            @RequestParam @DecimalMin(value = "0.0", message = "The salary should be a positive number")
            @Digits(integer = 10, fraction = 2) BigDecimal salary,
            @Parameter(
                    name = "daysOff",
                    description = "A list of dates representing a vacation",
                    required = true,
                    schema = @Schema(
                            type = "dates in ISO 8601 format",
                            minimum = "1"
                    )
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Size(min = 1, message = "Your vacation should be at least 1 day") List<LocalDate> daysOff) {
        BigDecimal payment = dayOffCalculatorService.calculateDaysOffPayment(salary, daysOff);
        return ResponseEntity.ok(payment);
    }
}
