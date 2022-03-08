package ru.clevertec.model.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class SAXHandler extends DefaultHandler {

    private Map<Integer, String[]> dataMap;
    private String[] data;
    private String tagName;
    private int id;
    private final String[] TAGS;

    public SAXHandler(String[] tags) {
        super();
        this.TAGS = tags;
        data = new String[TAGS.length - 2];
    }

    public Map<Integer, String[]> getDataMap() {
        return dataMap;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals(TAGS[0])) {
            dataMap = new HashMap<>();
        } else if (qName.equals(TAGS[1])) {
            id = Integer.parseInt(attributes.getValue("id"));
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
            data[i] = value;
            tagName = null;
        }
    }
}
