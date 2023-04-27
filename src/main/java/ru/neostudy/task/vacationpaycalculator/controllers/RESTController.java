package ru.neostudy.task.vacationpaycalculator.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neostudy.task.vacationpaycalculator.Calculator;


@RestController
@RequestMapping(value = "/calculacte")
public class RESTController {

    @GetMapping(value = {
            "/{avgSalaryInYear}/{countOfVacationDays}",
            "/{avgSalaryInYear}/{countOfVacationDays}/{vacationStart}"
            },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Calculator> calculatePay(
            @PathVariable(name = "avgSalaryInYear") Double avgSalaryInYear,
            @PathVariable(name = "countOfVacationDays") int countOfVacationDays,
            @PathVariable(required = false, name = "vacationStart") String vacationStart
    ) {
        return vacationStart == null ?
                ResponseEntity.ok(new Calculator(avgSalaryInYear, countOfVacationDays)) :
                ResponseEntity.ok(new Calculator(avgSalaryInYear, countOfVacationDays, vacationStart));
    }

    @GetMapping("/calculacte")
    public String info() {
        return """
                How to use:
                "/calculacte - for this page
                "/calculacte/{avgSalaryInYear}/{countOfVacationDays} - for calculate including non-business days
                "/calculacte/{avgSalaryInYear}/{countOfVacationDays}/{vacationStart} - for calculate excluding non-business days
                avgSalaryInYear - Double
                countOfVacationDays - int
                vacationStart - String in format "yyyy-MM-dd"
                """;
    }


}
