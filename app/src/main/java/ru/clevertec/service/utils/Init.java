package ru.clevertec.service.utils;

import ru.clevertec.service.utils.sax.SAXHandler;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

public class Init {

    private Init() {
    }

    public static final String ARGS_REGEX = ".*.+=.+";
    public static final String ITEM_ID_REGEX = "([1-9]\\d?)|100";
    public static final String ITEM_NAME_REGEX = "[A-ZА-Я][a-zа-я]{2,29}";
    public static final String ITEM_PRICE_REGEX = "(([1-9][\\d]{0,1}).[\\d]{2})|100\\.00";
    public static final String BOOLEAN_REGEX = "true|false";
    public static final String ITEM_QUANTITY_REGEX = "([1-9])|(1\\d)|20";
    public static final String CARDS_ID_REGEX = "\\d*";

    private static int saleDiscountPercent;
    private static int saleQuantity;
    private static int vatRate;
    private static String[] itemsTags;
    private static String[] cardsTags;
    private static String curr;
    private static String itemsFileName;
    private static String cardsFileName;
    private static String outputFileName;
    private static String errorLogFileName;
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
    private static Map<Integer, String[]> cardsMap;
    private static Map<Integer, String[]> itemsMap;

    public static Map<Integer, String[]> getCardsMap() {
        return cardsMap;
    }

    public static Map<Integer, String[]> getItemsMap() {
        return itemsMap;
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

    public static String getErrorLogFileName() {
        return errorLogFileName;
    }

    public static String getCurr() {
        return curr;
    }

    public static int getVatRate() {
        return vatRate;
    }

    public static String getTitle() {
        return title;
    }

    public static String getMarket() {
        return market;
    }

    public static String getAddress() {
        return address;
    }

    public static String getPhone() {
        return phone;
    }

    public static String getCashier() {
        return cashier;
    }

    public static String getDateString() {
        return dateString;
    }

    public static String getTimeString() {
        return timeString;
    }

    public static String getDateFormat() {
        return dateFormat;
    }

    public static String getTimeFormat() {
        return timeFormat;
    }

    public static String getSaleString() {
        return saleString;
    }

    public static String getCustomerString() {
        return customerString;
    }

    public static String getPersonalDiscountString() {
        return personalDiscountString;
    }

    public static String getTaxableTotString() {
        return taxableTotString;
    }

    public static String getVatString() {
        return vatString;
    }

    public static String getTotalString() {
        return totalString;
    }

    public static String getQuantityString() {
        return quantityString;
    }

    public static String getDescriptionString() {
        return descriptionString;
    }

    public static String getPriseString() {
        return priseString;
    }

    public static String getCostString() {
        return costString;
    }

    public static String getDiscountString() {
        return discountString;
    }

    public static String getTotalPriseString() {
        return totalPriseString;
    }

    public static void initialize(String path) {

        Properties properties = new Properties();

        try (Reader reader = new FileReader(path + "config.properties")) {
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
            errorLogFileName = properties.getProperty("errorLogFileName");
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

        itemsMap = SAXHandler.getMap(path + itemsFileName, itemsTags, ITEM_ID_REGEX);
        cardsMap = SAXHandler.getMap(path + cardsFileName, cardsTags, CARDS_ID_REGEX);
    }
}
