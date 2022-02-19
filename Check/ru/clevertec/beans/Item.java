package ru.clevertec.beans;

public class Item {
    private String name;
    private int price;
    private boolean isOnSale;
    private int quantity;
    private int cost;
    private int discount;
    private int discountedCost;

    public Item(String name, int price, boolean isOnSale, int quantity, int cost, int discount, int discountedCost) {
        this.name = name;
        this.price = price;
        this.isOnSale = isOnSale;
        this.quantity = quantity;
        this.cost = cost;
        this.discount = discount;
        this.discountedCost = discountedCost;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCost() {
        return cost;
    }

    public int getDiscount() {
        return discount;
    }

    public int getDiscountedCost() {
        return discountedCost;
    }
}
