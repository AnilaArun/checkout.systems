package com.supermarket.checkout.systems.service;

import com.supermarket.checkout.systems.model.Item;
import com.supermarket.checkout.systems.model.ItemOnOffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceCalculationServiceTest {
    @Mock
    private CheckoutService checkoutService;
    @Mock
    private ItemsOnOfferService itemsOnOfferService;
    private PriceCalculationService priceCalculationService;

    private SortedMap<String, Item> itemsMap = new TreeMap<>();
    private SortedMap<String, ItemOnOffer> itemsOnOfferMap = new TreeMap();


    @BeforeEach
    public void setUp() {
        priceCalculationService = new PriceCalculationService(checkoutService, itemsOnOfferService);
    }

    @Test
    void shouldGetTotalPriceIfOfferApplied() {
        Item item = new Item("A", 4, 50);
        Item itemB = new Item("B", 2, 30);
        itemsMap.put("A", item);
        itemsMap.put("B", itemB);

        ItemOnOffer itemOnOffer = new ItemOnOffer("A", 3, 130);
        ItemOnOffer itemOnOfferB = new ItemOnOffer("B", 2, 45);

        itemsOnOfferMap.put("A", itemOnOffer);
        itemsOnOfferMap.put("B", itemOnOfferB);
        when(checkoutService.getItemsMap()).thenReturn(itemsMap);
        when(itemsOnOfferService.getItemsOnOfferMap()).thenReturn(itemsOnOfferMap);

        double total = priceCalculationService.calculateTotalPrice();
        assertThat(total).isEqualTo(225.0);
    }

    @Test
    void shouldGetTotalPriceIfNoOfferApplied() {
        Item item = new Item("A", 4, 50);
        Item itemB = new Item("B", 2, 30);
        itemsMap.put("A", item);
        itemsMap.put("B", itemB);
        when(checkoutService.getItemsMap()).thenReturn(itemsMap);
        when(itemsOnOfferService.getItemsOnOfferMap()).thenReturn(itemsOnOfferMap);

        double total = priceCalculationService.calculateTotalPrice();
        assertThat(total).isEqualTo(260.0);
    }
}