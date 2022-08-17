package com.supermarket.checkout.systems.service;

import com.supermarket.checkout.systems.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckoutService {
    private final List<Item> items = new ArrayList<>();
    private final SortedMap<String, Item> itemsMap = new TreeMap<>();
    public List<Item> getAllItemsOnCart() {
        try {
            if (!items.isEmpty()) {
                return getSortedItemsList();
            } else {
                log.info("Checkout cart is empty.");
            }
        }  catch (Exception e) {
            log.info("An error occurred while retrieving all checkout data . Please try again. " + e.getMessage());
        }
        return items;
    }

    public List<Item> removeItemsFromCart(List<Item> itemsToRemove) {
        try {
            if (!itemsMap.isEmpty()) {
                for (Item value : itemsToRemove) {
                    log.info("Items to remove . {} ", value);
                    if (itemsMap.containsKey(value.getItemName())) {
                        if (value.getItemQuantity() == itemsMap.get(value.getItemName())
                                .getItemQuantity()) {
                            itemsMap.remove(value.getItemName());
                            items.remove(value);
                        } else {
                            itemsMap.get(value.getItemName())
                                    .setItemQuantity(itemsMap.get(value.getItemName()).getItemQuantity() -
                                            value.getItemQuantity());
                        }
                    }
                }
            } else {
                log.info("Checkout cart is empty.");
            }
        }  catch (Exception e) {
            log.info("An error occurred while retrieving all checkout data . Please try again. " + e.getMessage());
        }
        return getSortedItemsList();
    }
    public List<Item> addOrUpdateItemToCart(List<Item> itemsToAdd) {
        try {
            for (Item item : itemsToAdd) {
                log.info("Items to add . {} ", item);
                addOrUpdateItemsToList(item);
            }
        }  catch (Exception e) {
            log.info("An error occurred while adding or updating cart . Please try again. " + e.getMessage());
        }
        return getSortedItemsList();
    }
    private List<Item> getSortedItemsList() {
        removeAllItemsFromListIfNotEmpty();
        for (var entry : itemsMap.entrySet()) {
            this.items.add(entry.getValue());
        }
        return this.items;
    }
    private void removeAllItemsFromListIfNotEmpty() {
        if (!this.items.isEmpty()) {
            this.items.removeAll(this.items);
        }
    }

    private void addOrUpdateItemsToList(Item item) {
        log.info("Inside addOrUpdateItemsToList with item , {} ", item);
        if(itemsMap.isEmpty()) {
            itemsMap.put(item.getItemName(), item);
        } else {
            if(itemsMap.containsKey(item.getItemName())) {
                itemsMap.get(item.getItemName()).setItemQuantity(
                            itemsMap.get(item.getItemName()).getItemQuantity() + item.getItemQuantity());
            } else {
                itemsMap.put(item.getItemName(), item);
            }
        }
   }

   public SortedMap<String, Item> getItemsMap() {
        return this.itemsMap;
   }
}
