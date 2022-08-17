package com.supermarket.checkout.systems.controller;

import com.supermarket.checkout.systems.model.Item;
import com.supermarket.checkout.systems.model.ItemOnOffer;
import com.supermarket.checkout.systems.service.CheckoutService;
import com.supermarket.checkout.systems.service.ItemsOnOfferService;
import com.supermarket.checkout.systems.service.PriceCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Slf4j
public class SuperMarketCheckoutController {
    private final CheckoutService checkoutService;
    private final ItemsOnOfferService itemsOnOfferService;
    private final PriceCalculationService priceCalculationService;
    public SuperMarketCheckoutController(CheckoutService checkoutService,
                                         ItemsOnOfferService itemsOnOfferService,
                                         PriceCalculationService priceCalculationService) {
        this.checkoutService = checkoutService;
        this.itemsOnOfferService = itemsOnOfferService;
        this.priceCalculationService = priceCalculationService;
    }

    @GetMapping("/getAllOffersOnItems")
    public List<ItemOnOffer> getAllOffersOnItems() {
        return itemsOnOfferService.getAllItemsOnOffers();
    }

    @PostMapping("/addAnItemOnOffer/{name}/{quantity}/{price}")
    public ItemOnOffer addItemOnOffer(@PathVariable("name") String itemName,
                                         @PathVariable("quantity") int quantity,
                                         @PathVariable("price") double price) {
        return itemsOnOfferService.addItemOnOffer(new ItemOnOffer(itemName, quantity,price));
    }

    @PostMapping("/addItemsOnOffer")
    public List<ItemOnOffer> addItemsOnOffers(@RequestBody List<ItemOnOffer> itemsOnOffer) {
        return itemsOnOfferService.addItemsOnOffer(itemsOnOffer);
    }
    @PostMapping("/updateAnItemOnOffer/{name}/{quantity}/{price}")
    public ItemOnOffer updateItemOnOffer(@PathVariable("name") String itemName,
                                                 @PathVariable("quantity") int quantity,
                                                 @PathVariable("price") double price) {
        return itemsOnOfferService.updateItemOnOffer(new ItemOnOffer(itemName, quantity,price));
    }
    @PostMapping("/removeAnItemOnOffer/{name}/{quantity}/{price}")
    public boolean removeItemOnOffer(@PathVariable("name") String itemName,
                                         @PathVariable("quantity") int quantity,
                                         @PathVariable("price") double price) {
        return itemsOnOfferService.removeItemOnOffer(new ItemOnOffer(itemName, quantity,price));
    }

    @GetMapping("/cartItems")
    public List<Item> getAllItemsOnCart() {
        return checkoutService.getAllItemsOnCart();
    }
    @PostMapping("/removeItems")
    public List<Item> removeItemsFromCart(@RequestBody List<Item> items) {
        return checkoutService.removeItemsFromCart(items);
    }
    @PostMapping("/addOrUpdateItem")
    public List<Item> addOrUpdateItemsToCart(@RequestBody List<Item> items) {
        return checkoutService.addOrUpdateItemToCart(items);
    }
    @PostMapping("/totalPrice")
    public double getTotalPrice() {
        return priceCalculationService.calculateTotalPrice();
    }
}
