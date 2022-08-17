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
    private List<Item> items = new ArrayList<>();
    private SortedMap<String, Item> itemsMap = new TreeMap<>();
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
                for (int i = 0; i < itemsToRemove.size(); i++) {
                    log.info("Items to remove . {} ", itemsToRemove.get(i));
                    if (itemsMap.containsKey(itemsToRemove.get(i).getItemName())) {
                        if (itemsToRemove.get(i).getItemQuantity() == itemsMap.get(itemsToRemove.get(i).getItemName())
                                .getItemQuantity()) {
                            itemsMap.remove(itemsToRemove.get(i).getItemName());
                            items.remove(itemsToRemove.get(i));
                        } else {
                            Item item = itemsToRemove.get(i);
                            itemsMap.get(item.getItemName())
                                    .setItemQuantity(itemsMap.get(item.getItemName()).getItemQuantity() -
                                            item.getItemQuantity());
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
            for (int i=0; i< itemsToAdd.size(); i++) {
                log.info("Items to add . {} ", itemsToAdd.get(i));
                addOrUpdateItemsToList(itemsToAdd.get(i));
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
