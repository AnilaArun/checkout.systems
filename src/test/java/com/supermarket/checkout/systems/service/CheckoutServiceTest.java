package com.supermarket.checkout.systems.service;

import com.supermarket.checkout.systems.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CheckoutServiceTest {
    private CheckoutService checkoutService;
    List<Item> items = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        checkoutService = new CheckoutService();
        Item item = new Item("A", 1, 150);
        Item itemB = new Item("B", 1, 45);
        items.add(item);
        items.add(itemB);
    }

    @Test
    void shouldAddItemsToCart() {
        List<Item> returnItemList = checkoutService.addOrUpdateItemToCart(items);
        assertThat(returnItemList.size()).isEqualTo(2);
    }

    @Test
    void shouldUpdateItemQuantityIfItemAlreadyExists() {
        Item item = new Item("A", 2, 150);
        items.add(item);
        Item itemB = new Item("B", 3, 45);
        items.add(itemB);
        List<Item> returnItemList = checkoutService.addOrUpdateItemToCart(items);
        assertThat(returnItemList.get(0).getItemName()).isEqualTo("A");
        assertThat(returnItemList.get(1).getItemName()).isEqualTo("B");
        assertThat(returnItemList.get(0).getItemQuantity()).isEqualTo(3);
        assertThat(returnItemList.get(1).getItemQuantity()).isEqualTo(4);
    }

    @Test
    void shouldKeepTheItemsInSortedOrderInTheCart() {
        List<Item> newItem = new ArrayList<>();
        Item itemB = new Item("B", 3, 45);
        newItem.add(itemB);
        Item item = new Item("A", 2, 150);
        newItem.add(item);

        Item itemC = new Item("C", 3, 20);
        newItem.add(itemC);
        Item itemA = new Item("A", 2, 150);
        newItem.add(itemA);
        List<Item> returnItemList = checkoutService.addOrUpdateItemToCart(newItem);
        assertThat(returnItemList.get(0).getItemName()).isEqualTo("A");
        assertThat(returnItemList.get(1).getItemName()).isEqualTo("B");
        assertThat(returnItemList.get(2).getItemName()).isEqualTo("C");
        assertThat(returnItemList.get(0).getItemQuantity()).isEqualTo(4);
        assertThat(returnItemList.get(1).getItemQuantity()).isEqualTo(3);
        assertThat(returnItemList.get(2).getItemQuantity()).isEqualTo(3);
    }

    @Test
    void shouldGetAllItemsOnCart() {
        checkoutService.addOrUpdateItemToCart(items);
        List<Item> allItems = checkoutService.getAllItemsOnCart();
        assertThat(allItems.get(0).getItemName()).isEqualTo("A");
        assertThat(allItems.get(1).getItemName()).isEqualTo("B");
    }

    @Test
    void removeItemsFromCart() {
        checkoutService.addOrUpdateItemToCart(items);
        List<Item> removeItems = new ArrayList<>();
        Item item = new Item("A", 1, 150);

        removeItems.add(item);
        List<Item> itemsAfterRemove = checkoutService.removeItemsFromCart(removeItems);

        assertThat(itemsAfterRemove.get(0).getItemName()).isEqualTo("B");
    }
}