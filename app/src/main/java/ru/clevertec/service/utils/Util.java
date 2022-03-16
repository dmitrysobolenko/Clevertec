package ru.clevertec.service.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.clevertec.service.utils.Init.BOOLEAN_REGEX;
import static ru.clevertec.service.utils.Init.ITEM_NAME_REGEX;
import static ru.clevertec.service.utils.Init.ITEM_PRICE_REGEX;

public class Util {

    protected static final StringBuilder WRONG_DATA = new StringBuilder();

    private Util() {
    }

    public static String getPath() {
        return Thread.currentThread()
                .getContextClassLoader()
                .getResource("config.properties")
                .getPath()
                .replace("config.properties", "");
    }

    public static int calculatePercents(int x, int percent) {
        return (int) (x * percent / 100.0);
    }

    public static String line(String prefix, String str, int n, String suffix) {
        return prefix + str.repeat(n) + suffix;
    }

    public static String line(String str, int n, String suffix) {
        return str.repeat(n) + suffix;
    }

    private static String getMoneyString(int money) {
        return String.format("%s%.2f", Init.getCurr(), money / 100.0);
    }

    public static String formatMoney(int money, String len) {
        String str = getMoneyString(money);
        return line("", " ", len.length() - str.length(), str);
    }

    public static String formatMoney(int money) {
        String str = getMoneyString(money);
        return line("", " ", str.length(), str);
    }

    public static String middle(String str, int n) {
        String tab = " ".repeat((n - str.length()) / 2);
        return tab + str + tab + "\n";
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

    public static String verifyData(String data, String regex, String message) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        if (!matcher.matches()) {
            WRONG_DATA.append(message).append(data).append("\n");
            data = "#";
        }
        return data;
    }

    public static String verifyXML(String tagName, String data) {
        return switch (tagName) {
            case "name" -> verifyData(data, ITEM_NAME_REGEX, "Incorrect name in XML file: ");
            case "price" -> verifyData(data, ITEM_PRICE_REGEX, "Incorrect price in XML file: ");
            case "isOnSale" -> verifyData(data, BOOLEAN_REGEX, "Incorrect sale flag in XML file: ");
            default -> data;
        };
    }

    public static String getWrongData() {
        return String.valueOf(WRONG_DATA);
    }
}