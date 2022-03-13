package ru.clevertec.model.entities;

import java.util.Map;

public class Check {
    private Map<Integer, Item> items;
    private DiscountCard discountCard;

    public Check(Map<Integer, Item> items, DiscountCard discountCard) {
        this.items = items;
        this.discountCard = discountCard;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }
}
