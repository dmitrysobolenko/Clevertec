package ru.clevertec.utils;

import ru.clevertec.beans.DiscountCard;
import ru.clevertec.beans.Item;

import static ru.clevertec.utils.Util.calculatePercents;

public class ItemFactory {

    private ItemFactory() {
    }

    public static Item getInstance(String[] itemStrArray, int quantity, DiscountCard card) {

        String name;
        int price;
        boolean isOnSale;
        int cost;
        int discount;
        int discountedCost;

        name = itemStrArray[0];
        price = (int) (Double.parseDouble(itemStrArray[1]) * 100);
        isOnSale = Boolean.parseBoolean(itemStrArray[2]) && quantity >= Util.getSaleQuantity();
        cost = price * quantity;
        if (isOnSale) {
            discount = calculatePercents(Util.getSaleDiscountPercent(), cost);
        } else {
            discount = calculatePercents(card.getDiscountPercent(), cost);
        }
        discountedCost = cost - discount;

        return new Item(name, price, isOnSale, quantity, cost, discount, discountedCost);
    }
}



