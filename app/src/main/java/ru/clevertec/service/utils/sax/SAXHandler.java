package ru.clevertec.service.utils.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.service.utils.Util.verifyData;
import static ru.clevertec.service.utils.Util.verifyXML;

public class SAXHandler extends DefaultHandler {

    private Map<Integer, String[]> dataMap;
    private static Map<Integer, String[]> map = new HashMap<>();
    private String[] data;
    private String tagName;
    private int id;
    private final String[] TAGS;
    private final String REGEX;

    private SAXHandler(String[] tags, String regex) {
        super();
        this.TAGS = tags;
        data = new String[TAGS.length - 2];
        this.REGEX = regex;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals(TAGS[0])) {
            dataMap = new HashMap<>();
        } else if (qName.equals(TAGS[1])) {
            String idStr = verifyData(attributes.getValue("id"), REGEX, "Incorrect id in XML file: ");
            if (!idStr.equals("#")) {
                id = Integer.parseInt(idStr);
            }
        } else {
            tagName = qName;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(TAGS[1])) {
                dataMap.put(id, data);
            data = new String[TAGS.length - 2];
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (!value.isEmpty()) {
            int i = -1;
            for (int j = 0; j < TAGS.length - 2; j++) {
                if (TAGS[j + 2].equals(tagName)) {
                    i = j;
                    break;
                }
            }
            if (i == -1) {
                throw new SAXException();
            }
            if (!verifyXML(tagName, value).equals("#")) {
                data[i] = value;
            }
            tagName = null;
        }
    }

    public static Map<Integer, String[]> getMap(String path, String[] tags, String regex) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            SAXParser parser = factory.newSAXParser();
            SAXHandler handler;

            handler = new SAXHandler(tags, regex);
            parser.parse(new FileInputStream(path), handler);
            map = handler.dataMap;

        } catch (SAXException | ParserConfigurationException | IOException e) {
            System.err.println("XML file error: " + e.getMessage());
            System.exit(1);
        }
        return map;
    }
}
