package ru.clevertec.model.entities;

public class DiscountCardFactory {

    private DiscountCardFactory() {
    }

    public static DiscountCard getInstance(String[] cardStrArray) {

        String cardholder = "";
        boolean isActive = false;
        int discountPercent;

        if (cardStrArray != null) {
            cardholder = cardStrArray[0];
            isActive = Boolean.parseBoolean(cardStrArray[2]);
        }
        discountPercent = isActive ? Integer.parseInt(cardStrArray[1]) : 0;

        return new DiscountCard(cardholder, discountPercent);
    }
}