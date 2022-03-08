package ru.clevertec.model.utils;

import org.xml.sax.SAXException;
import ru.clevertec.model.beans.Check;
import ru.clevertec.model.beans.Item;
import ru.clevertec.model.sax.SAXHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {

    private Util() {
    }

    public static int calculatePercents(int x, int percent) {
        return (int) (x * percent / 100.0);
    }

    private static String line(String prefix, String str, int n, String suffix) {
        return prefix + str.repeat(n) + suffix;
    }

    private static String line(String str, int n, String suffix) {
        return str.repeat(n) + suffix;
    }

    private static String getMoneyString(int money) {
        return String.format("%s%.2f", Init.getCurr(), money / 100.0);
    }

    private static String formatMoney(int money, String len) {
        String str = getMoneyString(money);
        return line("", " ", len.length() - str.length(), str);
    }

    private static String formatMoney(int money) {
        String str = getMoneyString(money);
        return line("", " ", str.length(), str);
    }

    private static String middle(String str, int n) {
        String tab = " ".repeat((n - str.length()) / 2);
        return tab + str + tab + "\n";
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
        vat = calculatePercents(taxableTotal, Init.getVatRate());
        taxableTotalStr = formatMoney(taxableTotal);
        vatStr = formatMoney(vat);
        total = formatMoney(taxableTotal + vat);
        header = new StringBuilder(Init.getQuantityString());
        header.append(Init.getDescriptionString()).append(Init.getSaleString()).append(Init.getPriseString()).append(Init.getCostString());
        header.append(Init.getDiscountString()).append(Init.getTotalPriseString());
        checkWidth = header.length();
        date = Init.getDateString() + new SimpleDateFormat(Init.getDateFormat()).format(new Date());
        time = Init.getTimeString() + new SimpleDateFormat(Init.getTimeFormat()).format(new Date());

        sb = new StringBuilder("\n");

        sb.append(middle(Init.getTitle(), checkWidth));
        sb.append("\n");
        sb.append(middle(Init.getMarket(), checkWidth));
        sb.append(middle(Init.getAddress(), checkWidth));
        sb.append(middle(Init.getPhone(), checkWidth));
        sb.append("\n");
        sb.append(line(Init.getCashier(), " ", checkWidth - Init.getCashier().length() - date.length(), date));
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
            sb.append(line(" ", Init.getQuantityString().length() - quantity.length(), name));
            sb.append(" ".repeat(Init.getDescriptionString().length() - name.length()));
            sb.append(isOnSale ? Init.getSaleString() : " ".repeat(Init.getSaleString().length()));
            sb.append(formatMoney(price, Init.getPriseString()));
            sb.append(formatMoney(cost, Init.getCostString()));
            sb.append(formatMoney(discount, Init.getDiscountString()));
            sb.append(formatMoney(discountedCost, Init.getTotalPriseString())).append("\n");
        }

        sb.append(line("=", checkWidth, "\n"));

        if (!cardholder.isEmpty()) {
            sb.append(line(Init.getCustomerString(), " ", checkWidth - Init.getCustomerString().length() - cardholder.length(), cardholder)).append("\n");
            sb.append(line(Init.getPersonalDiscountString(), " ", checkWidth - Init.getPersonalDiscountString().length() - personalDiscount.length(), personalDiscount)).append("\n");
        }

        sb.append(line(Init.getTaxableTotString(), " ", checkWidth - Init.getTaxableTotString().length() - taxableTotalStr.length(), taxableTotalStr)).append("\n");
        sb.append(line(Init.getVatString(), " ", checkWidth - Init.getVatString().length() - vatStr.length(), vatStr)).append("\n");
        sb.append(line(Init.getTotalString(), " ", checkWidth - Init.getTotalString().length() - total.length(), total));

        return (sb.toString());
    }

    public static void symbolWrite(String str, String path) {
        try (Writer writer = new FileWriter(path)) {
            for (int i : str.chars().toArray()) {
                writer.write(i);
            }
        } catch (IOException e) {
            System.err.println("Output file error: " + e.getMessage());
        }
    }

}