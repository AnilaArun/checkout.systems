package com.supermarket.checkout.systems.service;

import com.supermarket.checkout.systems.model.ItemOnOffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

class ItemsOnOfferServiceTest {

    private ItemsOnOfferService itemsOnOfferService;

    @BeforeEach
    public void setUp() {
        itemsOnOfferService = new ItemsOnOfferService();
    }

    @Test
    void shouldAddItemOnOffer() {
        ItemOnOffer itemOnOffer = new ItemOnOffer("A", 3, 130);
        ItemOnOffer itemOnOfferAfter = itemsOnOfferService.addItemOnOffer(itemOnOffer);
        assertThat(itemOnOfferAfter.getItemName()).isEqualTo(itemOnOffer.getItemName());
    }
    @Test
    void shouldUpdateQuantityItemOnOffer() {
        ItemOnOffer itemOnOffer = new ItemOnOffer("A", 3, 130);
        ItemOnOffer itemOnOfferAfterAdding = itemsOnOfferService.addItemOnOffer(itemOnOffer);
        assertThat(itemOnOfferAfterAdding.getQuantity()).isEqualTo(itemOnOffer.getQuantity());
        ItemOnOffer itemOnOfferNew = new ItemOnOffer("A", 6, 130);
        ItemOnOffer itemOnOfferAfter = itemsOnOfferService.updateItemOnOffer(itemOnOfferNew);
        List<ItemOnOffer> itemsOnOffer= itemsOnOfferService.getAllItemsOnOffers();
        assertThat(itemsOnOffer.size()).isEqualTo(1);
        assertThat(itemOnOfferAfter.getQuantity()).isEqualTo(6);
    }

    @Test
    void getAllItemsOnOffers() {
        ItemOnOffer itemOnOffer = new ItemOnOffer("A", 3, 130);
        ItemOnOffer addedItemOnOffer = itemsOnOfferService.addItemOnOffer(itemOnOffer);
        ItemOnOffer anotherItemOnOffer = new ItemOnOffer("B", 2, 45);
        ItemOnOffer addedAnotherItemOnOffer = itemsOnOfferService.addItemOnOffer(itemOnOffer);
        assertThat(addedItemOnOffer.getItemName()).isEqualTo(itemOnOffer.getItemName());
        List<ItemOnOffer> itemsOnOffer= itemsOnOfferService.getAllItemsOnOffers();
        assertThat(itemsOnOffer.size()).isEqualTo(2);
    }

    @Test
    void removeItemOnOffer() {
        ItemOnOffer itemOnOffer = new ItemOnOffer("A", 3, 130);
        ItemOnOffer addedItemOnOffer = itemsOnOfferService.addItemOnOffer(itemOnOffer);
        assertThat(addedItemOnOffer.getItemName()).isEqualTo(itemOnOffer.getItemName());
        boolean isDeleted = itemsOnOfferService.removeItemOnOffer(itemOnOffer);
        assertTrue(isDeleted);

    }
}