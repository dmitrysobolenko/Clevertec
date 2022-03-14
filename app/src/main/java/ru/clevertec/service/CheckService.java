package ru.clevertec.service;

import ru.clevertec.model.entities.Check;
import ru.clevertec.model.entities.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static ru.clevertec.service.utils.Init.getAddress;
import static ru.clevertec.service.utils.Init.getCashier;
import static ru.clevertec.service.utils.Init.getCostString;
import static ru.clevertec.service.utils.Init.getCustomerString;
import static ru.clevertec.service.utils.Init.getDateFormat;
import static ru.clevertec.service.utils.Init.getDateString;
import static ru.clevertec.service.utils.Init.getDescriptionString;
import static ru.clevertec.service.utils.Init.getDiscountString;
import static ru.clevertec.service.utils.Init.getMarket;
import static ru.clevertec.service.utils.Init.getPersonalDiscountString;
import static ru.clevertec.service.utils.Init.getPhone;
import static ru.clevertec.service.utils.Init.getPriseString;
import static ru.clevertec.service.utils.Init.getQuantityString;
import static ru.clevertec.service.utils.Init.getSaleString;
import static ru.clevertec.service.utils.Init.getTaxableTotString;
import static ru.clevertec.service.utils.Init.getTimeFormat;
import static ru.clevertec.service.utils.Init.getTimeString;
import static ru.clevertec.service.utils.Init.getTitle;
import static ru.clevertec.service.utils.Init.getTotalPriseString;
import static ru.clevertec.service.utils.Init.getTotalString;
import static ru.clevertec.service.utils.Init.getVatRate;
import static ru.clevertec.service.utils.Init.getVatString;
import static ru.clevertec.service.utils.Util.calculatePercents;
import static ru.clevertec.service.utils.Util.formatMoney;
import static ru.clevertec.service.utils.Util.line;
import static ru.clevertec.service.utils.Util.middle;

public class CheckService {

    private CheckService() {
    }

    public static String getCheck(Check check) {

        Map<Integer, Item> items;
        String cardholder;
        String personalDiscount;
        int taxableTotal = 0;
        int vat;
        String taxableTotalStr;
        String vatStr;
        String total;
        StringBuilder header;
        int checkWidth;
        StringBuilder sb;
        String date;
        String time;

        items = check.getItems();
        cardholder = check.getDiscountCard().getCardholder();
        personalDiscount = check.getDiscountCard().getDiscountPercent() + "%";
        taxableTotal += items.values().stream().mapToInt((Item::getDiscountedCost)).sum();
        vat = calculatePercents(taxableTotal, getVatRate());
        taxableTotalStr = formatMoney(taxableTotal);
        vatStr = formatMoney(vat);
        total = formatMoney(taxableTotal + vat);
        header = new StringBuilder(getQuantityString());
        header.append(getDescriptionString()).append(getSaleString()).append(getPriseString()).append(getCostString());
        header.append(getDiscountString()).append(getTotalPriseString());
        checkWidth = header.length();
        date = getDateString() + new SimpleDateFormat(getDateFormat()).format(new Date());
        time = getTimeString() + new SimpleDateFormat(getTimeFormat()).format(new Date());

        sb = new StringBuilder("\n");

        sb.append(middle(getTitle(), checkWidth));
        sb.append("\n");
        sb.append(middle(getMarket(), checkWidth));
        sb.append(middle(getAddress(), checkWidth));
        sb.append(middle(getPhone(), checkWidth));
        sb.append("\n");
        sb.append(line(getCashier(), " ", checkWidth - getCashier().length() - date.length(), date));
        sb.append(line("\n", " ", checkWidth - date.length(), time));
        sb.append(line("\n", "-", checkWidth, "\n"));
        sb.append(header);
        sb.append(line("\n", "-", checkWidth, "\n"));

        for (Map.Entry<Integer, Item> item : items.entrySet()) {

            Item position;
            String name;
            int price;
            boolean isOnSale;
            String quantity;
            int cost;
            int discount;
            int discountedCost;

            position = item.getValue();
            name = position.getName();
            quantity = String.valueOf(position.getQuantity());
            isOnSale = position.isOnSale();
            price = position.getPrice();
            cost = position.getCost();
            discount = position.getDiscount();
            discountedCost = position.getDiscountedCost();

            sb.append(quantity);
            sb.append(line(" ", getQuantityString().length() - quantity.length(), name));
            sb.append(" ".repeat(getDescriptionString().length() - name.length()));
            sb.append(isOnSale ? getSaleString() : " ".repeat(getSaleString().length()));
            sb.append(formatMoney(price, getPriseString()));
            sb.append(formatMoney(cost, getCostString()));
            sb.append(formatMoney(discount, getDiscountString()));
            sb.append(formatMoney(discountedCost, getTotalPriseString())).append("\n");
        }

        sb.append(line("=", checkWidth, "\n"));

        if (!cardholder.isEmpty()) {
            sb.append(line(getCustomerString(), " ", checkWidth - getCustomerString().length() - cardholder.length(), cardholder)).append("\n");
            sb.append(line(getPersonalDiscountString(), " ", checkWidth - getPersonalDiscountString().length() - personalDiscount.length(), personalDiscount)).append("\n");
        }

        sb.append(line(getTaxableTotString(), " ", checkWidth - getTaxableTotString().length() - taxableTotalStr.length(), taxableTotalStr)).append("\n");
        sb.append(line(getVatString(), " ", checkWidth - getVatString().length() - vatStr.length(), vatStr)).append("\n");
        sb.append(line(getTotalString(), " ", checkWidth - getTotalString().length() - total.length(), total));

        return (sb.toString());
    }

}
