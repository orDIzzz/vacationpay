package ru.neostudy.task.vacationpaycalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void prepareData(){
        calculator = new Calculator(3600d,10);
    }

    @Test
    void testVacationPayExcludeBusinessDays() {
        assertEquals(100d,calculator.vacationPay());
    }
}