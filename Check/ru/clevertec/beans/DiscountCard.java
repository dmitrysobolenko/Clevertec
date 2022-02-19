package ru.clevertec.beans;

public class DiscountCard {
    private String cardholder;
    private int discountPercent;

    public DiscountCard(String cardholder, int discountPercent) {
        this.cardholder = cardholder;
        this.discountPercent = discountPercent;
    }

    public String getCardholder() {
        return cardholder;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

}
