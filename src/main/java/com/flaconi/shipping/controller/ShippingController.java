package com.flaconi.shipping.controller;

import com.flaconi.shipping.constants.Carrier;
import com.flaconi.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipment")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping("/deliverydate/{deliverydate}/carrier/{carriername}")
    public String getDeliveryDate(@PathVariable(name = "deliverydate") String deliverydate,@PathVariable(name = "carriername") String carriername){
      return shippingService.deliveryDate(deliverydate, Carrier.valueOf(carriername));
    }
}
