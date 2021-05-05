package com.flaconi.shipping.service;

import com.flaconi.shipping.config.CutOffConfig;
import com.flaconi.shipping.config.HolidayDetails;
import com.flaconi.shipping.constants.Carrier;
import com.flaconi.shipping.factory.HolidayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

class ShippingServiceTest {

    @InjectMocks
    private ShippingService shippingService;
    @Mock
    private HolidayDetails holidayDetails;
    @Mock
    private CutOffConfig cutOffConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reset(holidayDetails, cutOffConfig);

    }

    @Test
    void expectedArrivalDate() {

        when(holidayDetails.holidayList()).thenReturn(HolidayList.holidayList());
        when(cutOffConfig.getCutOffTime()).thenReturn("12");

        assertEquals(shippingService.deliveryDate("2021-01-01T12:21:38+00:00", Carrier.DHL), "2021-01-07");
        assertEquals(shippingService.deliveryDate("2021-01-01T09:21:38+00:00", Carrier.DHL), "2021-01-06");
        assertEquals(shippingService.deliveryDate("2021-01-04T12:21:38+00:00", Carrier.DHL), "2021-01-07");
        assertEquals(shippingService.deliveryDate("2021-01-04T09:21:38+00:00", Carrier.DHL), "2021-01-06");
        assertEquals(shippingService.deliveryDate("2021-03-08T09:21:38+00:00", Carrier.DHL), "2021-03-11");
        assertEquals(shippingService.deliveryDate("2021-03-08T12:21:38+00:00", Carrier.DHL), "2021-03-12");
        assertEquals(shippingService.deliveryDate("2021-05-01T09:21:38+00:00", Carrier.DHL), "2021-05-05");
        assertEquals(shippingService.deliveryDate("2021-12-24T11:21:38+00:00", Carrier.DHL), "2021-12-27");
        assertEquals(shippingService.deliveryDate("2021-12-24T12:21:38+00:00", Carrier.DHL), "2021-12-29");
        assertEquals(shippingService.deliveryDate("2021-12-24T09:21:38+00:00", Carrier.DHL), "2021-12-27");


    }

    @Test
    void isHoliday_Test_False() {
        when(holidayDetails.holidayList()).thenReturn(HolidayList.holidayList());
        assertFalse(shippingService.isHoliday("2021-02-14T09:21:38+00:00"));
    }

    @Test
    void isHoliday_Test_True() {
        when(holidayDetails.holidayList()).thenReturn(HolidayList.holidayList());
        assertTrue(shippingService.isHoliday("2021-03-08T09:21:38+00:00"));
    }

    @Test
    void isWeekEnd_Test_True() {
        when(holidayDetails.holidayList()).thenReturn(HolidayList.holidayList());
        assertTrue(shippingService.isWeekEnd("2021-05-09T09:21:38+00:00"));
    }

    @Test
    void isWeekEnd_Test_False() {
        when(holidayDetails.holidayList()).thenReturn(HolidayList.holidayList());
        assertFalse(shippingService.isWeekEnd("2021-05-07T09:21:38+00:00"));
    }

    @Test
    void isBeforeCutOffTime_Test_True() {
        when(cutOffConfig.getCutOffTime()).thenReturn("12");
        assertTrue(shippingService.isBeforeCutOffTime("2021-05-09T09:21:38+00:00"));
    }

    @Test
    void isBeforeCutOffTime_Test_False() {
        when(cutOffConfig.getCutOffTime()).thenReturn("12");
        assertFalse(shippingService.isBeforeCutOffTime("2021-05-09T12:21:38+00:00"));
    }

    @Test
    void calculateHolidaysCount_Test() {
        when(holidayDetails.holidayList()).thenReturn(HolidayList.holidayList());
        Integer holidayCount = shippingService.calculateHolidaysCount("2021-12-25T12:21:38+00:00");
        assertThat(holidayCount).isEqualTo(2);
    }
}