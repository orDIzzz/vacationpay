package ru.neostudy.task.vacationpaycalculator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * New instance takes 2 or 3 parameters.
 * {@linkvacationPay()} returns the amount of salary that the employee will receive on vacation.
 * If 2 arguments are passed: will be returned vacation salary without the weekend days
 * If 3 arguments are passed: will be returned vacation salary with the weekend days
 * No validating input data.
 */
public class Calculator {

    @JsonIgnore
    private Double avgSalaryOfYear;
    @JsonIgnore
    private int countOfVacationDays;
    @JsonIgnore
    private String vacationStartDate;
    private String vacationPay;

    /**
     * @param avgSalaryOfYear     - average salary for 12 month
     * @param countOfVacationDays - count of vacation days
     * @param vacationStartDate   - vacation start date
     */
    public Calculator(Double avgSalaryOfYear, int countOfVacationDays, String vacationStartDate) {
        this.avgSalaryOfYear = avgSalaryOfYear;
        this.countOfVacationDays = countOfVacationDays;
        this.vacationStartDate = vacationStartDate;
        this.vacationPay = new DecimalFormat("#.##").format(vacationPayCalc());
    }

    /**
     * @param avgSalaryOfYear     - average salary for 12 month
     * @param countOfVacationDays - count of vacation days
     */
    public Calculator(Double avgSalaryOfYear, int countOfVacationDays) {
        this.avgSalaryOfYear = avgSalaryOfYear;
        this.countOfVacationDays = countOfVacationDays;
        this.vacationPay = new DecimalFormat("#.##").format(vacationPayCalc());
    }

    public String getVacationPay() {
        return this.vacationPay;
    }

    /**
     * @return amount of salary in vacation including or excluding weekends
     */
    protected Double vacationPayCalc() {
        if (vacationStartDate == null) {
            return dayCost(avgSalaryOfYear) * countOfVacationDays;
        } else if (avgSalaryOfYear == 0d) {
            return 0.0;
        } else if (!vacationStartDate.isBlank()) {
            return dayCost(avgSalaryOfYear) * countOfBusinessDays(vacationStartDate, countOfVacationDays);
        } else {
            return 0.0;
        }
    }

    /**
     * Takes abstract month of 30 days for calculating
     * @param avgSalaryOfYear - amount of salary fo 12 month
     * @return cost of 1 day in vacation
     */
    private Double dayCost(Double avgSalaryOfYear) {
        return (avgSalaryOfYear / 365);
    }

    /**
     * Method calculate count of days without weekends that employee will be on vacation
     * @param vacationStartDate - vacation start date
     * @param countOfVacationDays - count of days in vacation
     * @return count of business days in vacation
     */
    private int countOfBusinessDays(String vacationStartDate, int countOfVacationDays) {
        // Создаем формат для установления даты в календаре
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Принимаем по умолчанию колчество рабочих дней = 0
        int countOfBusinessDaysInVacation = 0;
        // Создаем переменную для хранения даты после парсинга входных данных
        Date date;
        try {
            // Парсим входную строку даты для создания объекта Date. Делаем это в блоке try потому что
            // Formatter.parse() выкидывает ParseException
            date = formatter.parse(vacationStartDate);
        } catch (ParseException e) {
            return 0;
        }
        // Создаем экземпляр объекта Calendar, с помощью которого будем определять рабочий день или выходной
        Calendar calendar = Calendar.getInstance();
        // Устанавливаем дату календаря в начало отпуска работника
        calendar.setTime(date);
        // В цикле проверяем выходной ли день, если нет то добавляем один день к расчетному сроку отпуска
        for (int i = 0; i < countOfVacationDays; i++) {
            if (
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                            calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
            ) {
                calendar.add(Calendar.DATE, 1);
            } else {
                calendar.add(Calendar.DATE, 1);
                countOfBusinessDaysInVacation++;
            }

        }
        return countOfBusinessDaysInVacation;
    }
}
