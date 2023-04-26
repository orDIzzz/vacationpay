package ru.neostudy.task.vacationpaycalculator.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.neostudy.task.vacationpaycalculator.Calculator;

import java.text.DecimalFormat;

@RestController
public class RESTController {


    @GetMapping({
            "/calculate/{avgSalaryInYear}/{countOfVacationDays}",
            "/calculate/{avgSalaryInYear}/{countOfVacationDays}/{vacationStart}"
    })
    public String calculatePay(
            @PathVariable Double avgSalaryInYear,
            @PathVariable int countOfVacationDays,
            @PathVariable(required = false) String vacationStart){
        Double result =  vacationStart==null ?
                new Calculator(avgSalaryInYear,countOfVacationDays).vacationPay() :
                new Calculator(avgSalaryInYear,countOfVacationDays,vacationStart).vacationPay();
        return new DecimalFormat("#.##").format(result);
    }

    @GetMapping("/calculate")
    public String info(){
        return """
                How to use:
                "/calculate - for this page
                "/calculate/{avgSalaryInYear}/{countOfVacationDays} - for calculate including non-business days
                "/calculate/{avgSalaryInYear}/{countOfVacationDays}/{vacationStart} - for calculate excluding non-business days
                avgSalaryInYear - Double
                countOfVacationDays - int
                vacationStart - String in format "yyyy-MM-dd"
                """;
    }


}
