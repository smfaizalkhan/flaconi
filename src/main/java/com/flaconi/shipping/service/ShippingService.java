package com.flaconi.shipping.service;


import com.flaconi.shipping.config.CutOffConfig;
import com.flaconi.shipping.config.HolidayDetails;
import com.flaconi.shipping.constants.Carrier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final CutOffConfig cutOffConfig;
    private final HolidayDetails holidayDetails;

    /**
     *
     * @param wareHouseArrivalDateTime
     * @param carrierName
     * @return
     */
    public String deliveryDate(String wareHouseArrivalDateTime, Carrier carrierName){


    String deliveryDate ;
        Integer holidayCount  = calculateHolidaysCount(wareHouseArrivalDateTime);
        ZonedDateTime wareHouseProcessingTime  = ZonedDateTime.parse(wareHouseArrivalDateTime).plusDays(holidayCount);
        Integer weekEndCount  = calculateWeekEndCount(wareHouseProcessingTime);
        wareHouseProcessingTime = wareHouseProcessingTime.plusDays(weekEndCount);
        wareHouseProcessingTime = calculateCutOff(wareHouseProcessingTime);
        deliveryDate = calculateDeliveryDate(wareHouseProcessingTime,carrierName.name());
        return  deliveryDate;
    }


    public boolean isHoliday(String dateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);
        return holidayDetails.holidayList().contains(zonedDateTime.toLocalDate());
    }

    public Integer calculateHolidaysCount(String dateTime){
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);
        int holidayCount =0;
        if(!isHoliday(dateTime)) return holidayCount;
        for(LocalDate holidayDate : holidayDetails.holidayList()){
            if(holidayDate.equals(zonedDateTime.toLocalDate())){
                holidayCount++;
                zonedDateTime = zonedDateTime.plusDays(1);
            }
        }
        return holidayCount;
    }

    public boolean isWeekEnd(String dateTime){
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);
        return (zonedDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) || zonedDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY));
    }

    public boolean isBeforeCutOffTime(String dateTime){
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);
        LocalTime cutOffTime = LocalTime.of(Integer.parseInt(cutOffConfig.getCutOffTime()),Integer.parseInt("00"));
        return  zonedDateTime.toLocalTime().isBefore(cutOffTime);
    }

    private String calculateDeliveryDate(ZonedDateTime wareHouseProcessingTime,String carrierName) {
        ZonedDateTime deliveryDate =wareHouseProcessingTime.plusDays(Carrier.valueOf(carrierName).getDeliveryDays());
        deliveryDate = calculateWeekEndAndHoliday(deliveryDate);
        return deliveryDate.toLocalDate().toString();
    }

    private ZonedDateTime calculateCutOff(ZonedDateTime wareHouseProcessingTime) {

        if(!isBeforeCutOffTime(wareHouseProcessingTime.toString()))
            wareHouseProcessingTime = wareHouseProcessingTime.plusDays(1);
        wareHouseProcessingTime = calculateWeekEndAndHoliday(wareHouseProcessingTime);
        return wareHouseProcessingTime;
    }

    private ZonedDateTime calculateWeekEndAndHoliday(ZonedDateTime wareHouseProcessingTime) {
        Integer weekEndCount  = calculateWeekEndCount(wareHouseProcessingTime);
        wareHouseProcessingTime = wareHouseProcessingTime.plusDays(weekEndCount);
        Integer holidayCount  = calculateHolidaysCount(wareHouseProcessingTime.toString());
        wareHouseProcessingTime = wareHouseProcessingTime.plusDays(holidayCount);
        return wareHouseProcessingTime;
    }

    private Integer calculateWeekEndCount(ZonedDateTime wareHouseProcessingTime) {
        if(isWeekEnd(wareHouseProcessingTime.toString()))
            return wareHouseProcessingTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)?2:1;
        return 0;
    }
}
