package com.designpatterns;

import com.designpatterns.facade.OrderFacade;
import com.designpatterns.model.Cart;
import com.designpatterns.model.Customer;
import com.designpatterns.model.Item;
import com.designpatterns.subsystem.InventoryService;

public class Main {
    public static void main(String[] args) {
        // Prepare test data
        Customer customer = new Customer("CUST-001", "Alizhan", "alizhan@example.com", "Mangilik El Ave 55, Astana");
        Cart cart = new Cart();
        cart.addItem(new Item("PROD-1", "Mechanical Keyboard", 75.0, 1));
        cart.addItem(new Item("PROD-2", "Wireless Mouse", 25.0, 2));

        // 1. Standard Client using ONLY the Facade
        System.out.println("--- Scenario 1: Standard Client using Facade ---");
        OrderFacade orderFacade = new OrderFacade();
        String orderId = orderFacade.placeOrder(cart, customer);

        // 2. Advanced Client bypassing Facade to interact directly with Subsystems
        System.out.println("--- Scenario 2: Advanced Client Bypassing Facade ---");
        InventoryService advancedInventory = new InventoryService();
        boolean directCheck = advancedInventory.reserveStock(cart);
        System.out.println("Direct subsystem check result: " + (directCheck ? "Success" : "Failed"));
    }
}