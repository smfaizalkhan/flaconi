package com.flaconi.shipping.constants;

public enum Carrier {

    DHL(2),
    HERMES(3);

    private Integer deliveryDays;


    Carrier(int deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }
}
