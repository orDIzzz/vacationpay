package ru.neostudy.task.vacationpaycalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculatorIncludeWeekends;
    Calculator calculatorExcludeWeekends;

    @BeforeEach
    void prepareData(){
        calculatorIncludeWeekends = new Calculator(3650d,10);
        calculatorExcludeWeekends = new Calculator(3650d,10,"2023-04-27");
    }

    @Test
    void testVacationPayIncludeWeekends() {
        assertEquals("100",calculatorIncludeWeekends.getVacationPay());
    }

    @Test
    void testVacationPayExcludeWeekends() {
        assertEquals("70",calculatorExcludeWeekends.getVacationPay());
    }
}