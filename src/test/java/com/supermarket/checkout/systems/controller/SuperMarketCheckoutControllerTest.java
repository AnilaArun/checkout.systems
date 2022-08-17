package com.supermarket.checkout.systems.controller;

import com.supermarket.checkout.systems.model.Item;
import com.supermarket.checkout.systems.model.ItemOnOffer;
import com.supermarket.checkout.systems.service.CheckoutService;
import com.supermarket.checkout.systems.service.ItemsOnOfferService;
import com.supermarket.checkout.systems.service.PriceCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuperMarketCheckoutControllerTest {
    @Mock
    private CheckoutService checkoutService;
    @Mock
    private ItemsOnOfferService itemsOnOfferService;
    private SuperMarketCheckoutController controller;
    private List<ItemOnOffer> itemsOnOffers;
    private SortedMap<String, Item> itemsMap;
    private SortedMap<String, ItemOnOffer> itemsOnOfferMap;

    @BeforeEach
    public void setUp() {
        itemsOnOffers = new ArrayList<>();
        itemsMap = new TreeMap<>();
        itemsOnOfferMap = new TreeMap<>();
        PriceCalculationService priceCalculationService = new PriceCalculationService(checkoutService, itemsOnOfferService);
        controller = new SuperMarketCheckoutController(checkoutService,
                                                       itemsOnOfferService,
                priceCalculationService);
    }

    @Test
    public void shouldGetAllOffers() {
        itemsOnOffers.add(new ItemOnOffer("A", 3, 150));
        itemsOnOffers.add(new ItemOnOffer("B", 2, 45));

        when(itemsOnOfferService.getAllItemsOnOffers()).thenReturn(itemsOnOffers);

        List<ItemOnOffer> itemsOnOffer = controller.getAllOffersOnItems();

        assertThat(itemsOnOffer).isEqualTo(itemsOnOffers);
    }

    @Test
    public void shouldAddAnItemsOnOffer() {
        ItemOnOffer itemOnOffer = new ItemOnOffer("B", 2, 45.0);
        when(itemsOnOfferService.addItemOnOffer(itemOnOffer)).thenReturn(itemOnOffer);
        ItemOnOffer itemOnOfferAdded = controller.addItemOnOffer("B", 2, 45.0);
        assertThat(itemOnOfferAdded).isEqualTo(itemOnOffer);
    }
    @Test
    public void shouldAddAListOfItemsOnOffer() {
        itemsOnOffers.add(new ItemOnOffer("A", 3, 150));
        itemsOnOffers.add(new ItemOnOffer("B", 2, 45));
        when(itemsOnOfferService.addItemsOnOffer(itemsOnOffers)).thenReturn(itemsOnOffers);
        List<ItemOnOffer> itemsOnOfferAfterAdding = controller.addItemsOnOffers(itemsOnOffers);

        assertThat(itemsOnOffers.size()).isEqualTo(2);
        assertThat(itemsOnOfferAfterAdding).isEqualTo(itemsOnOffers);
    }

    @Test
    public void shouldUpdateWithItemOnOffer() {
        itemsOnOffers.add(new ItemOnOffer("A", 3, 150));
        ItemOnOffer itemOnOfferBefore = new ItemOnOffer("B", 2, 45.0);
        when(itemsOnOfferService.updateItemOnOffer(itemOnOfferBefore)).thenReturn(itemOnOfferBefore);
        ItemOnOffer itemOnOffer = controller.updateItemOnOffer("B", 2, 45.0);
        itemsOnOffers.add(itemOnOffer);
        assertThat(itemOnOffer).isEqualTo(itemOnOfferBefore);
        when(itemsOnOfferService.getAllItemsOnOffers()).thenReturn(itemsOnOffers);
        List<ItemOnOffer> itemsOnOffer = controller.getAllOffersOnItems();

        assertThat(itemsOnOffers.size()).isEqualTo(2);
        assertThat(itemsOnOffer).isEqualTo(itemsOnOffers);
    }

    @Test
    public void shouldRemoveItemOnOffer() {
        ItemOnOffer itemOnOffer = new ItemOnOffer("A", 3, 150);
        itemsOnOffers.add(itemOnOffer);
        when(itemsOnOfferService.removeItemOnOffer(itemOnOffer)).thenReturn(true) ;
        boolean isRemoved = controller.removeItemOnOffer(itemsOnOffers.get(0).getItemName(),
                                         itemsOnOffers.get(0).getQuantity(), itemsOnOffers.get(0).getPrice());
        assertTrue(isRemoved);
    }

    @Test
    public void shouldRetrieveAllItemsFromCart() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("A", 1, 50));
        items.add(new Item("B", 1, 30));
        when(checkoutService.getAllItemsOnCart()).thenReturn(items);

        List<Item> itemsFromCart = controller.getAllItemsOnCart();
        assertThat(itemsFromCart).isEqualTo(items);
    }

    @Test
    public void shouldRemoveItemsFromCart() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("A", 1, 150));
        items.add(new Item("B", 1, 45));
        List<Item> itemsAfterRemoval = new ArrayList<>();

        when(checkoutService.removeItemsFromCart(any())).thenReturn(itemsAfterRemoval);

        List<Item> itemsFromCart = controller.removeItemsFromCart(items);
        assertThat(itemsFromCart.size()).isEqualTo(0);
    }

    @Test
    public void shouldUpdateCartWithAnItem() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("A", 2, 150));
        items.add(new Item("B", 1, 45));
        when(checkoutService.addOrUpdateItemToCart(any())).thenReturn(items);

        List<Item> itemsFromCart = controller.addOrUpdateItemsToCart(items);
        assertThat(itemsFromCart.get(0).getItemQuantity()).isEqualTo(2);
        assertThat(itemsFromCart.size()).isEqualTo(2);
    }

    @Test
    public void shouldCalculateTheTotalPriceOnCart() {
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

        double total = controller.getTotalPrice();
        assertThat(total).isEqualTo(225.0);
    }
}