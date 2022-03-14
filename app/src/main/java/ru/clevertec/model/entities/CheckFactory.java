package ru.clevertec.model.entities;

import ru.clevertec.model.exceptions.InputDataException;

import java.util.LinkedHashMap;
import java.util.Map;

import static ru.clevertec.service.utils.Init.ARGS_REGEX;
import static ru.clevertec.service.utils.Init.ITEM_ID_REGEX;
import static ru.clevertec.service.utils.Init.ITEM_QUANTITY_REGEX;
import static ru.clevertec.service.utils.Init.getCardsMap;
import static ru.clevertec.service.utils.Init.getItemsMap;
import static ru.clevertec.service.utils.Util.verifyData;

public class CheckFactory {

    private static int itemId = 0;
    private static int quantity = 0;
    private static boolean miss = false;
    private static int length;

    private CheckFactory() {
    }

    public static Check getInstance(String[] checkStrArray) throws InputDataException {

        DiscountCard discountCard;
        Map<Integer, Item> items;
        String[] itemStrArray;
        length = checkStrArray.length;

        if (length == 0) {
            throw new InputDataException("No arguments found");
        }

        discountCard = getDiscountCard(checkStrArray);
        items = new LinkedHashMap<>();

        for (int i = 0; i < length; i++) {

            parseArg(checkStrArray[i]);
            itemStrArray = getItemsMap().get(itemId);

            for (String s : itemStrArray) {
                if (s == null) {
                    itemStrArray = null;
                    break;
                }
            }

            if (itemStrArray == null || miss) {
                continue;
            }

            if (items.containsKey(itemId)) {
                items.put(itemId, ItemFactory.getInstance(itemStrArray, items.get(itemId).getQuantity() + quantity, discountCard));
            } else {
                items.put(itemId, ItemFactory.getInstance(itemStrArray, quantity, discountCard));
            }
        }
        return new Check(items, discountCard);
    }

    private static DiscountCard getDiscountCard(String[] checkStrArray) {

        String cardString = checkStrArray[checkStrArray.length - 1];
        int customerId = -1;

        if (cardString.startsWith("card=")) {
            try {
                customerId = Integer.parseInt(cardString.replace("card=", ""));
            } catch (NumberFormatException e) {
                customerId = -2;
                System.err.println("Wrong discount card format: " + cardString);
            }
        }

        if (customerId != -1) {
            length--;
        }
        return DiscountCardFactory.getInstance(getCardsMap().get(customerId));
    }

    public static void parseArg(String arg) {
        String data = verifyData(arg, ARGS_REGEX, "Incorrect arg: ");
        if (!data.equals("#")) {
            String[] itemData = data.split("=");
            String id = verifyData(itemData[0], ITEM_ID_REGEX, "Incorrect id: ");
            String count = verifyData(itemData[1], ITEM_QUANTITY_REGEX, "Incorrect quantity: ");
            if (!id.equals("#") && !count.equals("#")) {
                itemId = Integer.parseInt(itemData[0]);
                quantity = Integer.parseInt(itemData[1]);
            } else miss = true;
        } else {
            miss = true;
        }
    }
}
