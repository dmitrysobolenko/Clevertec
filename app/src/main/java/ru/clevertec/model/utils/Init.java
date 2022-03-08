package ru.clevertec.model.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class Init {

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

    public static int getSaleDiscountPercent() {
        return saleDiscountPercent;
    }

    public static int getSaleQuantity() {
        return saleQuantity;
    }

    public static String getOutputFileName() {
        return outputFileName;
    }

    public static String getCurr() {
        return curr;
    }

    public static int getVatRate() {
        return vatRate;
    }

    public static String[] getItemsTags() {
        return itemsTags;
    }

    public static String[] getCardsTags() {
        return cardsTags;
    }

    public static String getItemsFileName() {
        return itemsFileName;
    }

    public static String getCardsFileName() {
        return cardsFileName;
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

        try (Reader reader = new FileReader(path)) {
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


    }
}
