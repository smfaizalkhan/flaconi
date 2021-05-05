package com.flaconi.shipping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class HolidayDetails {

    @Bean
    public List<LocalDate> holidayList(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<LocalDate> holidayList = new ArrayList<>();
        holidayList.add(LocalDate.parse("01/01/2021",dateTimeFormatter));
        holidayList.add(LocalDate.parse("08/03/2021",dateTimeFormatter)); //International Women's Day
        holidayList.add(LocalDate.parse("02/04/2021",dateTimeFormatter));  //Good Friday
        holidayList.add(LocalDate.parse("05/04/2021",dateTimeFormatter));  //  Easter Sunday
        holidayList.add(LocalDate.parse("01/05/2021",dateTimeFormatter));  // Labour Day
        holidayList.add(LocalDate.parse("13/05/2021",dateTimeFormatter));  // Ascension Day
        holidayList.add(LocalDate.parse("24/05/2021",dateTimeFormatter));  //  Whit Monday
        holidayList.add(LocalDate.parse("03/10/2021",dateTimeFormatter));   // German Unity Day
        holidayList.add(LocalDate.parse("25/12/2021",dateTimeFormatter)); // Christmas Day
        holidayList.add(LocalDate.parse("26/12/2021",dateTimeFormatter));  //2nd Day of Christmas
        return holidayList;
    }

}
