package com.supermarket.checkout.systems.service;

import com.supermarket.checkout.systems.model.Item;
import com.supermarket.checkout.systems.model.ItemOnOffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.SortedMap;
@Service
@Slf4j
public class PriceCalculationService {
    CheckoutService checkoutService;
    ItemsOnOfferService itemsOnOfferService;

    public PriceCalculationService(CheckoutService checkoutService, ItemsOnOfferService itemsOnOfferService) {
        this.checkoutService = checkoutService;
        this.itemsOnOfferService = itemsOnOfferService;
    }
    public double calculateTotalPrice() {
        double totalPrice = 0;
        SortedMap<String, Item> itemsMap = getItemsMap();
        SortedMap<String, ItemOnOffer> offerMap = getItemsOnOfferMap();
        if (!itemsMap.isEmpty()) {
            for (var entry : itemsMap.entrySet()) {
                Item item = entry.getValue();
                if (!offerMap.isEmpty()) {
                    if (offerMap.containsKey(entry.getKey())) {
                        totalPrice = calculateTotalPriceWithAnOffer(totalPrice, offerMap, entry, item);
                    } else {
                        totalPrice = calculateTotalPriceWithoutAnOffer(totalPrice, item);
                    }
                } else {
                    totalPrice = calculateTotalPriceWithoutAnOffer(totalPrice, item);
                }
            }
        } else {
            log.info("No Items in Cart");
        }
        return totalPrice;
    }

    private double calculateTotalPriceWithoutAnOffer(double totalPrice, Item item) {
        totalPrice += (item.getItemQuantity() * item.getItemPrice());
        return totalPrice;
    }

    private double calculateTotalPriceWithAnOffer(double totalPrice,
                                                  SortedMap<String,ItemOnOffer> offerMap,
                                                  Map.Entry<String,Item> entry,
                                                  Item item) {
        ItemOnOffer itemOnOffer = offerMap.get(entry.getKey());
        totalPrice += ((item.getItemQuantity() / itemOnOffer.getQuantity()) * itemOnOffer.getPrice())
                       + ((item.getItemQuantity() % itemOnOffer.getQuantity()) * item.getItemPrice());
        return totalPrice;
    }

    private SortedMap<String, ItemOnOffer> getItemsOnOfferMap() {
        return itemsOnOfferService.getItemsOnOfferMap();
    }

    private SortedMap<String, Item> getItemsMap() {
        return checkoutService.getItemsMap();
    }
}
