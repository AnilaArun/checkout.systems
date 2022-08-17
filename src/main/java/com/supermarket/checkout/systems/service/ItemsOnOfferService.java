package com.supermarket.checkout.systems.service;

import com.supermarket.checkout.systems.model.ItemOnOffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
@Slf4j
public class ItemsOnOfferService {
    private List<ItemOnOffer> itemsOnOffers = new ArrayList<>();
    private SortedMap<String, ItemOnOffer> itemsOnOfferMap = new TreeMap();

    public List<ItemOnOffer> add(ItemOnOffer itemOnOffer) {
        if(itemsOnOfferMap.isEmpty()) {
            itemsOnOfferMap.put(itemOnOffer.getItemName(), itemOnOffer);
        }
        return getSortedItemsList();
    }
    public List<ItemOnOffer> getAllItemsOnOffers() {
        try {
            if (!itemsOnOffers.isEmpty()) {
                return itemsOnOffers;
            } else {
                log.info("No offers exists.");
            }
        }  catch (Exception e) {
            log.info("An error occurred while retrieving data . Please try again. " + e.getMessage());
        }
        return itemsOnOffers;
    }
    public ItemOnOffer updateItemOnOffer(ItemOnOffer itemOnOffer) {
        try {
            log.info("Updating Item on offer , {} ", itemOnOffer);
            if (itemsOnOfferMap.isEmpty()) {
                itemsOnOfferMap.put(itemOnOffer.getItemName(), itemOnOffer);
            } else {
                if(itemsOnOfferMap.containsKey(itemOnOffer.getItemName())) {
                    itemsOnOfferMap.get(itemOnOffer.getItemName()).setQuantity(
                            itemsOnOfferMap.get(itemOnOffer.getItemName()).getQuantity() + itemOnOffer.getQuantity());
                } else {
                    itemsOnOfferMap.put(itemOnOffer.getItemName(), itemOnOffer);
                }
            }

        }  catch (Exception e) {
            log.info("An error occurred while adding itemsOnOffer . Please try again. " + itemOnOffer);
        }
        return itemOnOffer;
    }
    public ItemOnOffer addItemOnOffer(ItemOnOffer itemOnOffer) {
        try {
            log.info("Adding an item on offer , {} ", itemOnOffer);
            itemsOnOffers.add(itemOnOffer);
        }  catch (Exception e) {
            log.info("An error occurred while adding itemsOnOffer . Please try again. " + itemOnOffer);
        }
        return itemOnOffer;
    }

    public boolean removeItemOnOffer(ItemOnOffer itemOnOffer) {
        boolean isRemoved = false;
        log.info("Called remove Item On Offer for {} ", itemOnOffer);
        try {
            if (itemsOnOffers.contains(itemOnOffer)) {
                isRemoved = itemsOnOffers.remove(itemOnOffer);;
            } else {
                log.info("Could not find the item {} ", itemOnOffer);
            }
        }  catch (Exception e) {
            log.info("An error occurred while adding itemsOnOffer . Please try again. " + itemOnOffer);
        }
        return isRemoved;
    }

    private List<ItemOnOffer> getSortedItemsList() {
        if (!this.itemsOnOffers.isEmpty()) {
            this.itemsOnOffers.removeAll(this.itemsOnOffers);
        }
        for (var entry : itemsOnOfferMap.entrySet()) {
            this.itemsOnOffers.add(entry.getValue());
        }
        log.info("Items On Offers List : {} ", this.itemsOnOffers);
        return this.itemsOnOffers;
    }

    public SortedMap<String, ItemOnOffer> getItemsOnOfferMap() {
        return this.itemsOnOfferMap;
    }
}
