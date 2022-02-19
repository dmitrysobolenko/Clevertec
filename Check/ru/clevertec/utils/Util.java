package ru.clevertec.utils;

import org.xml.sax.SAXException;
import ru.clevertec.beans.Check;
import ru.clevertec.beans.Item;
import ru.clevertec.sax.SAXHandler;

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

    private static int saleDiscountPercent;
    private static int saleQuantity;
    private static int vatRate;
    private static String[] itemsTags;
    private static String[] cardsTags;
    private static String curr;
    private static String itemsFileName;
    private static String cardsFileName;
    private static String outputFileName;
    private static String title;
    private static String market;
    private static String address;
    private static String phone;
    private static String cashier;
    private static String dateString;
    private static String timeString;
    private static String dateFormat;
    private static String timeFormat;
    private static String saleString;
    private static String customerString;
    private static String personalDiscountString;
    private static String taxableTotString;
    private static String vatString;
    private static String totalString;
    private static String quantityString;
    private static String descriptionString;
    private static String priseString;
    private static String costString;
    private static String discountString;
    private static String totalPriseString;

    private static Map<Integer, String[]> itemMap = new HashMap<>();
    private static Map<Integer, String[]> cardMap = new HashMap<>();

    private Util() {
    }

    public static Map<Integer, String[]> getItemMap() {
        return itemMap;
    }

    public static Map<Integer, String[]> getCardMap() {
        return cardMap;
    }

    public static int getSaleDiscountPercent() {
        return saleDiscountPercent;
    }

    public static int getSaleQuantity() {
        return saleQuantity;
    }

    public static String getOutputFileName() {
        return outputFileName;
    }

    static {

        Properties properties = new Properties();

        try (Reader reader = new FileReader("ru/clevertec/resources/config.properties")) {
            properties.load(reader);
            saleDiscountPercent = Integer.parseInt(properties.getProperty("saleDiscountPercent"));
            saleQuantity = Integer.parseInt(properties.getProperty("saleQuantity"));
            vatRate = Integer.parseInt(properties.getProperty("vatRate"));
            curr = properties.getProperty("curr");
            itemsTags = properties.getProperty("itemsTags").split(";");
            cardsTags = properties.getProperty("cardsTags").split(";");
            itemsFileName = properties.getProperty("itemsFileName");
            cardsFileName = properties.getProperty("cardsFileName");
            outputFileName = properties.getProperty("outputFileName");
            title = properties.getProperty("title");
            market = properties.getProperty("market");
            address = properties.getProperty("address");
            phone = properties.getProperty("phone");
            cashier = properties.getProperty("cashier");
            dateString = properties.getProperty("dateString");
            timeString = properties.getProperty("timeString");
            dateFormat = properties.getProperty("dateFormat");
            timeFormat = properties.getProperty("timeFormat");
            saleString = properties.getProperty("saleString");
            customerString = properties.getProperty("customerString");
            personalDiscountString = properties.getProperty("personalDiscountString");
            taxableTotString = properties.getProperty("taxableTotString");
            vatString = properties.getProperty("vatString") + vatRate + "%";
            totalString = properties.getProperty("totalString");
            quantityString = properties.getProperty("quantityString");
            descriptionString = properties.getProperty("descriptionString");
            priseString = properties.getProperty("priseString");
            costString = properties.getProperty("costString");
            discountString = properties.getProperty("discountString");
            totalPriseString = properties.getProperty("totalPriseString");

        } catch (IOException e) {
            System.err.println("Config file error: " + e.getMessage());
            System.exit(1);
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            SAXParser parser = factory.newSAXParser();
            SAXHandler handler;

            handler = new SAXHandler(itemsTags);
            parser.parse(new FileInputStream(itemsFileName), handler);
            itemMap = handler.getDataMap();

            handler = new SAXHandler(cardsTags);
            parser.parse(new FileInputStream(cardsFileName), handler);
            cardMap = handler.getDataMap();

        } catch (SAXException | ParserConfigurationException | IOException e) {
            System.err.println("XML file error: " + e.getMessage());
            System.exit(1);
        }
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

    private static String getMoneyString(int money){
        return String.format("%s%.2f", curr , money / 100.0);
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
        vat = calculatePercents(taxableTotal, vatRate);
        taxableTotalStr = formatMoney(taxableTotal);
        vatStr = formatMoney(vat);
        total = formatMoney(taxableTotal + vat);
        header = new StringBuilder(quantityString);
        header.append(descriptionString).append(saleString).append(priseString).append(costString);
        header.append(discountString).append(totalPriseString);
        checkWidth = header.length();
        date = dateString + new SimpleDateFormat(dateFormat).format(new Date());
        time = timeString + new SimpleDateFormat(timeFormat).format(new Date());

        sb = new StringBuilder("\n");

        sb.append(middle(title, checkWidth));
        sb.append("\n");
        sb.append(middle(market, checkWidth));
        sb.append(middle(address, checkWidth));
        sb.append(middle(phone, checkWidth));
        sb.append("\n");
        sb.append(line(cashier, " ", checkWidth - cashier.length() - date.length(), date));
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
            sb.append(line(" ", quantityString.length() - quantity.length(), name));
            sb.append(" ".repeat(descriptionString.length() - name.length()));
            sb.append(isOnSale ? saleString : " ".repeat(saleString.length()));
            sb.append(formatMoney(price, priseString));
            sb.append(formatMoney(cost, costString));
            sb.append(formatMoney(discount, discountString));
            sb.append(formatMoney(discountedCost, totalPriseString)).append("\n");
        }

        sb.append(line("=", checkWidth, "\n"));

        if (!cardholder.isEmpty()) {
            sb.append(line(customerString, " ", checkWidth - customerString.length() - cardholder.length(), cardholder)).append("\n");
            sb.append(line(personalDiscountString, " ", checkWidth - personalDiscountString.length() - personalDiscount.length(), personalDiscount)).append("\n");
        }

        sb.append(line(taxableTotString, " ", checkWidth - taxableTotString.length() - taxableTotalStr.length(), taxableTotalStr)).append("\n");
        sb.append(line(vatString, " ", checkWidth - vatString.length() - vatStr.length(), vatStr)).append("\n");
        sb.append(line(totalString, " ", checkWidth - totalString.length() - total.length(), total));

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