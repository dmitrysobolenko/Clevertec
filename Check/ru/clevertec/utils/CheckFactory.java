package ru.clevertec.utils;

import ru.clevertec.beans.Check;
import ru.clevertec.beans.DiscountCard;
import ru.clevertec.beans.Item;
import ru.clevertec.exceptions.InputDataException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFactory {

    private CheckFactory() {
    }

    public static Check getInstance(String[] checkStrArray) throws InputDataException {

        DiscountCard discountCard;
        Map<Integer, Item> items;
        String[] itemStrArray;
        int customerId;
        List<Integer> wrongIds = new ArrayList<>();
        int itemId;
        int quantity;
        int length = checkStrArray.length;

        if (length == 0) {
            throw new InputDataException("No arguments found");
        }

        customerId = getCustomerId(checkStrArray);
        discountCard = DiscountCardFactory.getInstance(Util.getCardMap().get(customerId));

        if (customerId != -1) {
            length--;
        }

        items = new LinkedHashMap<>();

        for (int i = 0; i < length; i++) {

            Pattern pattern = Pattern.compile("\\d+-\\d+(?<!0)");
            Matcher matcher = pattern.matcher(checkStrArray[i]);
            if (!matcher.matches()) {
                throw new InputDataException("Wrong argument: " + checkStrArray[i]);
            }

            String[] itemData = checkStrArray[i].split("-");

            try {
                itemId = Integer.parseInt(itemData[0]);
                quantity = Integer.parseInt(itemData[1]);
            } catch (NumberFormatException e) {
                throw new InputDataException("Wrong argument: " + checkStrArray[i]);
            }
            itemStrArray = Util.getItemMap().get(itemId);

            if (itemStrArray == null) {
                wrongIds.add(itemId);
                continue;
            }

            if (items.containsKey(itemId)) {
                items.put(itemId, ItemFactory.getInstance(itemStrArray, items.get(itemId).getQuantity() + quantity, discountCard));
            } else {
                items.put(itemId, ItemFactory.getInstance(itemStrArray, quantity, discountCard));
            }
        }
        if (!wrongIds.isEmpty()) {
            for (Integer wrongId : wrongIds) {
                System.err.println("Item not found: " + wrongId);
            }
        }
        return new Check(items, discountCard);
    }

    private static int getCustomerId(String[] checkStrArray) {

        String cardString = checkStrArray[checkStrArray.length - 1];
        int customerId = -1;

        if (cardString.startsWith("card-")) {
            try {
                customerId = Integer.parseInt(cardString.replace("card-", ""));
            } catch (NumberFormatException e) {
                customerId = -2;
                System.err.println("Wrong discount card format: " + cardString);
            }
        }
        return customerId;
    }
}
