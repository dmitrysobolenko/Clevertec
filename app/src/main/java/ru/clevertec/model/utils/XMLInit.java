package ru.clevertec.model.utils;

import org.xml.sax.SAXException;
import ru.clevertec.model.sax.SAXHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XMLInit {

    private static final String[] ITEMS_TAGS = Init.getItemsTags();
    private static final String[] CARDS_TAGS = Init.getCardsTags();

    private static final String ITEMS_FILE_NAME = Init.getItemsFileName();
    private static final String CARDS_FILE_NAME = Init.getCardsFileName();

    private static Map<Integer, String[]> itemMap = new HashMap<>();
    private static Map<Integer, String[]> cardMap = new HashMap<>();

    public static Map<Integer, String[]> getItemMap() {
        return itemMap;
    }

    public static Map<Integer, String[]> getCardMap() {
        return cardMap;
    }

    public static void initialize(String path) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            SAXParser parser = factory.newSAXParser();
            SAXHandler handler;

            handler = new SAXHandler(ITEMS_TAGS);
            parser.parse(new FileInputStream(path + ITEMS_FILE_NAME), handler);
            itemMap = handler.getDataMap();

            handler = new SAXHandler(CARDS_TAGS);
            parser.parse(new FileInputStream(path + CARDS_FILE_NAME), handler);
            cardMap = handler.getDataMap();

        } catch (SAXException | ParserConfigurationException | IOException e) {
            System.err.println("XML file error: " + e.getMessage());
            System.exit(1);
        }
    }
}
